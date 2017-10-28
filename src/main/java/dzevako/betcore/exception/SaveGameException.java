package dzevako.betcore.exception;

/**
 * Исключение, возникающее при сохранении игры
 *
 * @author dzevako
 * @since Jan 14, 2016
 */
public class SaveGameException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public SaveGameException(String game, Exception e)
    {
        super("Exception saving game '" + game + "': " + e.getMessage());
    }
}
