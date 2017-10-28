package dzevako.betcore.web.driver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import dzevako.betcore.common.base.Timer;
import dzevako.betcore.exception.XpathSyntaxError;

/**
 * Абстрактный класс, реализующий поиск веб-элементов
 * @author dzevako
 * @since Sep 27, 2014
 */
public abstract class AbstractWebSearch
{

    protected SearchContext element;

    protected AbstractWebSearch(SearchContext element)
    {
        this.element = element;
    }

    /**
     * Количество попыток поиска элементов
     */
    protected abstract int checkAttempts();

    /**
     * Время между попытками
     */
    protected abstract int checkWaitTime();

    /**
     * Найти элемент, если не находит - пробовать checkAttemptsMax раз
     * если не находит вернуть null
     */
    public WebElement find(By xpath)
    {
        int checkAttempts = 1;
        while (checkAttempts <= checkAttempts())
        {
            try
            {
                return element.findElement(xpath);
            }
            catch (InvalidSelectorException e)
            {
                throw new XpathSyntaxError(e);
            }
            catch (NoSuchElementException | ElementNotVisibleException e)
            {
                if (checkAttempts == checkAttempts())
                {
                    return null;
                }
                else
                {
                    Timer.waitMillis(checkWaitTime());
                    checkAttempts += 1;
                }
            }
        }
        return null;
    }

    /**
     * Найти элемент по xpath
     */
    public WebElement find(String xpath)
    {
        return find(By.xpath(xpath));
    }

    /**
     * Получить список элементов
     */
    public List<WebElement> findAll(String xpath)
    {
        return element.findElements(By.xpath(xpath));
    }

    /**
     *  Получение элемента без ожиданий 
     */
    public WebElement get(String xpath)
    {
        try
        {
            return element.findElement(By.xpath(xpath));
        }
        catch (InvalidSelectorException e)
        {
            throw new XpathSyntaxError(e);
        }
        catch (NoSuchElementException e)
        {
            return null;
        }
    }

    /**
     * Получить текст элемента
     */
    public String getText(String xpath)
    {
        WebElement element = find(xpath);
        return null == element ? "" : element.getText();
    }

    /**
     * Получить текст элемента без ожиданий
     */
    public String getTextFast(String xpath)
    {
        WebElement element = get(xpath);
        return null == element ? "" : element.getText();
    }

    /**
     * Проверяет, существует ли элемент
     */
    public boolean isElementExists(String xpath)
    {
        try
        {
            element.findElement(By.xpath(xpath));
            return true;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }
}
