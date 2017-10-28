package dzevako.betcore.drivers.x1bet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.google.common.collect.Sets;

import dzevako.betcore.bet.Bet;
import dzevako.betcore.bet.BetImpl;
import dzevako.betcore.bettypes.AbstractBetType;
import dzevako.betcore.bettypes.BetKey;
import dzevako.betcore.bettypes.BetKeys;
import dzevako.betcore.bettypes.BetType;
import dzevako.betcore.bettypes.BetTypeExtractor;
import dzevako.betcore.common.Constants;
import dzevako.betcore.common.Messages;
import dzevako.betcore.common.base.Pair;
import dzevako.betcore.common.base.Timer;
import dzevako.betcore.exception.AuthenticationException;
import dzevako.betcore.exception.game.BetFormIsNotOpenException;
import dzevako.betcore.exception.game.BetNotFoundException;
import dzevako.betcore.exception.game.ConfirmException;
import dzevako.betcore.game.Game;
import dzevako.betcore.game.GameCodes;
import dzevako.betcore.game.GameImpl;
import dzevako.betcore.game.GameStub;
import dzevako.betcore.logger.Logger;
import dzevako.betcore.logger.SystemOutLogger;
import dzevako.betcore.utils.GameUtils;
import dzevako.betcore.utils.StringUtils;
import dzevako.betcore.web.driver.AbstractBKWebDriver;

/**
 * Веб драйвер для 1x-bet
 * @author dzevako
 * @since Apr 1, 2016
 */
public class X1WebDriver extends AbstractBKWebDriver
{
    private final static String SUCCESS_BET = "Ваша ставка принята!";
    private final static String ERROR_MODAL_HEADER_TEXT = "Ошибка";

    private static final String WIN = "Победа в матче";
    private static final String FORA = "Фора";
    private static final String TOTAL_B = "Тотал Б";
    private static final String TOTAL_M = "Тотал М";

    public static void main(String[] args)
    {
        X1WebDriver d = new X1WebDriver(GameCodes.BASKETBALL, new SystemOutLogger(), null, null, null,
                Constants.CHROME_BROWSER);
        try
        {

            d.start();
            //d.logIn();
            d.selectGameType();
            //d.logOut();
            for (;;)
            {
                d.getGamesSafe();
                d.waitTime(5);
            }
        }
        finally
        {
            d.shutdown();
        }
        //System.out.println(d.getGameContext("Франкавилья U20 — Стелла Азура U20"));
    }

    // признак, указывающий на то, что игры отсутствуют
    private boolean isEmptyGames = false;

    public X1WebDriver(String game, Logger logger, String url, String login, String password, String browser)
    {
        super(game, logger, new X1Keys(), url, login, password, browser);
    }

