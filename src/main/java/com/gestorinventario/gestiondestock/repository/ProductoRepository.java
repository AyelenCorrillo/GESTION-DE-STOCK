package com.gestorinventario.gestiondestock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestorinventario.gestiondestock.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Método automático para buscar productos cuyo nombre contenga la cadena,
    // ignorando mayúsculas/minúsculas.
    public List<Producto> findByNombreContainingIgnoreCase(String keyword);
}
