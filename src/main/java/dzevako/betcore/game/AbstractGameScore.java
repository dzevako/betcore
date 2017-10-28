package dzevako.betcore.game;

import dzevako.betcore.common.base.Pair;
import dzevako.betcore.exception.game.GameScoreUndefinedException;

/**
 * Объект, содержащий общий счет игры и счет партий
 * @author dzevako
 * @since Sep 14, 2014
 */
public abstract class AbstractGameScore implements GameScore
{
    /**
     * Проверка входных данных на пустоту
     */
    protected static void check(String data)
    {
        if (null == data || data.isEmpty())
        {
            throw new GameScoreUndefinedException();
        }
    }

    protected String string = "";
    protected int firstPoints = 0;
    protected int secondPoints = 0;

    //protected int set = 1;
    protected String[] prevSets = new String[] {};

    protected String[] parts = new String[] {};

    public AbstractGameScore(String data)
    {
        string = data.replace("Счёт:", "").replace("перерыв", "");//TODO убрать отсюда реплейсы
        String preparedData = prepareData();
        parts = preparedData.split(",");

        String[] curScore = getCurrentScore();
        firstPoints = Integer.parseInt(curScore[0]);
        secondPoints = Integer.parseInt(curScore[1]);

        initPreviousSets();
    }

    /**
     * Получить счет в текущем сете
     */
    protected String[] getCurrentScore()
    {
        return parts[parts.length - 1].split(":");
    }

    @Override
    public int getDiff()
    {
        return Math.abs(firstPoints - secondPoints);
    }

    @Override
    public int getDiff(int set)
    {
        if (set == getSet())
        {
            return getDiff();
        }
        int diff = 0;
        if (set > prevSets.length || set < 1)
        {
            return diff;
        }
        String sc = prevSets[set - 1];
        String[] points = sc.split(":");
        return Math.abs(Integer.parseInt(points[0]) - Integer.parseInt(points[1]));
    }

    @Override
    public int getFirstPoints()
    {
        return firstPoints;
    }

    @Override
    public int getMaxPoints()
    {
        return firstPoints > secondPoints ? firstPoints : secondPoints;
    }

    @Override
    public int getMinPoints()
    {
        return firstPoints > secondPoints ? secondPoints : firstPoints;
    }

    private Pair<Integer, Integer> getPoints(String strSet)
    {
        String[] points = strSet.split(":");
        return new Pair<>(Integer.parseInt(points[0]), Integer.parseInt(points[1]));
    }

    @Override
    public int getSecondPoints()
    {
        return secondPoints;
    }

    @Override
    public int getSet()
    {
        //return set;
        return parts.length - 1;
    }

    @Override
    public String getString()
    {
        return string;
    }

    @Override
    public int getTotal()
    {
        int set = getSet();
        int total = 0;
        while (set > 0)
        {
            total = total + getTotal(set);
            set--;
        }
        return total;
    }

    @Override
    public int getTotal(int set)
    {
        if (set > getSet())
        {
            return 0;
        }
        if (set == getSet())
        {
            return firstPoints + secondPoints;
        }
        Pair<Integer, Integer> points = getPoints(prevSets[set - 1]);
        return points.getFirst() + points.getSecond();
    }

    @Override
    public int getWinner(int set)
    {
        int winner = 0;
        if (set > prevSets.length || set < 1)
        {
            return winner;
        }
        String sc = prevSets[set - 1];
        String[] points = sc.split(":");
        int f = Integer.parseInt(points[0]);
        int s = Integer.parseInt(points[1]);
        return f == s ? 0 : f > s ? 1 : 2;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode() + firstPoints + secondPoints;
    }

    /**
     * Инициализировать предыдущие сеты
     */
    protected void initPreviousSets()
    {
        int set = getSet();
        if (set > 1)
        {
            prevSets = new String[set - 1];
            for (int i = 1; i < set; i++)
            {
                prevSets[i - 1] = parts[i];
            }
        }
    }

    /**
    * Подготовка данных для обработки: удаление всех скобок
    */
    protected String prepareData()
    {
        return string.replace("(", ",").replace(")", "").replace(" ", "");
    }
}
