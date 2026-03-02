package batalla.modelo;

public abstract class Personaje {

    public enum TipoPersonaje {
        HEROE,
        VILLANO
    }

    private int id;
    private String nombre;
    private String apodo;
    private TipoPersonaje tipo;
    private int vidaInicial;
    private int vida;
    private int fuerza;
    private int defensa;
    private int bendicion;
    private int victorias;

    public Personaje(String nombre, String apodo, TipoPersonaje tipo,
                     int vida, int fuerza, int defensa,
                     int bendicion) {

        this.nombre = nombre;
        this.apodo = apodo;
        this.tipo = tipo;
        this.vida = vida;
        this.vidaInicial = vida;
        this.fuerza = fuerza;
        this.defensa = defensa;
        this.bendicion = bendicion;
        this.victorias = 0;
    }

    public Personaje(int id, String nombre, String apodo, TipoPersonaje tipo,
                     int vida, int fuerza, int defensa,
                     int bendicion, int victorias) {

        this.id = id;
        this.nombre = nombre;
        this.apodo = apodo;
        this.tipo = tipo;
        this.vida = vida;
        this.vidaInicial = vida;
        this.fuerza = fuerza;
        this.defensa = defensa;
        this.bendicion = bendicion;
        this.victorias = victorias;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public void recibirDanio(int danio) {
        int danioReal = Math.max(0, danio - defensa);
        vida -= danioReal;
        if (vida < 0) vida = 0;
    }

    public abstract int atacar();

    // getters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public void resetearVida() { this.vida = vidaInicial; }
    public String getNombre() { return nombre; }
    public String getApodo() { return apodo; }
    public TipoPersonaje getTipo() { return tipo; }
    public int getVida() { return vida; }
    public int getVidaInicial() { return vidaInicial; }
    public int getFuerza() { return fuerza; }
    public int getDefensa() { return defensa; }
    public int getBendicion() { return bendicion; }
    public int getVictorias() { return victorias; }

    public void sumarVictoria() {
        this.victorias++;
    }
}