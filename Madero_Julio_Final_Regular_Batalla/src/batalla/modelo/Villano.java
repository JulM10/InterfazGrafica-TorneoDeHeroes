package batalla.modelo;

import java.util.Random;

public class Villano extends Personaje {

    private static final Random random = new Random();

    public Villano(String nombre, String apodo,
                   int vida, int fuerza,
                   int defensa, int bendicion) {

        super(nombre, apodo, TipoPersonaje.VILLANO,
              vida, fuerza, defensa, bendicion);
    }

    public Villano(int id, String nombre, String apodo,
                   int vida, int fuerza,
                   int defensa, int bendicion, int victorias) {

        super(id, nombre, apodo, TipoPersonaje.VILLANO,
              vida, fuerza, defensa, bendicion, victorias);
    }

    @Override
    public int atacar() {

        // El villano tiene daño variable caótico
        int bonusOscuro = random.nextInt(getBendicion() + 1);

        return getFuerza() + bonusOscuro;
    }
}