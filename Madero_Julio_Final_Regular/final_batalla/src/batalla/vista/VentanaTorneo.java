package batalla.vista;

import batalla.modelo.Torneo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaTorneo extends JFrame {

    private JLabel lblFase;
    private JLabel lblCruce1;
    private JLabel lblCruce2;
    private JLabel lblFinal;
    private JLabel lblCampeon;

    private JButton btnSiguienteFase;
    private JButton btnCerrar;

    public VentanaTorneo() {
        setTitle("🏆 Modo Torneo");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(20, 20, 35));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // ── TÍTULO ──
        JLabel lblTitulo = new JLabel("🏆 Modo Torneo", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(255, 200, 50));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // ── PANEL BRACKET ──
        JPanel panelBracket = new JPanel();
        panelBracket.setLayout(new BoxLayout(panelBracket, BoxLayout.Y_AXIS));
        panelBracket.setBackground(new Color(20, 20, 35));

        // Semifinal 1
        panelBracket.add(crearSeccion("⚔ Semifinal 1"));
        lblCruce1 = crearLabelCruce("? vs ?");
        panelBracket.add(lblCruce1);
        panelBracket.add(Box.createVerticalStrut(20));

        // Semifinal 2
        panelBracket.add(crearSeccion("⚔ Semifinal 2"));
        lblCruce2 = crearLabelCruce("? vs ?");
        panelBracket.add(lblCruce2);
        panelBracket.add(Box.createVerticalStrut(20));

        // Final
        panelBracket.add(crearSeccion("🏆 Final"));
        lblFinal = crearLabelCruce("Pendiente");
        panelBracket.add(lblFinal);
        panelBracket.add(Box.createVerticalStrut(20));

        // Campeon
        panelBracket.add(crearSeccion("👑 Campeon del Torneo"));
        lblCampeon = crearLabelCruce("Pendiente");
        lblCampeon.setForeground(new Color(255, 200, 50));
        lblCampeon.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panelBracket.add(lblCampeon);

        JScrollPane scroll = new JScrollPane(panelBracket);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(new Color(20, 20, 35));
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        // ── PANEL INFERIOR ──
        lblFase = new JLabel("Fase actual: Semifinal 1", SwingConstants.CENTER);
        lblFase.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblFase.setForeground(new Color(160, 160, 200));

        JPanel panelBottom = new JPanel(new BorderLayout(0, 6));
        panelBottom.setBackground(new Color(20, 20, 35));
        panelBottom.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setBackground(new Color(20, 20, 35));

        btnSiguienteFase = crearBoton("▶ Jugar Siguiente Batalla", new Color(60, 120, 60));
        btnSiguienteFase.setPreferredSize(new Dimension(220, 38));

        btnCerrar = crearBoton("✕ Cerrar", new Color(80, 40, 40));
        btnCerrar.setPreferredSize(new Dimension(100, 38));
        btnCerrar.addActionListener(e -> dispose());

        panelBotones.add(btnCerrar);
        panelBotones.add(btnSiguienteFase);

        panelBottom.add(lblFase, BorderLayout.CENTER);
        panelBottom.add(panelBotones, BorderLayout.SOUTH);
        panelPrincipal.add(panelBottom, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    // ── HELPERS ──
    private JPanel crearSeccion(String titulo) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(20, 20, 35));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(new Color(255, 200, 50));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(80, 80, 120));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(sep, BorderLayout.SOUTH);
        return panel;
    }

    private JLabel crearLabelCruce(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lbl.setForeground(new Color(200, 200, 220));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
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
    public void setSiguienteFaseListener(ActionListener l) {
        btnSiguienteFase.addActionListener(l);
    }

    public void actualizarBracket(Torneo torneo) {
        // Semifinal 1
        String nombre1 = torneo.getJugador1Semifinal1().getNombre();
        String nombre2 = torneo.getJugador2Semifinal1().getNombre();
        lblCruce1.setText(nombre1 + "  vs  " + nombre2);

        // Semifinal 2
        String nombre3 = torneo.getJugador1Semifinal2().getNombre();
        String nombre4 = torneo.getJugador2Semifinal2().getNombre();
        lblCruce2.setText(nombre3 + "  vs  " + nombre4);

        // Ganadores de semis
        if (torneo.getSemifinal1Ganador() != null) {
            lblCruce1.setText(lblCruce1.getText() +
                "  →  🏆 " + torneo.getSemifinal1Ganador().getNombre());
            lblCruce1.setForeground(new Color(100, 220, 100));
        }
        if (torneo.getSemifinal2Ganador() != null) {
            lblCruce2.setText(lblCruce2.getText() +
                "  →  🏆 " + torneo.getSemifinal2Ganador().getNombre());
            lblCruce2.setForeground(new Color(100, 220, 100));
        }

        // Final
        if (torneo.getSemifinal1Ganador() != null && torneo.getSemifinal2Ganador() != null) {
            lblFinal.setText(torneo.getSemifinal1Ganador().getNombre() +
                "  vs  " + torneo.getSemifinal2Ganador().getNombre());
        }

        // Campeon
        if (torneo.getCampeon() != null) {
            lblCampeon.setText("👑 " + torneo.getCampeon().getNombre());
            lblFinal.setText(lblFinal.getText() +
                "  →  👑 " + torneo.getCampeon().getNombre());
            lblFinal.setForeground(new Color(255, 200, 50));
            btnSiguienteFase.setEnabled(false);
            btnSiguienteFase.setText("✓ Torneo finalizado");
        }
    }

    public void actualizarFase(String texto) {
        lblFase.setText("Fase actual: " + texto);
    }
}