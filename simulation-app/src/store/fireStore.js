import { defineStore } from 'pinia';
import { ref } from 'vue';

const useFireStore = defineStore('fireStore', () => {
  const fireList = ref([]);
  const truckList = ref([]);

  return { fireList, truckList };
});

export default useFireStore;
