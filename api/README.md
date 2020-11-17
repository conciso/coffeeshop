

# Conciso Coffeeshop

todo: insert some description here

## Build and Run

### Build

```
 mvn clean install
```
This creates a docker image ```conciso-coffeeshop```.

### Build including integration test

```
 mvn clean install -P integration-test
```

### Run the service

To run the service (including the database):
```
docker-compose -f docker/src/test/docker/docker-compose.yml up
```
The service has the following rest resources:
- ```localhost:8081/info```
- ```localhost:8081/health```
- ```localhost:8080/swagger-ui.html```

### Stop the service

```
docker-compose -f docker/src/test/docker/docker-compose.yml down
```
