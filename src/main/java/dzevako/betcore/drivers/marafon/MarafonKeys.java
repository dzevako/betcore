package dzevako.betcore.drivers.marafon;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.common.collect.Lists;

import dzevako.betcore.bettypes.BetKeys;
import dzevako.betcore.common.Constants;
import dzevako.betcore.common.base.Pair;
import dzevako.betcore.web.driver.AbstractSiteKeys;
import dzevako.betcore.web.driver.SiteKeys;

/**
 * Необходимые ключи Марафон
 * @author dzevako
 * @since Feb 19, 2016
 */
public class MarafonKeys extends AbstractSiteKeys implements SiteKeys
{
    private static final String BREAK = "Пер.";
    private static final String GAME_TITLES_DELIMITER = " - ";

    @SuppressWarnings("unchecked")
    public MarafonKeys()
    {

        //@formatter:off
        replaceFromScoreStrings = 
                Lists.newArrayList(new Pair<>(" Таймаут", ""),
                                   new Pair<>(" ", ""));

        betTypeValueKeysOffsets.put(BetKeys.F1, 0);
        betTypeValueKeysOffsets.put(BetKeys.F2, 0);
        betTypeValueKeysOffsets.put(BetKeys.TM, 0);
        betTypeValueKeysOffsets.put(BetKeys.TB, 0);
        //@formatter:on
    }

    @Override
    public String getDefaultBrowser()
    {
        return Constants.CHROME_BROWSER;
    }

    @Override
    public int getGameForaColumn(String game, int team)
    {
        return team == 1 ? 5 : 6;
    }

    @Override
    public String getGameKey(String game)
    {
        return MFXpath.GAMES_KEY;
    }

    @Override
    public String getGameScoreKey()
    {
        return MFXpath.GAME_SCORE;
    }

    @Override
    public int getGameTBColumn(String game)
    {
        return 8;
    }

    @Override
    public String getGameTimeKey()
    {
        return MFXpath.GAME_TIME;
    }

    @Override
    public String getGameTitle(WebElement titleElement)
    {
        List<WebElement> elements = titleElement.findElements(By.xpath(MFXpath.GAME_TITLE_PART));
        return elements.get(0).getText() + GAME_TITLES_DELIMITER + elements.get(1).getText();
    }

    @Override
    public String getGameTitleElementKey()
    {
        return MFXpath.GAME_TITLE;
    }

    @Override
    public int getGameTMColumn(String game)
    {
        return 7;
    }

    @Override
    public int getGameWinColumn(String game, int team)
    {
        return team == 1 ? 3 : 4;
    }

    @Override
    public String getRateMainLineKey(String gameTitle, int column)
    {
        String[] teams = gameTitle.split(GAME_TITLES_DELIMITER);
        return String.format(MFXpath.MAIN_LINE_COLUMN_RATE, teams[0], teams[1], String.valueOf(column));
    }

    @Override
    public int getTimeFromString(String timeText)
    {
        if (timeText.contains(BREAK))
        {
            return 0;
        }
        return super.getTimeFromString(timeText);
    }

    @Override
    public String getValueMainLineKey(String gameTitle, int column)
    {
        String[] teams = gameTitle.split(GAME_TITLES_DELIMITER);
        return String.format(MFXpath.MAIN_LINE_COLUMN, teams[0], teams[1], String.valueOf(column));
    }
}
