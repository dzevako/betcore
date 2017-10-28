package dzevako.betcore.game.basketball;

import dzevako.betcore.game.AbstractGameScore;

/**
 * Объект, содержащий общий счет игры и счет партий для баскетбола
 * @author dzevako
 * @since Jun 12, 2015
 */
public class BasketballScore extends AbstractGameScore
{
    public static BasketballScore get(String data)
    {
        if (null == data || data.isEmpty())
        {
            return null;
        }
        //check(data);
        return new BasketballScore(data);
    }

    protected int time = 0;
    protected String mainScore = null;

    protected BasketballScore(String data)
    {
        super(data);
        init();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof BasketballScore)
        {
            //@formatter:off
            BasketballScore other = (BasketballScore)obj;
            return this.time == other.getTime() && 
                   this.mainScore == other.getMainScore() && 
                   this.firstPoints == other.getFirstPoints() && 
                   this.secondPoints == other.getSecondPoints();  
             //@formatter:on
        }
        else
        {
            return false;
        }
    }

    @Override
    protected String[] getCurrentScore()
    {
        return getSet() > 1 ? super.getCurrentScore() : getMainScore().split(":");
    }

    protected String getDelimiter()
    {
        return "мин";
    }

    public int getGameDiff()
    {
        return Math.abs(getGameFirstPoints() - getGameSecondPoints());
    }

    public int getGameFirstPoints()
    {
        String[] gameScore = mainScore.split(":");
        return Integer.parseInt(gameScore[0]);
    }

    public int getGameSecondPoints()
    {
        String[] gameScore = mainScore.split(":");
        return Integer.parseInt(gameScore[1]);
    }

    /**
     * Получить общий счет игры
     */
    public String getMainScore()
    {
        init();
        return mainScore;
    }

    @Override
    public int getSet()
    {
        int set = super.getSet();
        return set == 0 ? 1 : set;
    }

    @Override
    public int getTime()
    {
        return time;
    }

    /**
     * Инициализировать основной счет и время
     */
    private void init()
    {
        if (null != mainScore)
        {
            return;
        }
        String[] mainParts = parts[0].split(getDelimiter());
        if (mainParts.length == 2)
        {
            time = Integer.parseInt(mainParts[0]);
            mainScore = mainParts[1];
        }
        else
        {
            mainScore = mainParts[0];
        }
    }

    @Override
    protected String prepareData()
    {
        return super.prepareData().replace("OT", "").replace("ОТ", "");
    }
}