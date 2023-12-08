<script setup>
import { onMounted, ref } from "vue";
import L from "leaflet";

const markers = ref([]);
const map = ref();
const mapContainer = ref();
const coordinatesDisplay = ref("");

// Définissez une icône personnalisée pour les marqueurs
const customIcon = L.icon({
  iconUrl: './public/feu.png',
  iconSize: [32, 32], // Taille de l'icône [largeur, hauteur]
  iconAnchor: [16, 32], // Point d'ancrage de l'icône par rapport à son coin supérieur gauche
  popupAnchor: [0, -32], // Point d'ancrage du popup par rapport à l'icône
});

onMounted(() => {
  map.value = L.map(mapContainer.value).setView([45.76667, 4.88333], 14);
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

  const formatedLat = clickedLat.toFixed(6);
  const formatedLng = clickedLng.toFixed(6);

  // Ajoutez un marqueur à l'emplacement du clic avec l'icône personnalisée
  const marker = L.marker([clickedLat, clickedLng], { icon: customIcon }).addTo(map.value);

  // Ajoutez les coordonnées au tableau
  const id = markers.value.length + 1;
  markers.value.push({ id, lat: formatedLat, lng: formatedLng });

  // Mettez à jour la chaîne de coordonnées à afficher
  updateCoordinatesDisplay();

  // Ajoutez un gestionnaire d'événement de clic sur le marqueur pour afficher les coordonnées
  marker.bindPopup(`Latitude: ${formatedLat}, Longitude: ${formatedLng}`).openPopup();
}

function updateCoordinatesDisplay() {
  // Mettez à jour la chaîne de coordonnées à afficher
  coordinatesDisplay.value = markers.value
    .map(marker => `<strong>Feu n°${marker.id} :</strong> Latitude: ${marker.lat}, Longitude: ${marker.lng}`)
    .join("<br><br>");
  console.log(coordinatesDisplay);
}

function reset() {
  // Effacez le tableau markers
  markers.value = [];

  // Supprimez tous les marqueurs de la carte
  map.value.eachLayer(layer => {
    if (layer instanceof L.Marker) {
      map.value.removeLayer(layer);
    }
  });

  // Mettez à jour la chaîne de coordonnées à afficher après la réinitialisation
  updateCoordinatesDisplay();
}
</script>

<template>
  <div class="appContainer">
    <div ref="mapContainer" style="width: 640px; height: 640px"></div>

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

<style scoped></style>