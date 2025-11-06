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
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author melissa
 */
public class PrincipalController implements Initializable {

    @FXML
    private Label labelFecha;
    @FXML
    private Label labelHora;
    @FXML
    private Label labelCiudad;
    @FXML
    private Label labelTiempo;
    @FXML
    private ImageView ajustes;
    @FXML
    private ImageView internet;
    @FXML
    private ImageView listaCompra;
    @FXML
    private AnchorPane rootPane;

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
        
        labelCiudad.setText("Palma del Río");
        labelTiempo.setText("Cargando el tiempo...");
        
        new Thread(() -> {
        try {
            String apiKey = "ea62ba5408bb6d772c747a2616ac3e66";
            String ciudad = URLEncoder.encode("Palma del Río", "UTF-8");
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" 
                         + ciudad + "&units=metric&lang=es&appid=" + apiKey;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());
            double temp = json.getJSONObject("main").getDouble("temp");
            String desc = json.getJSONArray("weather").getJSONObject(0).getString("description");

            javafx.application.Platform.runLater(() ->
                labelTiempo.setText(String.format("%.1f°C — %s", temp, desc))
            );

        } catch (Exception e) {
            javafx.application.Platform.runLater(() ->
                labelTiempo.setText("Error al obtener el tiempo")
            );
        }
    }).start();
    }    

    @FXML
    private void irAjustes(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/ajustes.fxml"));

        Parent root = loader.load();
        Scene escena = new Scene(root);

        // Obtener el Stage actual desde la imagen
        Stage stage = (Stage) ajustes.getScene().getWindow();

        // Reemplazar la escena actual
        stage.setScene(escena);
        stage.setTitle("Ajustes");
    }

    @FXML
    private void irListaCompra(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/listaCompra.fxml"));

        Parent root = loader.load();
        Scene escena = new Scene(root);

        // Obtener el Stage actual desde la imagen
        Stage stage = (Stage) ajustes.getScene().getWindow();

        // Reemplazar la escena actual
        stage.setScene(escena);
        stage.setTitle("Lista de Compra");
    }

    @FXML
    private void abrirGoogle(MouseEvent event) {
        WebView webView = new WebView();
        webView.setPrefSize(400, 600);
        webView.getEngine().load("https://www.google.com");

        // Agregar el WebView al AnchorPane
        rootPane.getChildren().add(webView);
        AnchorPane.setTopAnchor(webView, 100.0);
        AnchorPane.setLeftAnchor(webView, 20.0);

        // Crear el botón de cerrar
        javafx.scene.control.Button btnCerrar = new javafx.scene.control.Button("Cerrar");
        btnCerrar.setLayoutX(365);
        btnCerrar.setLayoutY(70);
        btnCerrar.setOnAction(e -> rootPane.getChildren().removeAll(webView, btnCerrar));

        // Agregar el botón al AnchorPane 
        rootPane.getChildren().add(btnCerrar);
        btnCerrar.toFront(); 
    }
    
}
