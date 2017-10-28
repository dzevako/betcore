package dzevako.betcore.drivers.betcity;

import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import dzevako.betcore.bet.Bet;
import dzevako.betcore.bet.BetImpl;
import dzevako.betcore.bettypes.AbstractBetType;
import dzevako.betcore.bettypes.BetKey;
import dzevako.betcore.bettypes.BetType;
import dzevako.betcore.bettypes.BetTypeExtractor;
import dzevako.betcore.common.Constants;
import dzevako.betcore.common.Messages;
import dzevako.betcore.common.base.Pair;
import dzevako.betcore.common.base.Timer;
import dzevako.betcore.exception.game.BetFormIsNotOpenException;
import dzevako.betcore.exception.game.BetNotFoundException;
import dzevako.betcore.exception.game.ConfirmException;
import dzevako.betcore.exception.game.IncorrectBetSelectionException;
import dzevako.betcore.game.Game;
import dzevako.betcore.game.GameElement;
import dzevako.betcore.game.GameTitles;
import dzevako.betcore.game.GameType;
import dzevako.betcore.logger.Logger;
import dzevako.betcore.logger.SystemOutLogger;
import dzevako.betcore.utils.GameUtils;
import dzevako.betcore.web.driver.AbstractBKWebDriver;

/**
 * Веб драйвер для Бетсити
 * @author dzevako
 * @since Sep 27, 2014
 */
public class BetCityWebDriver extends AbstractBKWebDriver
{
    private enum Iframe
    {
        TOP(null, "btop"), CENTER(null, "center"), LEFT(CENTER, "left"), MIDDLE(CENTER, "middle");

        public String name;

        public Iframe parent;

        Iframe(Iframe parent, String name)
        {
            this.name = name;
            this.parent = parent;
        }
    }

    protected static final int BOOST_REFRESH_GAME_TIME = 15;

    protected static final int REFRESH_GAME_TIME = 60;

    public static void main(String[] args)
    {
        BetCityWebDriver r = new BetCityWebDriver(GameTitles.BASKETBALL, new SystemOutLogger(), null,
                Constants.CHROME_BROWSER);
        try
        {
            //r.start(res_url);
            //r.openResults();
            r.chooseGame();
            System.out.println(r.getResult("Детройт - Индиана."));
            System.out.println(r.getResult("Мемфис - Хьюстон"));
        }
        finally
        {
            r.shutdown();
        }
    }

    public final String res_url = startPage + "/results_new/";

    Iframe iframe = null;

    public BetCityWebDriver(String game, Logger logger, String url, String browser)
    {
        super(game, logger, new BetCityKeys(), url, browser);
    }

    public BetCityWebDriver(String game, Logger logger, String url, String login, String password, String browser)
    {
        super(game, logger, new BetCityKeys(), url, login, password, browser);
    }

    public void cancelBet(Game game)
    {
        log("Cancel...");
        click(BCXpath.X_CANCEL_BET);
        log("Bet was canceled.");
    }

    /**
     * Проверка соответствия ставки
     */
    protected void checkBet(Game game, BetType betType)
    {
        log("Check betType " + betType.getString() + " on form...");
        if (1 < findAll(BCXpath.X_CANCEL_BET).size())
        {
            throw new IncorrectBetSelectionException(game, "Several bets selected.");
        }

        String expected = game.getTitle();
        String actual = getText(BCXpath.X_GAME_TITLE);
        if (!expected.equals(actual))
        {
            throw new IncorrectBetSelectionException(game, expected, actual);
        }

        if (betType.isForGame())
        {
            return;
        }
        // добавить проверку для тотала в игре, разрешить небольшое изменение значения
        int set = GameUtils.getSet(game.getType(), game.getScore());
        expected = String.valueOf(set);
        actual = getText(BCXpath.X_BET_TITLE);
        if (!expected.equals(actual))
        {
            throw new IncorrectBetSelectionException(game, expected + " set", actual.substring(0, 1) + " set");
        }
    }

    /**
     * Выбрать вид спорта в результатах
     */
    public void chooseGame()
    {
        click(BCXpath.SPORT_FILTER);
        click(BCXpath.CLEAR_FILTER);
        click(String.format(BCXpath.CHOOSE_GAME, GameUtils.getGameTypeTitle(gameType)));
        click(BCXpath.SPORT_FILTER);
    }

