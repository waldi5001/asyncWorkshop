package de.codedoor.async.workshop.mqtt;

import static java.lang.String.format;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

class Publisher {

	public Publisher() {
		String host = "localhost";
		String port = "1883";
		String destination = "asyncAppTopic";

		try (MqttClient mqtt = new MqttClient(format("tcp://%s:%s", host, port), "publisher")) {
			MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
			mqttConnectOptions.setCleanSession(false);
			mqtt.connect(mqttConnectOptions);
			mqtt.publish(destination, "shutdown".getBytes(), 2, false);
			mqtt.disconnect();
			mqtt.close();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		new Publisher();
	}

}