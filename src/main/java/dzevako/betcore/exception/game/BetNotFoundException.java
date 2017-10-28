/**
 * 
 */
package dzevako.betcore.exception.game;

import java.util.Set;

import dzevako.betcore.bettypes.BetKey;
import dzevako.betcore.game.Game;
import dzevako.betcore.utils.StringUtils;

/**
 * Ставка определенного типа не найдена в игре
 * @author dzevako
 * @since Oct 21, 2014
 */
public class BetNotFoundException extends GamePlayException
{
    private static final long serialVersionUID = 1L;

    public BetNotFoundException(Game game)
    {
        super(game, "It's not possible to find bet of any types.");
    }

    public BetNotFoundException(Game game, BetKey key)
    {
        super(game, "It's not possible to find bet of type '" + key.getInfo() + "'.");
    }

    public BetNotFoundException(Game game, Set<BetKey> keys)
    {
        super(game, "It's not possible to find bet of following types " + StringUtils.toQuotedString(keys) + ".");
    }
}
