package dzevako.betcore.bettypes;

import dzevako.betcore.game.Game;
import dzevako.betcore.web.driver.SiteKeys;

/**
 * Ключи ставок для игрока
 * @author dzevako
 * @since Aug 23, 2015
 */
public class BetKeys
{
    public static final String WIN1 = "1";
    public static final String WIN2 = "2";
    public static final String F = "Ф";
    public static final String F1 = F + WIN1;
    public static final String F2 = F + WIN2;
    public static final String TB = "ТБ";
    public static final String TM = "ТМ";
    public static final String X = "X";

    /**
     * Получение обозначения форы с командой
     */
    public static String fora(int team)
    {
        return team == 1 ? F1 : F2;
    }

    private SiteKeys keys;

    public BetKeys(SiteKeys keys)
    {
        this.keys = keys;
    }

    public BetKey getGameFora(Game game, int team)
    {
        String info = team == 1 ? F1 : F2;
        return getValueKeyForMainLine(game.getTitle(), keys.getGameForaColumn(game.getType(), team), info);
    }

    public BetKey getGameTB(Game game)
    {
        return getValueKeyForMainLine(game.getTitle(), keys.getGameTBColumn(game.getType()), TB);
    }

    public BetKey getGameTM(Game game)
    {
        return getValueKeyForMainLine(game.getTitle(), keys.getGameTMColumn(game.getType()), TM);
    }

    public BetKey getGameWin(Game game, int team)
    {
        if (team != 1 && team != 2)
        {
            return null;
        }
        return team == 1 ? getValueKeyForMainLine(game.getTitle(), keys.getGameWinColumn(game.getType(), 1), WIN1)
                : getValueKeyForMainLine(game.getTitle(), keys.getGameWinColumn(game.getType(), 2), WIN2);
    }

    protected String getRateFromMainLine(String gameTitle, int column)
    {
        return keys.getRateMainLineKey(gameTitle, column);
    }

    protected String getValueFromMainLine(String gameTitle, int column)
    {
        return keys.getValueMainLineKey(gameTitle, column);
    }

    private BetKey getValueKeyForMainLine(String gameTitle, int column, String info)
    {
        String valueKey = "";
        Integer offset = keys.getBetTypeValueKeysOffsets().get(info);
        if (null != offset)
        {
            valueKey = getValueFromMainLine(gameTitle, column - offset);
        }
        return new BetKeyImpl(getRateFromMainLine(gameTitle, column), valueKey, info, true);
    }
}