package dzevako.betcore.drivers.betcity;

public class BCXpath
{
    //@formatter:off
    public static final String LOGIN_INPUT = "//input[@name='login']";
    public static final String PASSWORD_INPUT = "//input[@name='pwd']";
    /**Форма оформления ставки*/
    public static final String X_BET_FORM = "//form[@id='lcart']//table[@id='liveCart']/tbody";
    public static final String X_BET_RATE = X_BET_FORM + "//tr[3]/td[2]";
    public static final String X_BET_TITLE = X_BET_FORM + "//tr[3]/td[1]/span";
    public static final String X_BET_VALUE_INPUT = X_BET_FORM + "//input[@name='sum[undefined.0]']";
    public static final String X_CANCEL_BET = X_BET_FORM + "//a/img";
    public static final String X_GAME_TITLE = X_BET_FORM + "//tr[2]/td[1]/span";
    
    public static final String X_CONFIRM = X_BET_FORM + "//input[@name='lb' and @value='Оформить']";
    public static final String X_CLEAR_BASKET = X_BET_FORM + "//a[string()='[Очистить]']";
    
    /**Информация о счете и текущих ставках*/
    public static final String X_CLIENT_INFO = "//table[@width='300px']";
    public static final String CLIENT_AUTH = "//font/b[text()='%s']";
    /**Игровое поле ставок*/
    public static final String GAME_CONTAINER = "//div[@class='all']";
    public static final String X_GAME_FIELD = "//table[@id='dt']";
    public static final String X_GAME_FIELD1 = X_GAME_FIELD + "[1]";
    public static final String X_SET = X_GAME_FIELD1 + "//tr[3]/td[1]/strong";
    public static final String SET_PTRN = X_SET + "[string()= '%s-я %s']";
    //public static final String QUARTER_PTRN = X_SET + "[string()= '%s-я четверть']";
    public static final String X_GAME_REFRESH_BTN = "//a[@class='btn refresh']";


    public static final String X_GAME_SCORE = "//td[@class='red' and contains(text(), 'Счёт')]";
    public static final String X_BET_STOP = "//tr[3]/td[text()='Приём ставок временно остановлен']"; 
    /**Страница live*/
    public static final String X_LIVE = "//a[@id='mlive']"; 
    public static final String LIVE_MENU = "//div[contains(@class, 'tlivemenu')]";
    public static final String LIVE_BETS = LIVE_MENU + "//span[text()='Live-ставки']";
    public static final String X_LIVE_RESULTS = LIVE_MENU + "//span[text()='Live-результаты']";
    public static final String SPORTS_FILTER = "//a[text()='Фильтр по видам спорта']";
    public static final String SPORTS_FILTER_CHECK_BOX = SPORTS_FILTER + "/preceding-sibling::input";
    public static final String FILTER_BOX = "//div[@class='box_filter base']";
    public static final String SPORTS_FILTER_CLEAN = FILTER_BOX + "//a[text()='Очистить все']";
    public static final String GAME_FILTER_PTRN = FILTER_BOX + "//span[@class='item-pointer ng-binding'][string()='%s']/preceding-sibling::input";
    public static final String IS_LIVE_BETS = "//li[@class='active']/a/span[text()='Live-ставки']";

  
    public static final String IS_ON_LIVE = "//a[@id='mlive' and @class='act']";
    
  

    public static final String X_RATE = X_GAME_FIELD1 + "//tr[3]/td[last()]/a";
    public static final String X_LOGOUT_BTN = "//img[@alt='Выход']";
    public static final String X_NO_AUTH = "//font[text()='регистрация']";
    public static final String SCORE = X_CLIENT_INFO + "//table/tbody/tr[4]/td[2]/b[1]";
    
    
    public static final String CUR_BETS = X_CLIENT_INFO + "//table/tbody/tr[5]/td[2]/b[1]";
    public static final String X_SCORE_HISTORY = "//a[@id='mhistory']";
    /**Форма поставленной ставки*/
    public static final String DIV_ALERT = "//div[@id='msgalert']";
    public static final String X_BET_NOT_EXISTS_ALERT = "//div[@id='msgalert', text()='Исход снят с приема']";
    public static final String INTERNET_INTERRUPTED_ALERT = "//table[@id='msgalert', contains(string(), 'Проверьте соединение с Интернетом')]";
    public static final String TABLE_ALERT = "//table[@id='msgalert']";
    public static final String X_TAKE_BET_GAME = TABLE_ALERT + "//table//tr[1]/td";
    public static final String X_TAKE_BET_RATE = TABLE_ALERT + "//table//tr[2]/td[2]";
    public static final String X_TAKE_BET_TITLE = TABLE_ALERT + "//table//tr[2]/td[1]";
    
    public static final String X_CHANGE_RATE = TABLE_ALERT + "//tr/td[text()='Изменения в линии']";

    public static final String X_CHECK_SET_BET = TABLE_ALERT + "//td[contains(string(), 'Ваша ставка принята.')]";
    
    /**Результаты игр*/
    public static final String OVER_SCORE_PATTERN = "//div[contains(string(), '%s')]/div/div[contains(string(), '%s')]/following-sibling::div";
    /**Попытка связи с инетом*/
    public static final String TRY_AGAIN = "//button[@id='errorTryAgain']";
    /**Линии ставок*/
    public static final String MAIN_LINE = "//tr[@class='lbk']";
    public static final String MAIN_LINE_COLUMN = MAIN_LINE + "/td[%s]";

    public static final String VOLLEYBALL_SET_RESULT_LINE = X_GAME_FIELD + "/tbody/tr[1][contains(string(), 'Исходы по партиям:')]/following-sibling::tr[2]";
    public static final String BASKETBALL_SET_RESULT_LINE = X_GAME_FIELD + "/tbody/tr[1][contains(string(), 'Исходы по четвертям:')]/following-sibling::tr[2]";
    public static final String HOCKEY_SET_RESULT_LINE = X_GAME_FIELD + "/tbody/tr[1][contains(string(), 'Исходы по периодам:')]/following-sibling::tr[2]";
  //@formatter:on

    /**Вкладка с результатами матчей(не лайв)*/
    public static final String GAMES_RESULTS = "//a[@id='mresults']";
    public static final String GAMES_RESULTS_DATE = "//input[@id='calendar_date']";
    public static final String GAME_RESULT_SCORE = "//table//tr/td[@class='c2'][string()=\"%s\"]/following-sibling::td";
    public static final String SPORT_FILTER = "//input[@id='filterSport']/following-sibling::a";
    public static final String CLEAR_FILTER = "//a[contains(@class, 'uncheck_all')]";
    public static final String CHOOSE_GAME = "//li/span[string()=\"%s\"]/preceding-sibling::input";
    public static final String GAMES_RESULTS_EMPTY_PLACE = "//form[@id='mform']";
    public static final String GAME_CONTEXT_STRING = "//table/tbody/tr/td[@class='c2'][string()=\"%s\"]/../../../preceding-sibling::div[@class='title']";
    public static final String PREV_DAY_BTN = "//a[@class='btn arrow left']";
    public static final String NEXT_DAY_BTN = "//a[@class='btn arrow right']";
}
