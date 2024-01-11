package fr.lespimpons.application.event;

@FunctionalInterface
public interface EventListener {

    Object apply(Object event) throws Exception;

}
