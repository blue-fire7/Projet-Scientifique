package fr.lespimpons.application.logic.internal.entity;


import fr.lespimpons.application.pojo.geometry.Point;

public interface Vehicle {

    void move(Point destination);

    void returnToBase();
}
