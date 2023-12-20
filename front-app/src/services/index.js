import FireService from './fireSensorService';
import SocketService from './socketService';
import TruckService from './truckService';

export const fireService = new FireService();
export const truckService = new TruckService();
export const socketService = new SocketService();
