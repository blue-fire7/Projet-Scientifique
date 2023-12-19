/*
package fr.lespimpons.simulator.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenerateFireService {


    List<FireImpl> fires = new ArrayList<>();
    List<FireTruck> fireTrucks = new ArrayList<>();

    @Scheduled(fixedRate = 1000)
    public void update() {

        for (FireImpl fire : fires) {

            for (FireTruck fireTruck : fireTrucks) {
                fire.extinguishFire(fireTruck);
            }
        }

    }



}
*/
