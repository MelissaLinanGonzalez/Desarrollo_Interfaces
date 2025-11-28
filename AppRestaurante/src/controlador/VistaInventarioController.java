/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Familia;
import modelo.Inventario;
import modelo.ProductoInventario;

/**
 * FXML Controller class
 *
 * @author melissa
 */
public class VistaInventarioController implements Initializable {

    private final String RUTA_ARCHIVO = "src/data/inventario.txt";

    @FXML private TableColumn<ProductoInventario, String> colFamilia;
    @FXML private TableColumn<ProductoInventario, String> colNombre;
    @FXML private TableColumn<ProductoInventario, Double> colPrecio;
    @FXML private TableColumn<ProductoInventario, Integer> colUnidades;

    @FXML private ImageView volver;
    @FXML private TextField nombreProductote;
    @FXML private ComboBox<String> comboFamilia;
    @FXML private TextField precioProducto;
    @FXML private TextField unidadesProducto;

    @FXML private TableView<ProductoInventario> productosTabla;

    private Inventario inventario = new Inventario();
    private ObservableList<ProductoInventario> listaProductos = FXCollections.observableArrayList();
    @FXML
    private Button botonAniadir;
    @FXML
    private Button botonModificar;
    @FXML
    private Button botonEliminar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboFamilia.setItems(FXCollections.observableArrayList(
                "Refrescos",
                "Cervezas",
                "Entrantes",
                "Carnes",
                "Pescados",
                "Empanados",
                "Postres"
        ));
        
        colNombre.setCellValueFactory(e ->
            new SimpleStringProperty(e.getValue().getNombre()));

        colPrecio.setCellValueFactory(e ->
            new SimpleDoubleProperty(e.getValue().getPrecio()).asObject());

        colUnidades.setCellValueFactory(e ->
            new SimpleIntegerProperty(e.getValue().getStock()).asObject());

        colFamilia.setCellValueFactory(e ->
            new SimpleStringProperty(e.getValue().getFamilia().getNombre())
        );
        
        listaProductos.addAll(cargarLista());
        productosTabla.setItems(listaProductos);

    }    

    @FXML
    private void volverPrincipal(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/vistaPrincipal.fxml"));

        Parent root = loader.load();
        Scene escena = new Scene(root);

        // Obtener el Stage actual desde la imagen
        Stage stage = (Stage) volver.getScene().getWindow();

        // Reemplazar la escena actual
        stage.setScene(escena);
        stage.setTitle("FoodFlow");
    }

    @FXML
    private void seleccionarProducto(MouseEvent event) {
        ProductoInventario aux = productosTabla.getSelectionModel().getSelectedItem();

        if (aux != null) {
            nombreProductote.setText(aux.getNombre());
            precioProducto.setText(String.valueOf(aux.getPrecio()));
            unidadesProducto.setText(String.valueOf(aux.getStock()));
            comboFamilia.setValue(aux.getFamilia().getNombre());
        }
    }

    @FXML
    private void agregarProducto(MouseEvent event) {
        try {
            String nombre = nombreProductote.getText();
            String familia = comboFamilia.getValue();
            double precio = Double.parseDouble(precioProducto.getText());
            int unidades = Integer.parseInt(unidadesProducto.getText());

            ProductoInventario nuevo = new ProductoInventario(
                nombre, precio, unidades, new Familia(familia)
            );

            listaProductos.add(nuevo);
            inventario.agregarProducto(nuevo);

            guardarLista(listaProductos);
            limpiarCampos();

        } catch (Exception e) {
            mostrarAlerta("Error", "Datos incorrectos.");
        }
    }

    @FXML
    private void modificarProducto(MouseEvent event) {
        ProductoInventario aux = productosTabla.getSelectionModel().getSelectedItem();

        if (aux == null) {
            mostrarAlerta("Error", "Selecciona un producto.");
            return;
        }

        try {
            aux.setNombre(nombreProductote.getText());
            aux.setPrecio(Double.parseDouble(precioProducto.getText()));
            aux.setStock(Integer.parseInt(unidadesProducto.getText()));
            aux.setFamilia(new Familia(comboFamilia.getValue()));

            productosTabla.refresh();
            guardarLista(listaProductos);

        } catch (Exception e) {
            mostrarAlerta("Error", "Datos inválidos.");
        }
    }

    @FXML
    private void eliminarProducto(MouseEvent event) {
        ProductoInventario seleccionado = productosTabla.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Advertencia", "Selecciona un producto para eliminar.");
            return;
        }

        listaProductos.remove(seleccionado);
        inventario.eliminarProducto(seleccionado);

        guardarLista(listaProductos);
    }
    
    private void limpiarCampos() {
        nombreProductote.clear();
        precioProducto.clear();
        unidadesProducto.clear();
        comboFamilia.getSelectionModel().clearSelection();
    }
    
    private void guardarLista(List<ProductoInventario> productos) {
        File archivo = new File(RUTA_ARCHIVO);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (ProductoInventario p : productos) {
                bw.write(
                    p.getNombre() + "|" +
                    p.getFamilia().getNombre() + "|" +
                    p.getPrecio() + "|" +
                    p.getStock()
                );
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private List<ProductoInventario> cargarLista() {
        List<ProductoInventario> lista = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);

        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("\\|");

                if (datos.length == 4) {
                    String nombre = datos[0];
                    String familia = datos[1];
                    double precio = Double.parseDouble(datos[2]);
                    int stock = Integer.parseInt(datos[3]);

                    lista.add(new ProductoInventario(
                            nombre, precio, stock, new Familia(familia)
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

   
    
}
