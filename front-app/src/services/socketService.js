import { Client } from '@stomp/stompjs';
import { useSensorStore } from '../store/sensorStore';
import useFireTruckStore from '../store/fireTruckStore';

export default class SocketService {
  constructor() {
    console.log('lancement socket');
    this.client = new Client({
      brokerURL: 'ws://localhost:8080/ws',

      onConnect: () => {
        console.log('websocket connecté');
        this.client.subscribe('/topic/update/sensor', (msg) => {
          useSensorStore().updateFireSensor(JSON.parse(msg.body));
        });

        this.client.subscribe('/topic/update/truck', (msg) => {
          useFireTruckStore().updateTruck(JSON.parse(msg.body));
        });
      },

      onWebSocketError: (error) => {
        console.log(error);
      },

      onWebSocketClose: (event) => {
        console.log(event);
      },

      onStompError: (frame) => {
        // Will be invoked in case of error encountered at Broker
        // Bad login/passcode typically will cause an error
        // Complaint brokers will set `message` header with a brief message. Body may contain details.
        // Compliant brokers will terminate the connection after any error
        console.log('Broker reported error: ' + frame.headers['message']);
        console.log('Additional details: ' + frame.body);
      },
    });
    console.log('go');
    this.client.activate();
  }

  getClient() {
    return this.client;
  }
}
