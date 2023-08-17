package br.ufes.inf.nemo.vpzy.utils;

import br.ufes.inf.nemo.vpzy.logging.Logger;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.logging.Level;

/**
 * Provides helper methods regarding generic types in Java.
 *
 * @author Vítor E. Silva Souza (<a href="http://www.inf.ufes.br/~vitorsouza/">...</a>)
 */
public final class ReflectionUtil {
    private ReflectionUtil() {

    }

    public static Class<?> determineTypeArgument(Class<?> clazz) {
        Type genericSuperClass = clazz.getGenericSuperclass();
        ParameterizedType parametrizedType = null;

        while (parametrizedType == null) {
            if (genericSuperClass instanceof ParameterizedType) {
                parametrizedType = (ParameterizedType) genericSuperClass;
            } else {
                genericSuperClass = ((Class<?>) genericSuperClass).getGenericSuperclass();
            }
        }

        Type[] genericTypes = parametrizedType.getActualTypeArguments();
        Class<?> result = genericTypes.length == 0 ? null : (Class<?>) genericTypes[0];
        Logger.log(Level.FINE, "Determined \"{0}\" as the generic type parameter for class \"{1}\"",
                new Object[] { result, clazz });
        return result;
    }
}
