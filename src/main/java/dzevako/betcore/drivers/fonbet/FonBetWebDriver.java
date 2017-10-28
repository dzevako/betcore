package dzevako.betcore.drivers.fonbet;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import dzevako.betcore.game.GameStub;
import dzevako.betcore.logger.Logger;
import dzevako.betcore.logger.SystemOutLogger;
import dzevako.betcore.utils.GameUtils;
import dzevako.betcore.web.driver.AbstractBKWebDriver;

/**
 * Веб драйвер для Фонбет
 * @author dzevako
 * @since Sep 27, 2015
 */
public class FonBetWebDriver extends AbstractBKWebDriver
{
    public static void main(String[] args)
    {
        FonBetWebDriver d = new FonBetWebDriver(GameCodes.BASKETBALL, new SystemOutLogger(),
                "https://www.bk-fonbet.com/ru/", Constants.CHROME_BROWSER);
        try
        {
            d.start();
            for (;;)
            {
                d.getGamesSafe();
                d.waitTime(10);
            }
        }
        finally
        {
            d.shutdown();
        }

        //System.out.println(d.getGameContext("Франкавилья U20 — Стелла Азура U20"));
        //d.shutdown();
    }

    public FonBetWebDriver(String gameType, Logger logger, String url, String browser)
    {
        super(gameType, logger, new FonBetKeys(), url, browser);
    }

    public FonBetWebDriver(String game, Logger logger, String url, String login, String password, String browser)
    {
        super(game, logger, new FonBetKeys(), url, login, password, browser);
    }

