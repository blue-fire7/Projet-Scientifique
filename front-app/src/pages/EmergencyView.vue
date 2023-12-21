<template>
  <div style="display: flex">
    <FireMap
      class="col-8"
      :fireSensors="sensorStore.fireSensors"
      :trucks="trucks"
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

const trucks = ref([]);

const sensorStore = useSensorStore();

onBeforeMount(() => {
  // TODO: move this part to call the init call somewhere else or maybe not xd :)
  fireService.getFireSensors();
  trucks.value = truckService.getTrucks();
});
</script>

<style scoped></style>
