<template>
  <Navbar />
  <div class="app-content">
    <EmergencyView style="height: 100%; width: 100%" />
  </div>
</template>

<script setup>
import Navbar from './components/Navbar.vue';
import EmergencyView from './pages/EmergencyView.vue';
</script>
