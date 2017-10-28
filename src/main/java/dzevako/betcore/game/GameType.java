package dzevako.betcore.game;

import java.util.List;

import com.google.common.collect.Lists;

import dzevako.betcore.common.base.HasTitle;

/**
 * Тип игры. 
 * Содержит название игры (Волейбол, Настольный теннис и т.д.) и ключ(xpath)
 * @author dzevako
 * @since Mar 15, 2015
 */
public enum GameType implements HasTitle
{

    //@formatter:off
    VOLLEYBALL(GameTitles.VOLLEYBALL),
    BASKETBALL(GameTitles.BASKETBALL), 
    HOCKEY(GameTitles.HOCKEY),
    PINGPONG(GameTitles.PINGPONG),
    BEACH_VOLLEYBALL(GameTitles.BEACH_VOLLEYBALL);
    //@formatter:on

    private static List<GameType> GAME_TYPES_LIST = Lists.newArrayList(VOLLEYBALL, PINGPONG, BEACH_VOLLEYBALL,
            BASKETBALL, HOCKEY);

    public static GameType getType(String title)
    {
        for (GameType type : GAME_TYPES_LIST)
        {
            if (type.getTitle().equals(title))
            {
                return type;
            }
        }
        return null;
    }

    private String title;
    private String titleWithPoint;

    private GameType(String title)
    {
        this.title = title;
        this.titleWithPoint = title + ".";
    }

    public String getKey()
    {
        return "//div/b[contains(text(), '" + titleWithPoint + "')]/following-sibling::table[1]//td[2]";
    }

    @Override
    public String getTitle()
    {
        return title;
    }
}
