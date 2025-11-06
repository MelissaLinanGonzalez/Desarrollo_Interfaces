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
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelo.Alimento;

/**
 * FXML Controller class
 *
 * @author melissa
 */
public class ListaCompraController implements Initializable {

    @FXML
    private ImageView home;
    @FXML
    private Label labelFecha;
    @FXML
    private Label labelHora;
    @FXML
    private TextField nombreInput;
    @FXML
    private TextField cantidadInput;
    @FXML
    private Button bAgregar;
    @FXML
    private Button bEliminar;
    @FXML
    private Button bModificar;
    @FXML
    private ComboBox<String> comboTipo;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colCantidad;
    @FXML
    private TableColumn<?, ?> colTipo;
    @FXML
    private TableView<Alimento> alimentosTabla;
    
    private ObservableList<Alimento> gAlimento;
    

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
        
        comboTipo.getItems().addAll("Kilogramos", "Gramos", "Litros", "Unidades");
        
        gAlimento = FXCollections.observableArrayList();
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colTipo.setCellValueFactory(new PropertyValueFactory("tipoUnidad"));
        
        cargarLista();
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

    @FXML
    private void agregarAlimento(MouseEvent event) {
               
        String aNombre = nombreInput.getText();
        int aCantidad = parseInt(cantidadInput.getText());
        String atipoUnidad = comboTipo.getValue();
        
        Alimento elemento = new Alimento(aNombre, aCantidad, atipoUnidad);
        
        gAlimento.add(elemento);
        alimentosTabla.setItems(gAlimento);
        // Limpiar los campos
        nombreInput.clear();
        cantidadInput.clear();
        comboTipo.getSelectionModel().clearSelection();
        
        guardarLista(gAlimento);
    }

    @FXML
    private void modificarAlimento(MouseEvent event) {
        Alimento aux = alimentosTabla.getSelectionModel().getSelectedItem();
        
        if(aux==null){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setContentText("El alimento seleccionado no existe en la tabla");
            alerta.showAndWait();
        }else{
            
            String aNombre = nombreInput.getText();
            int aCantidad = parseInt(cantidadInput.getText());
            String atipoUnidad = comboTipo.getValue();

            Alimento elemento = new Alimento(aNombre, aCantidad, atipoUnidad);
            
            if(!gAlimento.contains(elemento)){
                aux.setNombre(aNombre);
                aux.setCantidad(aCantidad);
                aux.setTipoUnidad(atipoUnidad);
                
                alimentosTabla.refresh();
            }

        }
        
        guardarLista(gAlimento);
    }

    @FXML
    private void eliminarAlimento(MouseEvent event) {
        Alimento seleccionado = alimentosTabla.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Ningún elemento seleccionado");
            alerta.setHeaderText(null);
            alerta.setContentText("Selecciona un alimento de la tabla para eliminarlo.");
            alerta.showAndWait();
            return;
        }else{
            gAlimento.remove(seleccionado);
            alimentosTabla.refresh();
        }
        
        guardarLista(gAlimento);

    
    }

    @FXML
    private void seleccionarAlimento(MouseEvent event) {
        Alimento aux = alimentosTabla.getSelectionModel().getSelectedItem();
        
        if(aux!=null){
            nombreInput.setText(aux.getNombre());
            cantidadInput.setText(String.valueOf(aux.getCantidad()));
            comboTipo.setValue(aux.getTipoUnidad());
        }
    }
    
    public void guardarLista(List<Alimento> alimentos){
        File archivo = new File("src/data/alimentos.txt");
        
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            for(Alimento a : alimentos){
                bw.write(a.getNombre() + "|" + a.getCantidad() + "|" + a.getTipoUnidad());
                bw.newLine();
            }
            bw.close();
            
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public List<Alimento> cargarLista(){
        List<Alimento> lista = new ArrayList<>();
        File archivo = new File("data/alimentos.txt");
        if (!archivo.exists()){
            return lista;
        }
        try{
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while((linea = br.readLine()) != null){
                String[] datos = linea.split("\\|");
                if(datos.length == 3){
                    String nombre = datos[0];
                    int cantidad = Integer.parseInt(datos[1]);
                    String tipo = datos[2];
                    lista.add(new Alimento(nombre, cantidad, tipo));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return lista;
    }
    
}
