package dzevako.betcore.utils;

import java.util.Map;

import com.google.common.collect.Maps;

import dzevako.betcore.game.Game;
import dzevako.betcore.game.GameCodes;
import dzevako.betcore.game.GameOverScore;
import dzevako.betcore.game.GameScore;
import dzevako.betcore.game.GameTitles;
import dzevako.betcore.game.GameType;
import dzevako.betcore.game.basketball.BasketballGameOverScore;
import dzevako.betcore.game.basketball.BasketballScore;
import dzevako.betcore.game.hockey.HockeyGameOverScore;
import dzevako.betcore.game.hockey.HockeyScore;
import dzevako.betcore.game.volleyball.VolleyballGameOverScore;
import dzevako.betcore.game.volleyball.VolleyballScore;

/**
 * Утилитарные методы и константы для игр
 * @author dzevako
 * @since Sep 19, 2014
 */
public class GameUtils
{
    public static final int VOLLEYBALL_SET_POINTS = 25;

    public static final int VOLLEYBALL_LAST_SET_POINTS = 15;
    public static final int VOLLEYBALL_LAST_SET = 5;

    public static final int BEACH_VOLLEYBALL_SET_POINTS = 15;
    public static final int BEACH_VOLLEYBALL_LAST_SET_POINTS = 7;
    public static Map<String, String> CODES_TO_TITLES = Maps.newHashMap();

    public static final int PINGPONG_SET_POINTS = 11;

    public static final String SET = "партия";

    public static final String QUARTER = "четверть";

    public static final String PERIOD = "период";

    static
    {
        CODES_TO_TITLES.put(GameCodes.BASKETBALL, GameTitles.BASKETBALL);
        CODES_TO_TITLES.put(GameCodes.VOLLEYBALL, GameTitles.VOLLEYBALL);
        CODES_TO_TITLES.put(GameCodes.HOCKEY, GameTitles.HOCKEY);
    }

    /**
     * Возвращает объект окончательного счета игры в зависимости от ее типа
     */
    public static GameOverScore createScore(String info, String score)
    {
        if (score.isEmpty())
        {
            return null;
        }
        if (info.toLowerCase().contains(GameTitles.VOLLEYBALL.toLowerCase()))
        {
            return new VolleyballGameOverScore(score);
        }
        if (info.toLowerCase().contains(GameTitles.BASKETBALL.toLowerCase()))
        {
            return new BasketballGameOverScore(score);
        }
        if (info.toLowerCase().contains(GameTitles.HOCKEY.toLowerCase()))
        {
            return new HockeyGameOverScore(score);
        }
        return new HockeyGameOverScore(score);
    }

    /**
     * Получить название игры по ее коду
     */
    public static String getGameTypeTitle(String code)
    {
        return CODES_TO_TITLES.get(code);
    }

    /**
     * Получить объект счета игры
     */
    public static GameScore getScore(Game game)
    {
        return getScore(game.getType(), game.getScore());
    }

    /**
     * Получить объект счета игры
     */
    public static GameScore getScore(String type, String data)
    {
        if (GameTitles.VOLLEYBALL.equals(type))
        {
            return VolleyballScore.get(data);
        }
        if (GameTitles.BASKETBALL.equals(type))
        {
            return BasketballScore.get(data);
        }
        if (GameTitles.HOCKEY.equals(type))
        {
            return HockeyScore.get(data);
        }
        throw new RuntimeException("Game type " + type + " is not exists!");
    }

    /**
     * Получить текущий сет игры
     */
    //возможно, type  можно будет убрать
    public static int getSet(String type, String data)
    {
        try
        {
            String[] parts = data.split("\\(");
            if (parts.length == 1)
            {
                return 1;
            }
            String[] sets = parts[1].split(",");
            return GameType.HOCKEY.getTitle().equals(type) ? sets.length + 1 : sets.length;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    public static String getSetName(String type)
    {
        if (null == type)
        {
            throw new RuntimeException("Set name is undefined for game type = null");
        }
        if (GameTitles.VOLLEYBALL.equals(type) || GameTitles.PINGPONG.equals(type)
                || GameType.BEACH_VOLLEYBALL.getTitle().equals(type))
        {
            return SET;
        }
        if (GameTitles.BASKETBALL.equals(type))
        {
            return QUARTER;
        }
        if (GameTitles.HOCKEY.equals(type))
        {
            return PERIOD;
        }
        throw new RuntimeException("Set name is undefined for game type = " + type);
    }

    /**
     * Получить краткую строку сета, типа 2=14:9 или 2(7)=12:15 
     * Учитывает специфику бетсити и Фонбет
     */
    public static String getSetString(Game game)
    {
        String[] parts = game.getScore().split("\\(");
        int set = 1;
        String setScore = "";
        if (parts.length == 2)
        {
            String[] sets = parts[1].split(",");
            set = sets.length;
            setScore = sets[set - 1].replace(")", "");
        }

        // У бетсити время внутри счета
        String[] mainParts = parts[0].split("мин");
        if (parts.length == 1)
        {
            setScore = mainParts[mainParts.length - 1];
        }

        String time = "";
        // В фонбете время отдельно
        if (game.getTime() > 0)
        {
            time = String.valueOf(game.getTime());
        }
        else
        {
            time = mainParts.length == 2 ? mainParts[0].trim() : "";
        }

        if (!time.isEmpty())
        {
            time = "(" + time + ")";
        }

        return set + time + "=" + setScore;
    }
}
