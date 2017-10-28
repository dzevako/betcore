package dzevako.betcore.calc;

/**
 * Класс для расчета суммы ставки, в зависимости от условий
 * Ставим по 5% от банка. Сумма ставки не снижается.
 *
 * @author dzevako
 * @since Mar 12, 2016
 */
public class BetValueCalculator3 extends AbstractBetValueCalculator
{
    public BetValueCalculator3()
    {
        values.put(100, 20);
        values.put(150, 20);
        values.put(200, 20);
        values.put(250, 20);
        values.put(300, 20);
        values.put(400, 20);
        values.put(500, 20);
        values.put(600, 20);
        values.put(700, 20);
        values.put(800, 20);
        values.put(1000, 20);
        values.put(1200, 20);
        values.put(1500, 20);
        values.put(2000, 20);
        /*values.put(2500, 20);
        values.put(3000, 20);
        values.put(4000, 20);
        values.put(5000, 20);
        values.put(6000, 20);
        values.put(7000, 20);
        values.put(8000, 20);
        values.put(9000, 20);
        values.put(10000, 20);*/
    }

    public BetValueCalculator3(int minValue)
    {
        this();
        this.value = minValue;
    }
}
