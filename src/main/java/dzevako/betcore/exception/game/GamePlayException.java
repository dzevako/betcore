package dzevako.betcore.exception.game;

import dzevako.betcore.game.Game;

/**
 * Исключение, возникающее во время игры
 * @author dzevako
 * @since Oct 21, 2014
 */
public abstract class GamePlayException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    protected Game game;

    protected GamePlayException(Game game, String message)
    {
        super((game == null ? "" : "Exception in game '" + game.getTitle() + "': ") + message);
        this.game = game;
    }
}