    /**
     * Очистка корзины ставок
     */
    @Override
    public void clearBasket()
    {
        goToLeft();
        if (!isElementExists(BCXpath.X_CLEAR_BASKET))
        {
            log("Basket allready cleared.");
            return;
        }
        click(BCXpath.X_CLEAR_BASKET);
        int a = 10;
        while (a > 0)
        {
            if (!isElementExists(BCXpath.X_CLEAR_BASKET))
            {
                log("Basket was cleared.");
                return;
            }
            waitTime(1, false);
            a = a - 1;
        }
        refresh();
        logIn();
        log("ERROR: Error clearing basket!");
    }

    /**
     * Очистка фильтра с играми
     */
    public void clearGamesFilter()
    {
        WebElement checkBox = get(BCXpath.SPORTS_FILTER_CHECK_BOX);
        if (checkBox != null && checkBox.isSelected())
        {
            log("Spontaneously setting sports filter was detected!");
            click(BCXpath.SPORTS_FILTER);
            log("Clean games filter...");
            click(BCXpath.SPORTS_FILTER_CLEAN);
            click(BCXpath.SPORTS_FILTER);
        }
    }

    /**
     * Нажать кнопку "Обновить" в поле игры
     */
    private void clickGameRefresh()
    {
        click(BCXpath.X_GAME_REFRESH_BTN);
    }

    /**
     * Перейти на следующий день
     */
    public void clickNextDay()
    {
        click(BCXpath.NEXT_DAY_BTN);
    }

    /**
     * Перейти на предыдущий день
     */
    public void clickPrevDay()
    {
        click(BCXpath.PREV_DAY_BTN);
    }

    @Override
    public void confirm(Game game, Bet bet)
    {
        int value = bet.getValue();
        checkBet(game, bet.getType());//TODO эта проверка ничего не проверяет
        log("Send bet value... (value = " + value + ")");
        sendKeys(BCXpath.X_BET_VALUE_INPUT, String.valueOf(bet.getValue()));
        log("Click confirm...");
        click(BCXpath.X_CONFIRM);
        log("Click success.");
        String msg = getText(BCXpath.TABLE_ALERT);
        log("Table alert message: " + msg);
        if (isElementExists(BCXpath.X_CHECK_SET_BET))
        {
            log("Bet " + bet.getTitle() + " was confirmed successfully.");
            log("Confirmation score: " + game.getScore().toString());
            return;
        }

        if (isElementExists(BCXpath.X_CHANGE_RATE))
        {
            String rate = getText(BCXpath.X_BET_RATE);
            throw new ConfirmException(game, "Rate was changed to " + rate + ". Try confirm again...");
        }

        if (msg.contains("Проверьте соединение с Интернетом")
                || msg.contains("Сумма по ставкам превышает доступный баланс"))
        {
            throw new ConfirmException(game, msg);
        }
        throw new ConfirmException(game, "Не удалось оформить ставку по неизвестной причине");
    }

    //После всех попыток ждем еще 5 сек и проверяем, не поставилась ли ставка
    /**
     * Найти игру по названию
     */
    public Game findGame(String gameTitle)
    {
        if (null == gameTitle || gameTitle.isEmpty())
        {
            return null;
        }
        Set<Game> games = findGames();
        for (Game game : games)
        {
            if (gameTitle.equals(game.getTitle()))
            {
                return game;
            }
        }
        return null;
    }

    /**
     * Получение списка игр
     */
    @Override
    public Set<Game> findGames()
    {
        goToLiveBets();
        clearGamesFilter();
        if (isAnyGameOpened())
        {
            refreshGames();
        }

        return super.findGames();
    }

    public Bet getBet(Game game, BetKey key)
    {
        return getBet(game, Sets.newHashSet(key));
    }

    public Bet getBet(Game game, Set<BetKey> keys)
    {
        openGame(game);
        return getBetOnGame(game, keys);
    }

    @Override
    public Bet getBet(String gameTitle, BetKey key)
    {
        Game game = findGame(gameTitle);
        return getBet(game, key);
    }

    @Override
    public Bet getBetOnGame(Game game, BetKey key)
    {
        return getBetOnGame(game, Sets.newHashSet(key));
    }

    /**
     * Получение счета игры
     */
    /*public GameScore getGameScore()
    {
        String score = getText(Xpath.X_GAME_SCORE);
        return score.isEmpty() ? null : new VolleyballScore(score);
    }*/

