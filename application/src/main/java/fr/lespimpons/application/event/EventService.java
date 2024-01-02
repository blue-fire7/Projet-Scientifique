package fr.lespimpons.application.event;

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
    private final Map<Class<?>, List<EventListener<?>>> listeners;
    private final Map<Class<?>, List<RequestListener<?>>> requestListeners;

    private EventService() {
        this.executorService = Executors.newCachedThreadPool();
        this.listeners = new HashMap<>();
        this.requestListeners = new HashMap<>();
    }

    public static synchronized EventService getInstance() {
        if (instance == null) {
            instance = new EventService();
        }
        return instance;
    }

    public <T> void addListener(Class<T> clazz, EventListener<T> listener) {
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
            List<EventListener<?>> eventListeners = listeners.get(clazz);

            if (eventListeners != null) {
                for (EventListener<?> listener : eventListeners) {
                    executorService.execute(() -> listener.onEvent(event));
                }
            }
        }
    }

    public void removeListener(Class<?> clazz, EventListener<?> listener) {
        synchronized (listeners) {
            List<EventListener<?>> eventListeners = listeners.get(clazz);

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
}