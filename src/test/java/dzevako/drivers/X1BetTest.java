package dzevako.drivers;

import java.util.Iterator;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dzevako.betcore.bet.Bet;
import dzevako.betcore.bet.BetImpl;
import dzevako.betcore.bettypes.BetKey;
import dzevako.betcore.bettypes.BetKeyImpl;
import dzevako.betcore.bettypes.Fora;
import dzevako.betcore.drivers.x1bet.X1WebDriver;
import dzevako.betcore.drivers.x1bet.X1Xpath;
import dzevako.betcore.game.Game;
import dzevako.betcore.game.GameCodes;
import dzevako.betcore.logger.SystemOutLogger;

public class X1BetTest
{
    static X1WebDriver driver;

    @BeforeClass
    public static void setUp()
    {
        driver = new X1WebDriver(GameCodes.BASKETBALL, new SystemOutLogger(), null, null, null,
                dzevako.betcore.common.Constants.CHROME_BROWSER);
        driver.start("https://l10uxury39.com/live/");
    }

    @AfterClass
    public static void tearDown()
    {
        driver.shutdown();
    }

    @Test
    public void openBetFormTest()
    {
        driver.selectGameType();
        Set<Game> games = driver.getGamesSafe();
        Iterator<Game> it = games.iterator();
        //it.next();
        //it.next();
        Game game = it.next();

        String valueKey = String.format(X1Xpath.MAIN_LINE_COLUMN, game.getTitle(), 3);
        String rateKey = String.format(X1Xpath.MAIN_LINE_COLUMN_RATE, game.getTitle(), 4);

        BetKey key = new BetKeyImpl(rateKey, valueKey, "", true);

        driver.getBetOnGame(game, key);
        driver.clearBasket();
        driver.waitTime(3);
    }

    @Test
    public void openBetFormTest1()
    {
        driver.selectGameType();
        /*Set<Game> games = driver.getGamesSafe();
        Iterator<Game> it = games.iterator();
        it.next();
        //it.next();
        Game game = it.next();*/
        driver.waitTime(3);
        driver.click(String.format(X1Xpath.MAIN_LINE_COLUMN_RATE, "Коледж Белград (18) — Белград (18) (с ОТ)", 4));
        driver.waitTime(3);
    }

    @Test
    public void setBetTest()
    {
        driver.logIn();
        driver.selectGameType();
        Set<Game> games = driver.getGamesSafe();
        Iterator<Game> it = games.iterator();
        it.next();
        //it.next();
        Game game = it.next();

        String valueKey = String.format(X1Xpath.MAIN_LINE_COLUMN, game.getTitle(), 3);
        String rateKey = String.format(X1Xpath.MAIN_LINE_COLUMN_RATE, game.getTitle(), 4);

        BetKey key = new BetKeyImpl(rateKey, valueKey, "", true);

        driver.getBetOnGame(game, key);
        Bet bet = new BetImpl(Fora.get("Ф1(23.5)"));
        bet.setValue(50);
        driver.confirm(game, bet);
        driver.waitTime(3);
    }
}
