package dzevako.betcore.web.driver;

import java.util.Set;

import org.openqa.selenium.WebElement;

import dzevako.betcore.bet.Bet;
import dzevako.betcore.bettypes.BetKey;
import dzevako.betcore.bettypes.BetType;
import dzevako.betcore.game.Game;

/**
 * Интерфейс Веб Драйвера для сбора статистики
 * @author dzevako
 * @since Feb 19, 2016
 */
public interface StatisticWebDriver
{
    /**
     * Получить ставку по названию игры и ключу типа ставки
     */
    Bet getBet(String gameTitle, BetKey key);

    /**
     * Получить ставку в уже открытой игре
     */
    Bet getBetOnGame(Game game, BetKey key);

    /**
     * Получить тип ставки по ключу
     */
    BetType getBetType(BetKey key);

    /**
     * Получить контекст игры
     * @param title название игры
     */
    String getGameContext(String title);

    /**
     * Получить список игр безопасно
     */
    Set<Game> getGamesSafe();

    /**
     * Ключи сайта
     */
    SiteKeys getKeys();

    /**
     * Получить контейнер главной линии ставок
     * @param gameTitle название игры
     */
    WebElement getMainLine(String gameTitle);

    /**
     * Получить коэф. по ключу
     */
    Double getRate(BetKey key);

    /**
     * Получить контейнер линии ставок для текущего сета
     * @param type тип игры
     */
    WebElement getSetLine(String type);

    /**
     * Перейти по указанной ссылке
     */
    void goTo(String url);

    /**
     * Перейти в Лайв
     */
    void goToLiveBets();

    /**
     * Открыть указанную игру
     */
    void openGame(String title);

    /**
     * Обновить открытую игру
     */
    void refreshGame();

    /**
     * Обновить список игр
     */
    void refreshGames();

    /**
     * Обновить страницу источника
     */
    void refreshSite();

    /**
     * Выбор типа игры в лайве (для фильтрации)
     */
    void selectGameType();

    /**
     * Выключить драйвер
     */
    void shutdown();

    /**
     * Запустить драйвер
     */
    void start();

    /**
     * Ожидание, секунды
     */
    void waitTime(int i);
}
