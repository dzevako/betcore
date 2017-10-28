package dzevako.betcore.bettypes;

import java.util.Map;

import dzevako.betcore.common.Rates;

/**
 * Тип ставки Фора
 * @author dzevako
 * @since Jul 20, 2015
 */
public class Fora extends AbstractBetType
{
    private final static String F = "Ф";

    private final static String F1 = "Ф1";

    private final static String F2 = "Ф2";

    private final static String NEG = "-";

    public static Fora get(String data)
    {
        return new Fora(data);
    }

    private static Fora get0(Map<String, Double> types, boolean forGame)
    {
        for (String type : types.keySet())
        {
            if (type.contains(F) && type.contains(FOR_GAME) == forGame)
            {
                Fora f = new Fora(type);
                f.setRate(types.get(type));
                return f;
            }
        }
        return null;
    }

    /**
     * Найти фору на игру среди коллекции типов
     */
    public static Fora getFirstForSet(Map<String, Double> types)
    {
        return get0(types, false);
    }

    /**
     * Найти фору на игру среди коллекции типов
     */
    public static Fora getForGame(Map<String, Double> types)
    {
        return get0(types, true);
    }

    /**
     * Получить противоположную фору
     * @param f фора
     * @return противоположная фора
     */
    public static Fora getOpposite(Fora f)
    {
        if (null == f)
        {
            return null;
        }
        Fora opp = new Fora();
        opp.isPositive = !f.isPositive();
        opp.forGame = f.isForGame();
        opp.team = f.getTeam() == 1 ? 2 : 1;
        opp.value = -f.getValue();
        opp.setRate(Rates.getOpposite(f.getRate()));
        opp.setScorePoint(f.getScorePoint());
        return opp;
    }

    public static boolean isFora(String str)
    {
        return str.contains(F);
    }

    public static boolean isFora1(String str)
    {
        return str.contains(F1);
    }

    public static boolean isFora2(String str)
    {
        return str.contains(F2);
    }

    public static boolean isGameFora(String str)
    {
        return str.contains(F) && str.contains(FOR_GAME);
    }

    public static boolean isPosFora(String str)
    {
        return str.contains(F) && !str.contains(NEG);
    }

    private boolean isPositive;

    private double value;

    private Fora()
    {
        super("");
    }

    public Fora(String str)
    {
        super(str);
        check(str);
        isPositive = !str.contains("-");
        if (str.contains(F1))
        {
            team = 1;
        }
        if (str.contains(F2))
        {
            team = 2;
        }
        String[] parts = str.split("\\(");

        value = Double.parseDouble(parts[1].replace(")", "").replace(FOR_GAME, ""));
    }

    private void check(String str)
    {
        if (!str.contains(F) || !str.contains("(") || !str.contains(")"))
        {
            throw new RuntimeException("Type '" + str + "' is not Fora!");
        }
    }

    public double getAbsValue()
    {
        return Math.abs(getValue());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Fora getOpposite()
    {
        return getOpposite(this);
    }

    @Override
    public String getString()
    {
        return F + getTeam() + "(" + getValue() + ")" + (isForGame() ? FOR_GAME : "");
    }

    public double getValue()
    {
        return value;
    }

    public boolean isPositive()
    {
        return isPositive;
    }
}
