package fr.lespimpons.application.logic.internal.service;

import fr.lespimpons.application.logic.LogicManagement;

public class InitService {


    public void init() {
        LogicManagement.getInstance();
    }
}
