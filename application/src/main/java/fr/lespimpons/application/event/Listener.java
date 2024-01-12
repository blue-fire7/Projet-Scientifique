package fr.lespimpons.application.event;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation is used to mark methods that should be invoked when a certain event occurs.
 * The value of the annotation should be the class of the event that the method is interested in.
 *
 * @Target(ElementType.METHOD) - This indicates that this annotation can be used on methods.
 * @Retention(RetentionPolicy.RUNTIME) - This indicates that the annotation should be available at runtime.
 *
 * Example usage:
 *
 * @Listener(MyEvent.class)
 * public void onMyEvent(MyEvent event) {
 *     // handle the event
 * }
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface Listener {

    /**
     * The type of the event that the method is interested in.
     *
     * @return the class of the event
     */
    Class<?> value();


}
