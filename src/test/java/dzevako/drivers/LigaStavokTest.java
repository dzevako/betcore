package dzevako.drivers;

import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Maps;

import dzevako.betcore.Constants;
import dzevako.betcore.TestGameObject;
import dzevako.betcore.bet.Bet;
import dzevako.betcore.bettypes.BetKey;
import dzevako.betcore.bettypes.BetKeys;
import dzevako.betcore.bettypes.BetType;
import dzevako.betcore.bettypes.Fora;
import dzevako.betcore.bettypes.Total;
import dzevako.betcore.drivers.fonbet.FonBetKeys;
import dzevako.betcore.drivers.ligastavok.LigaStavokWebDriver;
import dzevako.betcore.game.Game;
import dzevako.betcore.game.GameTitles;
import dzevako.betcore.logger.SystemOutLogger;

/**
 * Тестирование Лиги ставок
 * @author dzevako
 * @since Oct 15, 2015
 */
public class LigaStavokTest
{
    static LigaStavokWebDriver driver;

    static Map<String, TestGameObject> expectedGames = Maps.newHashMap();

    @BeforeClass
    public static void setUp()
    {
        expectedGames.put("Сан Мигель Бирмен - Гинебра Сан-Мигель", new TestGameObject(
                "Сан Мигель Бирмен - Гинебра Сан-Мигель", GameTitles.BASKETBALL, "68:54(23:23,24:20,21:11)", 35));
        expectedGames.put("Локомотив Кубань (мол) - Автодор Саратов (мол)",
                new TestGameObject("Локомотив Кубань (мол) - Автодор Саратов (мол)", GameTitles.BASKETBALL,
                        "38:28(23:14,15:14,0:0,0:0)", 20));
        expectedGames.put("ПАОК (ж) - Кронос Агиноу Димитриоу (ж)", new TestGameObject(
                "ПАОК (ж) - Кронос Агиноу Димитриоу (ж)", GameTitles.BASKETBALL, "31:28(12:12,19:16)", 20));
        expectedGames.put("Загреб - Шибеник", new TestGameObject("Загреб - Шибеник", GameTitles.BASKETBALL, "", 0));
        expectedGames.put("Торино - Милан",
                new TestGameObject("Торино - Милан", GameTitles.BASKETBALL, "68:54(23:23,24:20,21:11)", 35));

        driver = new LigaStavokWebDriver("Баскетбол", new SystemOutLogger(), null, null, null,
                dzevako.betcore.common.Constants.CHROME_BROWSER);
        driver.start(Constants.LIGASTAVOK_LIVE_TEST);
    }

    @AfterClass
    public static void tearDown()
    {
        driver.shutdown();
    }

    BetKeys keys = new BetKeys(new FonBetKeys());

    @Test
    public void basketballTest()
    {
        Game game = expectedGames.get("1115Клостернойбург — Гмунден");
        driver.goTo(Constants.LIGASTAVOK_LIVE_TEST);
        Assert.assertTrue(1.15 == driver.getRate(keys.getGameWin(game, 1)));
        Assert.assertTrue(4.7 == driver.getRate(keys.getGameWin(game, 2)));
        Assert.assertTrue(-4 == ((Fora)driver.getBetType(keys.getGameFora(game, 1))).getValue());
        Assert.assertTrue(1.68 == driver.getRate(keys.getGameFora(game, 1)));
        Assert.assertTrue(4 == ((Fora)driver.getBetType(keys.getGameFora(game, 2))).getValue());
        Assert.assertTrue(2.05 == driver.getRate(keys.getGameFora(game, 2)));
        Assert.assertTrue(144.5 == ((Total)driver.getBetType(keys.getGameTB(game))).getValue());
        Assert.assertTrue(2.03 == driver.getRate(keys.getGameTB(game)));
        Assert.assertTrue(144.5 == ((Total)driver.getBetType(keys.getGameTM(game))).getValue());
        Assert.assertTrue(1.7 == driver.getRate(keys.getGameTM(game)));
    }

    @Test
    public void betFormTest()
    {
        driver.start();
        driver.logIn();
        Set<Game> games = driver.getGamesSafe();
        if (games.isEmpty())
        {
            Assert.fail("Активных игр не найдено!");
        }
        Game game = games.iterator().next();
        BetKeys keys = new BetKeys(new FonBetKeys());
        BetKey key = keys.getGameFora(game, 1);
        BetType betType = driver.getBetType(key);
        Bet bet = driver.getBetOnGame(game, key);
        Assert.assertEquals(bet.getType(), betType);

        key = keys.getGameWin(game, 1);
        betType = driver.getBetType(key);
        bet = driver.getBetOnGame(game, key);
        Assert.assertEquals(bet.getType(), betType);

        key = keys.getGameTM(game);
        betType = driver.getBetType(key);
        bet = driver.getBetOnGame(game, key);
        Assert.assertEquals(bet.getType(), betType);

        driver.logOut();
        driver.shutdown();
    }

    @Test
    public void gamesListTest()
    {
        Set<Game> games = driver.getGamesSafe();
        Assert.assertEquals(5, games.size());

        for (Game game : games)
        {
            TestGameObject expected = expectedGames.get(game.getTitle());
            Assert.assertEquals(expected.getScore(), game.getScore());
            Assert.assertEquals(expected.getTime(), game.getTime());
        }
    }

    @Test
    public void liveTest()
    {
        driver.start();
        driver.logIn();
        driver.getGamesSafe();
        Assert.assertTrue(0 < driver.getScore().getFirst());
        driver.shutdown();
    }
}
