package dzevako.betcore.exception.game;

import dzevako.betcore.game.Game;

/**
 * Исключение, возникающее при обнаружении некорректного коэффициента
 * @author dzevako
 * @since Oct 27, 2014
 */
public class IncorrectRateException extends GamePlayException
{
    private static final long serialVersionUID = 1L;

    public IncorrectRateException(Game game, Exception e)
    {
        this(game, e.getMessage().substring(0, 200) + "...");
    }

    public IncorrectRateException(Game game, String rate)
    {
        super(game, "Incorrect rate detected: " + rate);
    }
}
