package dzevako.betcore.game;

import org.openqa.selenium.WebElement;

/**
 * Фабрика по созданию элементов игр
 * @author dzevako
 * @since Mar 15, 2015
 */
public interface GameFactory
{
    GameElement create(WebElement element, String type);
}
