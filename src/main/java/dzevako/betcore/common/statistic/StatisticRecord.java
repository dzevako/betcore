package dzevako.betcore.common.statistic;

import dzevako.betcore.common.base.HasTitle;

/**
 * 
 * Интерфейс записи статистики
 * @author dzevako
 * @since Mar 9, 2015
 */
public interface StatisticRecord extends HasTitle
{
    /**
     * Данные статистики, например какие-то коэффициенты или счет в течении игры
     */
    /*
    Object getData();*/

    String getScore();
}
