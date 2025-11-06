/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author melissa
 */
public class Comanda {
    private String idMesa;
    private List<Producto> productos = new ArrayList<>();
    
    public Comanda(String idMesa){
        this.idMesa = idMesa;
    }

    public String getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(String idMesa) {
        this.idMesa = idMesa;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    
    public void agregarProducto(Producto p){
        productos.add(p);
    }
    
    public double getTotal(){
        return productos.stream().mapToDouble(Producto::getSubtotal).sum();
    }
}
