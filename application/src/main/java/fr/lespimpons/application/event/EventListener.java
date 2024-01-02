package fr.lespimpons.application.event;

public interface EventListener<T> {

    void onEvent(Object event);
}
