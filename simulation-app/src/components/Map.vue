<template>
  <div class="appContainer">
    <div id="map" style="width: 640px; height: 640px"></div>

    <div class="map-infos">
      <div class="app-actions">
        <h2>Placer vos incendies</h2>

      </div>

      <div class="coordinates" v-html="coordinatesDisplay"></div>
      <div class="validation">
        <button class="reset" @click="reset">Réinitialiser</button>
        <button class="launch">Lancer !</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import SensorService from '../services/SensorService';
import L from "leaflet";

const listFires = ref([]);
const map = ref(null);
const coordinatesDisplay = ref("");

onMounted(() => {
  // Initialiser la carte Leaflet
  map.value = L.map('map').setView([45.76667, 4.88333], 14);
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map.value);

  // Charger les capteurs après l'initialisation de la carte
  loadSensors();

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
    const marker = L.marker([sensor.latitude, sensor.longitude]).addTo(map.value);
    marker.bindPopup(`<b>${sensor.latitude}</b><br>${sensor.longitude}`);
  });
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
    radius: 500, // 1 kilomètre en mètres
  }).addTo(map.value);

  // Ajoutez le nouveau GeoJSON à la liste
  listFires.value.push(fire);

  // Mettez à jour la chaîne de coordonnées à afficher
  updateCoordinatesDisplay();
}

function updateCoordinatesDisplay() {
  // Mettez à jour la chaîne de coordonnées à afficher
  coordinatesDisplay.value = listFires.value
    .map((geojson, index) => `<strong>Feu n°${index + 1} :</strong> ${geojson.getLatLng().lat.toFixed(6)}, ${geojson.getLatLng().lng.toFixed(6)}`)
    .join("<br><br>");
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
</script>
