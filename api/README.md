# Conciso's Coffee Shop - REST Hypermedia API
This is the REST Hypermedia API for Conciso's Coffee Shop. It is based 
on the [HAL standard](http://stateless.co/hal_specification.html).

## Build with Maven

You can build this project with maven like this:

```
mvn package
```

## Run with Wildfly Swarm (Recommended)
After building the artifact with Maven you can run it as a Wildfly Swarm
application:

```
java -jar target/coffeeshop-swarm.jar
```

After starting the application the API will be available at 
[http://localhost:8080/api](http://localhost:8080/api).

## Deploy to Wildfly 
Alternatively you can simply deploy the application to a running Wildfly 
instance:

```
mvn wildfly:deploy-only
```

After deployment the API will be available at 
[http://localhost:8080/coffeeshop/api](http://localhost:8080/coffeeshop/api).

## API Discovery
To discover the API point your browser to the HAL Browser.

Wildfly Swarm:
[http://localhost:8080/browser.html#/api](http://localhost:8080/browser.html#/api)

Wildfly Deployment:
[http://localhost:8080/coffeeshop/browser.html#/coffeeshop/api](http://localhost:8080/coffeeshop/browser.html#/coffeeshop/api)