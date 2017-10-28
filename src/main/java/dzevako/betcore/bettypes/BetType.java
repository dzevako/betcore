package dzevako.betcore.bettypes;

/**
 * Интерфейс типа ставки
 * @author dzevako
 * @since Aug 13, 2015
 */
public interface BetType
{
    <B extends BetType> B getOpposite();

    public double getRate();

    public String getScorePoint();

    /**
     * Тип ставки
     */
    public String getString();

    public int getTeam();

    public boolean isForGame();

    public void setRate(double rate);

    /**
     * Точка установки ставки 3=5:12
     */
    void setScorePoint(String score);
}
