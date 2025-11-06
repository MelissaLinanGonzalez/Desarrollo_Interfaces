/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author melissa
 */
public class Familia {
    private String nombre;
    
    public Familia(String nombre){
        this.nombre = nombre;
    }
    
    public String getNombre(){
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
}
