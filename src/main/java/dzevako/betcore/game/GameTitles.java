package dzevako.betcore.game;

import java.util.Set;

import com.google.common.collect.Sets;

/**
 * Названия игр. Везде использовать эти константы.
 * @author dzevako
 * @since Oct 10, 2015
 */
public interface GameTitles
{
    public final static String VOLLEYBALL = "Волейбол";
    public final static String BASKETBALL = "Баскетбол";
    public final static String HOCKEY = "Хоккей";
    public final static String BEACH_VOLLEYBALL = "Пляжный волейбол";
    public final static String PINGPONG = "Настольный теннис";

    public static final Set<String> SET = Sets.newHashSet(VOLLEYBALL, BASKETBALL, HOCKEY);
}