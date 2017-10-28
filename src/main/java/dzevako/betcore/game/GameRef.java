package dzevako.betcore.game;

import dzevako.betcore.common.base.HasTitle;

/**
 * Ссылка на игру в списке игр
 * @author dzevako
 * @since May 21, 2015
 */
public class GameRef implements HasTitle
{
    private String title;
    private String type;
    private String href;
    private String score;

    public GameRef(String title, String type, String href, String score)
    {
        this.title = title;
        this.type = type;
        //this.href = Configuration.BASE_URL + href;
        this.score = score.replace("Счёт:", "").replace("перерыв", "").replace(" ", "");
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof GameRef)
        {
            return getTitle().equals(((GameRef)obj).getTitle());
        }
        return false;
    }

    public String getHref()
    {
        return href;
    }

    public String getScore()
    {
        return score;
    }

    @Override
    public String getTitle()
    {
        return title;
    }

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
    public String toString()
    {
        return "GameRef[title=" + title + "; href=" + href + "; score=" + score + "]";
    }
}
