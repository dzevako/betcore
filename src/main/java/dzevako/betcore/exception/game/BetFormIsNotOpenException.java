/**
 * 
 */
package dzevako.betcore.exception.game;

import dzevako.betcore.game.Game;

/**
 * Исключение, возникающее, когда форма оформления ставки не открылась
 * @author dzevako
 * @since Aug 29, 2015
 */
public class BetFormIsNotOpenException extends GamePlayException
{
    private static final long serialVersionUID = 1L;

    public BetFormIsNotOpenException(Game game)
    {
        super(game, "Bet form is not open!");
    }
}
