package dzevako.betcore.logger;

import dzevako.betcore.common.Constants;

/**
 * Фабрика логгеров
 *
 * @author dzevako
 * @since May 22, 2016
 */
public class LoggerFactory
{
    public static Logger get(String code, String path)
    {
        switch (code)
        {
        case (Constants.FILE_LOGGER):
            return new FileLogger(path);
        case (Constants.SYSOUT_LOGGER):
            return new SystemOutLogger();
        case (Constants.SEQUENCE_FILE_LOGGER):
            return new SequenceFileLogger(path);
        default:
            return new SystemOutLogger();
        }
    }
}
