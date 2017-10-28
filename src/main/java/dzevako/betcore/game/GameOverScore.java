package dzevako.betcore.game;

import java.util.List;

import com.google.common.collect.Lists;

import dzevako.betcore.common.base.Pair;

/**
 * Счет законченной игры
 * @author dzevako
 * @since Apr 1, 2015
 */
public abstract class GameOverScore
{
    protected final static String OT = "ОТ";

    protected int firstWins = 0;
    protected int secondWins = 0;
    protected int firstPoints = 0;
    protected int secondPoints = 0;
    protected String string = "";
    protected List<String> sets = Lists.newArrayList();

    public GameOverScore(GameScore score)
    {
        this(score.getString());
    }

    public GameOverScore(String score)
    {
        string = score.replace("Счёт:", "").replace("перерыв", "");
        String preparedData = getPreparedData();
        String[] parts = preparedData.split(",");
        //firstWins = Integer.parseInt(parts[0].substring(0, 1));
        //secondWins = Integer.parseInt(parts[0].substring(2, 3));
        for (int i = 1; i < parts.length; i++)
        {
            String set = parts[i];
            sets.add(set);
            String[] setScores = set.split(":");
            int f = Integer.parseInt(setScores[0]);
            int s = Integer.parseInt(setScores[1]);

            firstPoints = firstPoints + f;
            secondPoints = secondPoints + s;

            if (f == s)
            {
                continue;
            }
            if (f > s)
            {
                firstWins++;
            }
            else
            {
                secondWins++;
            }
        }
        if (sets.size() == 0)
        {
            throw new RuntimeException("No sets in score!");
        }
    }

    public int getDiff(int set)
    {
        if (set < 1 || set > sets.size())
        {
            throw new RuntimeException("Incorrect set! Set = " + set + "; size = " + sets.size());
        }
        Pair<Integer, Integer> points = getPoints(sets.get(set - 1));
        return Math.abs(points.getFirst() - points.getSecond());
    }

    public int getFirstPoints()
    {
        return firstPoints;
    }

    public int getFirstPoints(int set)
    {
        String[] setData = sets.get(set - 1).split(":");
        return Integer.parseInt(setData[0]);
    }

    public int getFirstWins()
    {
        return firstWins;
    }

    public int getGameTotal()
    {
        int total = 0;
        for (int set = 1; set <= sets.size(); set++)
        {
            total = total + getTotal(set);
        }
        return total;
    }

    protected Pair<Integer, Integer> getPoints(String strSet)
    {
        String[] points = strSet.split(":");
        return new Pair<>(Integer.parseInt(points[0]), Integer.parseInt(points[1]));
    }

    protected String getPreparedData()
    {
        return string.replace("(", ",").replace(")", "").replace(" ", "");
    }

    public int getSecondPoints()
    {
        return secondPoints;
    }

    public int getSecondPoints(int set)
    {
        String[] setData = sets.get(set - 1).split(":");
        return Integer.parseInt(setData[1]);
    }

    public int getSecondWins()
    {
        return secondWins;
    }

    public int getSetsCount()
    {
        return sets.size();
    }

    public String getString()
    {
        return string;
    }

    public int getTotal(int set)
    {
        if (set < 1 || set > sets.size())
        {
            throw new RuntimeException("Incorrect set!");
        }
        Pair<Integer, Integer> points = getPoints(sets.get(set - 1));
        return points.getFirst() + points.getSecond();
    }

    /**
     * Возвращает команду победителя игры
     */
    public abstract int getWinner();

    public int getWinner(int set)
    {
        Pair<Integer, Integer> points = getPoints(sets.get(set - 1));
        return points.getFirst() == points.getSecond() ? 0 : (points.getFirst() > points.getSecond() ? 1 : 2);
    }

    @Override
    public String toString()
    {
        return string;
    }
}
