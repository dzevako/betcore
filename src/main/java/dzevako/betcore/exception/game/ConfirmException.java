/**
 * 
 */
package dzevako.betcore.exception.game;

import dzevako.betcore.game.Game;

/**
 * Исключение выбрасывается, если есть проблемы с оформлении ставки
 * @author dzevako
 * @since Nov 29, 2014
 */
public class ConfirmException extends GamePlayException
{
    private static final long serialVersionUID = 1L;

    public ConfirmException(Game game, String message)
    {
        super(game, "Confirm exception: " + message);
    }
}
