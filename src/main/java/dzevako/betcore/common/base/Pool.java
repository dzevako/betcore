package dzevako.betcore.common.base;

import java.util.ArrayList;

/**
 * Пул объектов, содержащий не более maxSize элементов
 * @author dzevako
 * @since Sep 16, 2015
 */
public class Pool<T> extends ArrayList<T>
{
    private static final long serialVersionUID = 1L;
    private int maxSize;

    public Pool(int maxSize)
    {
        this.maxSize = maxSize;
    }

    @Override
    public boolean add(T e)
    {
        boolean add = super.add(e);
        if (size() > maxSize)
        {
            remove(0);
        }
        return add;
    }
}
