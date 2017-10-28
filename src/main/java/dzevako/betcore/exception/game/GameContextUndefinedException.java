package dzevako.betcore.exception.game;

/**
 * Исключение выбрасывается когда нет возможности получить контекст игры (Например: Баскетбол.НБА)
 * @author dzevako
 * @since Aug 30, 2015
 */
public class GameContextUndefinedException extends GamePlayException
{
    private static final long serialVersionUID = 1L;

    public GameContextUndefinedException()
    {
        super(null, "Game context is undefined.");
    }
}
