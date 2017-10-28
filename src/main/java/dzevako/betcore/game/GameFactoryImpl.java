/**
 * 
 */
package dzevako.betcore.game;

import org.openqa.selenium.WebElement;

import dzevako.betcore.game.basketball.Basketball;
import dzevako.betcore.game.hockey.Hockey;
import dzevako.betcore.game.volleyball.Volleyball;
import dzevako.betcore.web.driver.SiteKeys;

/**
 * Реализация фабрики по созданию игр
 * @author dzevako
 * @since Mar 15, 2015
 */
public class GameFactoryImpl implements GameFactory
{
    private SiteKeys keys;

    public GameFactoryImpl(SiteKeys keys)
    {
        this.keys = keys;
    }

    @Override
    public GameElement create(WebElement element, String type)
    {
        if (GameCodes.VOLLEYBALL.equals(type))
        {
            return new Volleyball(element, keys);
        }
        if (GameCodes.BASKETBALL.equals(type))
        {
            return new Basketball(element, keys);
        }
        if (GameCodes.HOCKEY.equals(type))
        {
            return new Hockey(element, keys);
        }
        throw new RuntimeException("Game can not be created! The type '" + type + "' is not found!");
    }
}
