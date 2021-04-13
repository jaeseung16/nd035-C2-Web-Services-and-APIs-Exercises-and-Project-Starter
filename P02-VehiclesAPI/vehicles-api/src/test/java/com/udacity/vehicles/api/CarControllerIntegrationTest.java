package com.udacity.vehicles.api;

import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CarControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JacksonTester<Car> json;

    @Test
    public void postCar() {
        ResponseEntity<Car> response =
                restTemplate.postForEntity("http://localhost:" + port + "/cars/", getCar(), Car.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    @Test
    public void getAllCars() {
        restTemplate.postForEntity("http://localhost:" + port + "/cars/", getCar(), Car.class);

        ResponseEntity<String> response =
                restTemplate.getForEntity("http://localhost:" + port + "/cars/", String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getCarWithId() {
        ResponseEntity<Car> carResponseEntity = restTemplate.postForEntity("http://localhost:" + port + "/cars/", getCar(), Car.class);
        Car car = carResponseEntity.getBody();

        Assert.notNull(car);

        Long id = carResponseEntity.getBody().getId();
        ResponseEntity<Car> response =
                restTemplate.getForEntity("http://localhost:" + port + "/cars/" + id, Car.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void updateCar() throws Exception {
        ResponseEntity<Car> carResponseEntity = restTemplate.postForEntity("http://localhost:" + port + "/cars/", getCar(), Car.class);
        Car car = carResponseEntity.getBody();

        Assert.notNull(car);

        Long id = carResponseEntity.getBody().getId();
        car.setCondition(Condition.NEW);

        String requestBody = json.write(car).getJson();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response =
                restTemplate.exchange("http://localhost:" + port + "/cars/{id}", HttpMethod.PUT, entity, String.class, id);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    private Car getCar() {
        Car car = new Car();
        car.setLocation(new Location(40.730610, -73.935242));
        Details details = new Details();
        Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
        details.setManufacturer(manufacturer);
        details.setModel("Impala");
        details.setMileage(32280);
        details.setExternalColor("white");
        details.setBody("sedan");
        details.setEngine("3.6L V6");
        details.setFuelType("Gasoline");
        details.setModelYear(2018);
        details.setProductionYear(2018);
        details.setNumberOfDoors(4);
        car.setDetails(details);
        car.setCondition(Condition.USED);
        return car;
    }
}
