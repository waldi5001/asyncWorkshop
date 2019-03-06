import paho.mqtt.publish as publish

publish.single(topic="asyncAppTopic", payload="Deine Nachricht", qos=2, client_id="", hostname="") 
