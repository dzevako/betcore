package dzevako.betcore.calc;

/**
 * Класс для расчета суммы ставки, в зависимости от условий
 * @author dzevako
 * @since Sep 20, 2015
 */
public class BetValueCalculator2 extends AbstractBetValueCalculator
{
    public static void main(String[] args)
    {
        BetValueCalculator2 c = new BetValueCalculator2();
        c.setMinValue(200);
        System.out.println(c.getValue(800));
        System.out.println(c.getValue(1800));
        System.out.println(c.getValue(3800));
        System.out.println(c.getValue(1800));
        System.out.println(c.getValue(7800));
        System.out.println(c.getValue(23800));
        System.out.println(c.getValue(4800));
        System.out.println(c.getValue(54800));

        System.out.println(2355 - 2355 % 50);
    }

    public BetValueCalculator2()
    {
        values.put(100, 10);
        values.put(150, 10);
        values.put(200, 10);
        values.put(250, 10);
        values.put(300, 10);
        values.put(400, 10);
        values.put(500, 12);
        values.put(600, 13);
        values.put(700, 14);
        values.put(800, 14);
        values.put(1000, 15);
        values.put(1200, 16);
        values.put(1500, 17);
        values.put(2000, 20);
        //values.put(2500, 10);
        //values.put(3000, 10);
        //values.put(4000, 10);
        //values.put(5000, 10);
        //values.put(7000, 10);
        //values.put(8000, 10);
        //values.put(10000, 10);
    }
}
