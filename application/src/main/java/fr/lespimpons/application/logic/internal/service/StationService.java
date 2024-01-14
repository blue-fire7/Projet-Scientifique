package fr.lespimpons.application.logic.internal.service;

import fr.lespimpons.application.logic.internal.entity.StationImpl;
import fr.lespimpons.application.logic.internal.repository.StationImplRepository;

import java.util.List;

public class StationService {

    private static StationService instance;
    private final StationImplRepository stationImplRepository = StationImplRepository.getInstance();

    private StationService() {
    }

    public static StationService getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (StationService.class) {
            if (instance == null) {
                instance = new StationService();
            }
        }
        return instance;
    }

    public List<StationImpl> getAllFireStations() {
        return stationImplRepository.findAll();
    }

    public StationImpl getFireStation(Long id) {
        return stationImplRepository.findById(id);
    }
}
