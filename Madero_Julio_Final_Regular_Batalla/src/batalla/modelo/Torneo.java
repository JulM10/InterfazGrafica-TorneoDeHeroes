package batalla.modelo;

import java.util.List;

public class Torneo {

    private List<Personaje> participantes;
    private Personaje semifinal1Ganador;
    private Personaje semifinal2Ganador;
    private Personaje campeon;
    private int faseActual; // 0 = semi1, 1 = semi2, 2 = final, 3 = terminado

    public Torneo(List<Personaje> participantes) {
        if (participantes.size() != 4) {
            throw new IllegalArgumentException("El torneo requiere exactamente 4 participantes.");
        }
        this.participantes = participantes;
        this.faseActual = 0;
    }

    public Personaje getJugador1Semifinal1() { return participantes.get(0); }
    public Personaje getJugador2Semifinal1() { return participantes.get(1); }
    public Personaje getJugador1Semifinal2() { return participantes.get(2); }
    public Personaje getJugador2Semifinal2() { return participantes.get(3); }

    public void setSemifinal1Ganador(Personaje p) {
        this.semifinal1Ganador = p;
        this.faseActual = 1;
    }

    public void setSemifinal2Ganador(Personaje p) {
        this.semifinal2Ganador = p;
        this.faseActual = 2;
    }

    public void setCampeon(Personaje p) {
        this.campeon = p;
        this.faseActual = 3;
    }

    public Personaje getSemifinal1Ganador() { return semifinal1Ganador; }
    public Personaje getSemifinal2Ganador() { return semifinal2Ganador; }
    public Personaje getCampeon()           { return campeon; }
    public int getFaseActual()              { return faseActual; }
    public List<Personaje> getParticipantes() { return participantes; }

    public boolean isTerminado() { return faseActual == 3; }
}