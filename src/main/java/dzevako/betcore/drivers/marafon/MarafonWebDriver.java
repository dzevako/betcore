package dzevako.betcore.drivers.marafon;

import java.util.List;
import java.util.Set;
import java.util.TimerTask;

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
 * Веб драйвер для Марафон
 * @author dzevako
 * @since Feb 19, 2016
 */
public class MarafonWebDriver extends AbstractBKWebDriver
{
    private final static String FORA = "Победа с учетом форы";
    private final static String TOTAL = "Тотал очков";
    private final static String MORE = "Больше";
    private final static String LESS = "Меньше";
    private final static String WIN_a = "Победитель матча";
    private final static String WIN_b = "победа";

    public static void main(String[] args)
    {
        MarafonWebDriver d = new MarafonWebDriver(GameCodes.BASKETBALL, new SystemOutLogger(), null, null, null,
                Constants.CHROME_BROWSER);
        try
        {

            d.start();
            //d.logIn();
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
    }

    private boolean isSelectGameTypeRequired = false;
    //private final static String START_PAGE = "https://www.marathonbookmakers.com/su/live/popular";

    public MarafonWebDriver(String game, Logger logger, String url, String login, String password, String browser)
    {
        super(game, logger, new MarafonKeys(), url, login, password, browser);

        new java.util.Timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                isSelectGameTypeRequired = true;
            }
        }, 900000, 900000);
    }

    private void cancelBet()
    {
        click(MFXpath.CANCEL_BET_WITH_CHANGES);
        if (isElementExists(MFXpath.MODAL_FORM))
        {
            log("Error cancelling bet...");
            click(MFXpath.CANCEL_BET_WITH_CHANGES);
        }
        else
        {
            log("Bet is canceled.");
            clearBasket();
        }
    }

    @Override
    public void clearBasket()
    {
        if (!isElementExists(MFXpath.CLEAR_BET))
        {
            log("Basket allready cleared.");
            return;
        }
        click(MFXpath.CLEAR_BET);
        int a = 5;
        while (a > 0)
        {
            if (!isElementExists(MFXpath.CLEAR_BET))
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

    private void closeModalFormIfAppear()
    {
        if (isElementExists(MFXpath.MODAL_FORM_CONFIRM))
        {
            log("Close modal form...");
            click(MFXpath.MODAL_FORM_CONFIRM);
        }
        waitTime(1);
        if (isElementExists(MFXpath.MODAL_FORM_CONFIRM))
        {
            log("Modal dialog is no closed...");
            click(MFXpath.MODAL_FORM_CONFIRM);
        }
        else
        {
            log("Modal form is closed.");
        }
    }

    //TODO вынести в общую часть
    @Override
    public void confirm(Game game, Bet bet)
    {
        int value = bet.getValue();
        log("Send bet value... (value = " + value + ")");
        sendKeys(MFXpath.BET_VALUE_INPUT, String.valueOf(bet.getValue()));
        log("Click confirm...");
        click(MFXpath.CONFIRM_BET);
        log("Check confirmation...");
        for (int i = 0; i < 10; i++)
        {
            if (isElementExists(MFXpath.BET_IS_CONFIRMED))
            {
                log("Bet " + bet.getTitle() + " was confirmed successfully.");
                log("Confirmation score: " + game.getScore().toString());
                closeModalFormIfAppear();
                return;
            }
            log("Bet is not confirmed...");
            if (isElementExists(MFXpath.BET_IS_CANCELED))
            {
                cancelBet();
                throw new ConfirmException(game, "Ставка не принята.");
            }
            log("Waiting confirmation...");
            waitTime(2);
        }
        throw new ConfirmException(game, "Не удалось оформить ставку по неизвестной причине.");

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
            rate = Double.parseDouble(getText(MFXpath.BET_RATE));
            log("rate = " + rate);
            betTitle = normalizeType(getText(MFXpath.BET_EVENT_TITLE), getText(MFXpath.BET_TYPE));
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
        //@formatter:off
        String value = getTextFast(key.getValueKey())
                .replace("+", "")
                .replace(",", ".")
                .replace("\n", "")
                .replace(String.valueOf(rate), "");
        //@formatter:on
        log("value = " + value);
        if (!value.isEmpty())
        {
            data = data + value;
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
        if (isSelectGameTypeRequired)
        {
            List<WebElement> showedTypes = findAll(MFXpath.GAME_CONTEXT_TITLE);
            if (showedTypes.size() > 1 || showedTypes.size() < 1
                    || !showedTypes.get(0).getText().equalsIgnoreCase(GameUtils.getGameTypeTitle(gameType)))
            {
                goToLiveBets();
                selectGameType();
            }
            isSelectGameTypeRequired = false;
        }

        return super.getGamesSafe();
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
        WebElement score = find(MFXpath.SCORE);
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

    @Override
    public void goToLiveBets()
    {
        if (isElementExists(MFXpath.IS_LIVE_ACTIVE))
        {
            return;
        }
        log("Go to live bets...");
        click(MFXpath.LIVE);
    }

    /**
     * Признак того, что форма оформления ставки открыта
     */
    private boolean isBetMakingFormOpened()
    {
        log("Check bet making form...");
        waitTime(1, false);
        if (isElementExists(MFXpath.CHANGE_RATE_SETTING))
        {
            log("Bet form is opened.");
            return true;
        }
        else
        {
            if (isElementExists(MFXpath.NO_BET_CHOOSED))
            {
                log("Bet form is not open!");
                return false;
            }
            else
            {
                log("Bet form is hided. Show form...");
                waitTime(1, false);
                click(MFXpath.SHOW_BET_FORM);
                waitTime(1, false);
                if (isElementExists(MFXpath.CHANGE_RATE_SETTING))
                {
                    log("Bet form is opened.");
                    return true;
                }
                else
                {
                    log("It is not possible to show the form...");
                    return false;
                }
            }
        }
    }

    @Override
    public void logIn()
    {
        if (isElementExists(MFXpath.SCORE))
        {
            return;
        }
        log(Messages.LOGIN_OPERATION);
        sendKeys(MFXpath.LOGIN_INPUT, login);
        click(MFXpath.PASSWORD_INPUT_FAKE);
        sendKeys(MFXpath.PASSWORD_INPUT, password);
        WebElement el = click(MFXpath.LOGIN_BTN);
        if (null == el)
        {
            waitTime(2);
            log("Auth unsuccessfull. Try again...");
            sendKeys(MFXpath.LOGIN_INPUT, login);
            sendKeys(MFXpath.PASSWORD_INPUT, password);
            el = click(MFXpath.LOGIN_BTN);
        }
        if (null == el)
        {
            throw new AuthenticationException();
        }
        log(Messages.AUTH_SUCCESS);

        setBetChangeIgnore();
    }

    @Override
    public void logOut()
    {
        log(Messages.LOGOUT_OPERATION);
        click(MFXpath.LOGOUT_BTN);
    }

    private String normalizeType(String title, String type)
    {
        String[] teams = title.split(" - ");
        type = type.replace(title, ""); // т.к. он тоже содержит title
        if (type.contains(WIN_a) || type.contains(WIN_b))
        {
            return type.contains(teams[0]) ? BetKeys.WIN1 : BetKeys.WIN2;
        }
        if (type.contains(FORA))
        {
            int team = type.contains(teams[0]) ? 1 : 2;
            String value = type.split(teams[team - 1])[1].replace(" ", "");
            return BetKeys.fora(team) + value;
        }
        if (type.contains(TOTAL))
        {
            String splitter = type.contains(MORE) ? MORE : LESS;
            String value = type.split(splitter)[1].replace(" ", "");
            String key = splitter.equals(MORE) ? BetKeys.TB : BetKeys.TM;
            return key + value;
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
            click(String.format(MFXpath.GAMES_MENU_PATTERN, typeTitle));
        }
        catch (Exception e)
        {
            log("Unable to choose " + typeTitle + " in live menu...");
        }
    }

    private void setBetChangeIgnore()
    {
        log("Ignoring changing bets...");
        clickIfExists(MFXpath.CHANGE_RATE_SETTING);
        clickIfExists(MFXpath.ALLOW_ALL_CHANGES_RATE);
        clickIfExists(MFXpath.CHANGE_RATE_SETTING);
    }

    @Override
    public void start()
    {
        Timer.waitSec(2);
        super.start();
        Timer.waitSec(1);
        while (!isElementExists(MFXpath.LOGIN_INPUT))
        {
            log("Problems with loading page..");
            refresh();
            Timer.waitSec(1);
        }
        log("Start page is loaded successfully.");

        goToLiveBets();

        Timer.waitSec(1);
        selectGameType();
    }
}
