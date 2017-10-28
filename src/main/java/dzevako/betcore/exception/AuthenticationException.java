package dzevako.betcore.exception;

/**
 * Ошибка аутентификации
 * @author dzevako
 * @since Oct 7, 2015
 */
public class AuthenticationException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public AuthenticationException()
    {
        super("Authentication unsuccessfull.");
    }
}
