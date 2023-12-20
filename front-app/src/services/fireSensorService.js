import { useSensorStore } from '../store/sensorStore';

export default class FireService {
  constructor() {}

  async getFireSensors() {
    if (import.meta.env.VITE_SERVER_URL) {
      let result = await fetch(`${import.meta.env.VITE_SERVER_URL}/sensors`);
      let resultObject = await result.json();
      console.log(resultObject);
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
