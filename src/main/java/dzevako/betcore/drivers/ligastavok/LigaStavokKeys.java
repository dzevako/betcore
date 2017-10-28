package dzevako.betcore.drivers.ligastavok;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.common.collect.Lists;

import dzevako.betcore.bettypes.BetKeys;
import dzevako.betcore.common.base.Pair;
import dzevako.betcore.game.GameTitles;
import dzevako.betcore.utils.GameUtils;
import dzevako.betcore.web.driver.AbstractSiteKeys;
import dzevako.betcore.web.driver.SiteKeys;

/**
 * Необходимые ключи Лиги Ставок
 * @author dzevako
 * @since Nov 14, 2015
 */
public class LigaStavokKeys extends AbstractSiteKeys implements SiteKeys
{
    private final static String TIMEOUT = "Перерыв ";
    private final static String MINUTE = "мин";

    public static void main(String[] args)
    {
        String str1 = "4-я четв. 40 мин. ТВ: Матч! Наш Спорт/Единая Лига ВТБ";
        String str2 = "4-я четв. 40 мин. Период - 10 мин. Овертайм - 5 мин.";
        String str3 = "4-я четв. 34 мин. Период - 10 мин. Овертайм - 5 мин";
        String str4 = "Перерыв 3-й четверти. 10 мин ";
        String str5 = "Перерыв 2-й четв. 20 мин. Период - 10 мин. Овертайм - 5 мин.";
        String str6 = "1-я четв. 6 мин. Период - 10 мин. Овертайм - 5 мин.";
        String str7 = "2-я четверть. 06 мин.";

    }

    @SuppressWarnings("unchecked")
    public LigaStavokKeys()
    {
        replaceFromScoreStrings = Lists.newArrayList(new Pair<>(" ", ""));
        betTypeValueKeysOffsets.put(BetKeys.F1, 1);
        betTypeValueKeysOffsets.put(BetKeys.F2, 1);
        betTypeValueKeysOffsets.put(BetKeys.TM, 1);
        betTypeValueKeysOffsets.put(BetKeys.TB, 2);
    }

    @Override
    public int getGameForaColumn(String game, int team)
    {
        return team == 1 ? 7 : 9;
    }

    @Override
    public String getGameKey(String game)
    {
        return String.format(LSXpath.GAME_KEY_PATTERN, GameUtils.getGameTypeTitle(game));
    }

    @Override
    public String getGameScore(WebElement container)
    {
        StringBuilder text = new StringBuilder();
        //for (WebElement el : getScoreElement(gameElement).findElements(By.xpath("./span")))
        for (WebElement el : container.findElements(By.xpath("./span")))
        {
            if (text.length() == 0)
            {
                text.append(el.getText()).append("(");
            }
            else
            {
                text.append(el.getText()).append(",");
            }
        }
        return text.append(",").toString().replace(",,", ")");
    }

    @Override
    public String getGameScoreKey()
    {
        return LSXpath.GAME_SCORE;
    }

    @Override
    public int getGameTBColumn(String game)
    {
        return 12;
    }

    @Override
    public int getGameTime(WebElement container)
    {
        return container == null ? 0 : getTimeFromString(container.getText().trim());
    }

    @Override
    public String getGameTimeKey()
    {
        return LSXpath.GAME_TIME;
    }

    @Override
    public String getGameTitle(WebElement titleElement)
    {
        return super.getGameTitle(titleElement).replace("\n", " - ");
    }

    //TODO Переделать! Сделано только для баскетбола.
    @Override
    public String getGameTitleElementKey()
    {
        //return LSXpath.GAME_TITLE;
        return String.format(LSXpath.GAME_KEY_PATTERN, GameTitles.BASKETBALL) + LSXpath.GAME_TITLE;
    }

    @Override
    public int getGameTMColumn(String game)
    {
        return 11;
    }

    @Override
    public int getGameWinColumn(String game, int team)
    {
        return team == 1 ? 3 : 5;
    }

    @Override
    public String getRateMainLineKey(String gameTitle, int column)
    {
        return getValueMainLineKey(gameTitle, column);
    }

    @Override
    public int getTimeFromString(String timeText)
    {
        if (timeText.startsWith(TIMEOUT))
        {
            int l = TIMEOUT.length();
            return Integer.parseInt(timeText.substring(l, l + 1)) * 10;
        }
        String part = timeText.split(MINUTE)[0];
        int partLen = part.length();
        return Integer.parseInt(part.substring(partLen - 3, partLen).replaceAll(" ", ""));
    }

    @Override
    public String getValueMainLineKey(String gameTitle, int column)
    {
        return String.format(LSXpath.MAIN_LINE_COLUMN, gameTitle.replace(" - ", ""), column);
    }
}
