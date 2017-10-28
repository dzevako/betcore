package dzevako.betcore.exception.game;

import dzevako.betcore.game.Game;

/**
 * Исключение, возникающее при проверки условий ставки при ее согласовании на форме
 * @author dzevako
 * @since Aug 25, 2015
 */
public class CheckBetPostConditionException extends GamePlayException
{
    private static final long serialVersionUID = 1L;

    public CheckBetPostConditionException(Game game)
    {
        this(game, "");
    }

    public CheckBetPostConditionException(Game game, String message)
    {
        super(game, "Checking bet failed. " + message);
    }
}