    public Bet getBetOnGame(Game game, Set<BetKey> keys)
    {
        if (isBetStop())
        {
            log("Now all bets is stopped.");
            throw new BetNotFoundException(game);
        }

        double rate = 0;
        String betTitle = null;
        WebElement betOnArena = null;
        BetType betType = null;
        BetKey key = null;

        //если вдруг не в том фрейме
        goToMiddle();

        for (BetKey key0 : keys)
        {
            log("Get a bet '" + key0.getInfo() + "'...");
            betOnArena = clickIfExists(key0.getRateKey());
            if (null != betOnArena)
            {
                key = key0;
                break;
            }
            log("Bet not found.");
        }

        if (null == betOnArena)
        {
            throw new BetNotFoundException(game, keys);
        }

        log("Bet found on game field: " + key.getInfo() + ", rate = " + betOnArena.getText());

        if (isBetMakingFormOpened())
        {
            rate = Double.parseDouble(getText(BCXpath.X_BET_RATE));
            betTitle = getText(BCXpath.X_BET_TITLE);
            betTitle = key.isForGame() ? betTitle + AbstractBetType.FOR_GAME : betTitle;
            betType = BetTypeExtractor.get(betTitle);
            checkBet(game, betType);
            betType.setRate(rate);
            log("Bet found: " + betType.getString() + ", rate = " + rate);
            return new BetImpl(betType);
        }
        else
        {
            throw new BetFormIsNotOpenException(game);
        }
    }

    /**
     * Получение типа ставки с игрового поля по ключу
     */
    @Override
    public BetType getBetType(BetKey key)
    {
        if (null == key)
        {
            log("betKey == null");
            return null;
        }
        log("BetType rate key = '" + key.getRateKey());
        Double rate = getRate(key);
        log("rate = " + rate);
        if (null == rate)
        {
            log("BetType '" + key.getInfo() + "' is not found on game field.");
            return null;
        }
        String data = key.getInfo();
        log("BetType value key = '" + key.getValueKey());
        String value = getTextFast(key.getValueKey()).replace("+", "");
        log("value = " + value);
        if (!value.isEmpty())
        {
            data = data + "(" + value + ")";
        }
        data = data + (key.isForGame() ? AbstractBetType.FOR_GAME : "");
        BetType betType = BetTypeExtractor.get(data);
        betType.setRate(rate);
        log("BetType found: " + betType.toString());
        return betType;
    }

    /**
     * Получить контекст уже открытой игры:
     * "Баскетбол. Чемпионат Болгарии (до 14 лет). Юноши. Финальный этап. София."
     */
    public String getGameContext()
    {
        //not work
        return getText(BCXpath.GAME_CONTEXT_STRING);
    }

    /**
     * Получить контекст игры
     * "Баскетбол. Чемпионат Болгарии (до 14 лет). Юноши. Финальный этап. София."
     */
    @Override
    public String getGameContext(String title)
    {
        try
        {
            return getText(String.format(BCXpath.GAME_CONTEXT_STRING, title));
        }
        catch (Exception e)
        {
            return "";
        }
    }

    /**
     * Получить контекст игры с определенной датой
     */
    public String getGameContext(String title, String date)
    {
        setDate(date);
        String result = getGameContext(title);
        if (result.isEmpty())
        {
            log("There are not results today for game '" + title + "'");
            clickPrevDay();
        }
        result = getGameContext(title);
        if (result.isEmpty())
        {
            log("There are not results in prev day for game '" + title + "'");
            setDate(date);
            clickNextDay();
        }
        return getGameContext(title);
    }

    /**
     * Получить элемент главной линии ставок (без ожидания)
     */
    @Override
    public WebElement getMainLine(String gameTitle)
    {
        WebElement el = get(BCXpath.MAIN_LINE);
        if (null == el)
        {
            log("getMainLine() returns NULL !");
        }
        return el;
    }

    /**
     * Получить коэффициент заданного типа ставки, если указанного типа нет - возвращает null
     */
    //TODO вынести в абстракт
    @Override
    public Double getRate(BetKey key)
    {
        String strRate = getTextFast(key.getRateKey());// + "/a");
        return strRate.isEmpty() ? null : Double.parseDouble(strRate);
    }

