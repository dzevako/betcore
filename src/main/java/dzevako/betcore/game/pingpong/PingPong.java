package dzevako.betcore.game.pingpong;

import org.openqa.selenium.WebElement;

import dzevako.betcore.game.AbstractGameElement;
import dzevako.betcore.game.GameTitles;
import dzevako.betcore.web.driver.SiteKeys;

/**
 * Настольный теннис
 * @author dzevako
 * @since Mar 15, 2015
 */
public class PingPong extends AbstractGameElement
{
    public PingPong(WebElement element, SiteKeys keys)
    {
        super(element, keys);
        type = GameTitles.PINGPONG;
    }

    @Override
    public boolean isTimed()
    {
        return false;
    }
}
