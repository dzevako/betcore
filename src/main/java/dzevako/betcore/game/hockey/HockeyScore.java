package dzevako.betcore.game.hockey;

import dzevako.betcore.game.basketball.BasketballScore;

/**
 * Объект, содержащий общий счет игры и счет партий для хоккея
 * @author dzevako
 * @since Aug 13, 2015
 * 
 * *** 46 мин Счёт: 3:3 (0:1, 3:2) ***
 */
public class HockeyScore extends BasketballScore
{
    public static HockeyScore get(String data)
    {
        check(data);
        return new HockeyScore(data);
    }

    protected HockeyScore(String data)
    {
        super(data);
    }

    @Override
    protected String[] getCurrentScore()
    {
        int f = Integer.parseInt(getMainScore().split(":")[0]);
        int s = Integer.parseInt(getMainScore().split(":")[1]);
        for (int i = 1; i < parts.length; i++)
        {
            f = f - Integer.parseInt(parts[i].split(":")[0]);
            s = s - Integer.parseInt(parts[i].split(":")[1]);
        }
        return new String[] { String.valueOf(f), String.valueOf(s) };
    }

    @Override
    public int getSet()
    {
        return parts.length;
    }
}