    /**
     * Получение коэффициентов указанных типов ставок на открытую(текущую) игру
     */
    public Map<BetKey, Double> getRates(Set<BetKey> keys)
    {
        if (!isAnyGameOpened())
        {
            log("Game is not opened. Rates are not to be determined.");
            return null;
        }

        if (isBetStop())
        {
            log("Betting is paused... ");
            return null;
        }

        log("Get rates... ");

        Map<BetKey, Double> rates = Maps.newHashMap();
        for (BetKey key : keys)
        {
            if (null == key)
            {
                continue;
            }
            Double rate = getRate(key);
            if (null == rate)
            {
                continue;
            }
            log(key.getInfo() + ": rate = " + rate);
            rates.put(key, rate);
        }

        if (rates.isEmpty())
        {
            log("All required rates is udefined. Return empty rates map.");
        }

        return rates;
    }

    /**
     * Получение коэффициентов указанных типов ставок на игру c заданнаым названием
     */
    public Map<BetKey, Double> getRates(String gameTitle, Set<BetKey> betTypes)
    {
        Game game = findGame(gameTitle);
        openGame(game);

        return getRates(betTypes);
    }

    /**
     * Получить результат игры со страницы результатов
     */
    public String getResult(String title)
    {
        try
        {
            return getText(String.format(BCXpath.GAME_RESULT_SCORE, title));
        }
        catch (Exception e)
        {
            return "";
        }
    }

    /**
     * Получить тип ставки наименьший Тотал Больше для текущей игры
     */
    /*public BetType getMinTB()
    {
        if (isBetStop())
        {
            log("getMinTB() returns null because betting stopped.");
            return null;
        }
    
        for (BetType type : BetType.TB_LIST)
        {
            if (null == getRate(type))
            {
                continue;
            }
            return type;
        }
        log("getMinTB() returns null because no betTypes found from TB_LIST.");
        return null;
    }*/

    /**
     * Получить тип ставки наименьший Тотал Больше для указанной игры
     */
    /*public BetType getMinTB(String gameTitle)
    {
        Game game = findGame(gameTitle);
        openGame(game);
    
        return getMinTB();
    }*/

    /**
     * Получить результат игры между указанными командами, 
     * искать за указанную дату, а если нет, то посмотреть днем позже, днем раньше
     */
    public String getResult(String title, String date)
    {
        date = date.replace(".15", ".2015").replace(".16", ".2016");
        setDate(date);
        String result = getResult(title);
        if (result.isEmpty())
        {
            log("There are not results today for game '" + title + "'");
            clickPrevDay();
        }
        result = getResult(title);
        if (result.isEmpty())
        {
            log("There are not results in prev day for game '" + title + "'");
            setDate(date);
            clickNextDay();
        }
        return getResult(title);
    }

    @Override
    public Pair<Double, Double> getScore()
    {
        goToScoreHistory();
        double sc = Double.parseDouble(getText(BCXpath.SCORE));
        double cb = Double.parseDouble(getText(BCXpath.CUR_BETS));
        log("Score = " + sc + "; Current bets = " + cb);
        return new Pair<>(sc, cb);
    }

    /**
     * Получить линию результатов партии(четверти, сета)
     */
    @Override
    public WebElement getSetLine(String gameType)
    {
        String xpath = "";
        if (GameType.VOLLEYBALL.getTitle().equals(gameType))
        {
            xpath = BCXpath.VOLLEYBALL_SET_RESULT_LINE;
        }
        if (GameType.BASKETBALL.getTitle().equals(gameType))
        {
            xpath = BCXpath.BASKETBALL_SET_RESULT_LINE;
        }
        if (GameType.HOCKEY.getTitle().equals(gameType))
        {
            xpath = BCXpath.HOCKEY_SET_RESULT_LINE;
        }
        WebElement el = get(xpath);
        if (null == el)
        {
            log("getSetResultsLine() returns NULL !");
        }
        return el;
    }

    public void goToLeft()
    {
        if (Iframe.LEFT.equals(iframe))
        {
            return;
        }
        switchToFrame(Iframe.LEFT);
    }

    private void goToLive()
    {
        if (isOnLive())
        {
            return;
        }
        log("Go to live...");
        goToTop();
        click(BCXpath.X_LIVE);
        goToMiddle();
    }

    @Override
    public void goToLiveBets()
    {
        if (isLiveBetsActive())
        {
            return;
        }
        goToLive();
        log("Go to live bets...");
        refreshGames();
    }

