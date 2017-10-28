/**
 * 
 */
package dzevako.betcore.game.volleyball;

import dzevako.betcore.game.GameOverScore;

/**
 * Счет законченной игры для Волейбола
 * @author dzevako
 * @since Jul 18, 2015
 */
public class VolleyballGameOverScore extends GameOverScore
{
    public VolleyballGameOverScore(String score)
    {
        super(score);
    }

    @Override
    public String getString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(firstWins).append(":").append(secondWins).append("(");
        for (String set : sets)
        {
            sb.append(set).append(",");
        }
        sb.setCharAt(sb.length() - 1, ')');
        return sb.toString();
    }

    /**
     * Возвращает номер команды-победителя игры
     */
    @Override
    public int getWinner()
    {
        return getFirstWins() > getSecondWins() ? 1 : 2;
    }
}
