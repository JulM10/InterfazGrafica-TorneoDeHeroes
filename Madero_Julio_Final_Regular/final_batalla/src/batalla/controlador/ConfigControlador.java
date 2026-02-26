package batalla.controlador;

import batalla.modelo.*;
import batalla.modelo.Personaje.TipoPersonaje;
import batalla.persistencia.PersonajeDAO;
import batalla.vista.VentanaBatalla;
import batalla.vista.VentanaComparador;
import batalla.vista.VentanaConfig;
import batalla.vista.VentanaReporte;
import batalla.vista.VentanaTorneo;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class ConfigControlador {

    private VentanaConfig vista;
    private List<Personaje> personajes;
    private static final Random random = new Random();

    public ConfigControlador(VentanaConfig vista) {
        this.vista = vista;
        this.personajes = new ArrayList<>();

        this.vista.setAgregarListener(e -> agregarPersonaje());
        this.vista.setEliminarListener(e -> eliminarPersonaje());
        this.vista.setIniciarListener(e -> iniciarBatalla());
        this.vista.setReporteListener(e -> abrirReporte());
        this.vista.setCompararListener(e -> abrirComparador());
        this.vista.setTorneoListener(e -> abrirTorneo());

        cargarPersonajesDesdeDB();
    }

    private void cargarPersonajesDesdeDB() {
        PersonajeDAO dao = new PersonajeDAO();
        List<Personaje> existentes = dao.obtenerTodos();

        for (Personaje p : existentes) {
            personajes.add(p);
            vista.agregarFilaTabla(
                    p.getNombre(),
                    p.getApodo(),
                    p.getTipo() == TipoPersonaje.HEROE ? "Heroe" : "Villano",
                    p.getVida(), p.getFuerza(), p.getDefensa());
        }
    }

    private void agregarPersonaje() {
        String nombre = vista.getNombre();
        String apodo = vista.getApodo();
        TipoPersonaje tipo = vista.getTipo();

        if (nombre.isEmpty() || apodo.isEmpty()) {
            vista.mostrarError("Nombre y apodo no pueden estar vacios.");
            return;
        }
        if (nombre.length() < 2 || apodo.length() < 2) {
            vista.mostrarError("Nombre y apodo deben tener al menos 2 caracteres.");
            return;
        }

        long heroesActuales = personajes.stream()
                .filter(p -> p.getTipo() == TipoPersonaje.HEROE).count();
        long villanosActuales = personajes.stream()
                .filter(p -> p.getTipo() == TipoPersonaje.VILLANO).count();

        if (tipo == TipoPersonaje.HEROE && heroesActuales >= 1) {
            vista.mostrarError("Ya hay un Heroe registrado. Solo se permite uno.");
            return;
        }
        if (tipo == TipoPersonaje.VILLANO && villanosActuales >= 1) {
            vista.mostrarError("Ya hay un Villano registrado. Solo se permite uno.");
            return;
        }

        boolean apodoDuplicado = personajes.stream()
                .anyMatch(p -> p.getApodo().equalsIgnoreCase(apodo));
        if (apodoDuplicado) {
            vista.mostrarError("Ese apodo ya esta en uso. Elegi otro.");
            return;
        }

        int vida = 100 + random.nextInt(61);
        int fuerza = 15 + random.nextInt(11);
        int defensa = 8 + random.nextInt(6);
        int bendicion = 30 + random.nextInt(71);

        Personaje nuevo;
        if (tipo == TipoPersonaje.HEROE) {
            nuevo = new Heroe(nombre, apodo, vida, fuerza, defensa, bendicion);
        } else {
            nuevo = new Villano(nombre, apodo, vida, fuerza, defensa, bendicion);
        }

        personajes.add(nuevo);
        vista.agregarFilaTabla(nombre, apodo,
                tipo == TipoPersonaje.HEROE ? "Heroe" : "Villano",
                nuevo.getVida(), nuevo.getFuerza(), nuevo.getDefensa());
        vista.limpiarCampos();
    }

    private void eliminarPersonaje() {
        int fila = vista.getFilaSeleccionada();
        if (fila == -1) {
            vista.mostrarError("Selecciona un personaje de la tabla para eliminar.");
            return;
        }
        personajes.remove(fila);
        vista.eliminarFilaTabla(fila);
    }

    private void iniciarBatalla() {
        String apodoHeroe = vista.getApodoHeroeSeleccionado();
        String apodoVillano = vista.getApodoVillanoSeleccionado();

        if (apodoHeroe == null) {
            vista.mostrarError("Selecciona un Heroe de la tabla haciendo clic en su fila.");
            return;
        }
        if (apodoVillano == null) {
            vista.mostrarError("Selecciona un Villano de la tabla haciendo clic en su fila.");
            return;
        }

        Heroe heroe = (Heroe) personajes.stream()
                .filter(p -> p.getApodo().equals(apodoHeroe))
                .findFirst().orElse(null);

        Villano villano = (Villano) personajes.stream()
                .filter(p -> p.getApodo().equals(apodoVillano))
                .findFirst().orElse(null);

        if (heroe == null || villano == null) {
            vista.mostrarError("Error al encontrar los personajes seleccionados.");
            return;
        }

        try {
            vista.dispose();
            // ── ACTUALIZADO: pasar todas las estadisticas ──
            VentanaBatalla ventanaBatalla = new VentanaBatalla(
                    heroe.getNombre(), heroe.getVida(), heroe.getFuerza(),
                    heroe.getDefensa(), heroe.getBendicion(), heroe.getVictorias(),
                    villano.getNombre(), villano.getVida(), villano.getFuerza(),
                    villano.getDefensa(), villano.getBendicion(), villano.getVictorias());
            new BatallaControlador(ventanaBatalla, heroe, villano);
            ventanaBatalla.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al abrir la batalla:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public List<Personaje> getPersonajes() {
        return personajes;
    }

    private void abrirReporte() {
        try {
            VentanaReporte reporte = new VentanaReporte();
            new ReporteControlador(reporte);
            reporte.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al abrir reporte:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void abrirComparador() {
        if (personajes.size() < 2) {
            vista.mostrarError("Necesitas al menos 2 personajes en la lista para comparar.");
            return;
        }

        // Obtener los dos seleccionados por apodo
        String apodo1 = vista.getApodoHeroeSeleccionado();
        String apodo2 = vista.getApodoVillanoSeleccionado();

        if (apodo1 == null || apodo2 == null) {
            vista.mostrarError("Selecciona un Heroe y un Villano de la tabla para comparar.");
            return;
        }

        Personaje p1 = personajes.stream()
                .filter(p -> p.getApodo().equals(apodo1))
                .findFirst().orElse(null);

        Personaje p2 = personajes.stream()
                .filter(p -> p.getApodo().equals(apodo2))
                .findFirst().orElse(null);

        if (p1 == null || p2 == null) {
            vista.mostrarError("Error al encontrar los personajes seleccionados.");
            return;
        }

        try {
            VentanaComparador comparador = new VentanaComparador(p1, p2);
            comparador.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al abrir comparador:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    private void abrirTorneo() {
    if (personajes.size() < 4) {
        vista.mostrarError("Necesitas al menos 4 personajes registrados para el torneo.");
        return;
    }

    // Mostrar dialogo para seleccionar 4 participantes
    List<String> nombres = personajes.stream()
        .map(p -> p.getNombre() + " (" + p.getApodo() + ")")
        .collect(java.util.stream.Collectors.toList());

    JList<String> lista = new JList<>(nombres.toArray(new String[0]));
    lista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    lista.setBackground(new Color(30, 30, 50));
    lista.setForeground(Color.WHITE);
    lista.setFont(new Font("Segoe UI", Font.PLAIN, 13));

    JScrollPane scroll = new JScrollPane(lista);
    scroll.setPreferredSize(new Dimension(300, 150));

    int resultado = JOptionPane.showConfirmDialog(null,
        new Object[]{"Selecciona exactamente 4 participantes (Ctrl+clic):", scroll},
        "Seleccionar participantes",
        JOptionPane.OK_CANCEL_OPTION);

    if (resultado != JOptionPane.OK_OPTION) return;

    int[] seleccionados = lista.getSelectedIndices();
    if (seleccionados.length != 4) {
        vista.mostrarError("Debes seleccionar exactamente 4 personajes.");
        return;
    }

    List<Personaje> participantes = new ArrayList<>();
    for (int idx : seleccionados) {
        participantes.add(personajes.get(idx));
    }

    try {
        VentanaTorneo ventanaTorneo = new VentanaTorneo();
        new TorneoControlador(ventanaTorneo, participantes);
        ventanaTorneo.setVisible(true);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
            "Error al abrir torneo:\n" + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
}