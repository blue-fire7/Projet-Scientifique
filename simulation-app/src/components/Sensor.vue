<template>
    <div>
        <h1>Vue des Capteurs</h1>

        <table>
            <thead>
                <th>Id</th>
                <th>Longitude</th>
                <th>Latitude</th>
            </thead>

            <tbody>
                <tr v-for="sensor in sensors" v-bind:key = "sensor.id">
                    <td> {{ sensor.id }}</td>
                    <td> {{ sensor.longitude }}</td>
                    <td> {{ sensor.latitude }}</td>
                </tr>
            </tbody>

        </table>
    
    </div>
</template>

<script>
import SensorService from '../services/SensorService'
    export default {
        name: '',
        data(){
            return {
                sensors : []
            }
        },
        methods: {
            getSensors(){
                SensorService.getSensors().then((response) => {
                    this.sensors = response.data;
                });
            }
        },
        created() {
            this.getSensors()
        }
    }


</script>