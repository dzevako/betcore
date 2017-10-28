package dzevako.drivers;

import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dzevako.betcore.bettypes.BetKey;
import dzevako.betcore.bettypes.BetKeyImpl;
import dzevako.betcore.drivers.marafon.MarafonWebDriver;
import dzevako.betcore.game.Game;
import dzevako.betcore.game.GameCodes;
import dzevako.betcore.logger.SystemOutLogger;

/**
 * Тестирование Марафон
 *
 * @author dzevako
 * @since Apr 12, 2016
 */
public class MarafonTest
{
    static MarafonWebDriver driver;

    @BeforeClass
    public static void setUp()
    {
        driver = new MarafonWebDriver(GameCodes.BASKETBALL, new SystemOutLogger(), null, null, null,
                dzevako.betcore.common.Constants.CHROME_BROWSER);
        driver.start("https://www.marathonbet.com/su/live/popular");
    }

    @AfterClass
    public static void tearDown()
    {
        driver.shutdown();
    }

    @Test
    public void openBetFormTest()
    {
        Set<Game> games = driver.getGamesSafe();
        Game game = games.iterator().next();

        String[] teams = game.getTitle().split(" - ");

        String valueKey = "//div[@id='id_container']//div[@id='container_EVENTS']//table/tbody[contains(@id, 'event_')]/tr[1]/td[1][contains(string(), \""
                + teams[0] + "\")][contains(string(), \"" + teams[1] + "\")]/../td[5]";
        String rateKey = valueKey + "/span";

        BetKey key = new BetKeyImpl(rateKey, valueKey, "", true);

        driver.getBetOnGame(game, key);
        driver.clearBasket();
        driver.waitTime(3);
    }
}
