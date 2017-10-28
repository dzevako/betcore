package dzevako.betcore.game;

/**
 * Простая реализация игры
 *
 * @author dzevako
 * @since Mar 28, 2016
 */
public class GameImpl extends AbstractGame
{
    public GameImpl(String type, String title, String score)
    {
        this.type = type;
        this.title = title;
        this.score = score;
    }

    public GameImpl(String type, String title, String score, int time)
    {
        this(type, title, score);
        this.time = time;
    }
}
