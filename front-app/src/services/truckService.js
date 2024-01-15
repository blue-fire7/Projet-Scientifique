export default class TruckService {
  constructor() {}

  async getTrucks() {
    let result = await fetch('http://localhost:8080/fire-trucks');
    result = await result.json();
    console.log(result);

    return result;
  }
}
