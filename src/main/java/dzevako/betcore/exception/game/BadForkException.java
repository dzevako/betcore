package dzevako.betcore.exception.game;

import dzevako.betcore.game.Game;

/**
 * Исключение, возникающее при выборе вилки, не удовлетворяющей заданным условиям
 * @author dzevako
 * @since Oct 27, 2014
 */
public class BadForkException extends GamePlayException
{
    private static final long serialVersionUID = 1L;

    public BadForkException(Game game, int win)
    {
        super(game, "Bad fork was detected. Possible win = " + win + ". It's less than required.");
    }
}
