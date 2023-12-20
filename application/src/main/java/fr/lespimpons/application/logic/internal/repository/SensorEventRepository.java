package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.dto.SensorDto;
import fr.lespimpons.application.logic.internal.entity.SensorEvent;
import fr.lespimpons.application.logic.internal.entity.SensorEventId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SensorEventRepository extends JpaRepository<SensorEvent, SensorEventId> {



}