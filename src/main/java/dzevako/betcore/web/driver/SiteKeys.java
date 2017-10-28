package dzevako.betcore.web.driver;

import java.util.Map;

import org.openqa.selenium.WebElement;

/**
 * Обязательные ключи, присутсвующие на любом сайте
 * @author dzevako
 * @since Oct 11, 2015
 */
public interface SiteKeys
{
    /**
     * Отступы колонок со значениями типов ставок от их коэффициентов в линии
     * Например, отступ для колонки с величиной тотала меньше 
     * для бетсити = 1, для фонбет = 2
     */
    Map<String, Integer> getBetTypeValueKeysOffsets();

    /**
     * Номер колонки с форой на игру (колонка с коэффициентом)
     */
    int getGameForaColumn(String game, int team);

    /**
     * Ключ к контейнеру игры в списке игр
     * @param game тип(код) игры (basketball, volleyball ..)
     */
    String getGameKey(String game);

    /**
     * Получить счет игры из элемента игры
     * @param scoreElement
     */
    String getGameScore(WebElement gameElement);

    /**
     * Номер колонки тотал больше на игру (колонка с коэффициентом)
     */
    int getGameTBColumn(String game);

    /**
     * Получить время игры
     * @param gameElement
     */
    int getGameTime(WebElement abstractGame);

    /**
     * Получить название игры из элемента
     * @param titleElement
     */
    String getGameTitle(WebElement titleElement);

    /**
     * Ключ к названию игры
     */
    String getGameTitleElementKey();

    /**
     * Ключи к названиям игр для определенного типа игры.
     * Используется для двухступенчатого поиска игр(1-название, 2-счет, время)
     */
    String getGameTitlesKeys(String gameType);

    /**
     * Номер колонки тотал меньше на игру (колонка с коэффициентом)
     */
    int getGameTMColumn(String game);

    /**
     * Номер колонки с победой в игре (колонка с коэффициентом)
     */
    int getGameWinColumn(String game, int team);

    /**
     * Получить указанную колонку c коэффициентом из главной линии 
     */
    String getRateMainLineKey(String gameTitle, int column);

    /**
     * Получить указанную колонку cо значением ставки, имеющей value, из главной линии 
     */
    String getValueMainLineKey(String gameTitle, int column);

    /**
     * Признак того, что счет игры содержит время игры
     */
    boolean isScoreContainsTime();
}
