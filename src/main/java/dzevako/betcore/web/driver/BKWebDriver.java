package dzevako.betcore.web.driver;

import java.util.Set;

import org.openqa.selenium.WebElement;

import dzevako.betcore.bet.Bet;
import dzevako.betcore.bettypes.BetKey;
import dzevako.betcore.bettypes.BetType;
import dzevako.betcore.common.base.Pair;
import dzevako.betcore.game.Game;

/**
 * Интерфейс веб драйвера для игры против букмекера
 *
 * @author dzevako
 * @since Feb 25, 2016
 */
//Разделить на 2 интерфейса
public interface BKWebDriver extends SiteWebDriver
{
    /**
     * Очистить корзину ставок
     */
    void clearBasket();

    /**
     * Оформить ставку
     */
    void confirm(Game game, Bet bet);

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
     * Получить контейнер главной линии ставок
     * @param gameTitle название игры
     */
    WebElement getMainLine(String gameTitle);

    /**
     * Получить коэф. по ключу
     */
    Double getRate(BetKey key);

    /**
     * Получить счет, вместе с текущими ставками
     */
    Pair<Double, Double> getScore();

    /**
     * Получить контейнер линии ставок для текущего сета
     * @param type тип игры
     */
    WebElement getSetLine(String type);

    /**
     * Перейти в Лайв
     */
    void goToLiveBets();

    /**
     * Войти в профиль
     */
    void logIn();

    /**
     * Выйти из профиля
     */
    void logOut();

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
     * Выбор типа игры в лайве (для фильтрации)
     */
    void selectGameType();
}
