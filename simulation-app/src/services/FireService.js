import axios from 'axios'

const FIRE_API_BASE_URL = 'http://localhost:8080/api/fire'

class FireService {
    getFires() {
        return axios.get(FIRE_API_BASE_URL);
    }
}

export default new FireService()