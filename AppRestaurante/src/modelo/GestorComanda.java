/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author melissa
 */
public class GestorComanda {
    private Map<String, Comanda> comandas = new HashMap<>();
    
    public Comanda getComanda(String idMesa) {
        // Si no existe, se crea una nueva
        if (!comandas.containsKey(idMesa)) {
            Comanda nueva = new Comanda(idMesa);
            comandas.put(idMesa, nueva);
        }
        // Devuelve la que ya existe o la recién creada
        return comandas.get(idMesa);
    }

    // Elimina la comanda de una mesa
    public void eliminarComanda(String idMesa) {
        comandas.remove(idMesa);
    }
}
