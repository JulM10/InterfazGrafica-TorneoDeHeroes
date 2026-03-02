package batalla.controlador;

import batalla.modelo.*;
import batalla.persistencia.PersonajeDAO;
import batalla.vista.VentanaBatalla;
import batalla.vista.VentanaTorneo;

import javax.swing.JOptionPane;
import java.util.List;

public class TorneoControlador {

    private VentanaTorneo vista;
    private Torneo torneo;
    private PersonajeDAO personajeDAO;

    public TorneoControlador(VentanaTorneo vista, List<Personaje> participantes) {
        this.vista        = vista;
        this.torneo       = new Torneo(participantes);
        this.personajeDAO = new PersonajeDAO();

        vista.actualizarBracket(torneo);
        vista.actualizarFase("Semifinal 1 — " +
            torneo.getJugador1Semifinal1().getNombre() + " vs " +
            torneo.getJugador2Semifinal1().getNombre());

        vista.setSiguienteFaseListener(e -> jugarSiguienteFase());
    }

    private void jugarSiguienteFase() {
        switch (torneo.getFaseActual()) {
            case 0 -> jugarBatalla(
                torneo.getJugador1Semifinal1(),
                torneo.getJugador2Semifinal1(),
                "Semifinal 1",
                ganador -> {
                    ganador.resetearVida();
                    torneo.setSemifinal1Ganador(ganador);
                    vista.actualizarBracket(torneo);
                    vista.actualizarFase("Semifinal 2 — " +
                        torneo.getJugador1Semifinal2().getNombre() + " vs " +
                        torneo.getJugador2Semifinal2().getNombre());
                }
            );
            case 1 -> jugarBatalla(
                torneo.getJugador1Semifinal2(),
                torneo.getJugador2Semifinal2(),
                "Semifinal 2",
                ganador -> {
                    ganador.resetearVida();
                    torneo.setSemifinal2Ganador(ganador);
                    vista.actualizarBracket(torneo);
                    vista.actualizarFase("Final — " +
                        torneo.getSemifinal1Ganador().getNombre() + " vs " +
                        torneo.getSemifinal2Ganador().getNombre());
                }
            );
            case 2 -> jugarBatalla(
                torneo.getSemifinal1Ganador(),
                torneo.getSemifinal2Ganador(),
                "Final",
                ganador -> {
                    ganador.resetearVida();
                    ganador.sumarVictoria();
                    personajeDAO.cargarId(ganador);
                    personajeDAO.insertar(ganador);
                    torneo.setCampeon(ganador);
                    vista.actualizarBracket(torneo);
                    vista.actualizarFase("Torneo finalizado");
                    JOptionPane.showMessageDialog(vista,
                        "👑 Campeon del Torneo: " + ganador.getNombre() +
                        "\n¡Felicitaciones!",
                        "Torneo Finalizado",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            );
            default -> vista.actualizarFase("Torneo ya finalizado.");
        }
    }

    private void jugarBatalla(Personaje p1, Personaje p2,
                               String fase, java.util.function.Consumer<Personaje> onFin) {
        // Resetear vida antes de cada batalla
        p1.resetearVida();
        p2.resetearVida();

        Heroe heroe;
        Villano villano;

        // Asignar roles temporales si ambos son del mismo tipo
        if (p1 instanceof Heroe && p2 instanceof Villano) {
            heroe   = (Heroe) p1;
            villano = (Villano) p2;
        } else if (p1 instanceof Villano && p2 instanceof Heroe) {
            heroe   = (Heroe) p2;
            villano = (Villano) p1;
        } else if (p1 instanceof Heroe) {
            heroe   = (Heroe) p1;
            villano = new Villano(p2.getId(), p2.getNombre(), p2.getApodo(),
                p2.getVida(), p2.getFuerza(), p2.getDefensa(),
                p2.getBendicion(), p2.getVictorias());
        } else {
            heroe   = new Heroe(p1.getId(), p1.getNombre(), p1.getApodo(),
                p1.getVida(), p1.getFuerza(), p1.getDefensa(),
                p1.getBendicion(), p1.getVictorias());
            villano = (Villano) p2;
        }

        try {
            VentanaBatalla ventanaBatalla = new VentanaBatalla(
                heroe.getNombre(),   heroe.getVida(),   heroe.getFuerza(),
                heroe.getDefensa(),  heroe.getBendicion(), heroe.getVictorias(),
                villano.getNombre(), villano.getVida(), villano.getFuerza(),
                villano.getDefensa(), villano.getBendicion(), villano.getVictorias()
            );

            BatallaControlador bc = new BatallaControlador(ventanaBatalla, heroe, villano);
            bc.setOnFinListener(ganador -> {
                ventanaBatalla.dispose();
                onFin.accept(ganador);
            });

            ventanaBatalla.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                "Error al iniciar batalla de " + fase + ":\n" + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}