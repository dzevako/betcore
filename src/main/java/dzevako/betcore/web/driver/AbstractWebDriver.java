package dzevako.betcore.web.driver;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import dzevako.betcore.common.Constants;
import dzevako.betcore.common.base.Timer;
import dzevako.betcore.game.GameFactory;
import dzevako.betcore.game.GameFactoryImpl;
import dzevako.betcore.logger.Logger;
import dzevako.betcore.utils.GameUtils;

/**
 * Абстрактный веб-драйвер
 *
 * @author dzevako
 * @since Sep 13, 2014
 */
public abstract class AbstractWebDriver extends AbstractWebSearch implements SiteWebDriver
{
    private static final int CHECK_ATTEMPTS = 10;

    private static final int REFRESH_ATTEMPTS = 3;

    private static final int CHECK_WAIT_TIME = 1000;

    /**
     * Для взамодействия с Selenium хрому не достаточно просто ChromeDriver.
     * 
     * Требуется так называемый "chromedriver server"
     * который представляет из себя бинарный файл специфичный для OS.
     * 
     * Сейчас подключено два chromedriver server:
     * linux chromedriver x86
     * linux chromedriver x64
     *
     * Если потребуется поддержка Windows, то нужно скачать 
     * еще один chromeserver и указать путь запуска для него в CHROME_DRIVER_EXE_PROPERTY.
     * 
     * Сейчас этот путь всегда указывает на bash-скрипт, который в зависимости от битности
     * запускает соотвественно либо 32-битный драйвер, либо 64-битный для linux.
     * 
     * @return экземпляр ChromeDriver 
     */
    private static WebDriver getChromeDriver()
    {
        //Установливает путь до места chromedriver server
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
                Constants.DRIVERS_DIR + "/chrome/chromedriver");

        ChromeOptions options = new ChromeOptions();
        //String browserPath = "/usr/bin/google-chrome-stable";
        String browserPath = Constants.DRIVERS_DIR + "/chrome/chrome/google-chrome";
        if (!browserPath.isEmpty())
        {
            options.setBinary(browserPath);
        }

        //Указываем на специально настроенный профиль (путь до него)
        String userProfile = Constants.PROFILES_DIR + "chrome";
        //String userProfile = System.getenv("HOME") + "/.config/google-chrome/webdriver/";
        options.addArguments("user-data-dir=" + userProfile);

