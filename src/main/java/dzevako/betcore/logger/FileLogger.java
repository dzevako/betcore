package dzevako.betcore.logger;

import java.io.IOException;

import dzevako.betcore.common.base.Timer;

/**
 * Логирование в файл
 * @author dzevako
 */
public class FileLogger extends DataWriter
{
    /**
     * Если файл уже существует, то запись продолжаются в него
     */
    public FileLogger(String fileName)
    {
        super(fileName);
    }

    public FileLogger(String path, String fileName)
    {
        super(path + fileName);
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "[path = " + file.getPath() + "]";
    }

    @Override
    protected void writeLine(String value) throws IOException
    {
        writer.append(Timer.dateTime() + " - ");
        super.writeLine(value);
    }
}
