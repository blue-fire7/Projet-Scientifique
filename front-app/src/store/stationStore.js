import { defineStore } from "pinia";
import { ref } from "vue";

export const useStationStore = defineStore("station", () => {
  const fireStations = ref([]);

  function updateFireStation(sensor) {
    console.log("update fire station");
    fireStations.value.push(sensor);
  }

  return { fireStations, updateFireStation };
});
