/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author melissa
 */
public class ProductoInventario {
    private String nombre;
    private double precio;
    private int stock;
    private Familia familia;

    public ProductoInventario(String nombre, double precio, int stock, Familia familia) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.familia = familia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }
    
    public void reducirStock(int cantidad){
        this.stock -= cantidad;
    }
    
    public void aumentarStock(int cantidad){
        this.stock += cantidad;
    }
}
