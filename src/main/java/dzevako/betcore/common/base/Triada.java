/**
 * 
 */
package dzevako.betcore.common.base;

/**
 * Тройка данных
 * @author dzevako
 * @since Nov 23, 2014
 */
public class Triada<X, Y, Z> extends Pair<X, Y>
{
    private Z third;

    public Triada(X first, Y second, Z third)
    {
        super(first, second);
        this.third = third;
    }

    public Z getThird()
    {
        return third;
    }
}
