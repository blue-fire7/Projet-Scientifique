<template>
  <div class="appContainer">
    <div class="col-8">
      <div id="map" style="width: 100%; height: 100%"></div>
    </div>
    <div class="map-infos col-4 bg-ground p-2">
      <div class="app-actions">
        <h2>Placer vos incendies</h2>
      </div>

      <!-- {{ fireDataList }} -->

      <div class="coordinates flex flex-column p-1">
        <span v-for="(geoJson, index) in listFires" :key="index">
          <strong>Feu n°{{ index + 1 }} :</strong>
          {{ geoJson.getLatLng().lat.toFixed(6) }},{{
            geoJson.getLatLng().lng.toFixed(6)
          }}
        </span>
      </div>
      <!-- coordinatesDisplay.value = listFires.value .map( (geojson, index) =>
      `<strong>Feu n°${index + 1} :</strong> ${geojson .getLatLng()
      .lat.toFixed(6)}, ${geojson.getLatLng().lng.toFixed(6)}` ) .join('<br /><br />'); -->
      <div class="validation">
        <button class="reset" @click="reset">Réinitialiser</button>
        <button class="launch" @click="launch">Lancer !</button>
        <!-- <button @click="socketService.sendFires()"></button> -->
        <button @click="stopSimulation">Stop simulation</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, computed, watch } from 'vue';
import SensorService from '../services/SensorService';
import SocketService from '../services/SocketService';
import L from 'leaflet';
import useFireStore from '../store/fireStore';
import { generateRID } from '../helpers/id';

const listFires = ref([]);
const map = ref(null);

const fireStore = useFireStore();
const socketService = new SocketService();

const fireCircles = ref({});

watch(
  () => fireStore.fireList,
  () => {
    fireStore.fireList.forEach((fire) => {
      if (fireCircles.value[fire.id]) {
        fireCircles.value[fire.id].setRadius(fire.diameter);
      } else {
        let circle = L.circle([fire.latitude, fire.longitude], {
          color: 'red',
          fillColor: '#f03',
          fillOpacity: 0.5,
          radius: 10,
        }).addTo(map.value);
        fireCircles.value[fire.id] = circle;
      }
    });
  },
  { deep: true }
);

const fireDataList = computed({
  get() {
    return listFires.value.map((fire) => ({
      latitude: fire.getLatLng().lat,
      longitude: fire.getLatLng().lng,
      diameter: fire.options.radius,
      power: 0.5,
    }));
  },
});

const stopSimulation = () => {
  socketService.sendStopSimulation();
};

var compteur = 1;

onMounted(() => {
  // Initialiser la carte Leaflet
  map.value = L.map('map').setView([45.76667, 4.88333], 14);
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(
    map.value
  );

  // Charger les capteurs après l'initialisation de la carte
  loadSensors();

  // connectWebSocket();

  // Ajoutez un feu sur la carte
  map.value.on('click', addFireOnClick);
});

function loadSensors() {
  SensorService.getSensors().then((response) => {
    const sensors = response.data;
    createMarkers(sensors);
  });
}

function createMarkers(sensors) {
  // Parcourir les capteurs et créer un marqueur pour chaque capteur
  sensors.forEach((sensor) => {
    const marker = L.marker([sensor.latitude, sensor.longitude]).addTo(
      map.value
    );
    marker.bindPopup(`<b>${sensor.latitude}</b><br>${sensor.longitude}`);
  });
}

function getRandomInt(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

function addFireOnClick(event) {
  // Récupérez les coordonnées du feu
  const clickedLat = event.latlng.lat;
  const clickedLng = event.latlng.lng;

  // Créez un GeoJSON autour du point cliqué avec un certain diamètre (par exemple, 1 kilomètre)
  const fire = L.circle([clickedLat, clickedLng], {
    color: 'red',
    fillColor: '#f03',
    fillOpacity: 0.5,
    power: getRandomInt(1, 9),
    radius: 200, // 1 kilomètre en mètres
  }).addTo(map.value);

  // Ajoutez le nouveau GeoJSON à la liste
  listFires.value.push(fire);

  // Mettez à jour la chaîne de coordonnées à afficher
  updateCoordinatesDisplay();
}

function updateCoordinatesDisplay() {
  // Mettez à jour la chaîne de coordonnées à afficher
}

function reset() {
  // Supprimez tous les feux de la carte
  listFires.value.forEach((geojson) => {
    map.value.removeLayer(geojson);
  });

  // Effacez le tableau de feux
  listFires.value = [];

  // Mettez à jour la chaîne de coordonnées à afficher après la réinitialisation
  updateCoordinatesDisplay();
}

function launch() {
  const fireData = listFires.value.map((fire) => ({
    id: compteur++,
    latitude: fire.getLatLng().lat,
    longitude: fire.getLatLng().lng,
    diameter: fire.options.radius,
    power: fire.options.power,
    sensorList: [],
  }));

  console.log(fireDataList.value);

  // SensorService.sensorsOnFire(fireData);
  socketService.sendFires(fireDataList.value);
}

function updateFiresList(updatedFires) {
  console.log(updatedFires);
  // Supprimez tous les feux actuels de la carte
  listFires.value.forEach((fire) => {
    map.value.removeLayer(fire);
  });

  // Effacez le tableau de feux
  listFires.value = [];

  // Ajoutez les feux mis à jour à la carte
  updatedFires.forEach((fireData) => {
    const fire = L.circle([fireData.latitude, fireData.longitude], {
      color: 'red',
      fillColor: '#f03',
      fillOpacity: 0.5,
      radius: fireData.diameter,
    }).addTo(map.value);

    // Ajoutez le nouveau GeoJSON à la liste
    listFires.value.push(fire);
  });

  // Mettez à jour la chaîne de coordonnées à afficher après la mise à jour
  updateCoordinatesDisplay();
}
</script>
