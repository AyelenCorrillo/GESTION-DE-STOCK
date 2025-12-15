package com.gestorinventario.gestiondestock.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.gestorinventario.gestiondestock.model.Producto;
import com.gestorinventario.gestiondestock.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // 1. Obtener todos los productos
    // Si keyword es nulo o vacío, devuelve todos. Si no, usa la búsqueda personalizada.
    public List<Producto> listarTodosLosProductos(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return productoRepository.findByNombreContainingIgnoreCase(keyword);
        }
        return productoRepository.findAll();
    }

    // 2. Guardar o Actualizar un producto
    public void guardarProducto(Producto producto) {
        productoRepository.save(producto);
    }

    // 3. Obtener un producto por ID (útil para edición)
    public Producto obtenerProductoPorId(Long id) {
        Optional<Producto> resultado = productoRepository.findById(id);
        if (resultado.isPresent()) {
            return resultado.get();
        }
        // Manejo simple de error si el producto no existe (puedes mejorar esto después)
        throw new RuntimeException("No se encontró el producto con ID: " + id);
    }

    // 4. Eliminar un producto
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    
    
}
