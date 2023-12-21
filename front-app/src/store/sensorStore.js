import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useSensorStore = defineStore('sensor', () => {
  const fireSensors = ref([]);
  const truckSensors = ref([]);

  function updateFireSensor(sensor) {
    console.log('update fire sensor');
    let oldSensor = fireSensors.value.find((s) => sensor.id === s.id);
    if (oldSensor) {
      oldSensor.level = sensor.level;
    } else {
      fireSensors.value.push(sensor);
      console.log(fireSensors.value);
    }
  }

  return { fireSensors, truckSensors, updateFireSensor };
});
