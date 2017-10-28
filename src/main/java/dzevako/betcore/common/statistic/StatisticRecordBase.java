package dzevako.betcore.common.statistic;

import dzevako.betcore.common.base.Timer;
import dzevako.betcore.game.Game;
import dzevako.betcore.game.GameRef;

/**
 * Абстрактная реализация записи статистики
 * @author dzevako
 * @since Mar 9, 2015
 */
public abstract class StatisticRecordBase implements StatisticRecord
{
    private String date;
    private String title;
    private String type;
    private String score;

    public StatisticRecordBase(Game game)
    {
        this(game.getTitle(), game.getType(), game.getScore());
    }

    @Deprecated
    public StatisticRecordBase(GameRef game)
    {
        this.title = game.getTitle();
        this.score = game.getScore();
    }

    public StatisticRecordBase(String title, String type, String score)
    {
        this(title, type, score, Timer.date());
    }

    public StatisticRecordBase(String title, String type, String score, String date)
    {
        this.date = date;
        this.title = title;
        this.type = type;
        this.score = score;
    }

    /**
     * Получить строку с данными по ставкам
     */
    protected abstract String getDataString();

    public String getDate()
    {
        return date;
    }

    @Override
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
    public String toString()
    {
        //@formatter:off
        return DataCodes.DATE + "=" + date + "; " +
               DataCodes.TYPE + "=" + type + "; " +
               DataCodes.TITLE + "=" + title + "; " +
               DataCodes.SCORE + "=" + score + "; " +
               DataCodes.GAME_DATA + "={" + getDataString() + "};";
        //@formatter:on
    }
}