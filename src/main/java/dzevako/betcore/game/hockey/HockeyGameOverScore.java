package dzevako.betcore.game.hockey;

import dzevako.betcore.common.base.Pair;
import dzevako.betcore.game.GameOverScore;

/**
 * Счет законченной игры для Хоккея
 * @author dzevako
 * @since Aug 13, 2015
 */
public class HockeyGameOverScore extends GameOverScore
{
    private final static String BULLITS = "бул.";
    String overTime;
    String bullits;

    public HockeyGameOverScore(String score)
    {
        super(score);
        if (sets.size() < 2)
        //if (sets.size() < 3)
        {
            System.out.println("Count of periods = " + sets.size() + ", but must be 3!");
            //throw new RuntimeException("Count of periods = " + sets.size() + ", but must be 3!");
        }
    }

    @Override
    protected String getPreparedData()
    {
        String data = super.getPreparedData();
        /*boolean bull = data.contains(BULLITS);
        data = data.replace(OVER_TIME, ",").replace(BULLITS, ",").replace(",,", ",");
        String parts = 
        if ()*/
        String[] parts = data.split(OT);
        String[] extraParts = parts[parts.length - 1].split(BULLITS);
        if (parts.length == 2)
        {
            //2:2 (0:2, 0:0, 2:0) (ОТ 0:0, бул. 1:0)           
            overTime = extraParts[0].replace("(", "").replace(")", "").replace(",", "");
        }

        if (extraParts.length == 2)
        {
            bullits = extraParts[1].replace("(", "").replace(")", "").replace(",", "");
        }

        String score = overTime == null ? extraParts[0] : parts[0];
        while (score.endsWith(")") || score.endsWith(","))
        {
            score = score.substring(0, score.length() - 1);
        }
        return score;
    }

    /**
     * Возвращает номер команды-победителя игры
     */
    @Override
    public int getWinner()
    {
        if (bullits != null)
        {
            Pair<Integer, Integer> bul = getPoints(bullits);
            return bul.getFirst() > bul.getSecond() ? 1 : 2;
        }
        if (overTime != null)
        {
            Pair<Integer, Integer> ot = getPoints(overTime);
            return ot.getFirst() > ot.getSecond() ? 1 : 2;
        }
        return firstPoints > secondPoints ? 1 : 2;
    }
}
