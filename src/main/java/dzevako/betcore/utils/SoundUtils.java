package dzevako.betcore.utils;

import java.io.IOException;

/**
 * Sound utility class. 
 * @author dzevako
 * @since Aug 20, 2015
 */
public final class SoundUtils
{

    public static void alarm()
    {
        try
        {
            Runtime.getRuntime().exec(getSoundCommand(200));
            Thread.sleep(2000);
            Runtime.getRuntime().exec(getSoundCommand(300));
            Thread.sleep(2000);
            Runtime.getRuntime().exec(getSoundCommand(400));
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private static String getSoundCommand(int freq)
    {
        return "speaker-test -t sine -f " + freq + " -l 1 -p -1";
    }
}
