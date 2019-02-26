# Async Workshop

## Zutaten

- [wlp](https://developer.ibm.com/wasdev/downloads/#asset/runtimes-wlp-javaee8)
- [activeMQ](http://activemq.apache.org/activemq-5158-release.html)
- [activeMQ ResourceAdapter](https://search.maven.org/search?q=a:activemq-rar)
- [hawtio](https://github.com/hawtio/hawtio/releases)

- [eclipse](https://www.eclipse.org/downloads/)
- [maven](https://maven.apache.org/download.cgi)

## Python installation

```bash
sudo apt-get install python python-pip
sudo pip install paho-mqtt
```

## Wichtige Befehle

```bash
activemq console
activemq start
activemq stop
```

## Links

- [activeMQ AdminGUI](http://HOST:8161/admin/)
- [hawtio](http://HOST:8161/hawtio/)

## hawtio

- download entpacken
- nach activemq/webapps/hawtio verschieben
- jetty.xml anpassen
- in activemq/conf/login.conf folgendes hinzufügen

```
karaf {
    org.apache.activemq.jaas.PropertiesLoginModule required
        org.apache.activemq.jaas.properties.user="users.properties"
        org.apache.activemq.jaas.properties.group="groups.properties";
};
```

## activemq

```xml
<destinationPolicy>
	<policyMap>
		<policyEntries>
			<policyEntry queue="asyncAppQueue">
				<deadLetterStrategy>
					<sharedDeadLetterStrategy processNonPersistent="true" enableAudit="false">
						<deadLetterQueue>
							<queue physicalName="asyncAppExceptionQueue" />
						</deadLetterQueue>
					</sharedDeadLetterStrategy>
				</deadLetterStrategy>
			</policyEntry>
		</policyEntries>
	</policyMap>
</destinationPolicy>
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans:beans xmlns="http://activemq.apache.org/schema/core"
	xmlns:beans="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
  http://activemq.apache.org/schema/core http://activemq.apache.org/schema/core/activemq-core.xsd">
```
