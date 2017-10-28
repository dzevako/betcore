/**
 * 
 */
package dzevako.betcore.game;

/**
 * Интерфейс объекта, содержащего счет игры
 * @author dzevako
 * @since Sep 19, 2014
 */
public interface GameScore
{
    /**
     * Разница в очках в текущей партии
     */
    public int getDiff();

    /**
     * Разница в очках в указанном сете
     */
    public int getDiff(int set);

    /**
     * Количество очков в текущем сете у первой команды
     */
    public int getFirstPoints();

    /**
     * Количество побед у первой команды
     */
    //public int getFirstWins();

    /**
     * Количество очков в текущем сете у лидирующей команды
     */
    public int getMaxPoints();

    /**
     * Количество очков в текущем сете у отстающей команды
     */
    public int getMinPoints();

    /**
     * Количество очков до конца сета
     */
    //public int getOverPoints();

    /**
     * Количество очков в текущем сете у второй команды
     */
    public int getSecondPoints();

    /**
     * Количество побед у второй команды
     */
    //public int getSecondWins();

    /**
     * Номер текущего сета
     */
    public int getSet();

    /**
     * Количество очков в сете (до скольки очков сет)
     */
    //public int getSetPoints();

    /**
     * Получение счета во всех сетах в виде строки
     */
    public String getString();

    /**
     * Время, сыгранное в текущем сете(либо во всей игре)
     */
    //int getTime();

    /**
     * Полулучить время в текущем сете
     */
    public int getTime();

    /**
     * Количество очков в сумме у обеих команд за игру
     */
    public int getTotal();

    /**
     * Количество очков в сумме у обеих команд в указанном сете
     */
    public int getTotal(int set);

    /**
     * Победитель указанного сета, если сет некорректен или не закончился, то 0
     */
    int getWinner(int set);
}
