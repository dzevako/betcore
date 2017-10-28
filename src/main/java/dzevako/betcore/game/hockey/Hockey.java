package dzevako.betcore.game.hockey;

import org.openqa.selenium.WebElement;

import dzevako.betcore.game.AbstractGameElement;
import dzevako.betcore.game.GameTitles;
import dzevako.betcore.web.driver.SiteKeys;

/**
 * Хоккей
 * @author dzevako
 * @since Aug 13, 2015
 */
public class Hockey extends AbstractGameElement
{

    public Hockey(WebElement element, SiteKeys keys)
    {
        super(element, keys);
        type = GameTitles.HOCKEY;
    }

    @Override
    public boolean isTimed()
    {
        return true;
    }
}