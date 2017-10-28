package dzevako.betcore.game.volleyball;

import dzevako.betcore.game.AbstractGameScore;
import dzevako.betcore.utils.GameUtils;

/**
 * Объект, содержащий общий счет игры и счет партий для волейбола
 * @author dzevako
 * @since Sep 19, 2014
 */
public class VolleyballScore extends AbstractGameScore
{
    public static VolleyballScore get(String data)
    {
        check(data);
        return new VolleyballScore(data);
    }

    private int firstWins = 0;

    private int secondWins = 0;

    protected VolleyballScore(String data)
    {
        super(data);
        firstWins = Integer.parseInt(parts[0].substring(0, 1));
        secondWins = Integer.parseInt(parts[0].substring(2, 3));
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof VolleyballScore)
        {
            //@formatter:off
            VolleyballScore other = (VolleyballScore)obj;
            return this.firstWins == other.getFirstWins() && 
                   this.secondWins == other.getSecondWins() && 
                   this.firstPoints == other.getFirstPoints() && 
                   this.secondPoints == other.getSecondPoints();  
             //@formatter:on
        }
        else
        {
            return false;
        }
    }

    /*@Override
    public int getDiff()
    {
        throw new RuntimeException("getDiff() is not supported by VolleballScore!");
    }*/

    /**
     * Получение номера текущего сета
     */
    protected int getCurrentSet()
    {
        return firstWins + secondWins + 1;
    }

    public int getFirstWins()
    {
        return firstWins;
    }

    public int getOverPoints()
    {
        return getSetPoints() - Math.max(getFirstPoints(), getSecondPoints());
    }

    public int getSecondWins()
    {
        return secondWins;
    }

    public int getSetPoints()
    {
        return getSet() >= GameUtils.VOLLEYBALL_LAST_SET ? GameUtils.VOLLEYBALL_LAST_SET_POINTS
                : GameUtils.VOLLEYBALL_SET_POINTS;
    }

    @Override
    public int getTime()
    {
        return 0;
    }
}
