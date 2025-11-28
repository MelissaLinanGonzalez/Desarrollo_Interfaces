/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.HashMap;
import java.util.Map;

public class GestorComanda {
    private static GestorComanda instance;
    private Map<String, Comanda> comandas;

    private GestorComanda() {
        comandas = new HashMap<>();
    }

    public static GestorComanda getInstance() {
        if (instance == null) {
            instance = new GestorComanda();
        }
        return instance;
    }

    public Comanda getComanda(String idMesa) {
        return comandas.computeIfAbsent(idMesa, k -> new Comanda(idMesa));
    }

    public void eliminarComanda(String idMesa) {
        comandas.remove(idMesa);
    }
    
    public void setComanda(String idMesa, Comanda comanda) {
    comandas.put(idMesa, comanda);
}

}
