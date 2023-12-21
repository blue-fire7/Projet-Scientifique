<template>
    <div>
        <h1>Vue des Feux</h1>

        <table>
            <thead>
                <th>Id</th>
                <th>Started</th>
            </thead>

            <tbody>
                <tr v-for="fire in fires" v-bind:key = "fire.id">
                    <td> {{ fire.id }}</td>
                    <td> {{ fire.startedAt }}</td>
                </tr>
            </tbody>

        </table>
    
    </div>
</template>

<script>
import FireService from '../services/FireService'
    export default {
        name: '',
        data(){
            return {
                fires : []
            }
        },
        methods: {
            getFires(){
                FireService.getFires().then((response) => {
                    this.fires = response.data;
                    console.log(this.fires);
                });
            }
        },
        created() {
            this.getFires()
        }
    }


</script>