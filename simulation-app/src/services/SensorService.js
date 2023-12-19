import axios from 'axios'

const SENSOR_API_BASE_URL = 'http://localhost:8080/api/sensor'

class SensorService {
    getSensors() {
        return axios.get(SENSOR_API_BASE_URL);
    }
}

export default new SensorService()