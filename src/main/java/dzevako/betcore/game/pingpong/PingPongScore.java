package dzevako.betcore.game.pingpong;

import dzevako.betcore.game.volleyball.VolleyballScore;
import dzevako.betcore.utils.GameUtils;

/**
 * Объект счета для настольного тенниса
 * @author dzevako
 * @since Mar 15, 2015
 */
public class PingPongScore extends VolleyballScore
{
    public PingPongScore(String data)
    {
        super(data);
    }

    @Override
    public int getSetPoints()
    {
        return GameUtils.PINGPONG_SET_POINTS;
    }
}
