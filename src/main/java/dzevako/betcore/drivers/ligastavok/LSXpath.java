package dzevako.betcore.drivers.ligastavok;

/**
 * Ключи к элементам Лиги Ставок
 * @author dzevako
 * @since Nov 12, 2015
 */
public class LSXpath
{
    /**Вход*/
    public static final String LOGIN_BOX = "//div[@class='loginForm rotate']";
    public static final String LOGIN_INPUT = LOGIN_BOX + "//input[@id='txtLogin']";
    public static final String PASSWORD_INPUT = LOGIN_BOX + "//input[@id='txtPassword']";
    public static final String LOGIN_BTN = LOGIN_BOX + "//input[@id='btnLogin']";

    public static final String ACCOUNT_BOX = "//div[@class='accountSelector']";
    public static final String SCORE = ACCOUNT_BOX
            + "//table[@class='accountContainer0']//td[contains(@class, 'accountAmount')]";

    /**Игровое поле*/
    public static final String GAMES = "//div[@class='centerColumn']/div/div[@id='listline']";
    //public static final String BASKETBALL_GAMES = GAMES + "/div[@class='event_content'][1]";
    //public static final String GAME_KEY_PATTERN = GAMES + "//table[@class='tevent']/tbody";
    public static final String GAME_KEY_PATTERN = GAMES
            + "/div/div[@class='event lgreen'][text()='%s']/following-sibling::div/div";

    /**Игры*/
    //public static final String BASKETBALL_EVENT = GAMES + "/div[@class='event_content'][1]/div[@class='cevent']/div";
    //public static final String VOLLEYBALL_EVENT = "";
    //public static final String HOCKEY_EVENT = "";

    //public static final String GAME_DATA = "./td[@class='eventCellName']";

    //public static final String GAME_TITLE = "./table/tbody/tr[1]/td/div/div[@class='stitle']";
    //public static final String GAME_SCORE = "./table/tbody/tr[2]/td/div/div/div/div[@class='score_comment lb-score-comment']";
    //public static final String GAME_TIME = "./table/tbody/tr[2]/td/div/div/div/div[@class='event_comment lb-event-comment']";

    public static final String GAME_TITLE = "//table/tbody/tr[1]/td/div/div[@class='stitle']";
    public static final String GAME_SCORE = "//table/tbody/tr/td[2]/div[starts-with(string(),'%s')]/../../following-sibling::tr/td/div/div/div/div[@class='score_comment lb-score-comment']";
    public static final String GAME_TIME = "//table/tbody/tr/td[2]/div[starts-with(string(),'%s')]/../../following-sibling::tr/td/div/div/div/div[@class='event_comment lb-event-comment']";

    //public static final String MAIN_LINE_COLUMN = GAMES
    //+ "/div[string()='%s']/following-sibling::div//table/tbody/tr[1]/td[%s]";

    public static final String MAIN_LINE_COLUMN = GAMES
            + "//table/tbody/tr/td[2]/div[contains(string(),'%s')]/../../td[%s]";

    /**Форма оформления ставки*/
    public static final String BET_FORM = "//div[@id='koBetsCart']";
    public static final String CLEAR_BASKET = BET_FORM + "//div[@class='clearCard']";
    public static final String BASKET_IS_EMPTY = BET_FORM + "//div[@class='cardIsEmpty2']";
    public static final String INFO_BOX = BET_FORM + "//div[@class='cardItem']";
    public static final String BET_GAME_TITLE = INFO_BOX + "/div[@class='title']";
    public static final String BET_TYPE = INFO_BOX + "/div[@class='partInfo'][2]";
    public static final String VALUE_BOX = INFO_BOX + "/div[@id='cartbetline']";
    public static final String BET_RATE = VALUE_BOX + "/div[contains(@class, 'qaf')]";
    public static final String BET_VALUE_INPUT = INFO_BOX + "/div[@class='betCnt']/input[1]";
    public static final String CONFIRM_BET = VALUE_BOX + "/div[@class='betCnt']/img[@class='bAction']";
    public static final String BET_IS_CANCELED = VALUE_BOX
            + "/div[@class='systemCalcInfo animo error'][contains(string(), 'Ставка не принята:')]";
    public static final String BET_IS_CONFIRMED = VALUE_BOX
            + "/div[@class='systemCalcInfo animo'][contains(string(), 'Ставка успешно зарегистрирована.')]";
    public static final String BET_HINT = VALUE_BOX + "/div[@class='hint']";
}