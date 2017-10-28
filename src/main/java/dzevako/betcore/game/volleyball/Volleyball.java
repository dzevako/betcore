/**
 * 
 */
package dzevako.betcore.game.volleyball;

import org.openqa.selenium.WebElement;

import dzevako.betcore.game.AbstractGameElement;
import dzevako.betcore.game.GameTitles;
import dzevako.betcore.web.driver.SiteKeys;

/**
 * Волейбол
 * @author dzevako
 * @since Mar 15, 2015
 */
public class Volleyball extends AbstractGameElement
{
    public Volleyball(WebElement element, SiteKeys keys)
    {
        super(element, keys);
        type = GameTitles.VOLLEYBALL;
    }

    @Override
    public boolean isTimed()
    {
        return false;
    }
}
