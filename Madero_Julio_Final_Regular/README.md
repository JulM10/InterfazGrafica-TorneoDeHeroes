# ⚔️ Simulador de Batalla — Examen Final

Proyecto Java con interfaz gráfica Swing, arquitectura MVC y base de datos MySQL.  
**Materia:** Interfaz Gráfica  
**Docente:** Giuliana Dealbera Etchechoury  
**Alumno:** Julio Madero — DNI 40.974.293

---

## 🗂 Estructura del proyecto

```
final_batalla/
├── src/
│   └── batalla/
│       ├── controlador/
│       │   ├── BatallaControlador.java
│       │   ├── ConfigControlador.java
│       │   ├── ReporteControlador.java
│       │   └── TorneoControlador.java
│       ├── main/
│       │   └── App.java
│       ├── modelo/
│       │   ├── Batalla.java
│       │   ├── Heroe.java
│       │   ├── Personaje.java
│       │   ├── Torneo.java
│       │   └── Villano.java
│       ├── persistencia/
│       │   ├── BatallaDAO.java
│       │   ├── ConexionDB.java
│       │   └── PersonajeDAO.java
│       └── vista/
│           ├── VentanaBatalla.java
│           ├── VentanaComparador.java
│           ├── VentanaConfig.java
│           ├── VentanaPrincipal.java
│           ├── VentanaReporte.java
│           └── VentanaTorneo.java
├── lib/
│   └── mysql-connector-j-9.5.0.jar
├── out/                               ← clases compiladas (generado automáticamente)
├── sql/
│   └── mysql.sql
└── README.md
```

---

## ⚙️ Requisitos previos

| Herramienta | Versión mínima |
|---|---|
| JDK | 11 o superior |
| MySQL Server | 8.0 o superior |
| MySQL Workbench | cualquier versión reciente |
| MySQL Connector/J | 9.x |

---

## 🗄 Configuración de la base de datos

Abrí MySQL Workbench y ejecutá el script `sql/mysql.sql` con ⚡, o bien:

```sql
SOURCE /ruta/al/proyecto/sql/mysql.sql;
```

Verificá la conexión en `ConexionDB.java`:

```java
private static final String URL  = "jdbc:mysql://localhost:3306/batalla_db";
private static final String USER = "root";
private static final String PASS = "tu_contraseña";
```

---

## 🔨 Compilar y ejecutar

Abrí Git Bash en la raíz del proyecto y ejecutá:

```bash
rm -rf out && mkdir out && javac -encoding UTF-8 -cp "lib/mysql-connector-j-9.5.0.jar" -d out -sourcepath src src/batalla/main/App.java && java -cp "out;lib/mysql-connector-j-9.5.0.jar" batalla.main.App
```

Este comando limpia la carpeta `out/`, recompila todo desde cero y lanza la aplicación en un solo paso.

---

## 🎮 Funcionalidades implementadas

### Batalla 1 vs 1
Seleccioná un héroe y un villano de la tabla, hacé clic en sus filas para marcarlos y presioná "Iniciar Batalla". La batalla se desarrolla turno a turno con el botón "Siguiente Turno". Al finalizar, los datos se guardan automáticamente en la base de datos y la vida de los personajes se resetea para la próxima batalla.

### Reporte General
Accesible desde el botón "Ver Reporte" en el menú de configuración. Muestra el ranking de personajes ordenado por victorias, el historial de las últimas 10 batallas con fecha, participantes y ganador, y estadísticas generales como la batalla más larga y el personaje MVP.

### Comparador de Personajes
Seleccioná un héroe y un villano de la tabla y presioná "Comparar". Se abre una ventana con las estadísticas lado a lado. Los valores se muestran en verde si ese personaje supera al rival en esa stat, en rojo si está por debajo, y en amarillo si empatan.

### Modo Torneo
Presioná "Torneo" y seleccioná exactamente 4 personajes con Ctrl+clic. Se arma un bracket automático con 2 semifinales y una final. Cada batalla se juega manualmente con el botón "Siguiente Turno". El ganador de cada batalla avanza a la siguiente fase hasta coronar un campeón.

---

## 🏗 Arquitectura MVC

```
Vista (Swing)             Controlador                  Modelo
─────────────────         ──────────────────           ──────────────
VentanaPrincipal          ConfigControlador    ──────► Personaje
VentanaConfig      ─────► BatallaControlador           Heroe
VentanaBatalla            ReporteControlador           Villano
VentanaReporte            TorneoControlador            Batalla
VentanaComparador                │                     Torneo
VentanaTorneo                    ▼
                          PersonajeDAO
                          BatallaDAO
                                 │
                                 ▼
                             MySQL BD
```

