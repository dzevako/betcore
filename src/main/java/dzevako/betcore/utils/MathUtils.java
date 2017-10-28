package dzevako.betcore.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Утилитарный класс с набором математических функций
 *
 * @author dzevako
 * @since Dec 12, 2015
 */
public class MathUtils
{
    public static double round(double value, int places)
    {
        if (places < 0 || Double.isInfinite(value) || Double.isNaN(value))
        {
            throw new IllegalArgumentException(
                    "Illegal arguments detected: value = '" + value + "' places = '" + places + "'");
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double round3(double value)
    {
        return round(value, 3);
    }
}
