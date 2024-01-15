import { defineStore } from 'pinia';
import { ref } from 'vue';
import { truckService } from '../services';

const useFireTruckStore = defineStore('fireTruck', () => {
  const fireTrucks = ref([]);

  async function getFireTrucks() {
    fireTrucks.value = await truckService.getTrucks();
    return fireTrucks;
  }

  function updateTrucks(trucks) {
    trucks.forEach((truck) => {
      let knownTruck = fireTrucks.value.find((t) => (t.id = trucks.id));
      if (knownTruck) {
        knownTruck.latitude = truck.latitude;
        knownTruck.longitude = truck.longitude;
      } else {
        fireTrucks.value.push(truck);
      }
    });
  }
  function updateTruck(truck) {
    let knownTruck = fireTrucks.value.find((t) => (t.id = truck.id));
    if (knownTruck) {
      if (truck.latitude === 0 && truck.longitude === 0) {
        fireTrucks.value.filter((t) => t.id != truck.id);
      } else {
        knownTruck.latitude = truck.latitude;
        knownTruck.longitude = truck.longitude;
      }
    } else {
      fireTrucks.value.push(truck);
    }
  }

  return { fireTrucks, getFireTrucks, updateTrucks, updateTruck };
});

export default useFireTruckStore;
