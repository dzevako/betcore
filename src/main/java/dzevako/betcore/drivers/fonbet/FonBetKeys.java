package dzevako.betcore.drivers.fonbet;

import com.google.common.collect.Lists;

import dzevako.betcore.bettypes.BetKeys;
import dzevako.betcore.common.base.Pair;
import dzevako.betcore.game.GameCodes;
import dzevako.betcore.web.driver.AbstractSiteKeys;
import dzevako.betcore.web.driver.SiteKeys;

/**
 * Необходимые ключи Фонбет
 * @author dzevako
 * @since Oct 11, 2015
 */
public class FonBetKeys extends AbstractSiteKeys implements SiteKeys
{
    public final static String FINISH_LABEL = "ИТОГ";

    @SuppressWarnings("unchecked")
    public FonBetKeys()
    {

        //@formatter:off
        replaceFromScoreStrings = 
        		Lists.newArrayList(new Pair<>(" Таймаут", ""), 
        		                   new Pair<>(" ИТОГ", FINISH_LABEL), 
        		                   new Pair<>(" итог", FINISH_LABEL), 
        		                   new Pair<>(" в перерыве", ""),
        		                   new Pair<>(" Матч приостановлен", ""), 
        		                   new Pair<>(" Матч не начался", ""), 
        		                   new Pair<>(" 2x20", ""), 
        		                   new Pair<>("OT:", "OT"), 
        		                   new Pair<>(" (", "("), 
        		                   new Pair<>("-", ":"), 
        		                   new Pair<>(" ", ","));

        betTypeValueKeysOffsets.put(BetKeys.F1, 1);
        betTypeValueKeysOffsets.put(BetKeys.F2, 1);
        betTypeValueKeysOffsets.put(BetKeys.TM, 2);
        betTypeValueKeysOffsets.put(BetKeys.TB, 1);
        //@formatter:on
    }

    /*@Override
    public String getDefaultBrowser()
    {
        // TODO Auto-generated method stub
        return Constants.CHROME_BROWSER;
    }*/

    @Override
    public int getGameForaColumn(String gameType, int team)
    {
        return team == 1 ? 11 : 13;
    }

    @Override
    public String getGameKey(String game)
    {
        if (GameCodes.BASKETBALL.equals(game))
        {
            return FBXpath.BASKETBALL_EVENT;
        }
        if (GameCodes.VOLLEYBALL.equals(game))
        {
            return FBXpath.VOLLEYBALL_EVENT;
        }
        if (GameCodes.HOCKEY.equals(game))
        {
            return FBXpath.HOCKEY_EVENT;
        }
        return null;
        //return FBXpath.GAME_KEY_PATTERN;
    }

    @Override
    public String getGameScoreKey()
    {
        return FBXpath.GAME_SCORE;
    }

    @Override
    public int getGameTBColumn(String gameType)
    {
        return 15;
    }

    @Override
    public String getGameTimeKey()
    {
        return FBXpath.GAME_TIME;
    }

    @Override
    public String getGameTitleElementKey()
    {
        return FBXpath.GAME_TITLE;
    }

    @Override
    public int getGameTMColumn(String game)
    {
        return 16;
    }

    @Override
    public int getGameWinColumn(String game, int team)
    {
        return team == 1 ? 4 : 6;
    }

    @Override
    public String getRateMainLineKey(String gameTitle, int column)
    {
        return getValueMainLineKey(gameTitle, column);
    }

    @Override
    public String getValueMainLineKey(String gameTitle, int column)
    {
        return String.format(FBXpath.MAIN_LINE_COLUMN, gameTitle, column);
    }
}
