package batalla.modelo;

import java.util.ArrayList;
import java.util.List;

public class Batalla {

    private Heroe heroe;
    private Villano villano;
    private Personaje ganador;
    private List<String> logTurnos;
    private int turnos;

    public Batalla(Heroe heroe, Villano villano) {
        this.heroe     = heroe;
        this.villano   = villano;
        this.logTurnos = new ArrayList<>();
        this.turnos    = 0;
    }
    public Personaje iniciarBatalla() {
        int turno = 1;

        while (heroe.estaVivo() && villano.estaVivo()) {
            logTurnos.add("---- Turno " + turno + " ----");

            int danioHeroe = heroe.atacar();
            villano.recibirDanio(danioHeroe);
            logTurnos.add(heroe.getNombre() + " ataca e inflige " + danioHeroe
                + " de daño. Vida restante de " + villano.getNombre()
                + ": " + villano.getVida());

            if (!villano.estaVivo()) {
                ganador = heroe;
                break;
            }

            int danioVillano = villano.atacar();
            heroe.recibirDanio(danioVillano);
            logTurnos.add(villano.getNombre() + " ataca e inflige " + danioVillano
                + " de daño. Vida restante de " + heroe.getNombre()
                + ": " + heroe.getVida());

            if (!heroe.estaVivo()) {
                ganador = villano;
                break;
            }

            turno++;
        }

        this.turnos = turno;
        logTurnos.add("GANADOR: " + ganador.getNombre());
        return ganador;
    }

    // ── Getters ──
    public List<String> getLogTurnos() { return logTurnos;  }
    public Personaje    getGanador()   { return ganador;    }
    public Heroe        getHeroe()     { return heroe;      }
    public Villano      getVillano()   { return villano;    }
    public int          getTurnos()    { return turnos;     }

    // ── Setters ──
    public void setGanador(Personaje ganador) { this.ganador = ganador; }
    public void setTurnos(int turnos)         { this.turnos  = turnos;  }
}