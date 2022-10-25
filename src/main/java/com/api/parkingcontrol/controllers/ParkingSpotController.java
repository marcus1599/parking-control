package com.api.parkingcontrol.controllers;


import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import com.api.parkingcontrol.dtos.Produtodto;
import com.api.parkingcontrol.models.Produto;
import com.api.parkingcontrol.services.ParkingSpotService;

@RestController
@CrossOrigin(origins ="*",maxAge=3600)
@RequestMapping("parking-spot")
public class ParkingSpotController {

        final ParkingSpotService parkingSpotService;

        public ParkingSpotController(ParkingSpotService parkingSpotService) {
             this.parkingSpotService= parkingSpotService;
        }

        
        @PostMapping
        public ResponseEntity<Object>saveParkingSpot(@RequestBody @Valid Produtodto ParkingSpotDto){
            
            if(parkingSpotService.existsByLicensePlatCar(ParkingSpotDto.getLicensePlatCar())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: A placa deste carro já está em Uso!");
            }
            if(parkingSpotService.existsByParkingSpotNumber(ParkingSpotDto.getParkingSpotNumber())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: A Vaga já está em Uso!");
            }
            if(parkingSpotService.existsByApartmentAndBlock(ParkingSpotDto.getApartment(),ParkingSpotDto.getBlock())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Bloco ou Apartamento já está em Uso!");
            }
            
            
            var ParkingSpotModel = new Produto();

            BeanUtils.copyProperties(ParkingSpotDto, ParkingSpotModel);
            ParkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

            return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(ParkingSpotModel));

        }
    @GetMapping
    public ResponseEntity<List<Produto>> getAllParkingSpot(){


        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") UUID id){
        Optional<Produto> parkingSpotModelOptional = parkingSpotService.findById(id);
        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot Not Found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id){

        Optional<Produto> parkingSpotModelOptional = parkingSpotService.findById(id);
       
        if(!parkingSpotModelOptional.isPresent())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot Not Found");
        }

        parkingSpotService.delete(parkingSpotModelOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted Sucefully");
    }
    @PutMapping
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id,
     @RequestBody @Valid Produtodto parkingSpotDto)
     {
        Optional<Produto> parkingSpotModelOptional = parkingSpotService.findById(id);
        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot Not Found");
        }

        var parkingSpotModel = new Produto();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
        parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());
       

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
    }
    
    

    
}
