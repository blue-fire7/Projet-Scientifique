import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useAppOptionStore = defineStore('appOption', () => {
  const showAllSensor = ref(false);

  return { showAllSensor };
});

export default useAppOptionStore;
