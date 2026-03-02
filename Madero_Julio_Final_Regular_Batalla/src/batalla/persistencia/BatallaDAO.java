package batalla.persistencia;

import batalla.modelo.Batalla;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BatallaDAO {

    public void guardarBatallaCompleta(Batalla batalla) {

        String sqlBatalla = "INSERT INTO batallas (heroe_id, villano_id, ganador_id) VALUES (?, ?, ?)";
        String sqlHistorial = "INSERT INTO historial_batalla (batalla_id, turno, descripcion) VALUES (?, ?, ?)";
        String sqlVictoria = "UPDATE personajes SET victorias = victorias + 1 WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection()) {

            conn.setAutoCommit(false); // 🔥 inicia transacción

            try (
                    PreparedStatement stmtBatalla = conn.prepareStatement(sqlBatalla, Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement stmtHistorial = conn.prepareStatement(sqlHistorial);
                    PreparedStatement stmtVictoria = conn.prepareStatement(sqlVictoria)) {

                // Insertar batalla
                stmtBatalla.setInt(1, batalla.getHeroe().getId());
                stmtBatalla.setInt(2, batalla.getVillano().getId());
                stmtBatalla.setInt(3, batalla.getGanador().getId());

                stmtBatalla.executeUpdate();

                ResultSet rs = stmtBatalla.getGeneratedKeys();
                int batallaId = 0;
                if (rs.next()) {
                    batallaId = rs.getInt(1);
                }

                // Insertar historial
                int turno = 0;

                for (String evento : batalla.getLogTurnos()) {

                    stmtHistorial.setInt(1, batallaId);
                    stmtHistorial.setInt(2, turno);
                    stmtHistorial.setString(3, evento);
                    stmtHistorial.executeUpdate();

                    if (evento.startsWith("---- Turno")) {
                        turno++;
                    }
                }

                // Registrar victoria
                stmtVictoria.setInt(1, batalla.getGanador().getId());
                stmtVictoria.executeUpdate();

                conn.commit();

                System.out.println("Batalla y historial guardados correctamente.");

            } catch (Exception e) {
                conn.rollback();
                System.out.println("Error. Rollback ejecutado.");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> getHistorialBatallas() {
        List<String[]> historial = new ArrayList<>();
        String sql = "SELECT b.id, b.fecha, " +
                "h.nombre as heroe_nombre, h.apodo as heroe_apodo, " +
                "v.nombre as villano_nombre, v.apodo as villano_apodo, " +
                "g.nombre as ganador_nombre, " +
                "(SELECT COUNT(*) FROM historial_batalla hb WHERE hb.batalla_id = b.id) as turnos " +
                "FROM batallas b " +
                "JOIN personajes h ON b.heroe_id = h.id " +
                "JOIN personajes v ON b.villano_id = v.id " +
                "JOIN personajes g ON b.ganador_id = g.id " +
                "ORDER BY b.fecha DESC LIMIT 10";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                historial.add(new String[] {
                        String.valueOf(rs.getInt("id")),
                        rs.getString("fecha"),
                        rs.getString("heroe_nombre") + " (" + rs.getString("heroe_apodo") + ")",
                        rs.getString("villano_nombre") + " (" + rs.getString("villano_apodo") + ")",
                        rs.getString("ganador_nombre"),
                        String.valueOf(rs.getInt("turnos"))
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historial;
    }

    public String[] getEstadisticas() {
        String[] stats = { "N/A", "N/A", "N/A" };

        // Batalla mas larga
        String sqlLarga = "SELECT b.id, g.nombre as ganador, " +
                "COUNT(hb.id) as total_turnos " +
                "FROM batallas b " +
                "JOIN historial_batalla hb ON hb.batalla_id = b.id " +
                "JOIN personajes g ON b.ganador_id = g.id " +
                "GROUP BY b.id, g.nombre " +
                "ORDER BY total_turnos DESC LIMIT 1";

        // Total de batallas
        String sqlTotal = "SELECT COUNT(*) as total FROM batallas";

        // Personaje con mas victorias
        String sqlMvp = "SELECT nombre, apodo, victorias FROM personajes " +
                "ORDER BY victorias DESC LIMIT 1";

        try (Connection conn = ConexionDB.getConnection()) {

            try (PreparedStatement stmt = conn.prepareStatement(sqlLarga);
                    ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats[0] = "Batalla #" + rs.getInt("id") +
                            " — Ganador: " + rs.getString("ganador") +
                            " — " + rs.getInt("total_turnos") + " turnos";
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlTotal);
                    ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats[1] = String.valueOf(rs.getInt("total"));
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlMvp);
                    ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats[2] = rs.getString("nombre") +
                            " (" + rs.getString("apodo") + ")" +
                            " — " + rs.getInt("victorias") + " victorias";
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }
}