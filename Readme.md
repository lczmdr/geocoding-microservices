# GEOCODING

Requirements
- Java 8
- Docker

The solucion was implemented using 2 microservices. 
- geocoding-app is a facade application that it simply connects with the geocoding-service using a rest template and returns back the response, but the idea is to switch it to use feign/ribbon.
- geocoding-service is a springboot/camel application that connects to the Google Maps geolocation API, validates the response and convert it into JSON.

Services tests can be executed from the command line using the maven wrapper
```sh
./mvnw test
```
Services can be tested and packaged with the next command
```sh
./mvnw install
```
All the services can be started using docker compose
```sh
docker-compose up
```

Once you are done you can bring down the services using
```sh
docker-compose down
```
To use it point your browser or favorite tool to the next url, replacing $ADDRESS with your desired destination.

>http://localhost:9000/geocoding-app/geolocation?address=$ADDRESS

### Example:
Request:
> http://localhost:9000/geocoding-app/geolocation?address=London

Response:

> {
> formattedAddress: "London, UK",
> latitude: "51.5073509",
> longitude: "-0.1277583",
> }

