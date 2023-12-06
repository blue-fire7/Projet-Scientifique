<script setup>
import { onMounted, ref } from "vue";
import L from "leaflet";

var MARKERS_MAX = 4;

var map = L.map('mapContainer.value').setView([45.76667, 4.88333], 15);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
  attribution:
    '&copy; <a href="https://openstreetmap.org/copyright">OpenStreetMap</a> contributors'
}).addTo(map);

// a layer group, used here like a container for markers
var markersGroup = L.layerGroup();
map.addLayer(markersGroup);

map.on('click', function (e) {
  // get the count of currently displayed markers
  var markersCount = markersGroup.getLayers().length;

  if (markersCount < MARKERS_MAX) {
    var marker = L.marker(e.latlng).addTo(markersGroup);
    return;
  }

  // remove the markers when MARKERS_MAX is reached
  markersGroup.clearLayers();
});

</script>

<template>
  <div class="simulator">
    <div id="mapContainer" style="width: 640px; height: 640px"></div>
    <div class="infos-simu">
      <h1>Salut</h1>
    </div>
  </div>
</template>

<style scoped></style>