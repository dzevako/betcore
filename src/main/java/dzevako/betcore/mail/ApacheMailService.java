package dzevako.betcore.mail;

import static j2html.TagCreator.body;
import static j2html.TagCreator.h1;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.style;
import static j2html.TagCreator.table;
import static j2html.TagCreator.tbody;
import static j2html.TagCreator.td;
import static j2html.TagCreator.tr;

import java.nio.charset.Charset;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * Реализация почтового сервиса, использующего библиотеку Apache Email
 * @author dzevako
 * @since Aug 5, 2015
 */
public class ApacheMailService implements MailService
{
    private static final String GOOGLE_HOST = "smtp.googlemail.com";

    private static final int GOOGLE_SMTP_PORT = 465;
    private static final String MAIL_LOGIN = "zevs.autobot";
    private static final String MAIL_PSWD = "Gmail*Zevs*070385";
    private static final String GMAIL_ADDRESS = MAIL_LOGIN + "@gmail.com";
    private static final String RECEIVER = "dzevako@gmail.com";

    public static void main(String[] args)
    {
        //@formatter:off
        String body = html().with(
                head().with(
                        style().withType("text/css").withText(".head1 { color: red;} .head2 { color: green;}")),
                body().with(
                h1("Heading!").withClass("head1"), 
                h3("Heading2!").withClass("head2"), 
                table().with(
                    tbody().with(
                        tr().with(
                            td().withText("zevs.autobot"),
                            td().withText("sdfggggtttttttt")),
                        tr().with(
                            td().withText("dzevakogmail7com"),
                            td().withText("wertwertwert"))    
                                 )))
                ).render();
      //@formatter:on
        new ApacheMailService().send("тест", body);
        System.out.println("");
    }

    @Override
    public void send(String subject, String body)
    {
        boolean success = false;
        int attempt = 1;
        while (attempt < 10 && !success)
        {
            try
            {
                trySend(subject, body);
                success = true;
            }
            catch (EmailException e)
            {
                System.out.println("Error send mail: " + e.getMessage());
                waitTime(300);
                attempt++;
                System.out.println("Try send again(attempt " + attempt + ")...");
            }
        }
        System.out.println("Send " + (success ? "success!" : "failed!"));
    }

    /**
     * Отправить сообщение в отдельном потоке
     */
    @Override
    public void sendAsync(final String subject, final String body)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                send(subject, body);
            }
        }).start();
    }

    private void trySend(String subject, String body) throws EmailException
    {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(GOOGLE_HOST);
        email.setSmtpPort(GOOGLE_SMTP_PORT);
        email.setAuthenticator(new DefaultAuthenticator(MAIL_LOGIN, MAIL_PSWD));
        email.setSSLOnConnect(true);
        email.setFrom(GMAIL_ADDRESS);
        email.setSubject(subject);
        email.setHtmlMsg(body);
        email.setCharset(Charset.defaultCharset().name());
        email.addTo(RECEIVER);
        email.send();
    }

    private void waitTime(int sec)
    {
        try
        {
            wait(sec * 1000);
        }
        catch (InterruptedException e)
        {
        }
    }
}
