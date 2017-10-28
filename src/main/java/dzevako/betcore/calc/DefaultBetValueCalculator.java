package dzevako.betcore.calc;

/**
 * Калькулятор по умолчанию.
 * Возвращает всегда одно и тоже значение ставки
 *
 * @author dzevako
 * @since Sep 2, 2016
 */
public class DefaultBetValueCalculator extends AbstractBetValueCalculator
{
    public DefaultBetValueCalculator(int value)
    {
        super(value);
    }

    @Override
    public int getValue(int score)
    {
        return value;
    }
}
