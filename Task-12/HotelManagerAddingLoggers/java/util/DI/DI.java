//package util.DI;
//
//import annotation.OwnInject;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Field;
//import java.sql.Connection;
//import java.util.HashMap;
//import java.util.Map;
//
//public class DI {
//    private static final Map<Class<?>, Object> singletonInstances = new HashMap<>();
//    private static DI instance;
//
//    private DI() {}
//
//    public static synchronized DI getInstance() {
//        if (instance == null) {
//            instance = new DI();
//        }
//        return instance;
//    }
//
//    public <T> void registerBean(Class<T> beanType, T bean) {
//        singletonInstances.put(beanType, bean);
//    }
//
//    public <T> T getBean(Class<T> beanType) {
//        return beanType.cast(singletonInstances.get(beanType));
//    }
//
//    public <T> T create(Class<T> clazz) {
//
//        if (clazz.isInterface() || clazz == Connection.class) {
//            throw new IllegalArgumentException("Cannot instantiate interface or Connection: " + clazz.getName());
//        }
//
//        try {
//            Constructor<T> constructor = clazz.getDeclaredConstructor();
//            constructor.setAccessible(true);
//            T instance = constructor.newInstance();
//            inject(instance);
//            registerBean(clazz, instance);
//            return instance;
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to create instance of " + clazz.getName(), e);
//        }
//    }
//
//    public void inject(Object target) {
//        Class<?> targetClass = target.getClass();
//        for (Field field : targetClass.getDeclaredFields()) {
//            if (field.isAnnotationPresent(OwnInject.class)) {
//                Class<?> dependencyType = field.getType();
//                Object dependency = singletonInstances.get(dependencyType);
//
//                if (dependency == null) {
//                    if (dependencyType == Connection.class) {
//                        throw new RuntimeException("Connection must be registered before using DI.");
//                    }
//                    dependency = create(dependencyType);
//                }
//
//                try {
//                    field.setAccessible(true);
//                    field.set(target, dependency);
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException("Failed to inject dependency into " + target.getClass().getName(), e);
//                }
//            }
//        }
//    }
//}

package util.DI;

import annotation.OwnInject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DI {
    private static final Map<Class<?>, Object> singletonInstances = new HashMap<>();
    private static DI instance;

    private DI() {}

    public static synchronized DI getInstance() {
        if (instance == null) {
            instance = new DI();
        }
        return instance;
    }

    public <T> void registerBean(Class<T> beanType, T bean) {
        singletonInstances.put(beanType, bean);
    }

    public <T> T getBean(Class<T> beanType) {
        return beanType.cast(singletonInstances.get(beanType));
    }

    public <T> T create(Class<T> clazz) {
        try {
            if (singletonInstances.containsKey(clazz)) {
                return (T) singletonInstances.get(clazz);
            }

            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T instance = constructor.newInstance();
            inject(instance);
            registerBean(clazz, instance);
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + clazz.getName(), e);
        }
    }

    public void inject(Object target) {
        Class<?> targetClass = target.getClass();
        for (Field field : targetClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(OwnInject.class)) {
                Class<?> dependencyType = field.getType();
                Object dependency = singletonInstances.get(dependencyType);
                if (dependency == null) {
                    dependency = create(dependencyType);
                }
                try {
                    field.setAccessible(true);
                    field.set(target, dependency);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to inject dependency into " + target.getClass().getName(), e);
                }
            }
        }
    }
}
