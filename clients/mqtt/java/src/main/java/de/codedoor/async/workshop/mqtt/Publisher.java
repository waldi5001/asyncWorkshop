package de.codedoor.async.workshop.mqtt;

import static java.lang.String.format;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

class Publisher {

	public static void main(String[] args) throws Exception {
		String host = env("ACTIVEMQ_HOST", "localhost");
		int port = Integer.parseInt(env("ACTIVEMQ_PORT", "1883"));
		final String destination = arg(args, 0, "asyncAppTopic");

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

	private static String env(String key, String defaultValue) {
		String rc = System.getenv(key);
		if (rc == null)
			return defaultValue;
		return rc;
	}

	private static String arg(String[] args, int index, String defaultValue) {
		if (index < args.length)
			return args[index];
		else
			return defaultValue;
	}

}