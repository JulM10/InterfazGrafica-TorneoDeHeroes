package batalla.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaBatalla extends JFrame {

    // Panel superior
    private JLabel lblPartida;
    private JLabel lblTurno;

    // Panel héroe
    private JLabel lblNombreHeroe;
    private JLabel lblVidaHeroe;

    // Panel villano
    private JLabel lblNombreVillano;
    private JLabel lblVidaVillano;

    // Log y botones
    private JTextArea areaLog;
    private JButton btnSiguienteTurno;
    private JButton btnRendirse;
    private JButton btnVolver;

    public VentanaBatalla(String nombreHeroe, int vidaHeroe, int fuerzaHeroe,
                          int defensaHeroe, int bendicionHeroe, int victoriasHeroe,
                          String nombreVillano, int vidaVillano, int fuerzaVillano,
                          int defensaVillano, int bendicionVillano, int victoriasVillano) {

        setTitle("⚔ Batalla en curso");
        setSize(750, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(20, 20, 35));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        // ── PANEL SUPERIOR ──
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 6));
        panelSuperior.setBackground(new Color(30, 30, 50));
        panelSuperior.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 120)));

        lblPartida = crearLabelInfo("Partida 1");
        lblTurno   = crearLabelInfo("Turno 0");

        panelSuperior.add(lblPartida);
        panelSuperior.add(lblTurno);
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // ── PANEL CENTRAL: personajes ──
        JPanel panelPersonajes = new JPanel(new GridLayout(1, 2, 10, 0));
        panelPersonajes.setBackground(new Color(20, 20, 35));

        // Héroe
        lblNombreHeroe = new JLabel("⚔ " + nombreHeroe, SwingConstants.CENTER);
        lblNombreHeroe.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNombreHeroe.setForeground(new Color(100, 180, 255));

        lblVidaHeroe = new JLabel("❤ Vida: " + vidaHeroe, SwingConstants.CENTER);
        lblVidaHeroe.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblVidaHeroe.setForeground(new Color(100, 220, 100));

        JLabel lblStatsHeroe = new JLabel(
            "<html><center>" +
            "⚔ Fuerza: " + fuerzaHeroe +
            " &nbsp;|&nbsp; 🛡 Defensa: " + defensaHeroe +
            "<br>✨ Bendicion: " + bendicionHeroe +
            " &nbsp;|&nbsp; 🏆 Victorias: " + victoriasHeroe +
            "</center></html>",
            SwingConstants.CENTER
        );
        lblStatsHeroe.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblStatsHeroe.setForeground(new Color(160, 160, 200));

        panelPersonajes.add(crearPanelPersonaje(
            lblNombreHeroe, lblVidaHeroe, lblStatsHeroe, new Color(30, 50, 80)
        ));

        // Villano
        lblNombreVillano = new JLabel("💀 " + nombreVillano, SwingConstants.CENTER);
        lblNombreVillano.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNombreVillano.setForeground(new Color(255, 100, 100));

        lblVidaVillano = new JLabel("❤ Vida: " + vidaVillano, SwingConstants.CENTER);
        lblVidaVillano.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblVidaVillano.setForeground(new Color(100, 220, 100));

        JLabel lblStatsVillano = new JLabel(
            "<html><center>" +
            "⚔ Fuerza: " + fuerzaVillano +
            " &nbsp;|&nbsp; 🛡 Defensa: " + defensaVillano +
            "<br>✨ Bendicion: " + bendicionVillano +
            " &nbsp;|&nbsp; 🏆 Victorias: " + victoriasVillano +
            "</center></html>",
            SwingConstants.CENTER
        );
        lblStatsVillano.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblStatsVillano.setForeground(new Color(160, 160, 200));

        panelPersonajes.add(crearPanelPersonaje(
            lblNombreVillano, lblVidaVillano, lblStatsVillano, new Color(50, 25, 25)
        ));

        panelPrincipal.add(panelPersonajes, BorderLayout.CENTER);

        // ── PANEL INFERIOR: log + botones ──
        JPanel panelInferior = new JPanel(new BorderLayout(0, 8));
        panelInferior.setBackground(new Color(20, 20, 35));

        areaLog = new JTextArea(8, 0);
        areaLog.setEditable(false);
        areaLog.setBackground(new Color(15, 15, 28));
        areaLog.setForeground(new Color(200, 200, 220));
        areaLog.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        areaLog.setLineWrap(true);
        areaLog.setWrapStyleWord(true);
        areaLog.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

        JScrollPane scroll = new JScrollPane(areaLog);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 120)));

        // ── BOTONES ──
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setBackground(new Color(20, 20, 35));

        btnVolver = crearBoton("⟵ Volver al inicio", new Color(60, 60, 100));
        btnVolver.setPreferredSize(new Dimension(180, 38));
        btnVolver.setEnabled(false);

        btnRendirse = crearBoton("⚑ Rendirse", new Color(100, 40, 40));
        btnRendirse.setPreferredSize(new Dimension(140, 38));

        btnSiguienteTurno = crearBoton("▶ Siguiente Turno", new Color(60, 120, 60));
        btnSiguienteTurno.setPreferredSize(new Dimension(180, 38));

        panelBotones.add(btnVolver);
        panelBotones.add(btnRendirse);
        panelBotones.add(btnSiguienteTurno);

        panelInferior.add(scroll, BorderLayout.CENTER);
        panelInferior.add(panelBotones, BorderLayout.SOUTH);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    // ── HELPERS ──
    private JPanel crearPanelPersonaje(JLabel lblNombre, JLabel lblVida,
                                        JLabel lblStats, Color fondo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(fondo);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 120)),
                BorderFactory.createEmptyBorder(16, 10, 16, 10)));
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblVida.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblStats.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalGlue());
        panel.add(lblNombre);
        panel.add(Box.createVerticalStrut(8));
        panel.add(lblVida);
        panel.add(Box.createVerticalStrut(6));
        panel.add(lblStats);
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JLabel crearLabelInfo(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(new Color(255, 200, 50));
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
    public void setSiguienteTurnoListener(ActionListener l) {
        btnSiguienteTurno.addActionListener(l);
    }

    public void setRrendirseListener(ActionListener l) {
        btnRendirse.addActionListener(l);
    }

    public void setVolverListener(ActionListener l) {
        btnVolver.addActionListener(l);
    }

    public void actualizarTurno(int turno) {
        lblTurno.setText("Turno " + turno);
    }

    public void actualizarVidaHeroe(int vida) {
        lblVidaHeroe.setText("❤ Vida: " + vida);
        lblVidaHeroe.setForeground(vida < 30
                ? new Color(255, 80, 80)
                : new Color(100, 220, 100));
    }

    public void actualizarVidaVillano(int vida) {
        lblVidaVillano.setText("❤ Vida: " + vida);
        lblVidaVillano.setForeground(vida < 30
                ? new Color(255, 80, 80)
                : new Color(100, 220, 100));
    }

    public void logEvento(String texto) {
        areaLog.append(texto + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }

    public void deshabilitarBotonTurno() {
        btnSiguienteTurno.setEnabled(false);
        btnSiguienteTurno.setText("✓ Batalla finalizada");
        btnRendirse.setEnabled(false);
        btnVolver.setEnabled(true);
    }
}