package dzevako.betcore.exception.game;

import dzevako.betcore.bettypes.BetType;
import dzevako.betcore.game.Game;

/**
 * Слишком низкая ставка
 * @author dzevako
 * @since Oct 22, 2014
 */
public class LowRateException extends GamePlayException
{
    private static final long serialVersionUID = 1L;

    public LowRateException(Game game, BetType betType)
    {
        super(game, "Too low rate was fixed with bet '" + betType.getString() + "'(rate = " + betType.getRate() + ")");
    }
}
