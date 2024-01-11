<template>
  <div class="bg-ground p-3 map-command">
    <!-- Header -->
    <div class="flex justify-center">
      <span class="size-2xl">État du réseau</span>
    </div>

    <template v-if="sensors.length < 1">
      <div class="w-full flex justify-center p-2">
        <span class="loader"></span>
      </div>
    </template>

    <template v-else>
      <div>
        <span>Montrer tous les capteurs</span>
        <input type="checkbox" v-model="showAllSensors" />
      </div>

      <div class="flex flex-column">
        <!-- Sensors statut -->
        <div v-if="props.sensors.length > 0" class="flex flex-column gap-1">
          <span class="size-xl"> État des capteurs </span>
          <div
            v-for="(sensor, index) in props.sensors.filter(
              (sensor) =>
                (!appOptions.showAllSensor &&
                  sensor.level &&
                  sensor.level > 0) ||
                appOptions.showAllSensor
            )"
            :key="index"
            class="flex flex-column bg-secondary b-round p-1"
          >
            <span>Niveau de feu: {{ sensor.level }}</span>
            <span>Position: </span>
            <div class="flex flex-column">
              <span>Latitude: {{ sensor.latitude }}</span>
              <span>Longitude: {{ sensor.longitude }}</span>
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
    </template>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import useAppOptionStore from '../store/optionStore';

const appOptions = useAppOptionStore();

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

// Options
const showAllSensors = computed({
  get() {
    return appOptions.showAllSensor;
  },
  set(val) {
    appOptions.showAllSensor = val;
  },
});
</script>
