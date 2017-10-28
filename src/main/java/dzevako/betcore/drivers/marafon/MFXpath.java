package dzevako.betcore.drivers.marafon;

import dzevako.betcore.game.GameTitles;

/**
 * Ключи к элементам Марафон
 * @author dzevako
 * @since Feb 19, 2016
 */
public class MFXpath
{
    public static final String MAIN_CONTAINER = "//div[@id='id_container']";

    /**Верхнее меню*/
    public static final String HEAD_MENU = MAIN_CONTAINER + "/div[@id='header_container']";
    public static final String LIVE = HEAD_MENU + "//a/span[text()='LIVE']";
    public static final String IS_LIVE_ACTIVE = HEAD_MENU + "//a[@class='act live']";
    public static final String AUTH_CONTAINER = HEAD_MENU + "//div[@class='head-right']";
    public static final String LOGIN_INPUT = AUTH_CONTAINER + "//input[@id='auth_login']";
    public static final String PASSWORD_INPUT = AUTH_CONTAINER + "//input[@id='auth_login_password']";
    public static final String PASSWORD_INPUT_FAKE = AUTH_CONTAINER + "//input[@class='undefined empty']";
    //+ "//input[@class='undefined empty']|input[@id='login_password']";
    public static final String LOGIN_BTN = AUTH_CONTAINER + "//button[text()='Войти']";
    public static final String LOGOUT_BTN = AUTH_CONTAINER + "//a[@id='logoutLink']";
    public static final String SCORE = AUTH_CONTAINER + "//b[@id='balance']";

    /**Меню с играми*/
    public static final String GAMES_MENU = MAIN_CONTAINER + "//div[@id='sportsMenuContainer']";
    public static final String BASKETBALL_MENU = GAMES_MENU + "//a[contains(text(),'" + GameTitles.BASKETBALL + "')]";
    public static final String VOLLEYBALL_MENU = GAMES_MENU + "//a[contains(text(),'" + GameTitles.VOLLEYBALL + "')]";
    public static final String HOCKEY_MENU = GAMES_MENU + "//a[contains(text() + ,'" + GameTitles.HOCKEY + "')]";
    public static final String GAMES_MENU_PATTERN = GAMES_MENU + "//a[contains(text(),'%s')]";

    /**Игры*/
    public static final String GAMES_CONTAINER = MAIN_CONTAINER + "//div[@id='container_EVENTS']";
    public static final String GAMES_KEY = GAMES_CONTAINER + "//table/tbody[contains(@id, 'event_')]/tr[1]";
    public static final String GAME_KEY = GAMES_KEY
            + "/td[1][contains(string(), \"%s\")][contains(string(), \"%s\")]/..";
    public static final String GAME_CONTEXT_TITLE = GAMES_CONTAINER
            + "//div[@class='sport-category-header']/div[@class='sport-category-label']";
    public static final String GAME_CONTEXT = GAMES_CONTAINER
            + "//div[@class='sport-category-content']//div[@class='category-label']/span";

    public static final String GAME_MAIN_CONTAINER = "./td[1]/table[@class='member-area-content-table']/tbody/tr[1]";
    public static final String GAME_TITLE = GAME_MAIN_CONTAINER + "/td[2]/div[1]";
    public static final String GAME_SCORE = GAME_MAIN_CONTAINER + "//div[@class='cl-left red']";
    public static final String GAME_TIME = GAME_MAIN_CONTAINER + "//td[@class='date']/div";
    public static final String GAME_TITLE_PART = "./div[@class='live-today-member-name nowrap ']";

    public static final String MAIN_LINE_COLUMN = GAME_KEY + "/td[%s]";
    public static final String MAIN_LINE_COLUMN_RATE = MAIN_LINE_COLUMN + "/span";
    //public static final String SET_LINE = GAME_KEY + "";
    //public static final String SET_LINE_COLUMN = GAME_KEY + "";

    /**Оформление ставки*/
    public static final String BET_FORM = "//div[@id='betslip-container']";
    public static final String SHOW_BET_FORM = BET_FORM + "//div[contains(@class, 'bet-slip')]";
    public static final String ONE_BET_CHOOSED = SHOW_BET_FORM + "/a[text()='(выбрано 1)']";
    public static final String NO_BET_CHOOSED = SHOW_BET_FORM + "/a[text()='(выбрано 0)']";
    public static final String BET_EVENT_TITLE = BET_FORM + "//span[@class='underline']";
    public static final String BET_TYPE = BET_FORM + "//div[@class='hint-box']";
    public static final String BET_RATE = BET_FORM + "//td[@class='choice-price']/span[@class='bold']";
    public static final String BET_VALUE_INPUT = BET_FORM + "//td[@class='stake']/input";
    public static final String CONFIRM_BET = BET_FORM
            + "//table[@class='panel-bet']//span[contains(text(), 'Сделать ставку')]";
    public static final String CLEAR_BET = BET_FORM + "//table[@class='panel-bet']//span[text()='x']";

    /**Настройки ставок*/
    public static final String CHANGE_RATE_SETTING = BET_FORM + "//span[text()='Соглашаться с изменением коэфф.']";
    public static final String ALLOW_INCREASE_CHANGES_RATE = CHANGE_RATE_SETTING
            + "//input[@id='betPlacingModeRadio_GreaterOrEqualsToCurrent']";
    public static final String ALLOW_ALL_CHANGES_RATE = CHANGE_RATE_SETTING + "//input[@id='betPlacingModeRadio_Any']";
    public static final String NEVER_ALLOW_CHANGES_RATE = CHANGE_RATE_SETTING
            + "//input[@id='betPlacingModeRadio_EqualsToCurrent']";

    /**Проверка поставленной ставки*/
    public static final String MODAL_FORM = "//div[@id='simplemodal-container']";
    public static final String BET_IS_CONFIRMED = MODAL_FORM + "//p[text()='Ваша ставка принята, спасибо']";
    public static final String BET_IS_CANCELED = MODAL_FORM + "//p[text()='Извините, Ваша ставка не принята.']";
    public static final String MODAL_FORM_CONFIRM = MODAL_FORM + "//button[@id='ok-button']";
    public static final String CONFIRM_BET_WITH_CHANGES = MODAL_FORM + "//button[@id='place-changed-terms']";
    public static final String CANCEL_BET_WITH_CHANGES = MODAL_FORM + "//button[@id='cancel-button']";
    public static final String SET_BET_ERROR_TEXT = "";

    //div[@id='id_container']//div[@id='container_EVENTS']//table/tbody[contains(@id, 'event_')]/tr[1]/td[6]/span колонку во всех строках выделяет
}
