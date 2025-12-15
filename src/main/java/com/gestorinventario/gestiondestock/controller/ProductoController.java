package com.gestorinventario.gestiondestock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestorinventario.gestiondestock.model.Producto;
import com.gestorinventario.gestiondestock.model.Proveedor;
import com.gestorinventario.gestiondestock.service.ProductoService;
import com.gestorinventario.gestiondestock.service.ProveedorService;


@Controller
public class ProductoController {

    // Inyectamos los servicios que vamos a necesitar
    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProveedorService proveedorService;

    // 1. Mostrar la lista de productos
    // Usamos @RequestParam para capturar el parámetro 'keyword' de la URL (ej: /?keyword=tv)
    @GetMapping("/")
    public String listarProductos(@RequestParam(required = false) String keyword, Model model) {
        // Llamamos al servicio con el keyword. Si keyword es nulo, devuelve todos.
        model.addAttribute("productos", productoService.listarTodosLosProductos(keyword));

        // Mantenemos el keyword en el modelo para que la caja de texto no se borre
        model.addAttribute("keyword", keyword);
        
        // Devolvemos el nombre de la plantilla HTML
        return "productos"; 
    }

    // 2. Mostrar el formulario para registrar un nuevo proveedor
    // Necesitamos que haya proveedores para poder crear productos
    @GetMapping("/proveedores/nuevo")
    public String mostrarFormularioRegistroProveedor(Model model) {
        // Creamos un objeto Proveedor vacío para enlazar los datos del formulario
        model.addAttribute("proveedor", new Proveedor());
        
        // Devolvemos la plantilla HTML del formulario
        return "nuevo_proveedor"; 
    }

    // 3. Guardar un nuevo proveedor
    @PostMapping("/proveedores/guardar")
    public String guardarProveedor(@ModelAttribute("proveedor") Proveedor proveedor) {
        // En este punto, no tenemos un ProveedorService, así que necesitamos crearlo o actualizarlo.
        // Iremos a crear un método guardar en el ProveedorService.
        
        // **Antes de continuar, ve al ProveedorService.java (Punto B) y añade el método guardarProveedor.**
        
        // Una vez que añades el método, vuelve aquí:
        proveedorService.guardarProveedor(proveedor); 
        
        // Redirigimos al usuario al formulario de nuevo proveedor, o a la lista principal (productos)
        return "redirect:/"; 
    }

    // 4. Mostrar el formulario para registrar un nuevo producto (GET)
    @GetMapping("/productos/nuevo")
    public String mostrarFormularioRegistroProducto(Model model) {
        // Objeto vacío para enlazar los datos
        model.addAttribute("producto", new Producto());
        
        // Lista de proveedores para el campo <select> del formulario
        model.addAttribute("listaProveedores", proveedorService.listarTodosLosProveedores());
        
        // Nombre de la plantilla HTML
        return "nuevo_producto"; 
    }

    // 5. Guardar un nuevo producto (POST)
    @PostMapping("/productos/guardar")
    public String guardarProducto(@ModelAttribute("producto") Producto producto) {
        productoService.guardarProducto(producto);
        // Redirigimos a la lista principal
        return "redirect:/"; 
    }

    // 6. Mostrar formulario para EDITAR un producto (GET)
    // El @PathVariable extrae el ID del producto de la URL
    @GetMapping("/productos/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable(name = "id") Long id, Model model) {
        
        // 1. Buscamos el producto existente por ID y lo agregamos al modelo
        Producto producto = productoService.obtenerProductoPorId(id);
        model.addAttribute("producto", producto);

        // 2. Necesitamos la lista de proveedores para el campo de selección (dropdown)
        model.addAttribute("listaProveedores", proveedorService.listarTodosLosProveedores());

        // Reutilizaremos la plantilla que ya creamos para registrar nuevos productos:
        return "nuevo_producto"; 
    }

    // 7. Eliminar un producto (GET/DELETE)
    // Usamos GET para simpleza, pero en producción usarías un método DELETE.
    @GetMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable(name = "id") Long id) {
        
        productoService.eliminarProducto(id);
        
        // Redirigimos a la lista principal después de eliminar
        return "redirect:/";
    }
    
}
