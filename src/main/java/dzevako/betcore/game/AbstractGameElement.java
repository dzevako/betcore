package dzevako.betcore.game;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import dzevako.betcore.web.driver.SiteKeys;

/**
 * Абстрактная Реализация элемента игры
 * @author dzevako
 * @since Sep 19, 2014
 */
public abstract class AbstractGameElement extends AbstractGame implements GameElement
{
    protected WebElement titleElement;

    public AbstractGameElement(WebElement element, SiteKeys keys)
    {
        titleElement = element.findElement(By.xpath(keys.getGameTitleElementKey()));
        title = keys.getGameTitle(titleElement);

        String scoreText = keys.getGameScore(element);

        if (!isTimed())
        {
            score = scoreText;
            return;
        }

        score = scoreText;
        time = keys.getGameTime(element);
    }

    @Override
    public void open()
    {
        titleElement.click();
    }

    /**
     * Получить счет и время из строки со счетом
     * Актуально для Бетсити 
     */
    //TODO убрать в бетсити кейс
    private void processScoreAndTime(String scoreText)
    {
        String[] parts = scoreText.split("мин");
        score = parts[parts.length - 1];
        if (parts.length > 1)
        {
            time = Integer.parseInt(parts[0]);
        }
    }
}