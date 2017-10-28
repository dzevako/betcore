package dzevako.betcore.calc;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Общая функциональность калькуляторов для расчета ставки
 *
 * @author dzevako
 * @since Jan 3, 2016
 */
public class AbstractBetValueCalculator implements BetValueCalculator
{
    public static final int MIN_VALUE = 50;
    public static final int MIN_ALIGN_VALUE = 300;
    public static final int MIN_STEP = 50;

    protected final Map<Integer, Integer> values = Maps.newLinkedHashMap();
    protected int value = MIN_VALUE;
    protected boolean isAlignScore = true;
    protected int minAlignValue = MIN_ALIGN_VALUE;

    public AbstractBetValueCalculator()
    {
    }

    public AbstractBetValueCalculator(int minValue)
    {
        this.value = minValue;
    }

    @Override
    public int getCurrentValue()
    {
        return value;
    }

    @Override
    public int getValue(int score)
    {
        int newValue = value;
        for (int v : values.keySet())
        {
            if (v <= newValue)
            {
                continue;
            }
            if (score / v >= values.get(v))
            {
                newValue = v;
            }
            else
            {
                break;
            }
        }
        value = value < newValue ? newValue : value;
        if (isAlignScoreScore() && value >= minAlignValue)
        {
            int appendix = score % MIN_VALUE;
            return value + appendix - (appendix > MIN_STEP ? 100 : 0);
        }
        return value;
    }

    public boolean isAlignScoreScore()
    {
        return isAlignScore;
    }

    /**
     * Установить выравнивание банка
     */
    public void setAlignScore(boolean isAlignScore)
    {
        this.isAlignScore = isAlignScore;
    }

    /**
     * Установить минимальное значение ставки, при котором банк будет выравниваться
     */
    public void setMinAlignValue(int minAlignScore)
    {
        this.minAlignValue = minAlignScore;
    }

    @Override
    public void setMinValue(int minValue)
    {
        value = minValue < MIN_VALUE ? MIN_VALUE : minValue - minValue % MIN_STEP;
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "[minValue = " + value + ", isAlignScore = " + isAlignScore + "]";
    }
}