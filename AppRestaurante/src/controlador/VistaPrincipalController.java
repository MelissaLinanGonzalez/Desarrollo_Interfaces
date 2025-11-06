/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author melissa
 */
public class VistaPrincipalController implements Initializable {

    @FXML
    private ImageView Lisa;
    @FXML
    private ImageView Amparo;
    @FXML
    private ImageView German;
    @FXML
    private Button btoInventario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void cambiarAMesas(MouseEvent event) {
        try {
        // Detectar cuál imagen se clicó
        ImageView imagenClicada = (ImageView) event.getSource();
        String nombreUsuario = imagenClicada.getId(); // Lisa, Amparo, German...

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/vistaDeMesas.fxml"));
        Parent root = loader.load();

        // Obtener el controlador de la siguiente vista
        VistaDeMesasController controlador = loader.getController();
        controlador.setUsuario(nombreUsuario); // Pasamos el nombre

        // Cambiar la escena
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Mesas de " + nombreUsuario);
        stage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void mostrarInventario(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/vistaInventario.fxml"));

        Parent root = loader.load();
        Scene escena = new Scene(root);

        // Obtener el Stage actual desde la imagen
        Stage stage = (Stage) btoInventario.getScene().getWindow();

        // Reemplazar la escena actual
        stage.setScene(escena);
        stage.setTitle("Inventario");
    }


    
}
