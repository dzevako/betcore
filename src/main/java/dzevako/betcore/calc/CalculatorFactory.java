package dzevako.betcore.calc;

/**
 * Фабрика калькуляторов
 *
 * @author dzevako
 * @since May 12, 2016
 */
public class CalculatorFactory
{
    public static BetValueCalculator get(String calcCode)
    {
        BetValueCalculator calculator;

        String[] parts = calcCode.split(":");

        int num = Integer.parseInt(parts[0]);
        switch (num)
        {
        case 1:
            calculator = new BetValueCalculator1();
            break;
        case 2:
            calculator = new BetValueCalculator2();
            break;
        case 3:
            calculator = new BetValueCalculator3();
            break;
        default:
            return null;
        }

        calculator.setMinValue(Integer.parseInt(parts[1]));

        return calculator;
    }
}
