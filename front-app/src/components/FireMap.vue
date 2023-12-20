<template>
  <div ref="mapContainer" style="height: 100%"></div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue';

import * as Leaflet from 'leaflet';

const map = ref();
const mapContainer = ref();
const coordinatesDisplay = ref('');

const props = defineProps({
  sensors: {
    default: [],
    type: Array,
  },
  trucks: {
    default: [],
  },
});

watch(props.sensors, () => {
  // placeMarkers(props.sensors);
});

// Définissez une icône personnalisée pour les marqueurs
const iconSensor1 = Leaflet.icon({
  iconUrl: './src/assets/sensors/sensor1.png',
  iconSize: [32, 32], // Taille de l'icône [largeur, hauteur]
  iconAnchor: [16, 32], // Point d'ancrage de l'icône par rapport à son coin supérieur gauche
  popupAnchor: [0, -32], // Point d'ancrage du popup par rapport à l'icône
});

const iconSensor2 = Leaflet.icon({
  iconUrl: './src/assets/sensors/sensor2.png',
  iconSize: [32, 32], // Taille de l'icône [largeur, hauteur]
  iconAnchor: [16, 32], // Point d'ancrage de l'icône par rapport à son coin supérieur gauche
  popupAnchor: [0, -32], // Point d'ancrage du popup par rapport à l'icône
});

const iconSensor3 = Leaflet.icon({
  iconUrl: './src/assets/sensors/sensor3.png',
  iconSize: [32, 32], // Taille de l'icône [largeur, hauteur]
  iconAnchor: [16, 32], // Point d'ancrage de l'icône par rapport à son coin supérieur gauche
  popupAnchor: [0, -32], // Point d'ancrage du popup par rapport à l'icône
});

const truckIcon = Leaflet.icon({
  iconUrl: './src/assets/camion.png',
  iconSize: [64, 64], // Taille de l'icône [largeur, hauteur]
  iconAnchor: [16, 32], // Point d'ancrage de l'icône par rapport à son coin supérieur gauche
  popupAnchor: [0, -32], // Point d'ancrage du popup par rapport à l'icône
});

const getIcon = (level) => {
  if (level < 3) {
    return iconSensor1;
  } else if (level < 6) {
    return iconSensor2;
  } else {
    return iconSensor3;
  }
};

const placeMarkers = (markers) => {
  markers.forEach((marker) => {
    new Leaflet.Marker([marker.lat, marker.long], {
      icon: getIcon(marker.level),
    }).addTo(map.value);
  });
};

const placeTrucks = (trucks) => {
  trucks.forEach((truck) => {
    new Leaflet.Marker([truck.position.lat, truck.position.long], {
      icon: truckIcon,
    }).addTo(map.value);
  });
};

onMounted(() => {
  map.value = Leaflet.map(mapContainer.value).setView([45.76667, 4.88333], 14);
  L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution:
      '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
  }).addTo(map.value);

  placeMarkers(props.sensors);
  placeTrucks(props.trucks);

  // Ajoutez un gestionnaire d'événement de clic sur la carte
  // map.value.on('click', addMarkerOnClick);
});

// function addMarkerOnClick(event) {
//   // Récupérez les coordonnées du clic
//   const clickedLat = event.latlng.lat;
//   const clickedLng = event.latlng.lng;

//   const formatedLat = clickedLat.toFixed(6);
//   const formatedLng = clickedLng.toFixed(6);

//   // Ajoutez un marqueur à l'emplacement du clic avec l'icône personnalisée
//   const marker = L.marker([clickedLat, clickedLng], { icon: customIcon }).addTo(map.value);

//   // Ajoutez les coordonnées au tableau
//   const id = markers.value.length + 1;
//   markers.value.push({ id, lat: formatedLat, lng: formatedLng });

//   // Mettez à jour la chaîne de coordonnées à afficher
//   updateCoordinatesDisplay();

//   // Ajoutez un gestionnaire d'événement de clic sur le marqueur pour afficher les coordonnées
//   marker.bindPopup(`Latitude: ${formatedLat}, Longitude: ${formatedLng}`).openPopup();
// }

function updateCoordinatesDisplay() {
  // Mettez à jour la chaîne de coordonnées à afficher
  coordinatesDisplay.value = markers.value
    .map(
      (marker) =>
        `<strong>Feu n°${marker.id} :</strong> Latitude: ${marker.lat}, Longitude: ${marker.lng}`
    )
    .join('<br><br>');
  console.log(coordinatesDisplay);
}

function reset() {
  // Effacez le tableau markers
  markers.value = [];

  // Supprimez tous les marqueurs de la carte
  map.value.eachLayer((layer) => {
    if (layer instanceof L.Marker) {
      map.value.removeLayer(layer);
    }
  });

  // Mettez à jour la chaîne de coordonnées à afficher après la réinitialisation
  updateCoordinatesDisplay();
}
</script>
