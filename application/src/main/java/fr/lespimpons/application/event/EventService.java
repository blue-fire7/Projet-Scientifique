package fr.lespimpons.application.event;

import fr.lespimpons.application.logic.Listener;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventService {
    private static EventService instance;

    private final ExecutorService executorService;
    private final Map<Class<?>, List<EventListener>> listeners;
    private final Map<Class<?>, List<RequestListener<?>>> requestListeners;

    private EventService() {
        this.executorService = Executors.newCachedThreadPool();
        this.listeners = new HashMap<>();
        this.requestListeners = new HashMap<>();

        this.getAllListener();
    }

    public static synchronized EventService getInstance() {
        if (instance == null) {
            instance = new EventService();
        }
        return instance;
    }

    private <T> void addListener(Class<T> clazz, EventListener listener) {
        synchronized (listeners) {
            listeners.computeIfAbsent(clazz, k -> new ArrayList<>()).add(listener);
        }
    }

    public <T> void addRequestListener(Class<T> clazz, RequestListener<T> listener) {
        synchronized (requestListeners) {
            requestListeners.computeIfAbsent(clazz, k -> new ArrayList<>()).add(listener);
        }
    }

    public void publishEvent(Object event) {
        Class<?> clazz = event.getClass();
        synchronized (listeners) {
            List<EventListener> eventListeners = listeners.get(clazz);

            if (eventListeners != null) {
                for (EventListener listener : eventListeners) {
                    executorService.execute(() -> {
                        try {
                            listener.apply(event);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        }
    }

    public void removeListener(Class<?> clazz, EventListener listener) {
        synchronized (listeners) {
            List<EventListener> eventListeners = listeners.get(clazz);

            if (eventListeners != null) {
                eventListeners.remove(listener);
            }
        }
    }

    public List<?> requestList(Class<?> clazz) {
        synchronized (requestListeners) {
            List<RequestListener<?>> eventListeners = requestListeners.get(clazz);

            if (eventListeners != null) {
                try {
                    return (List<?>) executorService.submit(() -> eventListeners.get(0).onRequest()).get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
            return List.of();
        }
    }


    public void getAllListener() {
        new Reflections(new ConfigurationBuilder().addUrls(ClasspathHelper.forPackage("fr.lespimpons.application"))
                .addScanners(Scanners.MethodsAnnotated)).getMethodsAnnotatedWith(Listener.class).forEach(method -> {

            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new RuntimeException("Method annotated with @Listener must have only one parameter");
            }

            Class<?> parameterType = parameterTypes[0];
            if (parameterType.isPrimitive()) {
                throw new RuntimeException("Method annotated with @Listener must have an object as parameter");
            }

            try {
                if (!method.getDeclaringClass().getMethod("getInstance").getReturnType()
                        .equals(method.getDeclaringClass())) {
                    throw new RuntimeException("Method getInstance must return the class");
                }
                addListener(parameterType, event -> {
                    try {
                        Object instance = method.getDeclaringClass().getMethod("getInstance").invoke(null);
                        return method.invoke(instance, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });


            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Method getInstance not found of");
            }
        });
    }
}