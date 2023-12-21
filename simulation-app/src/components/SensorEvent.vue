<template>
    <div>
      <h1>Vue des événements de capteurs</h1>
  
      <table>
        <thead>
          <th>SensorEventId</th>
          <th>Sensor</th>
          <th>Fire</th>
          <th>Level</th>
          <th>Update</th>
        </thead>
  
        <tbody>
          <tr v-for="sensorEvent in sensorEvents" :key="generateKey(sensorEvent)">
            <td>
              {{ sensorEvent.id.sensorId }}_{{ sensorEvent.id.fireId }}_{{ sensorEvent.id.updateAt }}
            </td>
            <td>{{ sensorEvent.sensor.id }}</td>
            <td>{{ sensorEvent.fire.id }}</td>
            <td>{{ sensorEvent.level }}</td>
            <td>{{ sensorEvent.updateAt }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script>
  import SensorEventService from '../services/SensorEventService';
  
  export default {
    name: '',
    data() {
      return {
        sensorEvents: [],
      };
    },
    methods: {
      getSensorEvents() {
        SensorEventService.getSensorEvents().then((response) => {
          this.sensorEvents = response.data;
          console.log(this.sensorEvents);
        });
      },
      generateKey(sensorEvent) {
        // Utilisez les propriétés de votre objet composite pour créer une clé unique
        return `${sensorEvent.id.sensorId}_${sensorEvent.id.fireId}_${sensorEvent.id.updateAt}`;
      },
    },
    created() {
      this.getSensorEvents();
    },
  };
  </script>
  