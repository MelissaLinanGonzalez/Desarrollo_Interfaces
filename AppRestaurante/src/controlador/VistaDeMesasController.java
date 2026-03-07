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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author melissa
 */
public class VistaDeMesasController implements Initializable {

    @FXML
    private Label usuarioNombre;
    @FXML
    private Label botonBarra;
    @FXML
    private Label botonSalon;
    @FXML
    private Label botonTerraza;
    @FXML
    private FlowPane pantallaBarra;
    @FXML
    private Label b1;
    @FXML
    private Label b2;
    @FXML
    private Label b3;
    @FXML
    private Label b4;
    @FXML
    private Label b5;
    @FXML
    private Label b6;
    @FXML
    private Label b7;
    @FXML
    private Label b8;
    @FXML
    private Label b9;
    @FXML
    private FlowPane pantallaSalon;
    @FXML
    private Label s1;
    @FXML
    private Label s2;
    @FXML
    private Label s3;
    @FXML
    private Label s4;
    @FXML
    private Label s5;
    @FXML
    private Label s6;
    @FXML
    private Label s7;
    @FXML
    private Label s8;
    @FXML
    private Label s9;
    @FXML
    private FlowPane pantallaTerraza;
    @FXML
    private Label t1;
    @FXML
    private Label t2;
    @FXML
    private Label t3;
    @FXML
    private Label t4;
    @FXML
    private Label t5;
    @FXML
    private Label t6;
    @FXML
    private Label t7;
    @FXML
    private Label t8;
    @FXML
    private Label t9;

    private String usuarioActual;
    @FXML
    private ImageView volver;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // --- Evento F1 para ayuda contextual ---
        botonBarra.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                    if (event.getCode() == KeyCode.F1) {
                        GestorAyuda.mostrarAyuda("Mesas");
                    }
                });
            }
        });

        // --- Tooltips ---
        Tooltip.install(botonBarra, new Tooltip("Ver las mesas de la barra"));
        Tooltip.install(botonSalon, new Tooltip("Ver las mesas del salón"));
        Tooltip.install(botonTerraza, new Tooltip("Ver las mesas de la terraza"));
        Tooltip.install(volver, new Tooltip("Volver a la pantalla principal"));
    }

    @FXML
    private void mostrarBarra(MouseEvent event) {
        mostrarPantalla(pantallaBarra);
    }

    @FXML
    private void mostrarSalon(MouseEvent event) {
        mostrarPantalla(pantallaSalon);
    }

    @FXML
    private void mostrarTerraza(MouseEvent event) {
        mostrarPantalla(pantallaTerraza);
    }

    @FXML
    private void irComanda(MouseEvent event) throws IOException {
        // Identificar qué label se pulsó
        Label mesaLabel = (Label) event.getSource();
        String mesaId = mesaLabel.getId();

        // Cargar la vista de comandas
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/vistaDeComandas.fxml"));
        Parent root = loader.load();

        // Obtener el controlador de la vista de comandas
        controlador.VistaDeComandasController controlador = loader.getController();
        controlador.setMesaId(mesaId); // Pasamos la mesa seleccionada

        // Mostrar la nueva escena
        Scene escena = new Scene(root);
        Stage stage = (Stage) volver.getScene().getWindow();
        stage.setScene(escena);
        stage.setTitle("Comanda");
    }

    public void setUsuario(String usuario) {
        this.usuarioActual = usuario;
        usuarioNombre.setText(usuario);
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

    private void mostrarPantalla(FlowPane pantalla) {
        // Ocultar todas
        pantallaBarra.setVisible(false);
        pantallaBarra.setManaged(false);
        pantallaSalon.setVisible(false);
        pantallaSalon.setManaged(false);
        pantallaTerraza.setVisible(false);
        pantallaTerraza.setManaged(false);

        // Mostrar solo la seleccionada
        pantalla.setVisible(true);
        pantalla.setManaged(true);
    }

    /**
     * Método invocado al pulsar el botón de ayuda (?).
     */
    @FXML
    private void mostrarAyudaMesas(MouseEvent event) {
        GestorAyuda.mostrarAyuda("Mesas");
    }

}
