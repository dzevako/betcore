package dzevako.betcore.bettypes;

/**
 * Объект содержащий ключ ставки и информаций для проверки корректности
 * @author dzevako
 * @since Aug 24, 2015
 */
public interface BetKey
{
    /**
     * Основная информация о типе ставки
     */
    String getInfo();

    /**
     * Ключ к коэффициенту типа ставки
     */
    String getRateKey();

    /**
     * Ключ к значению типа ставки(актуально для фор, тоталов)
     */
    String getValueKey();

    /**
     * Признак того что тип ставки на весь матч
     */
    boolean isForGame();
}
