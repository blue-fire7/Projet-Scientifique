const host = '192.168.78.85';

export default class TruckService {
  constructor() {}

  async getTrucks() {
    let result = await fetch(`http://${host}:8080/fire-trucks`);
    result = await result.json();
    console.log(result);

    return result;
  }
}
