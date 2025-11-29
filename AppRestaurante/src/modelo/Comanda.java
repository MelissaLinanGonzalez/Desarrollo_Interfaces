/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author melissa
 */
public class Comanda {
    private String idMesa;
    private List<Producto> productos;

    public Comanda(String idMesa) {
        this.idMesa = idMesa;
        this.productos = new ArrayList<>();
        cargarComanda(); // cargar productos desde archivo si existe
    }

    public String getIdMesa() { return idMesa; }

    public List<Producto> getProductos() { return productos; }

    public void agregarProducto(Producto p) {
        // Si el producto ya existe, aumentar cantidad
        for (Producto prod : productos) {
            if (prod.getNombre().equalsIgnoreCase(p.getNombre())) {
                prod.setCantidad(prod.getCantidad() + p.getCantidad());
                guardarComanda();
                return;
            }
        }
        productos.add(p);
        guardarComanda();
    }

    public double getTotalComanda() {
        return productos.stream().mapToDouble(Producto::getSubtotal).sum();
    }

    private void guardarComanda() {
        File archivo = new File("src/data/comandas/" + idMesa + ".txt");
        archivo.getParentFile().mkdirs(); 

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Producto p : productos) {
                bw.write(p.getNombre() + "|" + p.getPrecio() + "|" + p.getCantidad());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarComanda() {
        File archivo = new File("src/data/comandas/" + idMesa + ".txt");
        if (!archivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length == 3) {
                    String nombre = datos[0];
                    double precio = Double.parseDouble(datos[1]);
                    int cantidad = Integer.parseInt(datos[2]);
                    productos.add(new Producto(nombre, precio, cantidad));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void eliminarProducto(String nombre) {
        productos.removeIf(p -> p.getNombre().equalsIgnoreCase(nombre));
    }
}
