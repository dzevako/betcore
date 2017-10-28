package dzevako.betcore.game;

/**
 * Интерфейс игры, как веб элемента
 *
 * @author dzevako
 * @since Mar 28, 2016
 */
public interface GameElement extends Game
{
    /**
     * Ограничена ли игра по времени
     */
    boolean isTimed();

    /**
     * Зайти в игру (играть)
     */
    void open();
}
