package dzevako.betcore.drivers.betcity;

/**
 * Веб-драйвер для ВС
 *
 * @author dzevako
 * @since Sep 27, 2014
 */
public class BCWebDriver
{
    /*private enum Iframe
    {
        TOP(null, "btop"), CENTER(null, "center"), LEFT(CENTER, "left"), MIDDLE(CENTER, "middle");
    
        public String name;
    
        public Iframe parent;
    
        Iframe(Iframe parent, String name)
        {
            this.name = name;
            this.parent = parent;
        }
    }
    
    Iframe iframe = null;
    
    public void clickOrThrow(String xpath)
    {
        WebElement element = driver().findElement(By.xpath(xpath));
        element.click();
    }
    
    public void goToLeft()
    {
        if (Iframe.LEFT.equals(iframe))
        {
            return;
        }
        switchToFrame(Iframe.LEFT);
    }
    
    public void goToMiddle()
    {
        if (Iframe.MIDDLE.equals(iframe))
        {
            return;
        }
        switchToFrame(Iframe.MIDDLE);
    }
    
    public void goToTop()
    {
        if (Iframe.TOP.equals(iframe))
        {
            return;
        }
        switchToFrame(Iframe.TOP);
    }
    
    @Override
    public void refresh()
    {
        super.refresh();
        switchToFrame(iframe);
    }
    
    private void switchToFrame(Iframe i)
    {
        int attempt = 1;
        boolean refresh = false;
        while (attempt <= 10)
        {
            try
            {
                if (null == i)
                {
                    driver().switchTo().defaultContent();
                }
                else
                {
                    if (null == i.parent)
                    {
                        driver().switchTo().defaultContent();
                    }
                    else
                    {
                        switchToFrame(i.parent);
                    }
                    driver().switchTo().frame(i.name);
                }
                iframe = i;
                return;
            }
            catch (NoSuchFrameException e)
            {
                if (attempt == 10 && refresh)
                {
                    throw e;
                }
                else
                {
                    if (attempt == 10)
                    {
                        refresh();
                        refresh = true;
                        attempt = 1;
                    }
                    else
                    {
                        attempt = +1;
                        Timer.waitMillis(1000);
                    }
                }
            }
        }
    }*/
}
