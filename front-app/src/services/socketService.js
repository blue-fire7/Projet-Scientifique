import { Client } from '@stomp/stompjs';
import { useSensorStore } from '../store/sensorStore';

export default class SocketService {
  constructor() {
    console.log('lancement socket');
    this.client = new Client({
      brokerURL: 'wss://593e6da2a2715d.lhr.life/ws',

      onConnect: () => {
        console.log('websocket connecté');
        this.client.subscribe('/topic/update/sensor', (msg) => {
          useSensorStore().updateFireSensor(JSON.parse(msg.body));
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
