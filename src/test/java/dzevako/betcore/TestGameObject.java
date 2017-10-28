package dzevako.betcore;

import dzevako.betcore.game.Game;

/**
 * Объект игры для тестов
 * @author dzevako
 * @since Oct 16, 2015
 */
public class TestGameObject implements Game
{
    private String title;
    private String type;
    private String score;
    private int time;

    public TestGameObject(String title, String score, int time)
    {
        this(title, null, score, time);
    }

    public TestGameObject(String title, String type, String score, int time)
    {
        this.title = title;
        this.type = type;
        this.score = score;
        this.time = time;
    }

    // TODO добавить в тесты контекст
    @Override
    public String getContext()
    {
        return "";
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
    public void setContext(String ctx)
    {
    }
}
