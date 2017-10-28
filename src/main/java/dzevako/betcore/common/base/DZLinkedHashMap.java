package dzevako.betcore.common.base;

import java.util.LinkedHashMap;

/**
 * LinkedHashMap c возможностью достать первый и последний элемент
 * @author dzevako
 * @since Apr 19, 2015
 */
public class DZLinkedHashMap<K, V> extends LinkedHashMap<K, V>
{
    private static final long serialVersionUID = 1L;
    private K firstKey = null;
    private K lastKey = null;

    public K getFirstKey()
    {
        return firstKey;
    }

    public K getLastKey()
    {
        return lastKey;
    }

    @Override
    public V put(K key, V value)
    {
        if (keySet().isEmpty())
        {
            firstKey = key;
        }
        lastKey = key;
        return super.put(key, value);
    }
}
