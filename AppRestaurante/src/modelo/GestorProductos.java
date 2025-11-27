package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorProductos {

    private static GestorProductos instance;
    private final List<ProductoConFamilia> productos;

    private GestorProductos() {
        productos = new ArrayList<>();

        // Aquí agregas los productos que hay en cada familia
        productos.add(new ProductoConFamilia("CocaCola", 2.0, "Bebidas"));
        productos.add(new ProductoConFamilia("Agua", 1.5, "Bebidas"));
        productos.add(new ProductoConFamilia("Solomillo", 15.0, "Carnes"));
        productos.add(new ProductoConFamilia("Ración de bravas", 5.0, "Entrantes"));
        // etc.
    }

    public static GestorProductos getInstance() {
        if (instance == null) {
            instance = new GestorProductos();
        }
        return instance;
    }

    public List<ProductoConFamilia> getProductosPorFamilia(String familia) {
        return productos.stream()
                .filter(p -> p.getFamilia().equalsIgnoreCase(familia))
                .collect(Collectors.toList());
    }

    // Clase interna para relacionar producto con familia
    public static class ProductoConFamilia extends Producto {
        private String familia;

        public ProductoConFamilia(String nombre, double precio, String familia) {
            super(nombre, precio, 1); // cantidad inicial 1
            this.familia = familia;
        }

        public String getFamilia() {
            return familia;
        }
    }
}
