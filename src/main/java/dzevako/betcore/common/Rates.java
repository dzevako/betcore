package dzevako.betcore.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

import com.google.common.collect.Maps;

/**
 * Список возможных коэффициентов и соответствующих им противоположных коэффициентов
 * @author dzevako
 * @since Apr 19, 2015
 */
public class Rates
{
    static Map<Double, Double> rates1 = Maps.newLinkedHashMap();

    static Map<Double, Double> rates2 = Maps.newLinkedHashMap();

    static
    {
        File f = new File(System.getenv("HOME") + "/git/dz/betcore/src/main/resources/dzevako/betcore/common/RatesMap");
        System.out.println(f.exists());
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(f);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        while (scanner.hasNext())
        {
            String line = scanner.nextLine();
            String[] strRates = line.split(" ");
            rates1.put(Double.parseDouble(strRates[0]), Double.parseDouble(strRates[1]));
            rates2.put(Double.parseDouble(strRates[1]), Double.parseDouble(strRates[0]));
        }
    }

    /**
     * Подборка подходящего коэффициента
     */
    private static Double fromRates2(Double rate)
    {
        Double r = rates2.get(rate);
        if (null != r)
        {
            return r;
        }
        Double nearRate = 100.0;
        for (Double key : rates2.keySet())
        {
            if (Math.abs(key - rate) + rate < nearRate)
            {
                nearRate = key;
            }
            else
            {
                return rates2.get(nearRate);
            }
        }
        return null;
    }

    /**
     * Возвращает противоположный коэффициент
     */
    public static Double getOpposite(Double rate)
    {
        if (null == rate || rate == 1.0)
        {
            return null;
        }
        if (rate > 1.0 && rate < 2.01)
        {
            return rates1.get(rate);
        }
        else
        {
            return fromRates2(rate);
        }
    }
}
