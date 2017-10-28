package dzevako.betcore.drivers.x1bet;

import org.openqa.selenium.WebElement;

import com.google.common.collect.Lists;

import dzevako.betcore.bettypes.BetKeys;
import dzevako.betcore.common.base.Pair;
import dzevako.betcore.web.driver.AbstractSiteKeys;
import dzevako.betcore.web.driver.SiteKeys;

/**
 * Необходимые ключи для 1x-bet
 * @author dzevako
 * @since Apr 1, 2016
 */
public class X1Keys extends AbstractSiteKeys implements SiteKeys
{
    public final static String WITH_OT = " (с ОТ)";

    @SuppressWarnings("unchecked")
    public X1Keys()
    {

        //@formatter:off
        replaceFromScoreStrings = 
                Lists.newArrayList(new Pair<>("-", ":"),
                                   new Pair<>("[", "("),
                                   new Pair<>("]", ")"),
                                   new Pair<>(" ", ""));
        
        replaceFromScoreRegex = 
                Lists.newArrayList(new Pair<>("\\(.+\\)", ""));

        betTypeValueKeysOffsets.put(BetKeys.F1, 1);
        betTypeValueKeysOffsets.put(BetKeys.F2, 1);
        betTypeValueKeysOffsets.put(BetKeys.TM, 2);
        betTypeValueKeysOffsets.put(BetKeys.TB, 1);
        //@formatter:on
    }

    @Override
    public int getGameForaColumn(String game, int team)
    {
        return 1 == team ? 4 : 6;
    }

    @Override
    public String getGameKey(String game)
    {
        return X1Xpath.GAMES_KEY;
    }

    @Override
    protected String getGameScoreKey()
    {
        return X1Xpath.GAME_SCORE;
    }

    @Override
    public int getGameTBColumn(String game)
    {
        return 8;
    }

    @Override
    public int getGameTime(WebElement element)
    {
        return element == null ? 0 : getTimeFromString(element.getText().trim());
    }

    @Override
    protected String getGameTimeKey()
    {
        return X1Xpath.GAME_SCORE;
    }

    @Override
    public String getGameTitle(WebElement titleElement)
    {
        return super.getGameTitle(titleElement).replace(WITH_OT, "");
    }

    @Override
    public String getGameTitleElementKey()
    {
        //return X1Xpath.GAME_TITLE;
        return X1Xpath.GAME_TITLE_KEY;
    }

    @Override
    public String getGameTitlesKeys(String gameType)
    {
        return String.format(X1Xpath.GAME_TITLES_KEY_FOR_TYPE, gameType);
    }

    @Override
    public int getGameTMColumn(String game)
    {
        // Это только для баскета
        return 9;
    }

    @Override
    public int getGameWinColumn(String game, int team)
    {
        return 1 == team ? 1 : 2;
    }

    @Override
    public String getRateMainLineKey(String gameTitle, int column)
    {
        return String.format(X1Xpath.MAIN_LINE_COLUMN_RATE, gameTitle, String.valueOf(column));
    }

    @Override
    protected WebElement getScoreElement(WebElement element)
    {
        if (null == element)
        {
            throw new RuntimeException("Game has not score.");
        }
        return element;
    }

    @Override
    public int getTimeFromString(String timeText)
    {
        return Integer.parseInt(timeText.split("\\(")[1].split(":")[0]);
    }

    @Override
    public String getValueMainLineKey(String gameTitle, int column)
    {
        return String.format(X1Xpath.MAIN_LINE_COLUMN, gameTitle, String.valueOf(column));
    }

    @Override
    public boolean isScoreContainsTime()
    {
        return true;
    }
}
