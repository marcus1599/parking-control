package com.api.parkingcontrol.repositores;


import java.util.UUID;

import com.api.parkingcontrol.models.Produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ParkingSpotRepository extends JpaRepository<Produto, UUID> {

    boolean existsByLicensePlatCar(String licensePlatCar);
    boolean existsByParkingSpotNumber(String parkingSpotNumber);
    boolean existsByApartmentAndBlock(String apartment,String block);

}