        //options.addExtensions(new File(Constants.DRIVER_EXTENSIONS_DIR + "Marafon/marathon_proxy.crx"));
        //options.addArguments("--start-maximized");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        return new ChromeDriver(capabilities);
    }

    protected static SearchContext getDefaultDriver(String browserCode)
    {
        return Constants.CHROME_BROWSER.equals(browserCode) ? getChromeDriver() : getFirefoxDriver();
    }

    /**
     * Возвращает новый оптимизированный FirefoxDriver
     */
    protected static SearchContext getFirefoxDriver()
    {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        FirefoxProfile profile = getOptimizedFirefoxProfile();
        capabilities.setCapability(FirefoxDriver.PROFILE, profile);
        capabilities.setCapability(FirefoxDriver.BINARY, Constants.DRIVERS_DIR + "/firefox/firefox/firefox");
        return new FirefoxDriver(capabilities);
    }

    protected static FirefoxProfile getOptimizedFirefoxProfile()
    {
        FirefoxProfile firefoxProfile = new FirefoxProfile();

        //Память на вкладки
        firefoxProfile.setPreference("browser.sessionhistory.max_total_viewer", "1");
        //Значение 3 не просто так, иначе не работает авторефреш
        firefoxProfile.setPreference("browser.sessionhistory.max_entries", 3);
        firefoxProfile.setPreference("browser.sessionhistory.max_total_viewers", 1);
        firefoxProfile.setPreference("browser.sessionstore.max_tabs_undo", 0);
        //Асинхронные запросы к серверу
        firefoxProfile.setPreference("network.http.pipelining", true);
        firefoxProfile.setPreference("network.http.pipelining.maxrequests", 8);
        //Задержка отрисовки
        firefoxProfile.setPreference("nglayout.initialpaint.delay", "0");
        //Сканирование внутренним сканером загрузок
        firefoxProfile.setPreference("browser.download.manager.scanWhenDone", false);
        //Анимация переключения вкладок
        firefoxProfile.setPreference("browser.tabs.animate", false);
        //Автоподстановка
        firefoxProfile.setPreference("browser.search.suggest.enabled", false);
        //Анимация гифок
        firefoxProfile.setPreference("image.animation_mode", "none");
        //Резервные копии вкладок
        firefoxProfile.setPreference("browser.bookmarks.max_backups", 0);
        //Автодополнение
        firefoxProfile.setPreference("browser.formfill.enable", false);
        //Убрал дисковый кеш и кеш в памяти
        //firefoxProfile.setPreference("browser.cache.memory.enable", false);
        firefoxProfile.setPreference("browser.cache.disk.enable", false);
        //Сохранять файлы без подтверждения в tslogs
        firefoxProfile.setPreference("browser.download.folderList", 2);
        firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
        firefoxProfile.setPreference("browser.download.panel.shown", false);

        //NSDPRD-2022
        firefoxProfile.setPreference("javascript.options.methodjit.content", false);

        //firefoxProfile.setPreference("browser.download.dir", FileDownloader.TEMP_DIR.getAbsolutePath());
        firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                "text/html, application/xml, application/pdf, application/octet-stream, application/zip,text/plain, application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        firefoxProfile.setPreference("pdfjs.disabled", true);
        firefoxProfile.setPreference("focusmanager.testmode", true);
        firefoxProfile.setPreference("intl.accept_languages", "ru");

        try
        {
            firefoxProfile.addExtension(new File(Constants.DRIVER_EXTENSIONS_DIR + "FonBet/fb_proxy-0.1-fx-linux.xpi"));
            //firefoxProfile.addExtension(new File(Constants.DRIVER_EXTENSIONS_DIR + "zenmate-firefox.xpi"));
        }
        catch (IOException e)
        {
            System.out.println("Error installing Fonbet pliugin!");
        }

        return firefoxProfile;
    }

    /**
     * Возвращает оптимизированный FirefoxDriver на основе уже настроенного бинарника
     */
    protected static SearchContext getTunedFirefoxDriver()
    {
        //FirefoxProfile profile = getOptimizedFirefoxProfile();
        FirefoxProfile profile = new FirefoxProfile();
        FirefoxBinary binary = new FirefoxBinary(new File(Constants.DRIVERS_DIR + "firefox/firefox/firefox-bin"));
        return new FirefoxDriver(binary, profile);
    }

    protected final String startPage;

    //Вид обрабатываемых игр
    protected String gameType;
    protected String gameTypeTitle;

    //Фабрика игр
    protected GameFactory gameFactory;

    //Логгер драйвера
    protected Logger logger;

    protected SiteKeys keys;

    public AbstractWebDriver(String gameType, Logger logger, SearchContext driver, SiteKeys keys, String startPage)
    {
        super(driver);
        this.gameType = gameType;
        this.gameTypeTitle = GameUtils.getGameTypeTitle(gameType);
        this.logger = logger;
        this.startPage = startPage;
        if (keys == null)
        {
            throw new RuntimeException("SiteKeys cannot be null!");
        }
        this.gameFactory = new GameFactoryImpl(keys);
        this.keys = keys;
    }

    public AbstractWebDriver(String gameType, Logger logger, SiteKeys keys, String startPage, String browser)
    {
        this(gameType, logger, getDefaultDriver(browser), keys, startPage);
    }

    @Override
    protected int checkAttempts()
    {
        return CHECK_ATTEMPTS;
    }

    @Override
    protected int checkWaitTime()
    {
        return CHECK_WAIT_TIME;
    }

    /**
     * Кликнуть по указанному элементу и вернуть его
     */
    public WebElement click(String xpath)
    {
        try
        {
            WebElement element = find(xpath);
            click(element);
            return element;
        }
        catch (StaleElementReferenceException | ElementNotVisibleException e)
        {
            log("CLICK ERROR: " + e.getMessage());
            return null;
        }
    }

    /**
     * Кликнуть по элементу, если он не null
     */
    private void click(WebElement element)
    {
        if (null != element)
        {
            moveToElement(element);
            element.click();
        }
    }

    /**
     * Кликнуть по указанному элементу без ожидания
     */
    public WebElement clickIfExists(String xpath)
    {
        WebElement element = get(xpath);
        if (null == element)
        {
            return null;
        }
        click(element);
        return element;
    }

    protected void clickOrThrow(String xpath)
    {
        WebElement element = driver().findElement(By.xpath(xpath));
        element.click();
    }

    /**
     * Закрыть алерт
     */
    public void closeAlert()
    {
        driver().switchTo().alert().dismiss();
    }

    public WebDriver driver()
    {
        return (WebDriver)element;
    }

    public Object executeScript(String script, Object... args)
    {
        return ((JavascriptExecutor)element).executeScript(script, args);
    }

    /**
     * Найти элемент,
     * если не находит - перезагружает страницу и повторяет поиск
     */
    public WebElement findElementHard(By by)
    {
        int refreshAttempts = 1;
        while (refreshAttempts <= REFRESH_ATTEMPTS)
        {
            try
            {
                return find(by);
            }
            catch (NoSuchElementException e)
            {
                if (refreshAttempts == REFRESH_ATTEMPTS)
                {
                    return null;
                }
                else
                {
                    refreshAttempts += 1;
                    refresh();
                }
            }
        }
        return null;
    }

    @Override
    public SiteKeys getKeys()
    {
        return keys;
    }

    /**
     * Получить текст элемента
     */
    @Override
    public String getText(String xpath)
    {
        try
        {
            return super.getText(xpath);
        }
        catch (StaleElementReferenceException e)
        {
            log("GET_TEXT ERROR: " + e.getMessage());
            return "";
        }
    }

    /**
     * Получить текст элемента без ожиданий
     */
    @Override
    public String getTextFast(String xpath)
    {
        if (null == xpath || xpath.isEmpty())
        {
            return "";
        }
        try
        {
            return super.getTextFast(xpath);
        }
        catch (StaleElementReferenceException e)
        {
            log("GET_TEXT ERROR: " + e.getMessage());
            return "";
        }
    }

    @Override
    public void goTo(String url)
    {
        driver().navigate().to(url);
    }

    /**
     * Логирование драйвера
     * @param msg сообщение
     */
    public void log(String msg)
    {
        logger.write(msg);
    }

    /**
     * Проскролиться до элемента
     * Для некоторых сайтов это может быть необходимо.
     */
    protected void moveToElement(WebElement element)
    {
        //do nothing as default
    }

    /**
     * Обновить страницу
     */
    public void refresh()
    {
        driver().navigate().refresh();
    }

    /**
     * Обновить страницу сайта
     */
    @Override
    public void refreshSite()
    {
        log("Refresh site...");
        refresh();
    }

    public void sendKeys(String xpath, CharSequence keys)
    {
        if (null == keys)
        {
            return;
        }
        WebElement element = find(xpath);
        if (null == element)// || element.getTagName() != "input")
        {
            throw new RuntimeException("Can not sending keys into '" + xpath + "'");
        }
        //Стереть все из поля ввода,  если не Enter
        if (!keys.equals(Keys.ENTER))
        {
            element.clear();
        }
        element.sendKeys(keys);
    }

    /**
     * Остановить работу драйвера
     */
    @Override
    public void shutdown()
    {
        log("Driver shutdown operation...");
        driver().quit();
        log("Shutdown success.");
    }

    /**
     * Запустить веб-драйвер
     */
    @Override
    public void start()
    {
        start(startPage);
    }

    /**
     * Запустить веб-драйвер
     */
    public void start(String url)
    {
        driver().get(url);
    }

    /**
     * Ожидание в секундах c логированием
     */
    @Override
    public void waitTime(int seconds)
    {
        waitTime(seconds, true);
    }

    /**
     * Ожидание в секундах c возможностью вкл\выкл логирования
     */
    protected void waitTime(int seconds, boolean needLog)
    {
        if (needLog)
        {
            String interval = seconds > 60 && 0 == seconds % 60 ? "minutes" : "seconds";
            int value = "minutes".equals(interval) ? (int)(seconds / 60) : seconds;
            log("Wait " + value + " " + interval + "...");
        }
        Timer.waitSec(seconds);
    }
}