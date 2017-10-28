/**
 * 
 */
package dzevako.betcore.exception.game;

/**
 * Исключение, выпадающее в случае опустошения банка
 * @author dzevako
 * @since Sep 28, 2014
 */
public class BankEmptyException extends GamePlayException
{
    private static final long serialVersionUID = 1L;

    public BankEmptyException()
    {
        super(null, "Bank is empty!");
    }
}
