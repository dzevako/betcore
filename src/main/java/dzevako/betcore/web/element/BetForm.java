/**
 * 
 */
package dzevako.betcore.web.element;

import dzevako.betcore.bet.Bet;

/**
 * Интерфейс формы оформления ставки
 * @author dzevako
 * @since Sep 24, 2014
 */
public interface BetForm extends Bet
{
    /**
     * Нажать "Оформить"
     */
    void apply();

    /**
     * Нажать "Отменить"
     */
    void cancel();

    /**
     * Ввести значение ставки
     */
    void sendValue(double value);
}
