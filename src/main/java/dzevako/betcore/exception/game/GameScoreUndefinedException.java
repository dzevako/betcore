package dzevako.betcore.exception.game;

/**
 * Исключение выбрасывается когда пропадает счет игры, либо когда игра только начинается, а счета еще нет
 * @author dzevako
 * @since Aug 30, 2015
 */
public class GameScoreUndefinedException extends GamePlayException
{
    private static final long serialVersionUID = 1L;

    public GameScoreUndefinedException()
    {
        super(null, "Game score is undefined.");
    }
}
