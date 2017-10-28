package dzevako.betcore.web.driver;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.beust.jcommander.internal.Lists;
import com.google.common.collect.Maps;

import dzevako.betcore.common.Constants;
import dzevako.betcore.common.base.Pair;

/**
 * Общая часть ключей для сайтов
 *
 * @author dzevako
 * @since Nov 16, 2015
 */
public abstract class AbstractSiteKeys
{
    protected List<Pair<String, String>> replaceFromScoreStrings = Lists.newArrayList();
    protected List<Pair<String, String>> replaceFromScoreRegex = Lists.newArrayList();

    protected Map<String, Integer> betTypeValueKeysOffsets = Maps.newHashMap();

    public Map<String, Integer> getBetTypeValueKeysOffsets()
    {
        return betTypeValueKeysOffsets;
    }

    public String getDefaultBrowser()
    {
        return Constants.FIREFOX_BROWSER;
    }

    public String getGameScore(WebElement gameElement)
    {
        return replaceStrings(getScoreElement(gameElement).getText().trim());
    }

    protected abstract String getGameScoreKey();

    public int getGameTime(WebElement container)
    {
        WebElement timeElement = container.findElement(By.xpath(getGameTimeKey()));
        return timeElement == null ? 0 : getTimeFromString(timeElement.getText().trim());
    }

    protected abstract String getGameTimeKey();

    public String getGameTitle(WebElement titleElement)
    {
        if (null == titleElement)
        {
            throw new RuntimeException("Game has not title.");
        }
        return titleElement.getText().trim();
    }

    /**
     * Ключи к названиям игр для определенного типа игры.
     * Используется для двухступенчатого поиска игр(1-название, 2-счет, время)
     * Используется не во всех реализациях, поэтому тут заглушка
     */
    public String getGameTitlesKeys(String gameType)
    {
        return "";
    }

    protected WebElement getScoreElement(WebElement gameElement)
    {
        WebElement scoreElement = gameElement.findElement(By.xpath(getGameScoreKey()));
        if (null == scoreElement)
        {
            throw new RuntimeException("Game has not score.");
        }
        return scoreElement;
    }

    public int getTimeFromString(String timeText)
    {
        return Integer.parseInt(timeText.split(":")[0]);
    }

    public boolean isScoreContainsTime()
    {
        return false;
    }

    protected String replaceStrings(String score)
    {
        for (Pair<String, String> r : replaceFromScoreRegex)
        {
            score = score.replaceAll(r.getFirst(), r.getSecond());
        }
        for (Pair<String, String> r : replaceFromScoreStrings)
        {
            score = score.replace(r.getFirst(), r.getSecond());
        }
        return score;
    }
}
