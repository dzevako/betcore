package dzevako.betcore.game.basketball;

import org.openqa.selenium.WebElement;

import dzevako.betcore.game.AbstractGameElement;
import dzevako.betcore.game.GameTitles;
import dzevako.betcore.web.driver.SiteKeys;

/**
 * Баскетбол
 * @author dzevako
 * @since Jun 12, 2015
 */
public class Basketball extends AbstractGameElement
{
    public Basketball(WebElement element, SiteKeys keys)
    {
        super(element, keys);
        type = GameTitles.BASKETBALL;
    }

    @Override
    public boolean isTimed()
    {
        return true;
    }
}
