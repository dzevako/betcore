/**
 * 
 */
package dzevako.betcore.game;

import dzevako.betcore.common.base.HasTitle;

/**
 * Интерфейс игры
 * @author dzevako
 * @since Sep 19, 2014
 */
public interface Game extends HasTitle
{
    /**
     * Получить контекст игры (чемпионат, лига)
     */
    String getContext();

    /**
     * Получить текущий счет игры
     */
    String getScore();

    /**
     * Получить текущее время игры (минуты)
     */
    int getTime();

    /**
     * Тип игры
     */
    String getType();

    /**
     * Установить контекст игры (чемпионат, лига)
     */
    void setContext(String ctx);
}
