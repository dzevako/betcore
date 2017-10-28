package dzevako.betcore.logger;

import dzevako.betcore.common.base.Timer;

/**
 * Логгер, в котором можно менять лог-файл вручную
 *
 * @author dzevako
 * @since May 23, 2016
 */
public class SequenceFileLogger extends FileLogger
{
    private static String getFileName()
    {
        return "log" + Timer.date().replace(".", "_");
    }

    String path;

    public SequenceFileLogger(String path)
    {
        super(path + getFileName());
        this.path = path;
    }

    public void nextLogFile()
    {
        createWriter(path + getFileName());
    }
}
