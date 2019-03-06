import paho.mqtt.client as mqtt

broker_address=""
clientId=""
destinationName="asyncAppTopic"
clean=False

def on_subscribe(client, userdata, mid, granted_qos):
   print("Subscribed: "+str(mid)+" "+str(granted_qos))

def on_message(client, userdata, msg):
   print(msg.topic+" "+str(msg.qos)+" "+str(msg.payload))    

client = mqtt.Client(clientId,clean)
client.on_subscribe = on_subscribe
client.on_message = on_message
client.connect(broker_address)
client.subscribe(destinationName,2)

client.loop_forever()