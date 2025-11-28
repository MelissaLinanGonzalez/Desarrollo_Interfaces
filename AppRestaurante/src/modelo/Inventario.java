/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author melissa
 */
public class Inventario {
    private List<ProductoInventario> productos = new ArrayList<>();
    
    public void agregarProducto(ProductoInventario p){
        productos.add(p);
    }
    
    public void eliminarProducto(ProductoInventario p){
        productos.remove(p);
    }
    
    public List<ProductoInventario> getProductos(){
        return productos;
    }
    
    public List<ProductoInventario> getProductosPorFamilia(String nombreFamilia){
        return productos.stream()
                .filter(p -> p.getFamilia().getNombre().equalsIgnoreCase(nombreFamilia))
                .collect(Collectors.toList());
    }
    
    public ProductoInventario buscarProducto(String nombre){
        return productos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }
}