    public void goToMiddle()
    {
        if (Iframe.MIDDLE.equals(iframe))
        {
            return;
        }
        switchToFrame(Iframe.MIDDLE);
    }

    private void goToScoreHistory()
    {
        log("Going to score history...");
        goToTop();
        click(BCXpath.X_SCORE_HISTORY);
        goToMiddle();
    }

    public void goToTop()
    {
        if (Iframe.TOP.equals(iframe))
        {
            return;
        }
        switchToFrame(Iframe.TOP);
    }

    /**
     * Признак того, что какая-то игра уже открыта
     */
    private boolean isAnyGameOpened()
    {
        return isElementExists(BCXpath.GAME_CONTAINER);
    }

    private boolean isAuthenicated()
    {
        goToLeft();
        try
        {
            return null != findElementHard(By.xpath(String.format(BCXpath.CLIENT_AUTH, login)));
        }
        catch (Exception e)
        {
            log(Messages.NO_AUTH);
            log(e.getMessage());
            return false;
        }
    }

    /**
     * Проверка того, что форма ставки открыта
     */
    protected boolean isBetMakingFormOpened()
    {
        goToLeft();
        log("Check bet making form...");
        if (null == find(BCXpath.X_CONFIRM))
        {
            log("Bet Making Form is not open!");
            return false;
        }
        else
        {
            log("Bet Making Form is open...");
            return true;
        }
    }

    /**
     * Проверка, принимаются ли ставки
     */
    private boolean isBetStop()
    {
        return isElementExists(BCXpath.X_BET_STOP);
    }

    /**
     * Признак того, что ставки предлагаются на указанный сет
     */
    public boolean isCanBetOnCurrentSet(Game game)
    {
        int set = GameUtils.getSet(game.getType(), game.getScore());
        boolean isCanSetBet = isElementExists(
                String.format(BCXpath.SET_PTRN, set, GameUtils.getSetName(game.getType())));
        if (!isCanSetBet)
        {
            log("Game " + game.getTitle() + " hasn't bets on " + set + " set.");
        }
        return isCanSetBet;
    }

    /**
     * Признак активности вкладки "live-ставки"
     */
    private boolean isLiveBetsActive()
    {
        return isElementExists(BCXpath.IS_LIVE_BETS);
    }

    /**
     * Признак нахождения на live
     */
    private boolean isOnLive()
    {
        //смотря,  в каком фрейме находимся
        return isElementExists(BCXpath.LIVE_MENU) || isElementExists(BCXpath.IS_ON_LIVE);
    }

    @Override
    public void logIn()
    {
        log(Messages.CHECK_AUTH);
        refresh();
        if (isAuthenicated())
        {
            log(Messages.AUTH_SUCCESS);
            return;
        }

        log(Messages.LOGIN_OPERATION);
        goToTop();

        tryAuth();

        boolean auth = isAuthenicated();
        if (!auth)
        {
            log(Messages.ATTEMPT_2);
            tryAuth();
            auth = isAuthenicated();
        }

        if (!auth)
        {
            throw new RuntimeException(Messages.NO_AUTH);
        }

        log(Messages.AUTH_SUCCESS);
    }

    @Override
    public void logOut()
    {
        log(Messages.LOGOUT_OPERATION);
        goToTop();
        click(BCXpath.X_LOGOUT_BTN);
        waitTime(2);
        goToLeft();
        find(BCXpath.X_NO_AUTH);
        log(Messages.LOGOUT_SUCCESS);
    }

    /**
     * Зайти в игру
     */
    public void openGame(Game game)
    {
        if (null == game)
        {
            throw new RuntimeException("Game cannot be null!");
        }
        try
        {
            log("Open game '" + game.getTitle() + "'...");
            ((GameElement)game).open();
            waitGameLoading();
            log("Game opened.");
        }
        catch (Exception e)
        {
            log("Not already selected game found!\n" + e.getMessage());
            refreshGames();
            throw new RuntimeException("Not already selected game found!");
        }
    }

    /**
     * Открыть игру с указанным названием
     */
    @Override
    public void openGame(String gameTitle)
    {
        Game game = findGame(gameTitle);
        if (null == game)
        {
            log("Game " + gameTitle + " not found!");
        }
        else
        {
            openGame(game);
        }
    }