    @Override
    public void clearBasket()
    {
        if (!isElementExists(X1Xpath.CLEAR_BET))
        {
            log("Basket allready cleared.");
            return;
        }
        click(X1Xpath.CLEAR_BET);
        int a = 5;
        while (a > 0)
        {
            if (!isElementExists(X1Xpath.CLEAR_BET))
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

    private void closeModalDialogIfAppear()
    {
        if (isElementExists(X1Xpath.MODAL_DIALOG))
        {
            log("Close modal form...");
            click(X1Xpath.MODAL_DIALOG_CLOSE);
        }
        waitTime(1);
        if (isElementExists(X1Xpath.MODAL_DIALOG))
        {
            log("Modal dialog is no closed...");
            click(X1Xpath.MODAL_DIALOG_CLOSE);
        }
        else
        {
            log("Modal form is closed.");
        }
    }

    /**
     * Закрыть модальную форму
     */
    private void closeModalForm()
    {
        if (isElementExists(X1Xpath.MODAL_FORM))
        {
            log("Close modal form...");
            click(X1Xpath.MODAL_FORM_CLOSE);
        }
        waitTime(2);
        if (isElementExists(X1Xpath.MODAL_FORM))
        {
            log("Modal form is no closed...");
            click(X1Xpath.MODAL_FORM_CLOSE);
        }
        else
        {
            log("Modal form is closed.");
        }
    }

    @Override
    public void confirm(Game game, Bet bet)
    {
        int value = bet.getValue();
        log("Send bet value... (value = " + value + ")");
        sendKeys(X1Xpath.BET_VALUE_INPUT, String.valueOf(bet.getValue()));
        log("Click confirm...");
        click(X1Xpath.CONFIRM_BET);
        log("Check confirmation...");
        for (int i = 0; i < 15; i++)
        {
            if (isElementExists(X1Xpath.MODAL_FORM) && SUCCESS_BET.equals(getText(X1Xpath.MODAL_FORM_MESSAGE)))
            {
                log("Bet " + bet.getTitle() + " was confirmed successfully.");
                log("Confirmation score: " + game.getScore().toString());
                closeModalForm();
                return;
            }
            log("Bet is not confirmed...");
            if (isElementExists(X1Xpath.MODAL_FORM)
                    && ERROR_MODAL_HEADER_TEXT.equals(getText(X1Xpath.MODAL_FORM_HEADER)))
            {
                closeModalForm();
                throw new ConfirmException(game, "Ставка не принята.");
            }
            log("Waiting confirmation...");
            waitTime(1);
        }
        throw new ConfirmException(game, "Не удалось оформить ставку по неизвестной причине.");

    }

    /**
     * Возвращает список доступных игр из Лайва (СКОПИПАЩЕНО ИЗ ЛИГИ СТАВОК!!!)
     */
    @Override
    protected Set<Game> findGames()
    {
        Set<Game> games = Sets.newHashSet();

        log("Look up for " + gameType + "...");

        if (isEmptyGames)
        {
            if (isElementExists(getTypeKeyInMenu()))
            {
                selectGameType();
            }
            else
            {
                log("No games for current time.");
                return games;
            }

        }

        List<String> gameTitles;
        try
        {
            gameTitles = findAll(keys.getGameTitlesKeys(gameTypeTitle)).stream()
                    .map(t -> t.getText().replace(X1Keys.WITH_OT, "")).collect(Collectors.toList());
            log(String.valueOf(gameTitles.size()) + " games found.");
        }
        catch (Exception e)
        {
            log("No games found: " + StringUtils.getMessage(e));
            return games;
        }

        log("Resolving games...");
        for (String title : gameTitles)
        {
            try
            {//TODO сделать нормальные икспасы
                WebElement scEl = find(String.format(X1Xpath.GAME_SCORE, title));
                String score = keys.getGameScore(scEl);
                WebElement timeEl = find(String.format(X1Xpath.GAME_TIME, title));
                int time = keys.getGameTime(timeEl);

                Game game = new GameImpl(gameType, title, score, time);
                games.add(game);
                log(game.toString());
            }
            catch (Exception e)
            {
                log("WARNING: Game '" + title + "' has incorrect data. It was striked from game-list:\n"
                        + StringUtils.getMessage(e));
            }
        }

        if (games.isEmpty())
        {
            log("No games found.");
        }

        return games;
    }

    @Override
    public Bet getBet(String gameTitle, BetKey key)
    {
        return getBetOnGame(new GameStub(gameTitle), Sets.newHashSet(key));
    }

    @Override
    public Bet getBetOnGame(Game game, BetKey key)
    {
        return getBetOnGame(game, Sets.newHashSet(key));
    }

    //TODO подумать, как убрать
    public Bet getBetOnGame(Game game, Set<BetKey> keys)
    {
        double rate = 0;
        String betTitle = null;
        WebElement betOnArena = null;
        BetType betType = null;
        BetKey key = null;

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

        log("Bet found on game field: " + key.getInfo());// + ", rate = " + betOnArena.getText());

        if (isBetMakingFormOpened())
        {
            log("Get bet data on form...");
            rate = Double.parseDouble(getText(X1Xpath.BET_RATE));
            log("rate = " + rate);
            betTitle = normalizeType(getText(X1Xpath.BET_EVENT_TITLE), getText(X1Xpath.BET_TYPE));
            log("betTitle = " + betTitle);
            betTitle = key.isForGame() ? betTitle + AbstractBetType.FOR_GAME : betTitle;
            log("betTitle = " + betTitle);
            betType = BetTypeExtractor.get(betTitle);
            //checkBet(game, betType);
            //TODO сделать проверку ставки
            betType.setRate(rate);
            log("betType = " + betType.toString());
            log("Bet found: " + betType.getString() + ", rate = " + rate);
            return new BetImpl(betType);
        }
        else
        {
            throw new BetFormIsNotOpenException(game);
        }
    }

    @Override
    public BetType getBetType(BetKey key)
    {
        if (null == key)
        {
            log("betKey == null");
            return null;
        }
        log("BetType rate key = " + key.getRateKey());
        Double rate = getRate(key);
        log("rate = " + rate);
        if (null == rate)
        {
            log("BetType '" + key.getInfo() + "' is not found on game field.");
            return null;
        }
        String data = key.getInfo();
        log("BetType value key = " + key.getValueKey());

        String value = getTextFast(key.getValueKey());
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

    @Override
    public String getGameContext(String title)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Game> getGamesSafe()
    {
        Set<Game> games = super.getGamesSafe();
        isEmptyGames = games.isEmpty();
        return games;
    }

    @Override
    public WebElement getMainLine(String gameTitle)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pair<Double, Double> getScore()
    {
        WebElement score = find(X1Xpath.SCORE);
        double sc = Double.parseDouble(score.getText().replace("RUB", "").replace(" ", ""));
        log("Score = " + sc);
        return new Pair<>(sc, 0.0);
    }

    @Override
    public WebElement getSetLine(String type)
    {
        // TODO Auto-generated method stub
        return null;
    }

    private String getTypeKeyInMenu()
    {
        return String.format(X1Xpath.GAMES_MENU_PATTERN, GameUtils.getGameTypeTitle(gameType));
    }

    @Override
    public void goToLiveBets()
    {
        // TODO Auto-generated method stub
    }

    /**
     * Признак того, что форма оформления ставки открыта
     */
    private boolean isBetMakingFormOpened()
    {
        log("Check bet making form...");
        if (null == find(X1Xpath.BET_FORM))
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

    @Override
    public void logIn()
    {
        if (isElementExists(X1Xpath.SCORE))
        {
            return;
        }
        log(Messages.LOGIN_OPERATION);
        click(X1Xpath.LOGIN_BTN);
        sendKeys(X1Xpath.LOGIN_INPUT, login);
        sendKeys(X1Xpath.PASSWORD_INPUT, password);
        WebElement el = click(X1Xpath.LOGIN_CONFIRM);
        if (null == el)
        {
            waitTime(2);
            log("Auth unsuccessfull. Try again...");
            sendKeys(X1Xpath.LOGIN_INPUT, login);
            sendKeys(X1Xpath.PASSWORD_INPUT, password);
            el = click(X1Xpath.LOGIN_CONFIRM);
        }
        if (null == el)
        {
            throw new AuthenticationException();
        }

        waitTime(2);

        closeModalDialogIfAppear();

        log(Messages.AUTH_SUCCESS);

        //setBetChangeIgnore();
    }

    @Override
    public void logOut()
    {
        log(Messages.LOGOUT_OPERATION);
        click(X1Xpath.LOGOUT_BTN);
        waitTime(2, false);
        click(X1Xpath.MODAL_FORM_CONFIRM);
        waitTime(5);
        if (!isElementExists(X1Xpath.SCORE))
        {
            log(Messages.LOGOUT_SUCCESS);
        }
    }

    @Override
    protected void moveToElement(WebElement element)
    {
        //Actions action = new Actions(driver());
        //action.moveToElement(element).perform();
        int top = element.getRect().getY();
        int left = element.getRect().getX();
        JavascriptExecutor jex = ((JavascriptExecutor)driver());
        jex.executeScript("var winHeight = document.documentElement.clientHeight; "
                + "var winWidth = document.documentElement.clientWidth; if (" + top + " + 100 > winHeight || " + left
                + " + 100 > winWidth) window.scrollTo(" + left + " - 100, " + top + " - 100 );");
        waitTime(1, false);
    }

    private String normalizeType(String title, String type)
    {
        if (type.contains(WIN))
        {
            return type.replace(WIN, "").replace(" ", "");
        }
        if (type.contains(FORA))
        {
            return type.replace(FORA, BetKeys.F).replace(" ", "");
        }
        if (type.contains(TOTAL_M))
        {
            return type.replace(TOTAL_M, BetKeys.TM).replace(" ", "");
        }
        if (type.contains(TOTAL_B))
        {
            return type.replace(TOTAL_B, BetKeys.TB).replace(" ", "");
        }
        log("It is not normalization for type " + type + ". Please, define it!");
        return null;
    }

    @Override
    public void selectGameType()
    {
        String typeTitle = GameUtils.getGameTypeTitle(gameType);
        log("Choose " + typeTitle + "...");
        try
        {
            click(getTypeKeyInMenu());
            waitTime(2, false);
        }
        catch (Exception e)
        {
            log("Unable to choose " + typeTitle + " in live menu...");
        }
    }

    @Override
    public void start()
    {
        super.start();

        Timer.waitSec(1);
        selectGameType();
    }

    /*private void setBetChangeIgnore()
    {
        log("Ignoring changing bets...");
        clickIfExists(X1Xpath.CHANGE_RATE_SETTING);
        clickIfExists(X1Xpath.ALLOW_ALL_CHANGES_RATE);
        clickIfExists(X1Xpath.CHANGE_RATE_SETTING);
    }*/
}
