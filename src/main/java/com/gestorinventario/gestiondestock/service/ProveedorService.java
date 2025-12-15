package com.gestorinventario.gestiondestock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestorinventario.gestiondestock.model.Proveedor;
import com.gestorinventario.gestiondestock.repository.ProveedorRepository;


@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    // Método para obtener la lista completa de proveedores
    public List<Proveedor> listarTodosLosProveedores() {
        return proveedorRepository.findAll();
    }

    // Nuevo método para guardar o actualizar un proveedor
    public void guardarProveedor(Proveedor proveedor) {
        proveedorRepository.save(proveedor);
    }
    
}
