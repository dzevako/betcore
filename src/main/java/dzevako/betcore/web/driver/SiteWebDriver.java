package dzevako.betcore.web.driver;

/**
 * Интерфейс веб-драйвера для абстрактного сайта
 * @author dzevako
 * @since Sep 26, 2015
 * 
 */
//TODO доделать
public interface SiteWebDriver
{
    /**
     * Ключи сайта
     */
    SiteKeys getKeys();

    /**
     * Перейти по указанной ссылке
     */
    void goTo(String url);

    /**
     * Обновить страницу источника
     */
    void refreshSite();

    /**
     * Выключить драйвер
     */
    void shutdown();

    /**
     * Запустить драйвер
     */
    void start();

    /**
     * Ожидание, секунды
     */
    void waitTime(int i);
}
