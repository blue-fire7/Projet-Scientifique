import { Client } from '@stomp/stompjs';
import { useSensorStore } from '../store/sensorStore';

export default class SocketService {
  constructor() {
    this.sensorStore = useSensorStore();

    this.client = new Client({
      brokerURL: 'ws://192.168.254.85:8080/ws',

      onConnect: () => {
        this.client.subscribe('/topic/update/sensor', (msg) =>
          this.sensorStore.updateSensor(JSON.parse(msg.body))
        );
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
