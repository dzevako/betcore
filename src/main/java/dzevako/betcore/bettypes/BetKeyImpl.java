package dzevako.betcore.bettypes;

/**
 * Объект содержащий ключ ставки и информацию для проверки корректности
 * @author dzevako
 * @since Aug 24, 2015
 */
public class BetKeyImpl implements BetKey
{
    private String rateKey;
    private String valueKey;
    private String info;
    private boolean isForGame;

    public BetKeyImpl(String rateKey, String valueKey, String info, boolean isForGame)
    {
        this.rateKey = rateKey;
        this.valueKey = valueKey;
        this.info = info;
        this.isForGame = isForGame;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof BetKey)
        {
            return isForGame == ((BetKey)obj).isForGame() && info.equals(((BetKey)obj).getInfo());
        }
        return false;
    }

    @Override
    public String getInfo()
    {
        return info;
    }

    @Override
    public String getRateKey()
    {
        return rateKey;
    }

    @Override
    public String getValueKey()
    {
        return valueKey;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode() + 1;
    }

    @Override
    public boolean isForGame()
    {
        return isForGame;
    }

    @Override
    public String toString()
    {
        return "BetKey[vKey=" + valueKey + ", rKey=" + rateKey + ", info=" + info + ", forGame=" + isForGame + "]";
    }

}
