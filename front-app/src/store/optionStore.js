import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useAppOptionStore = defineStore('appOption', () => {
  const showAllSensor = ref(false);
  const showMode = ref('poly');

  return { showAllSensor, showMode };
});

export default useAppOptionStore;
