package fr.lespimpons.simulator.services;

import fr.lespimpons.simulator.entity.Fire;
import fr.lespimpons.simulator.repository.FireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FireService {
    private final FireRepository repository;

    public List<Fire> findAll() {
        return repository.findAll();
    }
}
