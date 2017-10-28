package dzevako.betcore.bettypes;

/**
 * Класс для извлечения типа ставки из формы оформления
 * @author dzevako
 * @since Aug 24, 2015
 */
public class BetTypeExtractor
{
    public static BetType get(String data)
    {
        if (data.startsWith(BetKeys.WIN1) || data.startsWith(BetKeys.WIN2) || data.startsWith(BetKeys.X))
        {
            return Win.get(data);
        }
        if (data.startsWith(BetKeys.TM) || data.startsWith(BetKeys.TB))
        {
            return Total.get(data);
        }
        if (data.startsWith(BetKeys.F1) || data.startsWith(BetKeys.F2))
        {
            return Fora.get(data);
        }
        return null;
    }
}
