package dzevako.betcore.common.base;

/**
 * Пара объектов
 * @author dzevako
 * @since Nov 8, 2014
 */
public class Pair<X, Y>
{
    private X first;
    private Y second;

    public Pair(X first, Y second)
    {
        this.first = first;
        this.second = second;
    }

    public X getFirst()
    {
        return first;
    }

    public Y getSecond()
    {
        return second;
    }

    @Override
    public String toString()
    {
        return first.toString() + ":" + second.toString();
    }
}
