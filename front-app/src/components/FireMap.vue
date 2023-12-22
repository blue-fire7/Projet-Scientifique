<template>
  <div ref="mapContainer" style="height: 100%"></div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue';

import * as Leaflet from 'leaflet';
import { storeToRefs } from 'pinia';
import { useSensorStore } from '../store/sensorStore';
import useAppOptionStore from '../store/optionStore';

const map = ref();
const mapContainer = ref();
const coordinatesDisplay = ref('');

const markers = ref([]);

const props = defineProps({
  fireSensors: {
    default: [],
    type: Array,
  },
  trucks: {
    default: [],
  },
});

watch(
  () => useSensorStore().fireSensors,
  (val) => {
    console.log('refresh');
    reset();
    placeMarkers(props.fireSensors);
  },
  { deep: true }
);

watch(
  () => useAppOptionStore().showAllSensor,
  () => {
    console.log('ça change');
    reset();
    placeMarkers(props.fireSensors);
  }
);

const iconSensor0 = Leaflet.icon({
  iconUrl: './src/assets/sensors/sensor0.png',
  iconSize: [32, 32], // Taille de l'icône [largeur, hauteur]
  iconAnchor: [16, 16], // Point d'ancrage de l'icône par rapport à son coin supérieur gauche
  popupAnchor: [0, -32], // Point d'ancrage du popup par rapport à l'icône
});

// Définissez une icône personnalisée pour les marqueurs
const iconSensor1 = Leaflet.icon({
  iconUrl: './src/assets/sensors/sensor1.png',
  iconSize: [32, 32], // Taille de l'icône [largeur, hauteur]
  iconAnchor: [16, 16], // Point d'ancrage de l'icône par rapport à son coin supérieur gauche
  popupAnchor: [0, -32], // Point d'ancrage du popup par rapport à l'icône
});

const iconSensor2 = Leaflet.icon({
  iconUrl: './src/assets/sensors/sensor2.png',
  iconSize: [32, 32], // Taille de l'icône [largeur, hauteur]
  iconAnchor: [16, 16], // Point d'ancrage de l'icône par rapport à son coin supérieur gauche
  popupAnchor: [0, -32], // Point d'ancrage du popup par rapport à l'icône
});

const iconSensor3 = Leaflet.icon({
  iconUrl: './src/assets/sensors/sensor3.png',
  iconSize: [32, 32], // Taille de l'icône [largeur, hauteur]
  iconAnchor: [16, 16], // Point d'ancrage de l'icône par rapport à son coin supérieur gauche
  popupAnchor: [0, -32], // Point d'ancrage du popup par rapport à l'icône
});

const truckIcon = Leaflet.icon({
  iconUrl: './src/assets/camion.png',
  iconSize: [64, 64], // Taille de l'icône [largeur, hauteur]
  iconAnchor: [32, 32], // Point d'ancrage de l'icône par rapport à son coin supérieur gauche
  popupAnchor: [0, -32], // Point d'ancrage du popup par rapport à l'icône
});

const getIcon = (level) => {
  if (level < 1) {
    return iconSensor0;
  } else if (level < 3) {
    return iconSensor1;
  } else if (level < 6) {
    return iconSensor2;
  } else {
    return iconSensor3;
  }
};

/**
 * Place les marqueurs et les zones de feu sur la carte
 * @param {Array} fires
 */
const placeMarkers = (fires) => {
  fires.forEach((fire) => {
    if (
      (!useAppOptionStore().showAllSensor && fire.level && fire.level > 0) ||
      useAppOptionStore().showAllSensor
    ) {
      let marker = new Leaflet.Marker([fire.latitude, fire.longitude], {
        icon: getIcon(fire.level),
      });
      markers.value.push(marker);
      marker.addTo(map.value);
    }
  });

  let fireOn = [];
  fires
    .filter((fire) => fire.level > 0)
    .forEach((fire) => {
      // if (fire.level > 0) {
      //   let circle = new Leaflet.Circle([fire.latitude, fire.longitude], {
      //     radius: fire.level * 100,
      //     interactive: false,
      //     color: 'red',
      //   });
      //   circle.addTo(map.value);
      // }
      fireOn.push([fire.latitude, fire.longitude]);
    });
  if (fireOn.length > 0) {
    Leaflet.polygon(fireOn, { weight: 40, color: 'red' }).addTo(map.value);
  }
};

const placeTrucks = (trucks) => {
  trucks.forEach((truck) => {
    new Leaflet.Marker([truck.position.latitude, truck.position.longitude], {
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

  placeMarkers(props.fireSensors);
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
    if (layer instanceof Leaflet.Marker) {
      map.value.removeLayer(layer);
    }
  });

  // Mettez à jour la chaîne de coordonnées à afficher après la réinitialisation
  updateCoordinatesDisplay();
}
</script>
