package dzevako.betcore.game;

/**
 * Абстрактная игра
 *
 * @author dzevako
 * @since Mar 29, 2016
 */
public abstract class AbstractGame implements Game
{
    protected String type;
    protected String title;
    protected String score;
    protected int time = 0;
    protected String context = "";

    @Override
    public boolean equals(Object otherGame)
    {
        if (null != otherGame && otherGame instanceof Game)
        {
            return this.getTitle().equals(((Game)otherGame).getTitle());
        }
        return false;
    }

    @Override
    public String getContext()
    {
        return context;
    }

    @Override
    public String getScore()
    {
        return score;
    }

    @Override
    public int getTime()
    {
        return time;
    }

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public String getType()
    {
        return type;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode() + title.length();
    }

    @Override
    public void setContext(String ctx)
    {
        context = ctx;
    }

    @Override
    public String toString()
    {
        return "Game[type='" + type + "', title='" + title + "', score=" + score.toString()
                + (time > 0 ? ", time=" + time + "мин" : "") + "]";
    }
}
