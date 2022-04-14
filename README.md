## Getting Started

Requires ```weatherApiKey``` key defined in ```src/resources/properties/application.properties```.

To run: ```mvn clean spring-boot:run ```

Defined endpoints:
- ```/potresi/rekordi/tedenski```
- ```/potresi/rekordi/mesecni```
- ```/potresi/zadnji```
- ```/statistika/klici```
- ```/statistika/avg```
  
## Reference
Application is built using the following applications: 
- __spring-boot__
- __maven__ 
- __jackson__ 
- __geojson-jackson__ 
- __junit-jupiter-engine__ 

For further reference, consider the following files:

__Application.json__ encompases most of the API.
__Conrtoller.java__ with endpint definitions.
__Analytics.java__ has the statistics module.

__ApplicationTests.java__ for unit tests under the test subfolder of src/.