import time
import paho.mqtt.client as mqtt
import random


def send_mqtt(message):
  print(message)
  return



def simulateFireSensor(mqttClient: mqtt.Client):
  """
  Simule un petit feu croissant puis réduit
  Sa durée est de 20 secondes, passant a 2 avant et incrémentant un autre capteur avant detre réduit en 4 secondes
  """
  print('début feu')
  mqttClient.publish('fire/sensor','fire_level,id=12 level=0')

  for i in range(10):
    mqttClient.publish('fire/sensor','fire_level,id=12 level=1')
    time.sleep(1)

  print('ca va augmenter')
  for i in range(5):
    mqttClient.publish('fire/sensor','fire_level,id=12 level=2')
    mqttClient.publish('fire/sensor','fire_level,id=23 level=1')
    time.sleep(1)

  print('pompier arrive')
  for i in range(3):
    mqttClient.publish('fire/sensor','fire_level,id=12 level=1')
    mqttClient.publish('fire/sensor','fire_level,id=23 level=0')
    time.sleep(1)  

  mqttClient.publish('fire/sensor','fire_level,id=12 level=0')
  
  return

def main():
  client = mqtt.Client(clean_session=True,userdata=None, transport='tcp')
  client.connect('localhost', port=1883, )
  simulateFireSensor(client)

if __name__ == "__main__":
  main()