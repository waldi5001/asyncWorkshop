package de.codedoor.async.workshop.mqtt;

import static java.lang.String.format;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

class Listener implements MqttCallback {

	public Listener(String[] args) {
		String host = "localhost";
		String port = "1883";
		String destination = "asyncAppTopic";

		try {
			String url = format("tcp://%s:%s", host, port);
			MqttClient mqtt = new MqttClient(url, "yourname");
			MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
			mqttConnectOptions.setCleanSession(false);
			mqtt.connectWithResult(mqttConnectOptions);
//			mqtt.setCallback(this);
//			mqtt.subscribe(destination, 2);

			mqtt.subscribe(destination, 2, new IMqttMessageListener() {
				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					String payload = new String(message.getPayload());
					System.out.println(payload);
				}
			});
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println(new String(message.getPayload()));
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println(String.format("Successfully delivered %s", token.getMessageId()));
	}

	@Override
	public void connectionLost(Throwable cause) {
		System.out.println(String.format("Connection lost: %s", cause.getMessage()));
	}

	public static void main(String[] args) throws Exception {
		new Listener(args);
	}

}