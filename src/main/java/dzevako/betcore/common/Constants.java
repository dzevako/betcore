package dzevako.betcore.common;

import java.util.Set;

import com.google.common.collect.Sets;

/**
 * Общие константы
 * @author dzevako
 *
 */
public class Constants
{
    public static final String MAIN_PATH = System.getenv().get("HOME") + "/robot/";
    public static final String DRIVERS_DIR = MAIN_PATH + "drivers/";
    public static final String DRIVER_EXTENSIONS_DIR = DRIVERS_DIR + "extensions/";
    public static final String PROFILES_DIR = DRIVERS_DIR + "profiles/";
    public static final String PLAYER_PATH = MAIN_PATH + "player/";
    public static final String STATISTIC_PATH = MAIN_PATH + "statistic/";
    public static final String REPORT_PATH = MAIN_PATH + "reports/";
    public static final String HTML_REPORT_PATH = REPORT_PATH + "html/";
    public static final String FONBET_STATISTIC_PATH = STATISTIC_PATH + "data/fonbet/";
    public static final String ANALYZE_PATH = MAIN_PATH + "analyze/";
    public static final String ANALYZE_RESULTS_PATH = ANALYZE_PATH + "results/";
    public static final String TRANSFORM_PATH = ANALYZE_PATH + "transform/";

    public static final String FONBET = "fonbet";
    public static final String BETCITY = "betcity";
    public static final String LIGASTAVOK = "ligastavok";
    public static final String MARAFON = "marafon";
    public static final String X1BET = "1xbet";

    public static final Set<String> SOURCES = Sets.newHashSet(BETCITY, FONBET, LIGASTAVOK, MARAFON, X1BET);

    public static final String CHROME_BROWSER = "chrome";
    public static final String FIREFOX_BROWSER = "firefox";

    public static final Set<String> BROWSERS = Sets.newHashSet(CHROME_BROWSER, FIREFOX_BROWSER);

    public static final String FILE_LOGGER = "file";
    public static final String SEQUENCE_FILE_LOGGER = "sequence";
    public static final String SYSOUT_LOGGER = "sysout";
}
