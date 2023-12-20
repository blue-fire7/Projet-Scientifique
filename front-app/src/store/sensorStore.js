import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useSensorStore = defineStore('sensor', () => {
  const sensors = ref([]);

  function updateSensor(sensor) {
    let oldSensor = sensors.find((s) => sensor.id === s.id);
    if (oldSensor) {
      oldSensor.level = sensor.level;
    } else {
      sensors.value.push(sensor);
    }
  }

  return { sensors };
});
