import axios from 'axios'

const SENSOR_API_BASE_URL = 'http://localhost:8080/api/sensor'
const FIRE_API_BASE_URL = 'http://localhost:8080/api/add-fire'

class SensorService {
    getSensors() {
        return axios.get(SENSOR_API_BASE_URL);
    }

    createFire(fireData) {
        return axios.post(FIRE_API_BASE_URL, fireData);
    }
}

export default new SensorService()