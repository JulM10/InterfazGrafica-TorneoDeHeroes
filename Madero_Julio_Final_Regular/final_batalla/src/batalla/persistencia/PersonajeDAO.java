package batalla.persistencia;

import batalla.modelo.Heroe;
import batalla.modelo.Personaje;
import batalla.modelo.Villano;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonajeDAO {

    public void insertar(Personaje p) {

        String sql = "INSERT INTO personajes " +
                "(nombre, apodo, tipo, vida, fuerza, defensa, bendicion, victorias) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "vida = VALUES(vida), fuerza = VALUES(fuerza), " +
                "defensa = VALUES(defensa), bendicion = VALUES(bendicion)";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getApodo());
            stmt.setString(3, p.getTipo().name());
            stmt.setInt(4, p.getVida());
            stmt.setInt(5, p.getFuerza());
            stmt.setInt(6, p.getDefensa());
            stmt.setInt(7, p.getBendicion());
            stmt.setInt(8, p.getVictorias());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                p.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarVictorias(Personaje p) {

        String sql = "UPDATE personajes SET victorias = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getVictorias());
            stmt.setInt(2, p.getId());

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                System.out.println("Victorias actualizadas correctamente para ID: " + p.getId());
            } else {
                System.out.println("No se encontró personaje con ID: " + p.getId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cargarId(Personaje p) {
        String sql = "SELECT id FROM personajes WHERE apodo = ?";
        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getApodo());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                p.setId(rs.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Personaje> obtenerTodos() {
    List<Personaje> lista = new ArrayList<>();
    String sql = "SELECT * FROM personajes ORDER BY tipo, nombre";

    try (Connection conn = ConexionDB.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Personaje p;
            String tipo = rs.getString("tipo");
            int id         = rs.getInt("id");
            String nombre  = rs.getString("nombre");
            String apodo   = rs.getString("apodo");
            int vida       = rs.getInt("vida");
            int fuerza     = rs.getInt("fuerza");
            int defensa    = rs.getInt("defensa");
            int bendicion  = rs.getInt("bendicion");
            int victorias  = rs.getInt("victorias");

            if (tipo.equals("HEROE")) {
                p = new Heroe(id, nombre, apodo, vida, fuerza, defensa, bendicion, victorias);
            } else {
                p = new Villano(id, nombre, apodo, vida, fuerza, defensa, bendicion, victorias);
            }
            lista.add(p);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}
}