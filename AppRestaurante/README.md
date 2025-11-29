# 🍽️ FoodFlow System

FoodFlow es un sistema diseñado para **gestionar comandas e inventario** de un restaurante de manera centralizada, reduciendo errores humanos y mejorando la eficiencia del servicio.  

Actualmente, el sistema cuenta con **4 vistas principales**:

---

## 🏠 Vista Principal
Desde aquí se puede acceder a:
- **Inventario**  
- **Usuarios para realizar comandas**  

Se registra el **ID del usuario** que ha iniciado sesión para identificar quién realiza las operaciones.

---

## 📦 Vista de Inventario
Incluye un **CRUD completo** conectado a `inventario.txt`:
- **Agregar, modificar o eliminar productos**
- Organizar productos por **familias** mediante un `ComboBox`
- Datos requeridos:
  - Nombre del producto
  - Precio unitario
  - Stock
  - Familia  

Permite mantener la **consistencia de datos** y facilita la gestión para futuras ventas.

---

## 🪑 Vista de Mesas
Permite visualizar y seleccionar mesas en diferentes áreas del restaurante:
- Barra  
- Salón  
- Terraza  

Se utiliza el **ID del usuario** para identificar al trabajador que atiende la mesa, aunque la vista es común para todos.

---

## 🧾 Vista de Comandas
Dividida en dos secciones:

### Resumen de la Comanda
- Lista los productos añadidos a la mesa
- Muestra **unidades y precio de cada producto**
- Calcula automáticamente la **suma total del ticket**

### Selección de Productos
- Botones de **familias de productos**  
- Productos correspondientes a la familia seleccionada  
- Permite agregar productos solo si **hay stock disponible**  

💡 Si un producto está agotado, aparece una alerta para evitar que se agregue.

---

## 🛠️ Modelos del Sistema

- **Producto**  
  Objeto principal que representa los productos disponibles. Contiene:
  - Nombre
  - Precio
  - Cantidad pedida
  - Usuario que añade el producto

- **Comanda**  
  Representa la comanda de cada mesa. Contiene:
  - ID de la mesa
  - Lista de productos de la comanda  

- **Familia**  
  Cada producto pertenece a una familia para facilitar su organización.

- **Inventario**  
  Lista de productos disponibles, guardados y leídos desde un archivo `.txt`.

- **GestorComanda**  
  - Guarda todas las comandas activas  
  - Comprueba si una mesa ya tiene comanda para no sobrescribirla  
  - Permite borrar la comanda de una mesa  
  - Mantiene la persistencia en memoria mientras se trabaja

- **ProductoInventario**  
  Relaciona los productos con el inventario, gestionando:
  - Nombre, precio, stock y familia
  - Métodos `aumentarStock` y `reducirStock` para mantener consistencia al vender o modificar inventario

---

## ⚠️ Control de Stock
La aplicación **verifica la disponibilidad de stock** al añadir productos a la comanda.  
Si un producto se encuentra agotado, se mostrará una alerta y no se añadirá al ticket, evitando errores en la gestión de inventario.

---
