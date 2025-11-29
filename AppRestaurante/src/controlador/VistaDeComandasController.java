package controlador;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import modelo.Comanda;
import modelo.Familia;
import modelo.GestorComanda;
import modelo.Producto;
import modelo.ProductoInventario;

public class VistaDeComandasController implements Initializable {

    @FXML private FlowPane productosDisponiblesPane;
    @FXML private FlowPane resumenComandaPane;
    @FXML private Label totalLabel;
    @FXML private ImageView volver;

    private String mesaId;
    @FXML
    private ImageView volver1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        mostrarProductosDisponibles("");
    }

    public void setMesaId(String mesaId) {
        this.mesaId = mesaId;
        Comanda c = cargarComanda(mesaId);
        GestorComanda.getInstance().setComanda(mesaId, c);
        refrescarResumenComanda();
    }

    @FXML
    private void filtrarFamilia(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String familia = btn.getText();
        mostrarProductosDisponibles(familia);
    }

    private void mostrarProductosDisponibles(String familia) {
        productosDisponiblesPane.getChildren().clear();
        List<Producto> productos = cargarProductosPorFamilia(familia);

        for (Producto p : productos) {
            Label lbl = new Label(p.getNombre() + " - " + p.getPrecio() + "€");
            lbl.setPadding(new Insets(10));
            lbl.setStyle("-fx-background-color: #eee; -fx-border-radius: 5; -fx-background-radius: 5;");
            lbl.setOnMouseClicked(e -> {
            String nombreProducto = p.getNombre();

            int stockActual = obtenerStockProducto(nombreProducto);

            if (stockActual <= 0) {
                mostrarAlerta("Sin stock", "No queda stock de " + nombreProducto);
                return;
            }
            boolean reducido = reducirStock(nombreProducto, 1);

            if (!reducido) {
                mostrarAlerta("Sin stock", "No queda stock suficiente de " + nombreProducto);
                return;
            }

            Comanda c = GestorComanda.getInstance().getComanda(mesaId);
            c.agregarProducto(new Producto(p.getNombre(), p.getPrecio(), 1));

            refrescarResumenComanda();
            
        });
            productosDisponiblesPane.getChildren().add(lbl);
        }
    }

    private List<Producto> cargarProductosPorFamilia(String familia) {
        List<Producto> lista = new ArrayList<>();
        File archivo = new File("src/data/inventario.txt");

        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("\\|");
                
                if (datos.length == 4) {
                    String nombre = datos[0];
                    String fam = datos[1];
                    double precio = Double.parseDouble(datos[2]);
                    if (familia.isEmpty() || fam.equalsIgnoreCase(familia)) {
                        lista.add(new Producto(nombre, precio, 0)); // cantidad inicial 0
                    }
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    private void refrescarResumenComanda() {
        resumenComandaPane.getChildren().clear();
        Comanda c = GestorComanda.getInstance().getComanda(mesaId);
        double total = 0;

        for (Producto p : c.getProductos()) {
            double subtotal = p.getPrecio() * p.getCantidad();
            total += subtotal;
            Label lbl = new Label(p.getNombre() + " x" + p.getCantidad() + " = " + subtotal + "€");
            lbl.setPadding(new Insets(5));
            lbl.setStyle("-fx-background-color: #dff0d8; -fx-border-radius: 5; -fx-background-radius: 5;");
            resumenComandaPane.getChildren().add(lbl);
        }

        totalLabel.setText("Total: " + total + "€");
    }

    private Comanda cargarComanda(String mesaId) {
        Comanda c = new Comanda(mesaId);
        return c;
    }

    @FXML
    private void volverMesas(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/vistaDeMesas.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) volver.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("FoodFlow");
    }
    
    private int obtenerStockProducto(String nombre) {
        File archivo = new File("src/data/inventario.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("\\|");

                if (datos[0].equalsIgnoreCase(nombre)) {
                    return Integer.parseInt(datos[3]); 
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        return -1; 
    }
    
    private boolean reducirStock(String nombreProducto, int cantidadReducir) {

    File archivo = new File("src/data/inventario.txt");
    List<String> lineas = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;

        while ((linea = br.readLine()) != null) {
            String[] datos = linea.split("\\|");

            if (datos[0].equalsIgnoreCase(nombreProducto)) {

                int stockActual = Integer.parseInt(datos[3]);

                if (stockActual < cantidadReducir) {
                    return false;
                }

                int nuevoStock = stockActual - cantidadReducir;

                linea = datos[0] + "|" + datos[1] + "|" + datos[2] + "|" + nuevoStock;
            }

            lineas.add(linea);
        }

    } catch (Exception e) { e.printStackTrace(); }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
        for (String l : lineas) {
            bw.write(l);
            bw.newLine();
        }
    } catch (Exception e) { e.printStackTrace(); }

    return true;
    }
    
    private void mostrarAlerta(String titulo, String mensaje) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setHeaderText(titulo);
    alert.setContentText(mensaje);
    alert.showAndWait();
    }
    
}
