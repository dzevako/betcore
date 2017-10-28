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
import dzevako.betcore.drivers.fonbet.FonBetWebDriver;
import dzevako.betcore.game.Game;
import dzevako.betcore.game.GameTitles;
import dzevako.betcore.logger.SystemOutLogger;

/**
 * Тестирование списка игр в Фонбет
 * @author dzevako
 * @since Oct 15, 2015
 */
public class FonBetTest
{
    static FonBetWebDriver driver;

    static Map<String, TestGameObject> expectedGames = Maps.newHashMap();

    @BeforeClass
    public static void setUp()
    {
        expectedGames.put("Црвена Звезда — Страсбург",
                new TestGameObject("Црвена Звезда — Страсбург", GameTitles.BASKETBALL, "55:44(19:20,24:12,12:12)", 28));
        expectedGames.put("Даруссафака — Сассари", new TestGameObject("Даруссафака — Сассари", "15:22(15:19,0:3)", 10));
        expectedGames.put("Клостернойбург — Гмунден", new TestGameObject("Клостернойбург — Гмунден",
                GameTitles.BASKETBALL, "68:63(20:20,18:24,19:12,11:7)", 40));
        expectedGames.put("Каллитеас — Астерас Агиос Димитриос", new TestGameObject(
                "Каллитеас — Астерас Агиос Димитриос", GameTitles.BASKETBALL, "32:46(6:15,15:15,11:16)", 29));
        expectedGames.put("ДАС Драпетсонас — Сурменон/Эллиникоу",
                new TestGameObject("ДАС Драпетсонас — Сурменон/Эллиникоу", "20:12", 9));

        driver = new FonBetWebDriver("Баскетбол", new SystemOutLogger(), null,
                dzevako.betcore.common.Constants.CHROME_BROWSER);
        //driver.start(Constants.FONBET_LIVE_TEST);
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
        Game game = expectedGames.get("Клостернойбург — Гмунден");
        driver.goTo(Constants.FONBET_LIVE_TEST);
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
            TestGameObject expected = expectedGames.get(game.getTitle().substring(4, game.getTitle().length()));
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
        driver.logOut();
        driver.shutdown();
    }
}
