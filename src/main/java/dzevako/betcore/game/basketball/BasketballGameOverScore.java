/**
 * 
 */
package dzevako.betcore.game.basketball;

import dzevako.betcore.common.base.Pair;
import dzevako.betcore.game.GameOverScore;

/**
 * Счет законченной игры для Баскетбола
 * @author dzevako
 * @since Jul 18, 2015
 */
public class BasketballGameOverScore extends GameOverScore
{
    String overTime;

    public BasketballGameOverScore(String score)
    {
        super(score);
        if (sets.size() == 5)
        {
            overTime = sets.get(4);
        }
        /*if (sets.size() < 4)
        {
            throw new RuntimeException("Count of sets = " + sets.size() + ", but must be 4!");
        }*/
    }

    @Override
    protected String getPreparedData()
    {
        return super.getPreparedData().replace(",ОТ", "").replace(",2ОТ", "").replace(",OT", "");
    }

    /**
     * Возвращает номер команды-победителя игры
     */
    @Override
    public int getWinner()
    {
        if (overTime == null)
        {
            return firstPoints > secondPoints ? 1 : 2;
        }
        else
        {
            Pair<Integer, Integer> ot = getPoints(overTime);
            return ot.getFirst() > ot.getSecond() ? 1 : 2;
        }
    }
}
