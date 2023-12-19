<template>
  <div class="bg-ground p-3">
    <!-- Header -->
    <div class="flex justify-center">
      <span class="size-2xl">État du réseau</span>
    </div>

    <div class="flex flex-column gap-2">
      <!-- Sensors statut -->
      <div v-if="props.sensors.length > 0">
        <span class="size-xl"> État des capteurs </span>
        <div
          v-for="(sensor, index) in props.sensors"
          :key="index"
          class="flex flex-column bg-secondary b-round p-1"
        >
          <span>Niveau de feu: {{ sensor.level }}</span>
          <span>Position: </span>
          <div class="flex flex-column">
            <span>Latitude: {{ sensor.lat }}</span>
            <span>Longitude: {{ sensor.long }}</span>
          </div>
        </div>
      </div>

      <!-- Trucks -->
      <div v-if="props.trucks.length > 0">
        <span class="size-xl">État des Camions</span>
        <div
          v-for="(truck, index) in props.trucks"
          :key="index"
          class="flex flex-column bg-secondary b-round p-1"
        >
          <span>Type de camion: {{ truck.type }}</span>
          <span>Position: </span>
          <div class="flex flex-column">
            <span>Latitude: {{ truck.lat }}</span>
            <span>Longitude: {{ truck.long }}</span>
          </div>
        </div>
      </div>
    </div>

    <div v-if="props.sensors.length < 0" class="flex justify-center">
      Aucune urgence en cours
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';

const props = defineProps({
  sensors: {
    default: [],
    type: Array,
  },
  trucks: {
    default: [],
    type: Array,
  },
});

onMounted(() => {
  console.log(props.sensors);
});
</script>
