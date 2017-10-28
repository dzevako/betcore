package dzevako.betcore.game;

/**
 * Заглушка для игры, содержащая только название
 * @author dzevako
 * @since Oct 25, 2015
 */
public class GameStub implements Game
{
    private String title;

    public GameStub(String title)
    {
        this.title = title;
    }

    @Override
    public String getContext()
    {
        return null;
    }

    @Override
    public String getScore()
    {
        return null;
    }

    @Override
    public int getTime()
    {
        return 0;
    }

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public String getType()
    {
        return null;
    }

    @Override
    public void setContext(String ctx)
    {
    }
}
