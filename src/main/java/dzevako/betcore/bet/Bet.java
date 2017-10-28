package dzevako.betcore.bet;

import dzevako.betcore.bettypes.BetType;
import dzevako.betcore.common.base.HasTitle;

/**
 * Интерфейс ставки
 * @author dzevako
 * @since Sep 27, 2014
 */
public interface Bet extends HasTitle
{
    /**
     * Коэффициент
     */
    double getRate();

    /**
     * Тип ставки
     */
    BetType getType();

    /**
     * Получить значение
     */
    int getValue();

    /**
     * Установить значение
     */
    void setValue(int value);
}
