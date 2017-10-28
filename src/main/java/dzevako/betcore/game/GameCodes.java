package dzevako.betcore.game;

import java.util.Set;

import com.google.common.collect.Sets;

/**
 * Коды игр
 *
 * @author dzevako
 * @since Jan 11, 2016
 */
public interface GameCodes
{
    public final static String VOLLEYBALL = "volleyball";
    public final static String BASKETBALL = "basketball";
    public final static String HOCKEY = "hockey";
    public final static String BEACH_VOLLEYBALL = "beach_volleyball";
    public final static String PINGPONG = "ping_pong";

    public static final Set<String> SET = Sets.newHashSet(VOLLEYBALL, BASKETBALL, HOCKEY);
}