La Vista nunca accede directamente al Modelo. Todo pasa por el Controlador.

---

## ❌ Errores comunes

| Error | Causa | Solución |
|---|---|---|
| `ClassNotFoundException: com.mysql.cj.jdbc.Driver` | Falta el `.jar` en el classpath | Verificar que el `.jar` está en `lib/` y el path es correcto |
| `Communications link failure` | MySQL no está corriendo | Iniciar MySQL Server y verificar `ConexionDB.java` |
| `Duplicate entry for key 'apodo'` | El apodo ya existe en BD | Cambiar el apodo o limpiar la tabla |
| `cannot find symbol` al compilar | Clase no encontrada | Verificar que `-sourcepath src` está en el comando |
| `ClassNotFoundException: batalla.main.App` | Separador de classpath incorrecto | Usar `;` en Git Bash Windows para el `-cp` al ejecutar |

---

## 🤖 Uso de Inteligencia Artificial

Este proyecto fue desarrollado con asistencia de Claude (Anthropic) como herramienta de apoyo al aprendizaje. A continuación se documenta cómo se utilizó la IA durante el desarrollo.

### Estrategia general

La IA se usó como un asistente técnico permanente a lo largo de todo el proyecto. No se le pidió que escribiera el proyecto completo de una vez, sino que se fue trabajando archivo por archivo, consultando dudas puntuales y pidiendo ajustes incrementales a medida que avanzaba el desarrollo.

### Tipos de prompts utilizados

**Diseño y estilo de las vistas**

Se le pidió a la IA que generara las ventanas con un estilo oscuro y coherente usando colores específicos de Swing. Por ejemplo:

> "Creame VentanaConfig con un diseño oscuro, fondo `new Color(20, 20, 35)`, botones con colores temáticos y una tabla que muestre los personajes registrados."

La IA generó el código completo de cada ventana con el estilo pedido, incluyendo `JSplitPane`, `JTable`, `JComboBox` con renderer personalizado y paneles de selección con labels de color.

**Resolución de errores con try-catch**

Cuando la aplicación fallaba silenciosamente (por ejemplo, el botón "Iniciar Batalla" no hacía nada), se usó la estrategia de agregar bloques `try-catch` para capturar el error y mostrarlo en pantalla. El prompt fue:

> "El botón de iniciar batalla se corta sin mostrar nada. Agregá mensajes de error con try-catch para hacerlo más intuitivo y poder ver qué está fallando."

Esto permitió identificar rápidamente errores de compilación tardía, problemas con IDs de personajes en 0, y errores de conexión a la base de datos.

**Ayuda con Script SQL**

Se ayudo a la normalizacion de la base de datos y la carga de valores fictisios en modo de prueba.

**Acompañamiento de código con dudas puntuales**


Cuando surgían errores específicos del compilador, se pegaba el mensaje de error exacto y se pedía la corrección:

> "Tengo este error en la línea 114: variable not in use. ¿Qué hago?"

> "symbol: method cargarId(Heroe) — cannot find symbol. ¿Qué falta?"

La IA analizaba el contexto, identificaba la causa raíz y proponía la corrección mínima necesaria sin reescribir código que ya funcionaba.

**Integración de funcionalidades nuevas**

Para cada funcionalidad nueva (Reporte, Comparador, Torneo) se enviaba el código actual del archivo a modificar y se pedía la integración:

> "Este es mi ConfigControlador.java actual. Agregale el listener para abrir el torneo y el método abrirTorneo() con selección de 4 personajes."

Esto aseguró que los cambios fueran compatibles con el código existente y no rompieran lo que ya funcionaba.

**Mejoras de UX**

Se pidieron mejoras de experiencia de usuario como:

> "Los personajes quedan con vida 0 después de una batalla y no se pueden volver a usar. Arreglalo para que la vida se resetee."

**La IA ayudo a escribir este README.md**

### Lo que NO hizo la IA

La IA no tomó decisiones de diseño por cuenta propia. Cada funcionalidad, flujo de navegación y criterio de validación fue definido por el alumno. La IA tradujo esas decisiones a código Java funcional y corrigió errores técnicos.

---

## 📋 Tecnologías utilizadas

- **Java** con **Swing** — interfaz gráfica de escritorio
- **JDBC** — conectividad con base de datos
- **MySQL** — motor de base de datos relacional
- **Arquitectura MVC** — separación de responsabilidades
- **Git Bash** — compilación y ejecución en terminal

## 👤 Integrante

| Nombre | DNI |
|---|---|
| Julio Madero | 40.974.293 |