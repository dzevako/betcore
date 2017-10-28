package dzevako.betcore.drivers.fonbet;

/**
 * Ключи к элементам Фонбет
 * @author dzevako
 * @since Sep 28, 2015
 */
public class FBXpath
{
    /**Начальная страница*/
    public static final String START_HEAD = "//div[@class='logoheader']";
    public static final String START_MENU = START_HEAD + "/div[@id='menu2']";
    public static final String LIVE = START_MENU + "//a[text()='Лайв']";

    /**Верхнее меню*/
    public static final String HEAD_MENU = "//div[@id='headerContainer']";
    public static final String LOGIN_INPUT = HEAD_MENU + "//div[@id='loginContainer']/input[@id='editLogin']";
    public static final String PASSWORD_INPUT = HEAD_MENU + "//div[@id='loginContainer']/input[@id='editPassword']";
    public static final String LOGIN_BTN = HEAD_MENU + "//div[@id='loginContainer']/div[@id='loginButtonLogin']";
    public static final String LOGOUT_BTN = HEAD_MENU + "//div[@id='logoutContainer']/div[@id='loginButtonLogout']";
    public static final String SCORE = HEAD_MENU + "//div[@id='logoutContainer']/span[@id='userStack']";
    public static final String REFRESH_SCORE_BTN = HEAD_MENU + "//div[@id='logoutContainer']/div[@id='buttonRefresh']";
    public static final String WRONG_SESSION = HEAD_MENU + "//div[@id='wrongPass']";

    /**Настройки ставок*/
    public static final String BETS = "//div[@id='couponContainer']";
    public static final String BET_SETTINGS = BETS + "/div[@id='divCouponHeader']";
    public static final String FAST_BET = BET_SETTINGS + "//div[@id='pictOneClick']";
    public static final String BET_RATE_CHANGE = BET_SETTINGS + "//div[@id='pictRateChange']";
    public static final String TOTAL_FORA_VALUE_CHANGE = BET_SETTINGS + "//div[@id='pictHandChange']";

    /**Меню с играми*/
    public static final String GAMES_MENU = "//div[@id='treeContainer']";
    public static final String GAMES_MENU_PATTERN = GAMES_MENU + "//span[text()='%s']";

    /**Игры*/
    public static final String BASKETBALL_EVENT = "//tr[contains(@class, 'sportColor3 level1')]";
    public static final String VOLLEYBALL_EVENT = "//tr[contains(@class, 'sportColor9 level1')]";
    public static final String HOCKEY_EVENT = "//tr[contains(@class, 'sportColor2 level1')]";
    public static final String GAME_KEY_PATTERN = "//tr[@class='trSegment']/td[contains(string(), \"%s\")]/../following-sibling::tr[contains(@class, 'trEvent')]";

    public static final String GAME_DATA = "./td[@class='eventCellName']";
    public static final String GAME_TITLE = GAME_DATA + "/div[@class='event' or @class='eventBlocked'] ";
    public static final String GAME_SCORE = GAME_DATA + "/div/div[contains(@class, 'eventScore')]";
    public static final String GAME_TIME = GAME_DATA + "/div/div[contains(@class, 'eventTime')]";

    public static final String MAIN_LINE = "//tr[contains(@class, 'level1')]/td[@class='eventCellName']/div[text()=\"%s\"]/../..";
    public static final String MAIN_LINE_COLUMN = MAIN_LINE + "/td[%s]";
    public static final String SET_LINE = MAIN_LINE + "/following-sibling::tr[contains(@class, 'level2')]";
    public static final String SET_LINE_COLUMN = SET_LINE + "/td[%s]";
    //public static final String GAME_CONTEXT = MAIN_LINE + "/preceding-sibling::tr[@class='trSegment']/td/div/div[2]";
    public static final String GAME_CONTEXT = "//tr[count(" + MAIN_LINE + "/preceding-sibling::tr)]/td/div/div[2]";

    public static final String VOLLEYBALL_SET_RESULT_LINE = null;
    public static final String BASKETBALL_SET_RESULT_LINE = null;
    public static final String HOCKEY_SET_RESULT_LINE = null;

    /**Оформление ставки*/
    public static final String BET_FORM = "//div[@id='divCouponNew']";
    public static final String BET_EVENT_TITLE = BET_FORM + "//span[contains(@id,'betEventName')]";
    public static final String BET_TYPE = BET_FORM + "//div[@class='stakeContent'][1]/span[@class='value']";
    public static final String BET_RATE = BET_FORM + "//div[@class='stakeContent'][2]/span[@class='value']";
    public static final String BET_VALUE_INPUT = BET_FORM + "//input[@id='couponNewSumEdit']";
    public static final String CONFIRM_BET = BET_FORM + "//div[@id='couponNewButtonPlaceBet']";
    public static final String CLEAR_BET = BET_FORM + "//div[@class='buttonDelete deleteCoupon']";

    public static final String SETTED_BET_FORM = "//div[@id='divCouponList']//div[@class='couponContent']";
    public static final String BET_IS_CONFIRMED = SETTED_BET_FORM + "//div[@class='cpnStatusplaced']";
    public static final String BET_IS_CANCELED = SETTED_BET_FORM + "//div[@class='cpnStatuserror']";
    public static final String HIDE_SETTED_BET = SETTED_BET_FORM + "//div[@class='buttonDelete deleteCoupon']";
    public static final String SET_BET_ERROR_TEXT = SETTED_BET_FORM + "//div[@class='cpnErrorText']";

    /**Статусы ставки*/
    public static final String BET_STATE_UNAVAILABLE = BET_FORM + "//span[text()='Котировка недоступна']";
    public static final String BET_STATE_CHANGED = BET_FORM + "//span[text()='Измен. котировки']";
    public static final String BET_STATE_BLOCKED = BET_FORM + "//span[text()='Котировка заблокирована']";
    public static final String AGREE_BTN = BET_FORM + "//span[text()='Согласен']";

    /**Страница с ошибкой. Файерфокс*/
    public static final String ERROR_PAGE_CONTAINER = "//div[@id='errorPageContainer']";
    public static final String TRY_AGAIN = ERROR_PAGE_CONTAINER + "/*[@id='errorTryAgain']";

}
