package dzevako.betcore.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * Запись данных в файл
 * @author dzevako
 * @since Aug 11, 2015
 */
public class DataWriter implements Logger
{
    protected File file;
    protected FileWriter writer;

    /**
     * Если файл уже существует, то запись продолжаются в него
     */
    public DataWriter(String fileName)
    {
        createWriter(fileName);
    }

    protected void createWriter(String fileName)
    {
        try
        {
            file = new File(fileName);

            if (!file.exists())
            {
                file.createNewFile();
            }

            writer = new FileWriter(file, true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getPath()
    {
        return file.getAbsolutePath().replace(file.getName(), "");
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName();
    }

    @Override
    public void write(String value)
    {
        try
        {
            writeLine(value);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    protected void writeLine(String value) throws IOException
    {
        writer.append(value + '\n').flush();
    }
}
