package dzevako.betcore.web.driver;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import com.google.common.collect.Sets;

import dzevako.betcore.bettypes.AbstractBetType;
import dzevako.betcore.bettypes.BetKey;
import dzevako.betcore.bettypes.BetType;
import dzevako.betcore.bettypes.BetTypeExtractor;
import dzevako.betcore.common.base.Timer;
import dzevako.betcore.game.Game;
import dzevako.betcore.logger.Logger;
import dzevako.betcore.utils.StringUtils;

/**
 * Веб драйвер для игры против абстрактного букмекера
 *
 * @author dzevako
 * @since Feb 25, 2016
 */
public abstract class AbstractBKWebDriver extends AbstractWebDriver implements BKWebDriver
{
    protected String login;
    protected String password;

    public AbstractBKWebDriver(String game, Logger logger, SearchContext driver, SiteKeys keys, String startPage)
    {
        super(game, logger, driver, keys, startPage);
    }

    //@formatter:off
    public AbstractBKWebDriver(
            String game, 
            Logger logger, 
            SearchContext driver, 
            SiteKeys keys, 
            String startPage, 
            String login, 
            String password)
    {
        this(game, logger, driver, keys, startPage);
        this.login = login;
        this.password = password;      
    }

    public AbstractBKWebDriver(
            String game, 
            Logger logger, 
            SiteKeys keys, 
            String startPage,
            String browser)
    {
        super(game, logger, keys, startPage, browser);
    }
  //@formatter:on

    public AbstractBKWebDriver(String game, Logger logger, SiteKeys keys, String startPage, String login,
            String password, String browser)
    {
        this(game, logger, keys, startPage, browser);
        this.login = login;
        this.password = password;
    }

    /**
     * Возвращает список доступных игр из Лайва
     */
    protected Set<Game> findGames()
    {
        Set<Game> games = Sets.newHashSet();
        StringBuilder gameList = new StringBuilder("Next games found: \n");

        log("Look up for " + gameType + "...");
        List<WebElement> elements = findAll(keys.getGameKey(gameType));
        if (null == elements)
        {
            waitTime(3, false);
            elements = findAll(keys.getGameKey(gameType));
        }
        for (WebElement element : elements)
        {
            try
            {
                Game game = gameFactory.create(element, gameType);
                games.add(game);
                gameList.append(game.toString() + '\n');
            }
            catch (StaleElementReferenceException e)
            {
                log("WARNING: StaleElementReferenceException for game " + elements.indexOf(element)
                        + ". It was striked from game-list.");
            }
            catch (Exception e)
            {
                log("WARNING: Game " + elements.indexOf(element)
                        + " has incorrect data. It was striked from game-list:\n" + StringUtils.getMessage(e));
            }
        }

        if (games.isEmpty())
        {
            log("No games found.");
        }
        else
        {
            log(gameList.toString());
        }
        return games;
    }

    @Override
    public BetType getBetType(BetKey key)
    {
        if (null == key)
        {
            log("betKey == null");
            return null;
        }
        log("BetType rate key = " + key.getRateKey());
        Double rate = getRate(key);
        log("rate = " + rate);
        if (null == rate)
        {
            log("BetType '" + key.getInfo() + "' is not found on game field.");
            return null;
        }
        String data = key.getInfo();
        log("BetType value key = " + key.getValueKey());
        //@formatter:off
        String value = getTextFast(key.getValueKey())
                .replace("+", "")
                .replace(",", ".");
        //@formatter:on
        log("value = " + value);
        if (!value.isEmpty())
        {
            data = data + "(" + value + ")";
        }
        data = data + (key.isForGame() ? AbstractBetType.FOR_GAME : "");
        BetType betType = BetTypeExtractor.get(data);
        betType.setRate(rate);
        log("BetType found: " + betType.toString());
        return betType;
    }

    /**
     * Получить список игр.
     * Дается 3 попытки.
     */
    @Override
    public Set<Game> getGamesSafe()
    {
        //первая попытка
        Set<Game> games = findGames();

        //вторая попытка
        if (games.isEmpty())
        {
            Timer.waitSec(5);
            games = findGames();
        }

        //третья попытка
        if (games.isEmpty())
        {
            Timer.waitSec(15);
            games = findGames();
        }

        return games;
    }

    /**
     * Получить коэффициент заданного типа ставки, если указанного типа нет - возвращает null
     */
    @Override
    public Double getRate(BetKey key)
    {
        String strRate = getTextFast(key.getRateKey());
        return strRate.isEmpty() ? null : Double.parseDouble(strRate.replace(",", "."));
    }

    @Override
    public void openGame(String title)
    {
        // do nothing
        // TODO подумать, как убрать
    }

    @Override
    public void refreshGame()
    {
        // do nothing
    }

    @Override
    public void refreshGames()
    {
        // do nothing
    }
}
