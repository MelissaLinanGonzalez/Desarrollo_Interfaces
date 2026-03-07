package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FXML Controller class
 *
 * @author melissa
 */
public class GestorProductos {

    private static GestorProductos instance;
    private final List<ProductoConFamilia> productos;

    private GestorProductos() {
        productos = new ArrayList<>();

        
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
    // Recomendado por ChatGPT para no tener que modificar todo el codigo anteriormente planteado
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
