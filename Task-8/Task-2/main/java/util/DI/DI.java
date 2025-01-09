package util.DI;

import annotation.OwnInject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DI {
    private static final Map<Class, Object> singletonInstances = new HashMap<>();
    private static DI instance;
    private DI() {}
    public <T> void registerBean(Class<T> beanType, T bean) {
        singletonInstances.put(beanType, bean);
    }
    public <T> T getBean(Class<T> beanType){
        return (T) singletonInstances.get(beanType);
    }
    public synchronized static DI getInstance() {
        if (instance == null) {
            instance = new DI();
        }
        return instance;
    }
    public static void infectDependencies(Object target) throws IllegalAccessException {
        Class<?> targetClass = target.getClass();
        for (Field field : targetClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(OwnInject.class)) {
                Class<?> fieldType = field.getType();
                Object dependency = singletonInstances.get(fieldType);
                if (dependency == null) {
                    throw new RuntimeException("No dependency found for type: " + fieldType.getName());
                }
                field.setAccessible(true);
                field.set(target, dependency);
            }
        }
    }
}
