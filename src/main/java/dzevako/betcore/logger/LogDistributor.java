/**
 * 
 */
package dzevako.betcore.logger;

import java.io.File;

/**
 * Распределитель логов
 * @author dzevako
 * @since Sep 19, 2014
 */
@Deprecated
public class LogDistributor
{
    private Logger webLog;
    private Logger processorLog;
    private Logger sessionLog;

    public LogDistributor(String path, String playerName)
    {
        String playerPath = path + playerName + "/";

        File dir = new File(playerPath);
        dir.mkdirs();

        webLog = new FileLogger(playerPath + "webLog");
        processorLog = new FileLogger(playerPath + "processorLog");
        sessionLog = new FileLogger(playerPath + "sessionLog");
    }

    public Logger getProcessorLog()
    {
        return processorLog;
    }

    public Logger getSessionLog()
    {
        return sessionLog;
    }

    public Logger getWebLog()
    {
        return webLog;
    }
}
