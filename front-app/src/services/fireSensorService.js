import { useSensorStore } from '../store/sensorStore';
import { useStationStore } from '../store/stationStore';

export default class FireService {
  constructor() {}

  async getFireSensors() {
    if (true) {
      let result = await fetch(`https://593e6da2a2715d.lhr.life/sensors`);
      let resultObject = await result.json();
      useSensorStore().fireSensors = resultObject;
    } else {
      console.log('trigg');
      const store = useSensorStore();
      store.sensors = [
        {
          id: 12,
          level: 9,
          lat: 45.786191,
          long: 4.86105,
        },
      ];
    }
  }

  async getFireStations() {
    let result = await fetch(`http://localhost:8080/fire-stations`);
    useStationStore().fireStations = await result.json();
  }

}
