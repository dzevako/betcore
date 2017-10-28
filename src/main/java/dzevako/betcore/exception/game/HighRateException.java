package dzevako.betcore.exception.game;

import dzevako.betcore.bettypes.BetType;
import dzevako.betcore.game.Game;

/**
 * Исключение, возникающее при слишком высоком коэффициэнте ставки
 * @author dzevako
 * @since Oct 21, 2014
 */
public class HighRateException extends GamePlayException
{
    private static final long serialVersionUID = 1L;

    public HighRateException(Game game, BetType betType)
    {
        super(game, "Too high rate was fixed with bet '" + betType.getString() + "'(rate = " + betType.getRate() + ")");
    }
}
