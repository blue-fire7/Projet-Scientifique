package fr.lespimpons.application.logic.internal.mapper;

import fr.lespimpons.application.logic.dto.FireTruckDto;
import fr.lespimpons.application.logic.internal.entity.FireTruck;

import java.math.BigDecimal;

public class FireTruckMapper {


    public static FireTruckDto toDto(FireTruck fireTruck) {


        BigDecimal longitude = fireTruck.getLongitude();
        if (longitude == null) {
            longitude = BigDecimal.ZERO;
        }

        BigDecimal latitude = fireTruck.getLatitude();
        if (latitude == null) {
            latitude = BigDecimal.ZERO;
        }
        return new FireTruckDto(fireTruck.getId(), longitude.doubleValue(), latitude.doubleValue(), fireTruck.getFireTruckType()
                .getType());
    }
}
