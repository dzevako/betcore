package dzevako.betcore.drivers.ligastavok;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import dzevako.betcore.drivers.fonbet.FBXpath;
import dzevako.betcore.drivers.marafon.MFXpath;
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
import dzevako.betcore.utils.StringUtils;
import dzevako.betcore.web.driver.AbstractBKWebDriver;

/**
 * Веб драйвер для Лиги Ставок
 * @author dzevako
 * @since Nov 12, 2015
 */
public class LigaStavokWebDriver extends AbstractBKWebDriver
{
    public static void main(String[] args)
    {
        LigaStavokWebDriver d = new LigaStavokWebDriver(GameCodes.BASKETBALL, new SystemOutLogger(), null, null, null,
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
            //d.logOut();
        }
        finally
        {
            d.shutdown();
        }

        //System.out.println(d.getGameContext("Франкавилья U20 — Стелла Азура U20"));
        //d.shutdown();
    }

    private boolean isTitlesDefined = false;
    private List<String> currentGameTitles;

    public LigaStavokWebDriver(String game, Logger logger, String url, String login, String password, String browser)
    {
        super(game, logger, new LigaStavokKeys(), url, login, password, browser);
    }

    @Override
    public void clearBasket()
    {
        if (isElementExists(LSXpath.BASKET_IS_EMPTY))
        {
            log("Basket allready cleared.");
            return;
        }
        click(LSXpath.CLEAR_BASKET);
        int a = 10;
        while (a > 0)
        {
            if (isElementExists(LSXpath.BASKET_IS_EMPTY))
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
        int value = bet.getValue();
        log("Send bet value... (value = " + value + ")");
        sendKeys(LSXpath.BET_VALUE_INPUT, String.valueOf(bet.getValue()));
        log("Click confirm...");
        click(LSXpath.CONFIRM_BET);
        log("Check confirmation...");
        for (int i = 0; i < 10; i++)
        {
            if (isElementExists(LSXpath.BET_IS_CONFIRMED))
            {
                log("Bet " + bet.getTitle() + " was confirmed successfully.");
                log("Confirmation score: " + game.getScore().toString());
                clearBasket();
                return;
            }
            log("Bet is not confirmed...");
            if (isElementExists(MFXpath.BET_IS_CANCELED))
            {
                clearBasket();
                throw new ConfirmException(game, "Ставка не принята.");
            }
            log("Waiting confirmation...");
            waitTime(1);
        }
        throw new ConfirmException(game, "Не удалось оформить ставку по неизвестной причине.");

    }

    //TODO вынести в абстракт

    /**
     * Возвращает список доступных игр из Лайва
     */
    @Override
    protected Set<Game> findGames()
    {
        Set<Game> games = Sets.newHashSet();
        StringBuilder gameList = new StringBuilder("Next games found: \n");

        log("Look up for " + gameType + "...");

        while (!isTitlesDefined)
        {
            try
            {
                currentGameTitles = findAll(keys.getGameTitleElementKey()).stream().map(t -> t.getText())
                        .filter(s -> s.contains("\n")).collect(Collectors.toList());
                isTitlesDefined = true;
                log("Game titles defined successfully.");
            }
            catch (Exception e)
            {
                log("Game titles are not defined!");
                waitTime(1);
            }
        }

        for (String title : currentGameTitles)
        {
            try
            {//TODO сделать нормальные икспасы
                String[] titleParts = title.split("\n");
                WebElement scEl = find(String.format(LSXpath.GAME_SCORE, titleParts[0]));
                String score = keys.getGameScore(scEl);
                WebElement timeEl = find(String.format(LSXpath.GAME_TIME, titleParts[0]));
                int time = keys.getGameTime(timeEl);

                Game game = new GameImpl(gameType, title.replace("\n", " - "), score, time);
                games.add(game);
                gameList.append(game.toString() + '\n');
            }
            catch (Exception e)
            {
                log("WARNING: Game '" + title.replace("\n", " - ")
                        + "' has incorrect data. It was striked from game-list:\n" + StringUtils.getMessage(e));
            }
        }
        isTitlesDefined = false;

        if (games.isEmpty())
        {
            log("No games found.");
        }
        else
        {
            log(gameList.toString());
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
            rate = Double.parseDouble(getText(LSXpath.BET_RATE));
            log("rate = " + rate);
            betTitle = normalizeType(getText(LSXpath.BET_GAME_TITLE), getText(LSXpath.BET_TYPE));
            log("betTitle = " + betTitle);
            betTitle = key.isForGame() ? betTitle + AbstractBetType.FOR_GAME : betTitle;
            log("betTitle = " + betTitle);
            betType = BetTypeExtractor.get(betTitle);
            log("betType = " + betType.toString());
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

    @Override
    public String getGameContext(String title)
    {
        // TODO Auto-generated method stub
        return null;
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
        WebElement score = find(LSXpath.SCORE);
        double sc = Double.parseDouble(score.getText().split(",")[0]);
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
        // do nothing
    }

    private boolean isBetMakingFormOpened()
    {
        WebElement el = get(LSXpath.CLEAR_BASKET);
        return el != null && el.isDisplayed();
    }

    @Override
    public void logIn()
    {
        if (isElementExists(LSXpath.ACCOUNT_BOX))
        {
            return;
        }
        log(Messages.LOGIN_OPERATION);
        sendKeys(LSXpath.LOGIN_INPUT, login);
        sendKeys(LSXpath.PASSWORD_INPUT, password);
        WebElement el = click(FBXpath.LOGIN_BTN);
        if (null == el)
        {
            waitTime(2);
            log("Auth unsuccessfull. Try again...");
            sendKeys(LSXpath.LOGIN_INPUT, login);
            sendKeys(LSXpath.PASSWORD_INPUT, password);
            el = click(LSXpath.LOGIN_BTN);
        }
        if (null == el)
        {
            throw new AuthenticationException();
        }
        //Проверка того что аккаунт загрузился
        while (!isElementExists(LSXpath.ACCOUNT_BOX))
        {
            waitTime(1);
        }
        log(Messages.AUTH_SUCCESS);
    }

    @Override
    public void logOut()
    {
        // no logout
        // do nothing
    }

    /**
     * Получение нормализованной строки с типом ставки
     * Победитель — 2
     * Фора (26,50) — К2
     * Тотал (158,50) — бол
     */
    private String normalizeType(String title, String type)
    {
        if (type == null || type.isEmpty())
        {
            log("Incorrect betType on form is detected (empty or null)");
            return "";
        }

        String team = type.substring(type.length() - 1, type.length());

        if (type.contains("Победитель"))
        {
            return team;
        }

        String betValue = type.split(" ")[1].replace("0)", ")"); //(26,50)

        if (type.contains("Фора"))
        {
            return BetKeys.F + team + betValue;
        }

        if (type.contains("Тотал") && type.contains("мен"))
        {
            return BetKeys.TM + betValue;
        }

        if (type.contains("Тотал") && type.contains("бол"))
        {
            return BetKeys.TB + betValue;
        }

        return "";
    }

    @Override
    public void openGame(String title)
    {
        // do nothing
    }

    @Override
    public void refreshGame()
    {
        // do nothing
    }

    @Override
    public void refreshGames()
    {
        // do nothing
    }

    @Override
    public void selectGameType()
    {
        // TODO Auto-generated method stub

    }

}
