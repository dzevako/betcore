package dzevako.betcore.mail;

/**
 * Почтовый сервис
 * @author dzevako
 * @since Aug 5, 2015
 */
public interface MailService
{
    /**
     * Отправить оповещение
     * @param subject тема письма
     * @param body тело письма
     */
    void send(String subject, String body);

    /**
     * Отправить оповещение в отдельном потоке
     * @param subject тема письма
     * @param body тело письма
     */
    void sendAsync(String subject, String string);
}
