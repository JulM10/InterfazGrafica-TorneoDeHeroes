package batalla.controlador;

import batalla.modelo.*;
import batalla.persistencia.BatallaDAO;
import batalla.persistencia.PersonajeDAO;
import batalla.vista.VentanaBatalla;
import batalla.vista.VentanaConfig;
import batalla.vista.VentanaPrincipal;

public class BatallaControlador {

    private VentanaBatalla vista;
    private Batalla batalla;
    private Heroe heroe;
    private Villano villano;
    private int turnoActual;

    private PersonajeDAO personajeDAO;
    private BatallaDAO batallaDAO;
    private java.util.function.Consumer<Personaje> onFinListener;

    public BatallaControlador(VentanaBatalla vista, Heroe heroe, Villano villano) {
        this.vista = vista;
        this.heroe = heroe;
        this.villano = villano;
        this.batalla = new Batalla(heroe, villano);
        this.turnoActual = 0;
        this.personajeDAO = new PersonajeDAO();
        this.batallaDAO = new BatallaDAO();

        vista.setSiguienteTurnoListener(e -> {
            try {
                ejecutarTurno();
            } catch (Exception ex) {
                vista.logEvento("❌ Error en turno: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        vista.setRrendirseListener(e -> {
            try {
                rendirse();
            } catch (Exception ex) {
                vista.logEvento("❌ Error al rendirse: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // listener del boton volver ──
        vista.setVolverListener(e -> volverAlInicio());

        vista.logEvento("⚔ ¡Que comience la batalla!");
        vista.logEvento(heroe.getNombre() + " (❤ " + heroe.getVida() + ")"
                + "  vs  "
                + villano.getNombre() + " (❤ " + villano.getVida() + ")");
        vista.logEvento("──────────────────────────");
    }

    private void ejecutarTurno() {
        if (!heroe.estaVivo() || !villano.estaVivo()) {
            vista.logEvento("⚠ La batalla ya termino.");
            return;
        }

        turnoActual++;
        vista.actualizarTurno(turnoActual);
        vista.logEvento("\n── Turno " + turnoActual + " ──");

        // Heroe ataca
        int danioHeroe = heroe.atacar();
        villano.recibirDanio(danioHeroe);
        vista.logEvento("⚔ " + heroe.getNombre() + " ataca: " + danioHeroe
                + " de daño → vida de " + villano.getNombre() + ": " + villano.getVida());
        vista.actualizarVidaVillano(villano.getVida());

        if (!villano.estaVivo()) {
            finalizarBatalla(heroe);
            return;
        }

        // Villano ataca
        int danioVillano = villano.atacar();
        heroe.recibirDanio(danioVillano);
        vista.logEvento("💀 " + villano.getNombre() + " ataca: " + danioVillano
                + " de daño → vida de " + heroe.getNombre() + ": " + heroe.getVida());
        vista.actualizarVidaHeroe(heroe.getVida());

        if (!heroe.estaVivo()) {
            finalizarBatalla(villano);
        }
    }

    private void rendirse() {
        vista.logEvento("\n⚑ " + heroe.getNombre() + " se rindio.");
        finalizarBatalla(villano);
    }

    private void finalizarBatalla(Personaje ganador) {
    batalla.setGanador(ganador);
    batalla.setTurnos(turnoActual);
    ganador.sumarVictoria();

    vista.logEvento("\n──────────────────────────");
    vista.logEvento("🏆 ¡Ganador: " + ganador.getNombre() + "!");
    vista.logEvento("   Vida restante: " + ganador.getVida());
    vista.deshabilitarBotonTurno();

    // Si es batalla de torneo, no guardar en BD
    if (onFinListener != null) {
        vista.logEvento("🏆 Batalla de torneo — no se guarda en BD.");
        onFinListener.accept(ganador);
        return;
    }

    // Batalla normal — guardar en BD
    try {
        vista.logEvento("💾 Guardando en base de datos...");

        personajeDAO.cargarId(heroe);
        personajeDAO.cargarId(villano);

        heroe.resetearVida();
        villano.resetearVida();

        personajeDAO.insertar(heroe);
        vista.logEvento("   ✔ Heroe guardado (ID: " + heroe.getId() +
                " | Vida reseteada a: " + heroe.getVida() + ")");

        personajeDAO.insertar(villano);
        vista.logEvento("   ✔ Villano guardado (ID: " + villano.getId() +
                " | Vida reseteada a: " + villano.getVida() + ")");

        if (heroe.getId() == 0 || villano.getId() == 0) {
            vista.logEvento("❌ Error: uno de los personajes no obtuvo ID de la BD.");
            return;
        }

        batallaDAO.guardarBatallaCompleta(batalla);
        vista.logEvento("   ✔ Batalla guardada (turno " + turnoActual + ")");
        vista.logEvento("✅ Todo guardado correctamente.");
        vista.logEvento("\n⟵ Podes volver al inicio con el boton de abajo.");

    } catch (Exception e) {
        vista.logEvento("❌ Error al guardar en BD: " + e.getMessage());
        vista.logEvento("   Causa: " + (e.getCause() != null ? e.getCause().getMessage() : "desconocida"));
        e.printStackTrace();
    }
}

    // volver a VentanaPrincipal ──
    private void volverAlInicio() {
        vista.dispose();
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
        ventanaPrincipal.setComenzarListener(e -> {
            ventanaPrincipal.dispose();
            VentanaConfig config = new VentanaConfig();
            new ConfigControlador(config);
            config.setVisible(true);
        });
        ventanaPrincipal.setVisible(true);
    }

    public void setOnFinListener(java.util.function.Consumer<Personaje> listener) {
        this.onFinListener = listener;
    }
}