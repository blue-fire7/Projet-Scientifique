<template>
<div id="app">
  <Map />
</div>

</template>

<script>
import Sensor from './components/Sensor.vue'
import Map from './components/Map.vue'
import Fire from './components/Fire.vue'
import SensorEvent from './components/SensorEvent.vue'

export default {
  components: {
    Sensor,
    Map,
    Fire,
    SensorEvent,
}
}
</script>