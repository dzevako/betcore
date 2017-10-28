package dzevako.betcore.calc;

/**
 * Класс для расчета суммы ставки, в зависимости от условий
 * @author dzevako
 * @since Sep 20, 2015
 */
public class BetValueCalculator1 extends AbstractBetValueCalculator
{
    public static void main(String[] args)
    {
        BetValueCalculator1 c = new BetValueCalculator1();
        c.setMinValue(100);
        System.out.println(c.getValue(800));
        System.out.println(c.getValue(1800));
        System.out.println(c.getValue(3800));
        System.out.println(c.getValue(1800));
        System.out.println(c.getValue(7800));
        System.out.println(c.getValue(23800));
        System.out.println(c.getValue(4800));
        System.out.println(c.getValue(54800));
    }

    public BetValueCalculator1()
    {
        values.put(200, 7);
        values.put(300, 8);
        values.put(400, 8);
        values.put(500, 8);
        values.put(700, 8);
        values.put(800, 9);
        values.put(1000, 9);
        values.put(1200, 9);
        values.put(1500, 10);
        values.put(2000, 10);
        values.put(2500, 10);
        //values.put(3000, 10);
        //values.put(4000, 10);
        //values.put(5000, 10);
        //values.put(7000, 10);
        //values.put(8000, 10);
        //values.put(10000, 10);
    }
}
