package com.exampleCar.Car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    private CarRepository carRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody Car car){
        try {
            if (car == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR");
            }
            carRepository.save(car);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            System.out.println("error in creazione auto");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping
    public List<Car> getAllCars(){
        return carRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity getCar(@PathVariable Long id){
        if (!carRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("car not present");
        }
        Optional<Car> getCar =carRepository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(getCar);
    }

    @PutMapping("/{id}")
    public ResponseEntity editCar(@PathVariable Long id, @RequestBody Car car)throws Exception{
        if (!carRepository.existsById(id)){
            throw new Exception("car not exist");
        }
        car.setid(id);
        car=carRepository.save(car);
        return ResponseEntity.status(HttpStatus.OK).body(car);
    }
    @DeleteMapping
    public ResponseEntity delete(@RequestParam Long id){
        if (!carRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("error Car not exist");
        }
        carRepository.deleteById(id);
       return  ResponseEntity.status(HttpStatus.OK).body("cancelled");
    }
    @DeleteMapping("/all")
    public ResponseEntity deleteAll(){
        carRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();

    }




}
