package batalla.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaReporte extends JFrame {

    private JTable tablaRanking;
    private JTable tablaHistorial;
    private DefaultTableModel modeloRanking;
    private DefaultTableModel modeloHistorial;

    private JLabel lblBatallaLarga;
    private JLabel lblTotalBatallas;
    private JLabel lblMvp;

    private JButton btnRefrescar;
    private JButton btnCerrar;

    public VentanaReporte() {
        setTitle("📊 Reporte General");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(20, 20, 35));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // ── TÍTULO ──
        JLabel lblTitulo = new JLabel("📊 Reporte General", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(255, 200, 50));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // ── PANEL SCROLL CENTRAL ──
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(new Color(20, 20, 35));

        // ── SECCIÓN RANKING ──
        panelContenido.add(crearSeparador("🏆 Ranking de Personajes"));
        panelContenido.add(Box.createVerticalStrut(8));

        modeloRanking = new DefaultTableModel(
            new String[]{"#", "Nombre", "Apodo", "Tipo", "Vida", "Fuerza", "Defensa", "Victorias"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tablaRanking = crearTabla(modeloRanking);
        JScrollPane scrollRanking = new JScrollPane(tablaRanking);
        scrollRanking.setPreferredSize(new Dimension(750, 160));
        scrollRanking.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        scrollRanking.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 120)));
        panelContenido.add(scrollRanking);
        panelContenido.add(Box.createVerticalStrut(16));

        // ── SECCIÓN HISTORIAL ──
        panelContenido.add(crearSeparador("📜 Historial — Ultimas 10 Batallas"));
        panelContenido.add(Box.createVerticalStrut(8));

        modeloHistorial = new DefaultTableModel(
            new String[]{"#", "Fecha", "Heroe", "Villano", "Ganador", "Turnos"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tablaHistorial = crearTabla(modeloHistorial);
        JScrollPane scrollHistorial = new JScrollPane(tablaHistorial);
        scrollHistorial.setPreferredSize(new Dimension(750, 180));
        scrollHistorial.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        scrollHistorial.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 120)));
        panelContenido.add(scrollHistorial);
        panelContenido.add(Box.createVerticalStrut(16));

        // ── SECCIÓN ESTADÍSTICAS ──
        panelContenido.add(crearSeparador("📈 Estadisticas Generales"));
        panelContenido.add(Box.createVerticalStrut(8));

        JPanel panelStats = new JPanel(new GridLayout(3, 1, 0, 8));
        panelStats.setBackground(new Color(30, 30, 50));
        panelStats.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 120)),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        panelStats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        lblBatallaLarga  = crearLabelStat("⚔ Batalla mas larga: cargando...");
        lblTotalBatallas = crearLabelStat("📊 Total de batallas: cargando...");
        lblMvp           = crearLabelStat("🏆 Personaje MVP: cargando...");

        panelStats.add(lblBatallaLarga);
        panelStats.add(lblTotalBatallas);
        panelStats.add(lblMvp);

        panelContenido.add(panelStats);
        panelContenido.add(Box.createVerticalStrut(10));

        JScrollPane scrollPrincipal = new JScrollPane(panelContenido);
        scrollPrincipal.setBorder(null);
        scrollPrincipal.getViewport().setBackground(new Color(20, 20, 35));
        panelPrincipal.add(scrollPrincipal, BorderLayout.CENTER);

        // ── BOTONES ──
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setBackground(new Color(20, 20, 35));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        btnRefrescar = crearBoton("🔄 Refrescar", new Color(60, 80, 140));
        btnRefrescar.setPreferredSize(new Dimension(140, 38));

        btnCerrar = crearBoton("✕ Cerrar", new Color(80, 40, 40));
        btnCerrar.setPreferredSize(new Dimension(100, 38));
        btnCerrar.addActionListener(e -> dispose());

        panelBotones.add(btnRefrescar);
        panelBotones.add(btnCerrar);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    // ── HELPERS ──
    private JTable crearTabla(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        tabla.setBackground(new Color(25, 25, 45));
        tabla.setForeground(new Color(200, 200, 220));
        tabla.setSelectionBackground(new Color(60, 80, 140));
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.getTableHeader().setBackground(new Color(40, 40, 70));
        tabla.getTableHeader().setForeground(new Color(255, 200, 50));
        tabla.setRowHeight(24);
        tabla.setGridColor(new Color(50, 50, 80));
        return tabla;
    }

    private JPanel crearSeparador(String titulo) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(20, 20, 35));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(new Color(255, 200, 50));

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(80, 80, 120));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(sep, BorderLayout.SOUTH);
        return panel;
    }

    private JLabel crearLabelStat(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(new Color(180, 180, 220));
        return lbl;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ── MÉTODOS para el controlador ──
    public void setRefrescarListener(ActionListener l) {
        btnRefrescar.addActionListener(l);
    }

    public void limpiarRanking()   { modeloRanking.setRowCount(0); }
    public void limpiarHistorial() { modeloHistorial.setRowCount(0); }

    public void agregarFilaRanking(int pos, String nombre, String apodo,
                                    String tipo, int vida, int fuerza,
                                    int defensa, int victorias) {
        modeloRanking.addRow(new Object[]{
            pos, nombre, apodo, tipo, vida, fuerza, defensa, victorias
        });
    }

    public void agregarFilaHistorial(String id, String fecha, String heroe,
                                      String villano, String ganador, String turnos) {
        modeloHistorial.addRow(new Object[]{id, fecha, heroe, villano, ganador, turnos});
    }

    public void setEstadisticas(String batallaLarga, String totalBatallas, String mvp) {
        lblBatallaLarga.setText("⚔ Batalla mas larga: " + batallaLarga);
        lblTotalBatallas.setText("📊 Total de batallas: " + totalBatallas);
        lblMvp.setText("🏆 Personaje MVP: " + mvp);
    }
}