package dzevako.betcore.calc;

/**
 * Интерфейс калькулятора для расчета суммы ставки
 *
 * @author dzevako
 * @since Jan 3, 2016
 */
public interface BetValueCalculator
{
    /**
     * Получить текущее значение
     */
    int getCurrentValue();

    /**
     * Получить значение в зависимости от имеющегося банка
     */
    int getValue(int score);

    /**
     * Установить минимальное значение ставки
     */
    void setMinValue(int value);
}
