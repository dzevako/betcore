package dzevako.betcore.bet;

import dzevako.betcore.bettypes.BetType;

/**
 *  Реализация ставки
 */
public class BetImpl implements Bet
{
    //private String title;
    private BetType type;
    private int value;

    public BetImpl(BetType betType)
    {
        //this.title = title;
        this.type = betType;
    }

    public BetImpl(BetType betType, int value)
    {
        this(betType);
        this.value = value;
    }

    @Override
    public double getRate()
    {
        return type.getRate();
    }

    @Override
    public String getTitle()
    {
        //return title;
        return "";
    }

    @Override
    public BetType getType()
    {
        return type;
    }

    @Override
    public int getValue()
    {
        return value;
    }

    @Override
    public void setValue(int value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "Bet # " + getType().getString() + ": value = " + getValue() + ", rate = " + getRate();
    }
}
