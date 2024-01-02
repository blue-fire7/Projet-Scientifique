package fr.lespimpons.application.event;

import java.util.List;

public interface RequestListener<T> {


    List<T> onRequest();

}