    /**
     * Открыть вкладку с результатами матчей.
     */
    public void openResults()
    {
        WebElement el = find(BCXpath.GAMES_RESULTS);
        String classValue = el.getAttribute("class");
        if (null == classValue || classValue.isEmpty())
        {
            log("Go to games results...");
            el.click();
        }
        setToday();
    }

    @Override
    public void refresh()
    {
        super.refresh();
        switchToFrame(iframe);
    }

    /**
     * Обновить поле игры
     */
    @Override
    public void refreshGame()
    {
        log("Refresh game...");
        clickGameRefresh();
        waitGameLoading();
    }

    /**
     * Обновление live-ставок
     */
    @Override
    public void refreshGames()
    {
        log("Refresh games...");
        goToMiddle();
        goToLive();
        click(BCXpath.LIVE_BETS);
        clearGamesFilter();
    }

    @Override
    public void selectGameType()
    {
        // TODO Auto-generated method stub        
    }

    /**
     * Ввести текст(2 попытки)
     */
    public void sendKeys(String xpath, String keys)
    {
        try
        {
            super.sendKeys(xpath, keys);
        }
        catch (StaleElementReferenceException e)
        {
            log("SEND_KEYS ERROR: " + e.getMessage());
            waitTime(BOOST_REFRESH_GAME_TIME);
            log("Send keys attempt 2... ");
            super.sendKeys(xpath, keys);
            log("Sending success.");
        }
    }

    /**
     * На странице результатов выставить указанную дату
     */
    public void setDate(String date)
    {
        WebElement el = find(BCXpath.GAMES_RESULTS_DATE);
        String text = el.getAttribute("value");
        if (date.equals(text))
        {
            return;
        }
        el.clear();
        el.sendKeys(date);
        el.sendKeys(Keys.ENTER);
        click(BCXpath.GAMES_RESULTS_EMPTY_PLACE);
    }

    /**
     * Установка фильтра отображаемых игр в соответствии с gameTypes
     */
    public void setGamesFilter()
    {
        log("Set games filter...");
        click(BCXpath.SPORTS_FILTER);
        log("Clean games filter...");
        click(BCXpath.SPORTS_FILTER_CLEAN);

        String xpath = String.format(BCXpath.GAME_FILTER_PTRN, keys.getGameKey(gameType));
        if (!get(xpath).isSelected())
        {
            log("Set filter = " + keys.getGameKey(gameType));
            click(xpath);
        }

        click(BCXpath.SPORTS_FILTER);
    }

    /**
     * На странице результатов выставить сегодняшнюю дату
     */
    public void setToday()
    {
        setDate(Timer.date2());
    }

    private void switchToFrame(Iframe i)
    {
        int attempt = 1;
        boolean refresh = false;
        while (attempt <= 10)
        {
            try
            {
                if (null == i)
                {
                    driver().switchTo().defaultContent();
                }
                else
                {
                    if (null == i.parent)
                    {
                        driver().switchTo().defaultContent();
                    }
                    else
                    {
                        switchToFrame(i.parent);
                    }
                    driver().switchTo().frame(i.name);
                }
                iframe = i;
                return;
            }
            catch (NoSuchFrameException e)
            {
                if (attempt == 10 && refresh)
                {
                    throw e;
                }
                else
                {
                    if (attempt == 10)
                    {
                        refresh();
                        refresh = true;
                        attempt = 1;
                    }
                    else
                    {
                        attempt = +1;
                        Timer.waitMillis(1000);
                    }
                }
            }
        }
    }

    private void tryAuth()
    {
        log(Messages.TRY_AUTH);

        sendKeys(BCXpath.LOGIN_INPUT, login);
        sendKeys(BCXpath.PASSWORD_INPUT, login);
        sendKeys(BCXpath.PASSWORD_INPUT, Keys.ENTER);
    }

    /**
     * Ожидание загрузки игры
     */
    private void waitGameLoading()
    {
        WebElement element = find(BCXpath.X_GAME_SCORE);
        if (null == element)
        {
            log("Game wasn't loaded!");
            refreshGames();
            //throw new NoSuchElementException("Game wasn't loaded!");
        }
    }

    /**
     * Подождать, если ставки остановлены
     */
    protected void waitIfBetStopped()
    {
        while (isBetStop())
        {
            log("Now all bets is stopped. Wait...");
            waitTime(REFRESH_GAME_TIME);
            refreshGame();
        }
    }
}
