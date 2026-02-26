package batalla.controlador;

import batalla.modelo.Personaje;
import batalla.persistencia.BatallaDAO;
import batalla.persistencia.PersonajeDAO;
import batalla.vista.VentanaReporte;
import java.util.List;

public class ReporteControlador {

    private VentanaReporte vista;
    private PersonajeDAO personajeDAO;
    private BatallaDAO batallaDAO;

    public ReporteControlador(VentanaReporte vista) {
        this.vista        = vista;
        this.personajeDAO = new PersonajeDAO();
        this.batallaDAO   = new BatallaDAO();

        vista.setRefrescarListener(e -> cargarDatos());

        cargarDatos();
    }

    public void cargarDatos() {
        cargarRanking();
        cargarHistorial();
        cargarEstadisticas();
    }

    private void cargarRanking() {
        vista.limpiarRanking();
        List<Personaje> ranking = personajeDAO.obtenerTodos();
        ranking.sort((a, b) -> Integer.compare(b.getVictorias(), a.getVictorias()));

        int pos = 1;
        for (Personaje p : ranking) {
            vista.agregarFilaRanking(
                pos++,
                p.getNombre(),
                p.getApodo(),
                p.getTipo().name(),
                p.getVida(),
                p.getFuerza(),
                p.getDefensa(),
                p.getVictorias()
            );
        }
    }

    private void cargarHistorial() {
        vista.limpiarHistorial();
        List<String[]> historial = batallaDAO.getHistorialBatallas();
        for (String[] fila : historial) {
            vista.agregarFilaHistorial(
                fila[0], fila[1], fila[2], fila[3], fila[4], fila[5]
            );
        }
    }

    private void cargarEstadisticas() {
        String[] stats = batallaDAO.getEstadisticas();
        vista.setEstadisticas(stats[0], stats[1], stats[2]);
    }
}