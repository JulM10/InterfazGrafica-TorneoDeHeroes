package batalla.modelo;

import java.util.Random;

public class Heroe extends Personaje {

    private static final Random random = new Random();

    public Heroe(String nombre, String apodo,
                 int vida, int fuerza,
                 int defensa, int bendicion) {

        super(nombre, apodo, TipoPersonaje.HEROE,
              vida, fuerza, defensa, bendicion);
    }

    public Heroe(int id, String nombre, String apodo,
                 int vida, int fuerza,
                 int defensa, int bendicion, int victorias) {

        super(id, nombre, apodo, TipoPersonaje.HEROE,
              vida, fuerza, defensa, bendicion, victorias);
    }

    @Override
    public int atacar() {
        int critico = random.nextInt(100) < getBendicion() ? 2 : 1;
        return getFuerza() * critico;
    }
}