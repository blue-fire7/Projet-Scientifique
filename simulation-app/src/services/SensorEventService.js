import axios from 'axios'

const SENSOR_EVENT_API_BASE_URL = 'http://localhost:8080/api/sensor_event'

class SensorEventService {
    getSensorEvents() {
        return axios.get(SENSOR_EVENT_API_BASE_URL);
    }
}

export default new SensorEventService()