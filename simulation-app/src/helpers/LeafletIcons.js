import * as Leaflet from 'leaflet';

const iconSensor0 = Leaflet.icon({
  iconUrl: './src/assets/sensors/sensor0.png',
  iconSize: [32, 32], // Taille de l'icône [largeur, hauteur]
  iconAnchor: [16, 16], // Point d'ancrage de l'icône par rapport à son coin supérieur gauche
  popupAnchor: [0, -32], // Point d'ancrage du popup par rapport à l'icône
});

// Définissez une icône personnalisée pour les marqueurs
const iconSensor1 = Leaflet.icon({
  iconUrl: './src/assets/sensors/sensor1.png',
  iconSize: [32, 32], // Taille de l'icône [largeur, hauteur]
  iconAnchor: [16, 16], // Point d'ancrage de l'icône par rapport à son coin supérieur gauche
  popupAnchor: [0, -32], // Point d'ancrage du popup par rapport à l'icône
});

const iconSensor2 = Leaflet.icon({
  iconUrl: './src/assets/sensors/sensor2.png',
  iconSize: [32, 32], // Taille de l'icône [largeur, hauteur]
  iconAnchor: [16, 16], // Point d'ancrage de l'icône par rapport à son coin supérieur gauche
  popupAnchor: [0, -32], // Point d'ancrage du popup par rapport à l'icône
});

const iconSensor3 = Leaflet.icon({
  iconUrl: './src/assets/sensors/sensor3.png',
  iconSize: [32, 32], // Taille de l'icône [largeur, hauteur]
  iconAnchor: [16, 16], // Point d'ancrage de l'icône par rapport à son coin supérieur gauche
  popupAnchor: [0, -32], // Point d'ancrage du popup par rapport à l'icône
});

const truckIcon = Leaflet.icon({
  iconUrl: './src/assets/camion.png',
  iconSize: [64, 64], // Taille de l'icône [largeur, hauteur]
  iconAnchor: [32, 32], // Point d'ancrage de l'icône par rapport à son coin supérieur gauche
  popupAnchor: [0, -32], // Point d'ancrage du popup par rapport à l'icône
});

const stationIcon = Leaflet.icon({
  iconUrl: './src/assets/fire-station.png',
  iconSize: [32, 32], // Taille de l'icône [largeur, hauteur]
  iconAnchor: [16, 16], // Point d'ancrage de l'icône par rapport à son coin supérieur gauche
  popupAnchor: [0, -32], // Point d'ancrage du popup par rapport à l'icône
});

const getFireIcon = (level) => {
  if (level < 1) {
    return iconSensor0;
  } else if (level < 3) {
    return iconSensor1;
  } else if (level < 6) {
    return iconSensor2;
  } else {
    return iconSensor3;
  }
};

export { getFireIcon, truckIcon, stationIcon };
