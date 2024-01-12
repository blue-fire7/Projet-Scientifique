import { Client } from '@stomp/stompjs';
import useFireStore from '../store/fireStore';
import { generateRID } from '../helpers/id';

export default class SocketService {
  constructor() {
    console.log('constr');
    this.client = new Client({
      brokerURL: 'ws://localhost:8080/ws',

      onConnect: () => {
        console.log('ouai');
        this.client.subscribe('/topic/update/fires', (message) => {
          const updatedFires = JSON.parse(message.body);
          useFireStore().fireList = updatedFires;
        });

        this.client.subscribe('/topic/update/trucks', (message) => {
          const updatedTrucks = JSON.parse(message.body);
          useFireStore().truckList = updatedTrucks;
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

    this.client.activate();
  }

  sendFires(firesList) {
    firesList.forEach((fire) => {
      fire.id = generateRID();
    });
    this.client.publish({
      destination: '/app/addFires',
      body: JSON.stringify(firesList),
    });
  }

  sendStopSimulation() {
    this.client.publish({ destination: '/app/stopSimulation' });
  }
}
