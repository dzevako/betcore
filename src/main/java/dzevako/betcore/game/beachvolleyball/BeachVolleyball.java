/**
 * 
 */
package dzevako.betcore.game.beachvolleyball;

import org.openqa.selenium.WebElement;

import dzevako.betcore.game.AbstractGameElement;
import dzevako.betcore.game.GameTitles;
import dzevako.betcore.web.driver.SiteKeys;

/**
 * Пляжный волейбол
 * @author dzevako
 * @since Mar 15, 2015
 */
public class BeachVolleyball extends AbstractGameElement
{
    public BeachVolleyball(WebElement element, SiteKeys keys)
    {
        super(element, keys);
        type = GameTitles.BEACH_VOLLEYBALL;
    }

    @Override
    public boolean isTimed()
    {
        return false;
    }
}