    //TODO refactoring
    @Override
    public void clearBasket()
    {
        if (!find(FBXpath.CLEAR_BET).isDisplayed())
        {
            log("Basket allready cleared.");
            return;
        }
        click(FBXpath.CLEAR_BET);
        int a = 5;
        while (a > 0)
        {
            if (!find(FBXpath.CLEAR_BET).isDisplayed())
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

    @Override
    public void confirm(Game game, Bet bet)
    {
        //setBetChangeIgnore();
        int value = bet.getValue();
        log("Send bet value... (value = " + value + ")");
        sendKeys(FBXpath.BET_VALUE_INPUT, String.valueOf(bet.getValue()));
        log("Click confirm...");
        click(FBXpath.CONFIRM_BET);
        log("Check confirmation...");
        for (int i = 0; i < 10; i++)
        {
            if (isElementExists(FBXpath.BET_IS_CONFIRMED))
            {
                log("Bet " + bet.getTitle() + " was confirmed successfully.");
                log("Confirmation score: " + game.getScore().toString());
                hideBet();
                return;
            }
            log("Bet is not confirmed...");
            if (isElementExists(FBXpath.BET_IS_CANCELED))
            {
                hideBet();
                throw new ConfirmException(game, "Ставка отменена букмекерской конторой.");
            }
            if (isElementExists(FBXpath.BET_STATE_BLOCKED))
            {
                hideBet();
                throw new ConfirmException(game, "Не удалось оформить ставку т.к. она заблокирована.");
            }
            log("Waiting confirmation...");
            waitTime(1);
        }
        throw new ConfirmException(game, "Не удалось оформить ставку по неизвестной причине");
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
            rate = Double.parseDouble(getText(FBXpath.BET_RATE));
            betTitle = normalizeType(getText(FBXpath.BET_EVENT_TITLE), getText(FBXpath.BET_TYPE));
            betTitle = key.isForGame() ? betTitle + AbstractBetType.FOR_GAME : betTitle;
            betType = BetTypeExtractor.get(betTitle);
            //checkBet(game, betType);
            //TODO сделать проверку ставки
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
     * Получить контекст игры
     */
    @Override
    public String getGameContext(String gameTitle)
    {
        try
        {
        //@formatter:off
             String script = "function getContext(gameTitle) "
                + "{ var tds = document.getElementsByClassName('eventCellName'); var i = 0; "
                + "for (; i < tds.length; i++) "
                + "{ var s = tds[i].textContent; "
                + "if (s != null && s.includes(gameTitle)) break; } "
                + "var tr = tds[i].parentNode; "
                + "while (tr.className != 'trSegment') "
                + "tr = tr.previousSibling; "
                + "return tr.textContent.trim(); }; "
                + "return getContext('"
                + gameTitle + "');";
        //@formatter:on
            return String.valueOf(executeScript(script));
        }
        catch (Exception e)
        {
            log("Error taking game context: " + e.getMessage());
            return "";
        }
        /*int i = 0;
        String context = "";
        while (i < 5 && context.isEmpty())
        {
            context = getTextFast(String.format(FBXpath.GAME_CONTEXT, gameTitle));
            waitTime(1);
            i++;
        }
        return context;*/
    }

    /**
     * Получить элемент главной линии ставок (без ожидания)
     */
    @Override
    public WebElement getMainLine(String gameTitle)
    {
        WebElement el = get(String.format(FBXpath.MAIN_LINE, gameTitle));
        if (null == el)
        {
            log("getMainLine() returns NULL !");
        }
        return el;
    }

    @Override
    public Pair<Double, Double> getScore()
    {
        WebElement refreshButton = find(FBXpath.REFRESH_SCORE_BTN);
        if (refreshButton != null)
        {
            log("Refresh score...");
            refreshButton.click();
            waitTime(2);
        }
        WebElement score = find(FBXpath.SCORE);
        double sc = Double.parseDouble(score.getText().replace("Баланс:", "").replace("RUB", "").replace(" ", ""));
        log("Score = " + sc);
        return new Pair<>(sc, 0.0);
    }

    /**
     * Получить линию четверти, сета, периода
     */
    @Override
    public WebElement getSetLine(String gameTitle)
    {
        WebElement el = get(String.format(FBXpath.SET_LINE, gameTitle));
        if (null == el)
        {
            log("getSetLine() returns NULL !");
        }
        return el;
    }

    @Override
    public void goToLiveBets()
    {
        // do nothing
    }

    /**
     * Скрыть сделанную ставку
     * 
     */
    private void hideBet()
    {
        try
        {
            log("Hide setted bet...");
            click(FBXpath.HIDE_SETTED_BET);
            log("Bet is hided.");
        }
        catch (Exception e)
        {
            log("Error while hiding setted(canceled) bet.");
        }
    }

    /**
     * Признак того, что форма оформления ставки открыта
     */
    private boolean isBetMakingFormOpened()
    {
        log("Check bet making form...");
        if (null == find(FBXpath.BET_FORM))
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
        if (find(FBXpath.REFRESH_SCORE_BTN).isDisplayed())
        {
            return;
        }
        log(Messages.LOGIN_OPERATION);
        sendKeys(FBXpath.LOGIN_INPUT, login);
        sendKeys(FBXpath.PASSWORD_INPUT, password);
        WebElement el = click(FBXpath.LOGIN_BTN);
        if (null == el)
        {
            waitTime(2);
            log("Auth unsuccessfull. Try again...");
            sendKeys(FBXpath.LOGIN_INPUT, login);
            sendKeys(FBXpath.PASSWORD_INPUT, password);
            el = click(FBXpath.LOGIN_BTN);
        }
        if (null == el)
        {
            throw new AuthenticationException();
        }
        log(Messages.AUTH_SUCCESS);

        //В Фонбет должен быть включен игнор изменения кэфов
        setBetChangeIgnore();
    }

    @Override
    public void logOut()
    {
        log(Messages.LOGOUT_OPERATION);
        click(FBXpath.LOGOUT_BTN);
    }

    /**
     * Получение нормализованной строки с типом ставки
     */
    private String normalizeType(String title, String type)
    {
        if (type.length() == 1 && (title.contains("(+") || title.contains("(-")))
        {
            Matcher m = Pattern.compile("\\(.+\\)").matcher(title.replace("(ж)", ""));
            m.find();
            String foraValue = m.group().replace("+", "");
            return BetKeys.F + type + foraValue;
        }
        String winStr = "Поб ";
        if (type.contains(winStr))
        {
            return type.replace(winStr, "");
        }
        String tbStr = "> ";
        if (type.contains(tbStr))
        {
            return BetKeys.TB + "(" + type.replace(tbStr, "") + ")";
        }
        String tmStr = "< ";
        if (type.contains(tmStr))
        {
            return BetKeys.TM + "(" + type.replace(tmStr, "") + ")";
        }
        return "";
    }

    @Override
    public void selectGameType()
    {
        click(String.format(FBXpath.GAMES_MENU_PATTERN, GameUtils.getGameTypeTitle(gameType)));
    }

    /**
     * Установить игнорирование изменение кэфа при ставке
     */
    private void setBetChangeIgnore()
    {
        log("Set ignore rate changing...");
        WebElement el1 = find(FBXpath.BET_RATE_CHANGE);
        if (el1 == null)
        {
            throw new RuntimeException("Error find rate changing button.");
        }
        while (!"Принимать ставки с изменёнными коэффициентами".equals(el1.getAttribute("title")))
        {
            log("Rate changing = " + el1.getAttribute("title"));
            if (el1.isDisplayed())
            {
                el1.click();
            }
            waitTime(1);
        }
        log("Rate changing = " + el1.getAttribute("title"));

        log("Set ignore total-fora value changing...");
        WebElement el2 = find(FBXpath.TOTAL_FORA_VALUE_CHANGE);
        if (el2 == null)
        {
            throw new RuntimeException("Error find total-fora value changing button.");
        }
        while (!"Принимать ставки с изменёнными тоталами/форами".equals(el2.getAttribute("title")))
        {
            log("Total-fora value changing = " + el2.getAttribute("title"));
            if (el1.isDisplayed())
            {
                el2.click();
            }
            waitTime(1);
        }
        log("Total-fora value changing = " + el1.getAttribute("title"));
    }

    @Override
    public void start()
    {
        Timer.waitSec(1);
        super.start();

        Timer.waitSec(1);
        while (!isElementExists(FBXpath.LOGIN_INPUT))
        {
            log("Problems with loading page..");
            refresh();
            Timer.waitSec(1);
        }
        log("Start page is loaded successfully.");

        Timer.waitSec(1);
        selectGameType();
    }
}