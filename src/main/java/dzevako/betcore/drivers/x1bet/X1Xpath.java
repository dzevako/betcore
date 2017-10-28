package dzevako.betcore.drivers.x1bet;

/**
 * Ключи к элементам 1x-bet
 * @author dzevako
 * @since Apr 1, 2016
 */
public class X1Xpath
{
    /**Верхнее меню*/
    public static final String TOP_CONTAINER = "//div[@id='fTop']";
    public static final String HEAD_MENU = TOP_CONTAINER + "//div[@id='games_top_menu']";
    public static final String LIVE = HEAD_MENU + "//a[@id='live_href']";
    public static final String AUTH_CONTAINER = TOP_CONTAINER + "//div[@id='loginout']";
    public static final String LOGIN_INPUT = AUTH_CONTAINER + "//input[@id='userLogin']";
    public static final String PASSWORD_INPUT = AUTH_CONTAINER + "//input[@id='userPassword']";
    //public static final String PASSWORD_INPUT_FAKE = AUTH_CONTAINER + "//input[@id='undefined empty']";
    public static final String LOGIN_CONFIRM = AUTH_CONTAINER + "//a[@id='userConButton']";
    //+ "//input[@class='undefined empty']|input[@id='login_password']";
    public static final String LOGIN_BTN = AUTH_CONTAINER + "//span[text()='Войти']";
    public static final String LOGOUT_BTN = AUTH_CONTAINER + "//span[text()='Выход']";
    public static final String SCORE = AUTH_CONTAINER + "//span[@id='uMoney']";
    public static final String REFRESH_SCORE = AUTH_CONTAINER + "//img";

    public static final String MAIN_CONTAINER = "//div[@id='maincontent']";

    /**Меню с играми*/
    public static final String GAMES_MENU = MAIN_CONTAINER + "//div[@id='allSport']";
    public static final String GAMES_MENU_PATTERN = GAMES_MENU + "/ul/li/a/span[text()='%s']/..";

    /**Игры*/
    public static final String GAMES_CONTAINER = MAIN_CONTAINER + "//div[@id='games_content']";
    public static final String GAMES_KEY = GAMES_CONTAINER + "//div[@class='tb2']";
    public static final String GAME_TITLES_KEY_FOR_TYPE = GAMES_KEY
            + "/div[1]//a[contains(text(), '%s')]/span[@class='gname hotGameTitle']";
    public static final String GAME_TITLE_KEY = GAMES_KEY + "/div[1]//span[@class='gname hotGameTitle']";
    public static final String GAME_KEY = GAME_TITLE_KEY + "[contains(text(), \"%s\")]/../../../../../../..";
    public static final String GAME_CONTEXT_TITLE = GAMES_CONTAINER + "";
    public static final String GAME_CONTEXT = GAMES_CONTAINER + "";

    //public static final String GAME_DATA_CONTAINER = "./div[1]";
    //public static final String GAME_TITLE = GAME_DATA_CONTAINER + "//span[@class='gname hotGameTitle']";
    //public static final String GAME_SCORE = GAME_DATA_CONTAINER + "//span[@class='nums']";
    //public static final String GAME_TIME = GAME_DATA_CONTAINER + "//span[@class='nums']";

    public static final String GAME_TITLE = GAME_KEY + "//span[@class='gname hotGameTitle']";
    public static final String GAME_SCORE = GAME_KEY + "//span[@class='nums']";
    public static final String GAME_TIME = GAME_KEY + "//span[@class='nums']";

    public static final String MAIN_LINE_COLUMN = GAME_KEY + "/div[2]/table/tbody/tr[2]/td[%s]";
    public static final String MAIN_LINE_COLUMN_RATE = MAIN_LINE_COLUMN + "/a";

    /**Оформление ставки*/
    public static final String BET_FORM = "//div[@id='cuponFix']";
    public static final String BET_DATA = BET_FORM + "//div[@id='all_bets']";
    public static final String BET_EVENT_TITLE = BET_DATA + "//div[@class='teams ']/span";
    public static final String BET_TYPE = BET_DATA + "//span[@class='team']";
    public static final String BET_RATE = BET_DATA + "//span[contains(@class, 'bet ')]";
    public static final String BET_BLOCK = "//div[@class='gameBlockInCoupon']";

    public static final String BET_VALUE_INPUT = BET_FORM + "//input[@id='bet_input']";
    public static final String CONFIRM_BET = BET_FORM + "//input[@id='goPutBetButton']";
    public static final String CLEAR_BET = BET_DATA + "//div[@class='del-rate']";

    /**Настройки ставок*/
    public static final String CHANGE_RATE_SETTING = BET_FORM + "//select[@id='allE']";
    public static final String ALLOW_INCREASE_CHANGES_RATE = CHANGE_RATE_SETTING + "/option[@value='2']";
    public static final String ALLOW_ALL_CHANGES_RATE = CHANGE_RATE_SETTING + "/option[@value='1']";
    public static final String NEVER_ALLOW_CHANGES_RATE = CHANGE_RATE_SETTING + "/option[@value='0']']";

    /**Проверка поставленной ставки*/
    // public static final String MODAL_FORM = "//div[@id='simplemodal-container']";
    /*public static final String BET_IS_CONFIRMED = MODAL_FORM + "//p[text()='Ваша ставка принята, спасибо']";
    public static final String BET_IS_CANCELED = MODAL_FORM + "//p[text()='Извините, Ваша ставка не принята.']";
    public static final String OK_BUTTON = MODAL_FORM + "//button[@id='ok-button']";
    public static final String CONFIRM_BET_WITH_CHANGES = MODAL_FORM + "//button[@id='place-changed-terms']";
    public static final String CANCEL_BET_WITH_CHANGES = MODAL_FORM + "//button[@id='cancel-button']";
    public static final String SET_BET_ERROR_TEXT = "";*/

    public static final String MODAL_DIALOG = "//div[@class='arcticmodal-container']";
    public static final String MODAL_DIALOG_CLOSE = MODAL_DIALOG + "//div[text()='Закрыть']";

    public static final String MODAL_FORM = "//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-dialog-buttons']";
    public static final String MODAL_FORM_CONFIRM = MODAL_FORM + "/div/div/button[1]/span";
    public static final String MODAL_FORM_MESSAGE = MODAL_FORM + "/div[@id='alert_dialog']/span"; // Ваша ставка принята!
    public static final String MODAL_FORM_HEADER = MODAL_FORM + "/div[1]/span"; //Сообщение, Ошибка
    public static final String MODAL_FORM_CLOSE = MODAL_FORM + "/div[1]/a/span";

}
