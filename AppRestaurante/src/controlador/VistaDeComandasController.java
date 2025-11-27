package controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import modelo.Comanda;
import modelo.GestorComanda;
import modelo.GestorProductos;
import modelo.Producto;
import modelo.GestorProductos.ProductoConFamilia;

public class VistaDeComandasController implements Initializable {

    @FXML
    private FlowPane productosPane;  // FlowPane donde se muestran los productos

    private String mesaId;  // ID de la mesa activa

    public void setMesaId(String mesaId) {
        this.mesaId = mesaId;
        recargarComanda();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización si hace falta
    }

    @FXML
    private void filtrarFamilia(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String familia = btn.getText();

        productosPane.getChildren().clear();

        // Obtengo los productos de la familia
        List<ProductoConFamilia> productos = GestorProductos.getInstance().getProductosPorFamilia(familia);

        for (ProductoConFamilia p : productos) {
            Label lbl = new Label(p.getNombre() + " - " + p.getPrecio() + "€");
            lbl.setPadding(new Insets(10));
            lbl.setStyle("-fx-background-color: #eee; -fx-border-radius: 5; -fx-background-radius: 5;");

            // Al pulsar el producto, lo añadimos a la comanda de la mesa
            lbl.setOnMouseClicked(e -> {
                Comanda c = GestorComanda.getInstance().getComanda(mesaId);
                c.agregarProducto(new Producto(p.getNombre(), p.getPrecio(), 1)); // agregamos copia con cantidad 1
                recargarComanda(); // refresca la lista de la mesa
                System.out.println("Agregado a " + mesaId + ": " + p.getNombre());
            });

            productosPane.getChildren().add(lbl);
        }
    }

    private void recargarComanda() {
        productosPane.getChildren().clear();
        Comanda c = GestorComanda.getInstance().getComanda(mesaId);
        List<Producto> productosMesa = c.getProductos();

        for (Producto p : productosMesa) {
            Label lbl = new Label(p.getNombre() + " - " + p.getPrecio() + "€ x" + p.getCantidad());
            lbl.setPadding(new Insets(10));
            lbl.setStyle("-fx-background-color: #dff0d8; -fx-border-radius: 5; -fx-background-radius: 5;");
            productosPane.getChildren().add(lbl);
        }
    }
}
