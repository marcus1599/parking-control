package com.api.parkingcontrol.services;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import com.api.parkingcontrol.models.Produto;
import com.api.parkingcontrol.repositores.ParkingSpotRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingSpotService {
 
        final ParkingSpotRepository ParkingSpotRepository;

        public ParkingSpotService(ParkingSpotRepository ParkingSpotRepository){
                this.ParkingSpotRepository = ParkingSpotRepository;
        }


        @Transactional
        public Object save(Produto parkingSpotModel) {
            return ParkingSpotRepository.save(parkingSpotModel);
        }


        public boolean existsByLicensePlatCar(String licensePlatCar) {
            return ParkingSpotRepository.existsByLicensePlatCar(licensePlatCar);
        }


        public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
            return ParkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
        }


        public boolean existsByApartmentAndBlock(String apartment, String block) {
            return ParkingSpotRepository.existsByApartmentAndBlock(apartment, block);
        }


        public List<Produto> findAll() {
            return ParkingSpotRepository.findAll();
        }


        public Optional<Produto> findById(UUID id) {
            return ParkingSpotRepository.findById(id);
        }

        @Transactional
        public void delete(Produto parkingSpotModel) {
            ParkingSpotRepository.delete(parkingSpotModel);
        }
        

}
