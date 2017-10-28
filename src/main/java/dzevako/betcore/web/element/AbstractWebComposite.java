package dzevako.betcore.web.element;

import org.openqa.selenium.WebElement;

import dzevako.betcore.web.driver.AbstractWebSearch;

/**
 * Абстрактный составной веб элемент
 * @author dzevako
 * @since Sep 27, 2014
 */
public abstract class AbstractWebComposite extends AbstractWebSearch
{
    private static final int CHECK_ATTEMPTS = 5;
    private static final int CHECK_WAIT_TIME = 500;

    protected AbstractWebComposite(WebElement element)
    {
        super(element);
    }

    @Override
    protected int checkAttempts()
    {
        return CHECK_ATTEMPTS;
    }

    @Override
    protected int checkWaitTime()
    {
        return CHECK_WAIT_TIME;
    }

    protected WebElement element()
    {
        return (WebElement)element;
    }
}