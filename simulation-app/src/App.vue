<script setup>
import { onMounted, ref } from "vue";
import L from "leaflet";

const markers = ref([]);
const map = ref();
const mapContainer = ref();
const coordinatesDisplay = ref("");

onMounted(() => {
  map.value = L.map(mapContainer.value).setView([51.505, -0.09], 13);
  L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
    maxZoom: 19,
    attribution:
      '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
  }).addTo(map.value);

  // Ajoutez un gestionnaire d'événement de clic sur la carte
  map.value.on('click', addMarkerOnClick);
});

function addMarkerOnClick(event) {
  // Récupérez les coordonnées du clic
  const clickedLat = event.latlng.lat;
  const clickedLng = event.latlng.lng;

  // Ajoutez un marqueur à l'emplacement du clic
  const marker = L.marker([clickedLat, clickedLng]).addTo(map.value);

  // Ajoutez les coordonnées au tableau
  markers.value.push({ lat: clickedLat, lng: clickedLng });

  // Mettez à jour la chaîne de coordonnées à afficher
  updateCoordinatesDisplay();

  // Ajoutez un gestionnaire d'événement de clic sur le marqueur pour afficher les coordonnées
  marker.bindPopup(`Latitude: ${clickedLat}, Longitude: ${clickedLng}`).openPopup();
}

function updateCoordinatesDisplay() {
  // Mettez à jour la chaîne de coordonnées à afficher
  coordinatesDisplay.value = markers.value
    .map(marker => `Latitude: ${marker.lat}, Longitude: ${marker.lng}`)
    .join("<br>");
}

</script>

<template>
  <div class="appContainer">
    <div ref="mapContainer" style="width: 640px; height: 640px"></div>
    <div v-html="coordinatesDisplay"></div>
  </div>
</template>

<style scoped></style>