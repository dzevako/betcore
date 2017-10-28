/**
 * 
 */
package dzevako.betcore.game.beachvolleyball;

import dzevako.betcore.game.volleyball.VolleyballScore;
import dzevako.betcore.utils.GameUtils;

/**
 * Объект счета для пляжного волейбола
 * @author dzevako
 * @since Mar 15, 2015
 */
public class BeachVolleyballScore extends VolleyballScore
{
    public BeachVolleyballScore(String data)
    {
        super(data);
    }

    @Override
    public int getSetPoints()
    {
        return getSet() >= GameUtils.VOLLEYBALL_LAST_SET ? GameUtils.BEACH_VOLLEYBALL_LAST_SET_POINTS
                : GameUtils.BEACH_VOLLEYBALL_SET_POINTS;
    }

}