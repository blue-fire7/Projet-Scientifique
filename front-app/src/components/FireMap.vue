<template>
  <div ref="mapContainer" style="height: 100%"></div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue';

import * as Leaflet from 'leaflet';
import { useSensorStore } from '../store/sensorStore';
import useAppOptionStore from '../store/optionStore';
import useFireTruckStore from '../store/fireTruckStore';
import { useStationStore } from '../store/stationStore';
import { fireService } from '../services/index.js';
import { getFireIcon, truckIcon, stationIcon } from '../helpers/LeafletIcons';

const map = ref();
const mapContainer = ref();

const props = defineProps({
  fireSensors: {
    default: [],
    type: Array,
  },
  trucks: {
    default: [],
  },
  stations: {
    default: [],
  },
});

watch(
  () => useSensorStore().fireSensors,
  () => {
    resetMarkers('fire');
    placeMarkers(props.fireSensors);
    placeFires(props.fireSensors);
  },
  { deep: true }
);

watch(
  () => useAppOptionStore().showAllSensor,
  () => {
    resetMarkers('fire');
    placeMarkers(props.fireSensors);
    placeFires(props.fireSensors);
  }
);

watch(
  () => useStationStore().fireStations,
  () => {
    placeStations(props.stations);
  }
);

watch(
  () => useAppOptionStore().showMode,
  () => {
    removeCircles();
    removePoly();
    placeFires(props.fireSensors);
  }
);

watch(
  () => useFireTruckStore().fireTrucks,
  () => {
    resetMarkers('truck');
    console.log(props.trucks);
    placeTrucks(props.trucks);
  }
);

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
        icon: getFireIcon(fire.level),
        markerType: 'fire',
      });
      marker.addTo(map.value);
    }
  });
};

const placeFires = (fires) => {
  let fireSensors = [];
  let fireArray = fires.filter((fire) => fire.level > 0);
  if (useAppOptionStore().showMode === 'circle') {
    fireArray.forEach((fire) => {
      let circle = new Leaflet.Circle([fire.latitude, fire.longitude], {
        radius: fire.level * 100,
        interactive: false,
        color: 'red',
      });
      circle.addTo(map.value);
    });
  } else if (useAppOptionStore().showMode === 'poly') {
    let ids = getFireIds(fireArray);
    ids.forEach((id) => {
      fireSensors.push(
        fireArray
          .filter((fire) => fire.fireId === id)
          .map((fire) => [fire.latitude, fire.longitude])
      );
    });

    // fireArray.push([])
    // fireOn.push([fire.latitude, fire.longitude]);
  }

  fireArray.forEach((fire) => {});
  if (fireSensors.length > 0) {
    Leaflet.polygon(fireSensors, { weight: 40, color: 'red' }).addTo(map.value);
  }
};

const getFireIds = (fires) => {
  let ids = [];
  fires.forEach((fire) => {
    if (fire.fireId && !ids.includes(fire.fireId)) ids.push(fire.fireId);
  });
  return ids;
};

const placeTrucks = (trucks) => {
  trucks.forEach((truck) => {
    new Leaflet.Marker([truck.latitude, truck.longitude], {
      markerType: 'truck',
      icon: truckIcon,
    }).addTo(map.value);
  });
};

const placeStations = (stations) => {
  stations.forEach((station) => {
    let marker = new Leaflet.Marker([station.latitude, station.longitude], {
      icon: stationIcon,
      markerType: 'station',
    });
    marker.addTo(map.value);
    bindPopupStation(station, marker);
  });
};

const bindPopupStation = (station, marker) => {
  const popup = Leaflet.popup();
  popup.setContent(`<h1>Chargement...</h1>`);
  marker.bindPopup(popup).on('popupopen', () => {
    fireService.getFireStation(station.id).then((data) => {
      let content = `<h2>SDIS: ${data.id}</h2>`;
      content += `<p>Nombre de camions en caserne : ${data.availableFireTruck}/${data.nbFireTruck}</p>`;
      content += `<p>Nombre d'équipes disponible : ${data.nbAvailableTeams}/${data.nbTeams}</p>`;
      popup.setContent(content);
    });
  });
};

onMounted(() => {
  map.value = Leaflet.map(mapContainer.value, { zoomControl: true }).setView(
    [45.76667, 4.88333],
    14
  );
  L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution:
      '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
  }).addTo(map.value);

  placeMarkers(props.fireSensors);
  // placeTrucks(props.trucks);
  placeStations(props.stations);
  placeFires(props.fireSensors);
});

function resetMarkers(markerType) {
  // Supprimez tous les marqueurs de la carte
  map.value.eachLayer((layer) => {
    if (
      layer instanceof Leaflet.Marker &&
      layer.options.markerType === markerType
    ) {
      map.value.removeLayer(layer);
    }
  });
}

function removeCircles() {
  map.value.eachLayer((layer) => {
    if (layer instanceof Leaflet.Circle) {
      map.value.removeLayer(layer);
    }
  });
}

function removePoly() {
  map.value.eachLayer((layer) => {
    if (layer instanceof Leaflet.Polygon) {
      map.value.removeLayer(layer);
    }
  });
}
</script>
