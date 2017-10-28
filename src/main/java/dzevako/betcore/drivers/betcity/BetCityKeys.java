package dzevako.betcore.drivers.betcity;

import com.google.common.collect.Lists;

import dzevako.betcore.bettypes.BetKeys;
import dzevako.betcore.common.base.Pair;
import dzevako.betcore.game.GameTitles;
import dzevako.betcore.utils.GameUtils;
import dzevako.betcore.web.driver.AbstractSiteKeys;
import dzevako.betcore.web.driver.SiteKeys;

/**
 * Необходимые ключи Бетсити
 * @author dzevako
 * @since Oct 11, 2015
 */
public class BetCityKeys extends AbstractSiteKeys implements SiteKeys
{
    @SuppressWarnings("unchecked")
    public BetCityKeys()
    {
        replaceFromScoreStrings = Lists.newArrayList(new Pair<>("Счёт:", ""), new Pair<>("перерыв", ""),
                new Pair<>(" ", ""));
        betTypeValueKeysOffsets.put(BetKeys.F1, 1);
        betTypeValueKeysOffsets.put(BetKeys.F2, 1);
        betTypeValueKeysOffsets.put(BetKeys.TM, 1);
        betTypeValueKeysOffsets.put(BetKeys.TB, 2);
    }

    @Override
    public int getGameForaColumn(String game, int team)
    {
        if (GameTitles.BASKETBALL.equals(game))
        {
            return team == 1 ? 11 : 13;
        }
        return 0;
    }

    @Override
    public String getGameKey(String type)
    {
        //TODO вынести в класс с ключами
        return "//div/b[contains(text(), '" + GameUtils.getGameTypeTitle(type)
                + ".')]/following-sibling::table[1]//td[2]";
    }

    @Override
    public String getGameScoreKey()
    {
        //TODO вынести в класс с ключами
        return "./div[@class='red' and not(contains(text(), 'online'))]";
    }

    @Override
    public int getGameTBColumn(String game)
    {
        if (GameTitles.BASKETBALL.equals(game))
        {
            return 16;
        }
        return 0;
    }

    @Override
    public String getGameTimeKey()
    {
        return null;
    }

    @Override
    public String getGameTitleElementKey()
    {
        return "./a";
    }

    @Override
    public int getGameTMColumn(String game)
    {
        if (GameTitles.BASKETBALL.equals(game))
        {
            return 15;
        }
        return 0;
    }

    @Override
    public int getGameWinColumn(String game, int team)
    {
        if (team == 1)
        {
            return 4;
        }
        else
        {
            return GameTitles.VOLLEYBALL.equals(game) ? 5 : 6;
        }
    }

    @Override
    public String getRateMainLineKey(String gameTitle, int column)
    {
        return getValueMainLineKey(gameTitle, column) + "/a";
    }

    @Override
    public String getValueMainLineKey(String gameTitle, int column)
    {
        return String.format(BCXpath.MAIN_LINE_COLUMN, column);
    }

    @Override
    public boolean isScoreContainsTime()
    {
        return true;
    }
}