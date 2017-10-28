package dzevako.betcore.bettypes;

import java.util.Map;

import dzevako.betcore.common.Rates;

/**
 * Тип ставки на победу
 * @author dzevako
 * @since Jul 29, 2015
 */
public class Win extends AbstractBetType
{

    private final static String T1 = "1";

    private final static String T2 = "2";

    private static Win get(Map<String, Double> types, String str1, String str2)
    {
        if (types == null)
        {
            return null;
        }
        for (String type : types.keySet())
        {
            if (type.equals(str1) || type.equals(str2))
            {
                Win f = new Win(type);
                f.setRate(types.get(type));
                return f;
            }
        }
        return null;
    }

    public static Win get(String data)
    {
        return new Win(data);
    }

    /**
     * Найти победу на игру среди коллекции типов
     */
    public static Win getForGame(Map<String, Double> types)
    {
        return get(types, T1 + FOR_GAME, T2 + FOR_GAME);
    }

    /**
     * Найти победу на сет среди коллекции типов
     */
    public static Win getForSet(Map<String, Double> types)
    {
        return get(types, T1, T2);
    }

    /**
     * Получить противоположную победу(на др. команду)
     * @param w победа
     * @return противоположная победа
     */
    public static Win getOpposite(Win w)
    {
        if (null == w || 1.0 == w.getRate())
        {
            return null;
        }
        Win opp = new Win();
        opp.forGame = w.isForGame();
        opp.team = w.getTeam() == 1 ? 2 : 1;
        opp.setRate(Rates.getOpposite(w.getRate()));
        opp.setScorePoint(w.getScorePoint());
        return opp;
    }

    // TODO Добавить ничью
    //private final static String X = "X";

    private Win()
    {
        super("");
    }

    private Win(String str)
    {
        super(str);
        check(str);
        team = str.contains(T1) ? 1 : 2;
    }

    private void check(String str)
    {
        if (str.length() > 2 || !str.contains(T1) && !str.contains(T2))
        {
            throw new RuntimeException("Type '" + str + "' is not Win Type!");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Win getOpposite()
    {
        return getOpposite(this);
    }

    @Override
    public String getString()
    {
        return getTeam() + (isForGame() ? FOR_GAME : "");
    }
}