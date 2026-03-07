package controlador;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

/**
 * Clase utilitaria que proporciona un Sistema de Ayuda Sensible al Contexto.
 * Muestra información de ayuda específica según la pantalla en la que se
 * encuentre el usuario.
 *
 * Uso: GestorAyuda.mostrarAyuda("NombreVista");
 * Activación: Tecla F1 o botón de ayuda (?).
 *
 * @author melissa
 */
public class GestorAyuda {

    /**
     * Muestra un Alert de tipo INFORMATION con la ayuda correspondiente
     * a la vista (contexto) indicada.
     *
     * @param contexto Nombre de la vista actual
     *                 ("Principal", "Mesas", "Comandas", "Inventario")
     */
    public static void mostrarAyuda(String contexto) {

        String titulo;
        String cabecera;
        String contenido;

        switch (contexto) {

            case "Principal":
                titulo = "Ayuda – Pantalla Principal";
                cabecera = "Bienvenido a FoodFlow System";
                contenido =
                    "Esta es la pantalla de inicio de la aplicación.\n\n"
                  + "• Selecciona un usuario (Lisa, Amparo o Germán) haciendo clic "
                  + "en su imagen para acceder a la vista de mesas asignada a ese camarero.\n\n"
                  + "• Pulsa el botón «Inventario» en la parte inferior para gestionar "
                  + "los productos del restaurante (añadir, modificar o eliminar).\n\n"
                  + "• Pulsa F1 en cualquier pantalla para obtener ayuda contextual.";
                break;

            case "Mesas":
                titulo = "Ayuda – Vista de Mesas";
                cabecera = "Gestión de Mesas del Restaurante";
                contenido =
                    "En esta pantalla puedes gestionar las mesas del restaurante.\n\n"
                  + "• En la parte izquierda verás tres zonas: BARRA (rojo), "
                  + "SALÓN (azul) y TERRAZA (verde). Haz clic en una zona para "
                  + "ver sus mesas.\n\n"
                  + "• Cada mesa se muestra como una etiqueta con su código "
                  + "(B1, S1, T1…). Haz clic en una mesa para abrir su comanda.\n\n"
                  + "• Pulsa la flecha «Volver» en la esquina superior izquierda "
                  + "para regresar a la pantalla principal.\n\n"
                  + "• Pulsa F1 en cualquier momento para volver a ver esta ayuda.";
                break;

            case "Comandas":
                titulo = "Ayuda – Vista de Comandas";
                cabecera = "Gestión de la Comanda de una Mesa";
                contenido =
                    "En esta pantalla gestionas la comanda de la mesa seleccionada.\n\n"
                  + "• A la derecha se muestran los productos disponibles agrupados "
                  + "por familia (Refrescos, Cervezas, Entrantes…). Usa los botones "
                  + "de familia para filtrar.\n\n"
                  + "• Haz clic en un producto disponible para añadirlo a la comanda. "
                  + "Se descontará automáticamente del stock.\n\n"
                  + "• A la izquierda se muestra el resumen de la comanda con el total. "
                  + "Haz clic en un producto del resumen para seleccionarlo y luego "
                  + "pulsa «Eliminar» para quitarlo (se devuelve el stock).\n\n"
                  + "• Pulsa «Cobrar Comanda» para cerrar y cobrar la comanda de esta mesa.\n\n"
                  + "• Pulsa la flecha «Volver» para regresar a la vista de mesas.\n\n"
                  + "• Pulsa F1 en cualquier momento para volver a ver esta ayuda.";
                break;

            case "Inventario":
                titulo = "Ayuda – Vista de Inventario";
                cabecera = "Gestión del Inventario de Productos";
                contenido =
                    "En esta pantalla gestionas el inventario del restaurante.\n\n"
                  + "• La tabla central muestra todos los productos con su familia, "
                  + "nombre, precio y unidades disponibles.\n\n"
                  + "• Para AÑADIR un producto: rellena los campos Nombre, Familia "
                  + "(desplegable), Precio y Unidades, y pulsa el botón «Añadir».\n\n"
                  + "• Para MODIFICAR un producto: selecciónalo en la tabla (se "
                  + "rellenarán los campos), modifica lo necesario y pulsa «Modificar».\n\n"
                  + "• Para ELIMINAR un producto: selecciónalo en la tabla y pulsa "
                  + "«Eliminar». Se borrará del inventario.\n\n"
                  + "• Pulsa la flecha «Volver» para regresar a la pantalla principal.\n\n"
                  + "• Pulsa F1 en cualquier momento para volver a ver esta ayuda.";
                break;

            default:
                titulo = "Ayuda – FoodFlow System";
                cabecera = "Ayuda General";
                contenido =
                    "No se ha encontrado ayuda específica para esta pantalla.\n\n"
                  + "Navega a cualquier vista de la aplicación y pulsa F1 para "
                  + "obtener ayuda contextual sobre esa pantalla.";
                break;
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecera);

        // Usamos un Label para que el texto sea más legible y permita wrap
        Label label = new Label(contenido);
        label.setWrapText(true);
        label.setMaxWidth(480);

        alert.getDialogPane().setContent(label);
        alert.getDialogPane().setPrefWidth(520);
        alert.showAndWait();
    }
}
