package dzevako.betcore.bettypes;

import java.util.Map;

import dzevako.betcore.common.Rates;

/**
 * Ставка тотал
 * @author dzevako
 * @since Aug 3, 2015
 */
public class Total extends AbstractBetType
{

    private final static String TB = "ТБ";

    private final static String TM = "ТМ";

    public static Total get(String data)
    {
        return new Total(data);
    }

    public static Total getForGame(Map<String, Double> types, boolean isTB)
    {
        if (types == null)
        {
            //Статистика может быть собрана не для всех сетов матча
            return null;
        }
        for (String type : types.keySet())
        {
            if ((type.contains(TB) || type.contains(TM)) && type.contains(FOR_GAME))
            {
                Total t = new Total(type);
                t.setRate(types.get(type));
                return (type.contains(TB) && isTB || type.contains(TM) && !isTB) ? t : Total.getOpposite(t);
            }
        }
        return null;
    }

    private static Total getForSet(Map<String, Double> types, boolean isTB, Double value)
    {
        if (types == null)
        {
            return null;
        }
        Total result = null;
        for (String type : types.keySet())
        {
            if ((type.contains(TB) || type.contains(TM)) && !type.contains(FOR_GAME))
            {
                Total t = new Total(type);
                if (null == value)
                {
                    if (result == null
                            || (t.getValue() < result.getValue() && isTB || t.getValue() > result.getValue() && !isTB))
                    {
                        result = t;
                        result.setRate(types.get(type));
                    }
                }
                else
                {
                    if (t.getValue() == value.doubleValue())
                    {
                        result = t;
                        result.setRate(types.get(type));
                        break;
                    }
                }
            }
        }
        if (null == result)
        {
            return null;
        }
        return (result.isTB && isTB || !result.isTB && !isTB) ? result : Total.getOpposite(result);
    }

    /**
     * Найти тотал меньше на сет среди коллекции типов
     */
    public static Total getMaxTMForSet(Map<String, Double> types)
    {
        return getForSet(types, false, null);
    }

    /**
     * Найти тотал больше на сет среди коллекции типов
     */
    public static Total getMinTBForSet(Map<String, Double> types)
    {
        return getForSet(types, true, null);
    }

    /**
     * Найти противоположную ставку для указанной
     */
    public static Total getOpposite(Total t)
    {
        if (null == t)
        {
            return null;
        }
        Total opp = new Total();
        opp.isTB = !t.isTB();
        opp.forGame = t.isForGame();
        opp.value = t.getValue();
        opp.setRate(Rates.getOpposite(t.getRate()));
        opp.setScorePoint(t.getScorePoint());
        return opp;
    }

    /**
     * Найти тотал больше на игру среди коллекции типов
     */
    public static Total getTBForGame(Map<String, Double> types)
    {
        return getForGame(types, true);
    }

    /**
     * Найти тотал меньше на игру среди коллекции типов
     */
    public static Total getTMForGame(Map<String, Double> types)
    {
        return getForGame(types, false);
    }

    private boolean isTB;

    private double value;

    private Total()
    {
        super("");
    }

    private Total(String str)
    {
        super(str);
        check(str);

        isTB = str.contains(TB);
        String[] parts = str.split("\\(");
        value = Double.parseDouble(parts[1].replace(")", "").replace(FOR_GAME, ""));
    }

    private void check(String str)
    {
        if (!str.contains(TB) && !str.contains(TM) || !str.contains("(") || !str.contains(")"))
        {
            throw new RuntimeException("Type '" + str + "' is not Total!");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Total getOpposite()
    {
        return getOpposite(this);
    }

    @Override
    public String getString()
    {
        return (isTB ? TB : TM) + "(" + value + ")" + (isForGame() ? FOR_GAME : "");
    }

    public double getValue()
    {
        return value;
    }

    public boolean isTB()
    {
        return isTB;
    }
}
