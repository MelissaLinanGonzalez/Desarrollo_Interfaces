/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author melissa
 */
public class Alimento {
    private String nombre;
    private int cantidad;
    private String tipoUnidad;
    
    public Alimento(){
        
    }
    
    public Alimento(String nombre, int cantidad, String tipoUnidad){
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.tipoUnidad = tipoUnidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoUnidad() {
        return tipoUnidad;
    }

    public void setTipoUnidad(String tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
    }
    
    
    
}
