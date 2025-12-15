package com.gestorinventario.gestiondestock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestorinventario.gestiondestock.model.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    // Spring Data JPA automáticamente proporciona:
    // save(), findById(), findAll(), delete(), etc.
    
}
