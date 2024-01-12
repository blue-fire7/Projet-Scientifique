<template>
  <Navbar />
  <div class="app-content">
    <Map style="height: 100%; width: 100%" />
  </div>
</template>

<script>
import Map from './components/Map.vue';
import Navbar from './components/Navbar.vue';

export default {
  components: {
    Map,
    Navbar,
  },
};
</script>
