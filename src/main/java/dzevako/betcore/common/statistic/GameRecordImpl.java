package dzevako.betcore.common.statistic;

import java.util.List;

import com.google.common.collect.Lists;

import dzevako.betcore.game.Game;

/**
 * Подробная запись игры
 * Каждый интервал времени записывается счет и кэфы требуемых типов ставок
 * @author dzevako
 * @since Mar 9, 2015
 */
public class GameRecordImpl extends StatisticRecordBase implements GameRecord
{
    List<GameSnapshot> data = Lists.newArrayList();

    public GameRecordImpl(Game game, List<GameSnapshot> data)
    {
        super(game);
        this.data = data;
    }

    public GameRecordImpl(String title, String type, String score, List<GameSnapshot> data)
    {
        super(title, type, score);
        this.data = data;
    }

    public GameRecordImpl(String title, String type, String score, List<GameSnapshot> data, String date)
    {
        super(title, type, score, date);
        this.data = data;
    }

    @Override
    protected String getDataString()
    {
        StringBuilder sb = new StringBuilder();
        for (GameSnapshot snapshot : data)
        {
            if (!sb.toString().isEmpty())
            {
                sb.append('|');
            }
            sb.append(snapshot.toString());
        }
        return sb.toString();
    }
}
