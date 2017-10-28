package dzevako.betcore.bettypes;

/**
 * Общая часть ставки
 * @author dzevako
 * @since Jul 29, 2015
 */
public abstract class AbstractBetType implements BetType
{
    public final static String FOR_GAME = "g";
    protected boolean forGame;
    protected double rate = 1.0;
    protected int team = 0;
    protected String scorePoint = "";

    //public abstract <B extends AbstractBetType> B getOpposite();

    public AbstractBetType(String str)
    {
        forGame = str.contains(FOR_GAME);
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof BetType && this.getString().equals(((BetType)obj).getString());
    }

    @Override
    public double getRate()
    {
        return rate;
    }

    @Override
    public String getScorePoint()
    {
        return scorePoint;
    }

    @Override
    public int getTeam()
    {
        return team;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode() + 1;
    }

    @Override
    public boolean isForGame()
    {
        return forGame;
    }

    @Override
    public void setRate(double rate)
    {
        this.rate = rate;
    }

    /**
     * Точка установки ставки 3=5:12
     */
    @Override
    public void setScorePoint(String score)
    {
        this.scorePoint = score;
    }

    @Override
    public String toString()
    {
        return getString() + " = " + getRate();
    }
}
