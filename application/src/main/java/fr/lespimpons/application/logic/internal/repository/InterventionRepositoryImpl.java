package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.internal.entity.Intervention;

public class InterventionRepositoryImpl extends Repository<Intervention, Long> {

private static InterventionRepositoryImpl instance;

    public static InterventionRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new InterventionRepositoryImpl();
        }
        return instance;
    }
}
