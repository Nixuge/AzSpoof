package me.nixuge.azbypass.utils;

import java.lang.reflect.Field;
import java.util.Map;

import com.google.common.collect.Maps;

/* 
 * This class was taken in a good part from Pokechu22's WorldDownloader.
 * Credits there.
 */

public class ReflectionUtils {
    static final Map<Class<?>, Map<Class<?>, Field>> CACHE = Maps.newHashMap();

    /**
     * Uses Java's reflection API to find an inaccessible field of the given
     * type in the given class.
     * <p>
     * This method's result is undefined if the given class has multiple
     * fields of the same type.
     *
     * @param typeOfClass
     *            Class that the field should be read from
     * @param typeOfField
     *            The type of the field
     * @return The field, with {@link Field#setAccessible(boolean)} already called
     */
    public static Field findField(Class<?> typeOfClass, Class<?> typeOfField) {
        if (CACHE.containsKey(typeOfClass)) {
            Map<Class<?>, Field> fields = CACHE.get(typeOfClass);
            if (fields.containsKey(typeOfField)) {
                return fields.get(typeOfField);
            }
        }

        Field[] fields = typeOfClass.getDeclaredFields();

        for (Field f : fields) {
            if (f.getType().equals(typeOfField)) {
                try {
                    f.setAccessible(true);

                    if (!CACHE.containsKey(typeOfClass)) {
                        CACHE.put(typeOfClass, Maps.<Class<?>, Field>newHashMap());
                    }

                    CACHE.get(typeOfClass).put(typeOfField, f);

                    return f;
                } catch (Exception e) {
                    throw new RuntimeException(
                            "AzBypass: Couldn't get private Field of type \""
                                    + typeOfField + "\" from class \"" + typeOfClass
                                    + "\" !", e);
                }
            }
        }

        throw new RuntimeException(
                "AzBypass: Couldn't find any Field of type \""
                        + typeOfField + "\" from class \"" + typeOfClass
                        + "\" !");
    }
}
