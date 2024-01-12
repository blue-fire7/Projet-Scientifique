<template>
  <div style="display: flex">
    <FireMap
      class="col-8"
      :fireSensors="sensorStore.fireSensors"
      :trucks="trucks"
      :stations="stationStore.fireStations"
    />
    <MapCommand
      class="col-4"
      :sensors="sensorStore.fireSensors"
      :trucks="trucks"
    />
  </div>
</template>

<script setup>
import { onBeforeMount, ref } from 'vue';
import MapCommand from '../components/MapCommand.vue';
import { fireService, truckService } from '../services/index';
import FireMap from '../components/FireMap.vue';
import { useSensorStore } from '../store/sensorStore';
import { useStationStore } from '../store/stationStore';

const trucks = ref([]);

const sensorStore = useSensorStore();
const stationStore = useStationStore();

onBeforeMount(() => {
  // TODO: move this part to call the init call somewhere else or maybe not xd :)
  fireService.getFireSensors();
  trucks.value = truckService.getTrucks();
  fireService.getFireStations();
});
</script>

<style scoped></style>
