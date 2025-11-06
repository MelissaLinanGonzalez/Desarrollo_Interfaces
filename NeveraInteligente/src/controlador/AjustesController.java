/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author melissa
 */
public class AjustesController implements Initializable {

    @FXML
    private Label labelFecha;
    @FXML
    private Label labelHora;
    @FXML
    private ImageView home;
    @FXML
    private Button bGeneral;
    @FXML
    private Button bTemperatura;
    @FXML
    private Button bAlarmas;
    @FXML
    private Button bSistema;
    @FXML
    private VBox pantallaGeneral;
    @FXML
    private ComboBox<String> comboIdioma;
    @FXML
    private VBox pantallaTemperaturas;
    @FXML
    private ComboBox<String> comoTemperatura;
    @FXML
    private VBox pantallaAlarmas;
    @FXML
    private VBox pantallaSistema;
    @FXML
    private ComboBox<String> comboWifi;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM");

        Timeline reloj = new Timeline(
            new KeyFrame(Duration.seconds(0), e -> {
                LocalDateTime ahora = LocalDateTime.now();
                labelHora.setText(ahora.format(formatoHora));
                labelFecha.setText(ahora.format(formatoFecha));
            }),
            new KeyFrame(Duration.seconds(1))
        );
        reloj.setCycleCount(Timeline.INDEFINITE);
        reloj.play();
        
        comboIdioma.getItems().addAll("Español", "Inglés", "Francés");
        comoTemperatura.getItems().addAll("ºC", "ºF");
        comboWifi.getItems().addAll("CasaDelPixel", "Wi-FightThePower", "ConexiónFugaz", "SeñalPerdida", "RouterDeLaGalaxia", "HackersNoBienvenidos");
    }    

    @FXML
    private void irHome(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/principal.fxml"));

        Parent root = loader.load();
        Scene escena = new Scene(root);

        // Obtener el Stage actual desde la imagen
        Stage stage = (Stage) home.getScene().getWindow();

        // Reemplazar la escena actual
        stage.setScene(escena);
        stage.setTitle("FrigoPie");
        
        
    }

    private void mostrarPantalla(VBox pantalla) {
    // Ocultar todas
        pantallaGeneral.setVisible(false); pantallaGeneral.setManaged(false);
        pantallaTemperaturas.setVisible(false); pantallaTemperaturas.setManaged(false);
        pantallaSistema.setVisible(false); pantallaSistema.setManaged(false);
        pantallaAlarmas.setVisible(false); pantallaAlarmas.setManaged(false);

        // Mostrar solo la seleccionada
        pantalla.setVisible(true);
        pantalla.setManaged(true);
    }
    @FXML
    private void mostrarGeneral(MouseEvent event) {
        mostrarPantalla(pantallaGeneral);
    }

    @FXML
    private void mostrarTemperatura(MouseEvent event) {
        mostrarPantalla(pantallaTemperaturas);
    }

    @FXML
    private void mostrarAlarmas(MouseEvent event) {
        mostrarPantalla(pantallaAlarmas);
    }

    @FXML
    private void mostrarSistema(MouseEvent event) {
        mostrarPantalla(pantallaSistema);
    }
    
}
