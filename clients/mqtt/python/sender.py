import paho.mqtt.client as mqtt #import the client1

broker_address=""
clientId=""
destinationName="asyncAppTopic"
message="Hey Ho, lets go"
qos=2
lastWill=False

client = mqtt.Client(clientId) #create new instance
client.connect(broker_address) #connect to broker
result = client.publish(destinationName,message,qos,lastWill)
print result.rc
client.disconnect()
