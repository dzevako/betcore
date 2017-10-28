package dzevako.betcore.logger;

import java.io.IOException;

/**
 * Параллельное логирование в файл и в консоль System.out
 * @author dzevako
 * @since Nov 1, 2015
 */
public class FileLoggerWithSystemOut extends FileLogger
{
    public FileLoggerWithSystemOut(String fileName)
    {
        super(fileName);
    }

    @Override
    public void writeLine(String value) throws IOException
    {
        super.writeLine(value);
        System.out.println(value);
    }
}
