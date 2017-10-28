package dzevako.betcore.logger;

import dzevako.betcore.common.base.Timer;

/**
 * Логгер, делающий запись в System.out
 * @author dzevako
 * @since Oct 31, 2015
 */
public class SystemOutLogger implements Logger
{
    @Override
    public String toString()
    {
        return getClass().getSimpleName();
    }

    @Override
    public void write(String value)
    {
        System.out.println(Timer.time() + " - " + value);
    }
}
