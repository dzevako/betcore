/**
 * 
 */
package dzevako.betcore.utils;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

import dzevako.betcore.common.base.HasTitle;

/**
 * Утилитарные методы для работы со строками
 * @author dzevako
 * @since Nov 1, 2014
 */
public class StringUtils
{
    /**
     * Получить строку, содержащую указанную строку, 
     * дополненную пробельными символами до длины length
     */
    public static String getCompleteString(String str, int length)
    {
        while (str.length() < length)
        {
            str = str + " ";
        }
        return str;
    }

    public static String getMessage(Exception e)
    {
        if (e == null || e.getMessage() == null)
        {
            return "null";
        }
        String msg = e.getMessage();
        String ellipsis = "...";
        String enter = "\n";
        int defLength = 200;
        if (msg.contains(enter))
        {
            String[] parts = msg.split(enter);
            if (parts[0].length() > defLength)
            {
                return parts[0].substring(0, defLength) + ellipsis;
            }
            else
            {
                return parts[0] + enter + (parts[1].length() > defLength ? (parts[1].substring(0, defLength) + ellipsis)
                        : (parts[1] + ellipsis));
            }
        }
        else
        {
            return msg.length() > defLength ? (msg.substring(0, defLength) + ellipsis) : msg;
        }
    }

    public static List<String> getTitles(Collection<? extends HasTitle> objects)
    {
        List<String> titles = Lists.newArrayList();
        for (HasTitle object : objects)
        {
            titles.add(object.getTitle());
        }
        return titles;
    }

    public static String getTitlesString(Collection<? extends HasTitle> objects)
    {
        if (objects.isEmpty())
        {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (HasTitle object : objects)
        {
            sb.append("'").append(object.getTitle()).append("'").append(", ");
        }
        String titles = sb.toString();
        return titles.toString().substring(0, titles.length() - 3);
    }

    /**
     * Получить строку из коллекции, каждый элемент в одинарных кавычках 
     */
    public static String toQuotedString(Iterable<?> objects)
    {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Object object : objects)
        {
            sb.append(first ? "'" : ", '").append(object.toString()).append("'");
            first = false;
        }
        return sb.toString();
    }

    public static String toString(Iterable<?> objects, String delimiter)
    {
        StringBuilder sb = new StringBuilder();
        for (Object object : objects)
        {
            sb.append(object.toString()).append(delimiter);
        }
        return sb.toString();
    }
}
