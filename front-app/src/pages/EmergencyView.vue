<template>
  <div style="display: flex">
    <FireMap class="col-8" :sensors="fireSensors" :trucks="trucks" />
    <MapCommand class="col-4" :sensors="fireSensors" :trucks="trucks" />
  </div>
</template>

<script setup>
import { onBeforeMount, ref } from 'vue';
import MapCommand from '../components/MapCommand.vue';
import { fireService, truckService } from '../services/index';
import FireMap from '../components/FireMap.vue';

const fireSensors = ref([]);

const trucks = ref([]);

onBeforeMount(() => {
  fireSensors.value = fireService.getFireSensors();
  trucks.value = truckService.getTrucks();
  console.log(fireSensors.value);
});
</script>

<style scoped></style>
