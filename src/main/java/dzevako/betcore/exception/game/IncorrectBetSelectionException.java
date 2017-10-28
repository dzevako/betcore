package dzevako.betcore.exception.game;

import dzevako.betcore.game.Game;

/**
 * Ошибка выбора ставки
 * @author dzevako
 * @since Sep 29, 2014
 */
public class IncorrectBetSelectionException extends GamePlayException
{
    private static final long serialVersionUID = 1L;

    public IncorrectBetSelectionException(Game game)
    {
        this(game, "");
    }

    public IncorrectBetSelectionException(Game game, String message)
    {
        super(game, "Incorrect bet selected. " + message);
    }

    public IncorrectBetSelectionException(Game game, String expected, String actual)
    {
        this(game, "Expected: '" + expected + "', actual: '" + actual + "'.");
    }
}
