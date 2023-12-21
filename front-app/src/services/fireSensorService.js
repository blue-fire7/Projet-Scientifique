import { useSensorStore } from '../store/sensorStore';

export default class FireService {
  constructor() {}

  async getFireSensors() {
    if (true) {
      let result = await fetch(`https://a7062995db3c53.lhr.life/sensors`);
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
}
