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
import dzevako.betcore.bettypes.BetKeys;
import dzevako.betcore.bettypes.Fora;
import dzevako.betcore.bettypes.Total;
import dzevako.betcore.drivers.betcity.BetCityKeys;
import dzevako.betcore.drivers.betcity.BetCityWebDriver;
import dzevako.betcore.game.Game;
import dzevako.betcore.game.GameTitles;
import dzevako.betcore.logger.SystemOutLogger;

/**
 * Тестирование Бетсити
 * @author dzevako
 * @since Oct 15, 2015
 */
public class BetCityTest
{
    static BetCityWebDriver driver;

    static Map<String, TestGameObject> expectedGames = Maps.newHashMap();

    @BeforeClass
    public static void setUp()
    {
        expectedGames.put("Триглав - Радивой Корач",
                new TestGameObject("Триглав - Радивой Корач", "38:53(16:23,8:17,14:13)", 9));
        expectedGames.put("ЦСКА - Маккаби Т-А", new TestGameObject("ЦСКА - Маккаби Т-А", "44:29(29:20,15:9)", 0));
        expectedGames.put("Дарюшшафака - Сарденья Сассари",
                new TestGameObject("Дарюшшафака - Сарденья Сассари", "6:13", 5));
        expectedGames.put("Сундсвалль Дрэгонс - Емтланд",
                new TestGameObject("Сундсвалль Дрэгонс - Емтланд", "57:60(24:19,22:26,11:15)", 6));
        expectedGames.put("Ден Босх - Веерт", new TestGameObject("Ден Босх - Веерт", "12:14", 8));

        driver = new BetCityWebDriver("Баскетбол", new SystemOutLogger(), null,
                dzevako.betcore.common.Constants.CHROME_BROWSER);
        driver.start(Constants.BETCITY_LIVE_TEST);
    }

    @AfterClass
    public static void tearDown()
    {
        driver.shutdown();
    }

    BetKeys keys = new BetKeys(new BetCityKeys());

    @Test
    public void basketballTest()
    {
        Game game = new TestGameObject(null, GameTitles.BASKETBALL, null, 0);
        driver.goTo(Constants.BETCITY_BASKETBALL);
        Assert.assertTrue(1.24 == driver.getRate(keys.getGameWin(game, 1)));
        Assert.assertTrue(3.8 == driver.getRate(keys.getGameWin(game, 2)));
        Assert.assertTrue(-7.5 == ((Fora)driver.getBetType(keys.getGameFora(game, 1))).getValue());
        Assert.assertTrue(1.87 == driver.getRate(keys.getGameFora(game, 1)));
        Assert.assertTrue(7.5 == ((Fora)driver.getBetType(keys.getGameFora(game, 2))).getValue());
        Assert.assertTrue(1.87 == driver.getRate(keys.getGameFora(game, 2)));
        Assert.assertTrue(148.5 == ((Total)driver.getBetType(keys.getGameTM(game))).getValue());
        Assert.assertTrue(1.94 == driver.getRate(keys.getGameTM(game)));
        Assert.assertTrue(148.5 == ((Total)driver.getBetType(keys.getGameTB(game))).getValue());
        Assert.assertTrue(1.8 == driver.getRate(keys.getGameTB(game)));
    }

    @Test
    public void gameListTest()
    {
        driver.goTo(Constants.BETCITY_LIVE_TEST);
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
    public void gameLoadingTest()
    {
        driver.goTo(null);
        driver.goToLiveBets();
        Set<Game> games = driver.getGamesSafe();
        if (games.isEmpty())
        {
            Assert.fail("Игр не найдено!");
        }
        driver.openGame(games.iterator().next().getTitle());
        driver.refreshGame();
        driver.refreshGame();
        driver.refreshGame();
        driver.refreshGame();
    }
}