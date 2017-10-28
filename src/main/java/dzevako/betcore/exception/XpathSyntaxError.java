/**
 * 
 */
package dzevako.betcore.exception;

import org.openqa.selenium.InvalidSelectorException;

/**
 * Ошибка синтаксиса Xpath
 * @author dzevako
 * @since Oct 18, 2014
 */
public class XpathSyntaxError extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public XpathSyntaxError(InvalidSelectorException e)
    {
        super(e.getMessage());
    }
}
