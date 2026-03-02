package batalla.vista;

import batalla.modelo.Personaje;
import javax.swing.*;
import java.awt.*;

public class VentanaComparador extends JFrame {

    public VentanaComparador(Personaje p1, Personaje p2) {
        setTitle("⚖ Comparador de Personajes");
        setSize(700, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(20, 20, 35));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // ── TÍTULO ──
        JLabel lblTitulo = new JLabel("⚖ Comparador de Personajes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(255, 200, 50));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // ── PANEL CENTRAL: comparacion ──
        JPanel panelComparacion = new JPanel(new GridLayout(1, 3, 10, 0));
        panelComparacion.setBackground(new Color(20, 20, 35));

        // Columna izquierda — Personaje 1
        panelComparacion.add(crearColumnaPersonaje(p1, new Color(30, 50, 80),
            p1.getTipo().name().equals("HEROE")
                ? new Color(100, 180, 255)
                : new Color(255, 100, 100), p2));

        // Columna central — etiquetas de stats
        panelComparacion.add(crearColumnaEtiquetas());

        // Columna derecha — Personaje 2
        panelComparacion.add(crearColumnaPersonaje(p2, new Color(50, 25, 50),
            p2.getTipo().name().equals("HEROE")
                ? new Color(100, 180, 255)
                : new Color(255, 100, 100), p1));

        panelPrincipal.add(panelComparacion, BorderLayout.CENTER);

        // ── BOTÓN CERRAR ──
        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBottom.setBackground(new Color(20, 20, 35));

        JButton btnCerrar = new JButton("✕ Cerrar");
        btnCerrar.setBackground(new Color(80, 40, 40));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.setPreferredSize(new Dimension(100, 38));
        btnCerrar.addActionListener(e -> dispose());

        panelBottom.add(btnCerrar);
        panelPrincipal.add(panelBottom, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    private JPanel crearColumnaPersonaje(Personaje p, Color fondo,
                                          Color colorNombre, Personaje rival) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(fondo);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 120)),
            BorderFactory.createEmptyBorder(16, 12, 16, 12)
        ));

        // Nombre y tipo
        JLabel lblNombre = new JLabel(p.getNombre(), SwingConstants.CENTER);
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNombre.setForeground(colorNombre);
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblApodo = new JLabel("\"" + p.getApodo() + "\"", SwingConstants.CENTER);
        lblApodo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblApodo.setForeground(new Color(160, 160, 200));
        lblApodo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTipo = new JLabel(p.getTipo().name(), SwingConstants.CENTER);
        lblTipo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTipo.setForeground(colorNombre);
        lblTipo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lblNombre);
        panel.add(Box.createVerticalStrut(4));
        panel.add(lblApodo);
        panel.add(Box.createVerticalStrut(4));
        panel.add(lblTipo);
        panel.add(Box.createVerticalStrut(20));

        // Stats con color comparativo
        panel.add(crearStatLabel("Vida",      p.getVida(),      rival.getVida()));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearStatLabel("Fuerza",    p.getFuerza(),    rival.getFuerza()));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearStatLabel("Defensa",   p.getDefensa(),   rival.getDefensa()));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearStatLabel("Bendicion", p.getBendicion(), rival.getBendicion()));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearStatLabel("Victorias", p.getVictorias(), rival.getVictorias()));
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JLabel crearStatLabel(String stat, int valor, int valorRival) {
        JLabel lbl = new JLabel(String.valueOf(valor), SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (valor > valorRival) {
            lbl.setForeground(new Color(100, 220, 100)); // verde = gana
        } else if (valor < valorRival) {
            lbl.setForeground(new Color(255, 80, 80));   // rojo = pierde
        } else {
            lbl.setForeground(new Color(200, 200, 100)); // amarillo = empate
        }
        return lbl;
    }

    private JPanel crearColumnaEtiquetas() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(25, 25, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 8, 16, 8));

        // Espacio para alinear con nombre/apodo/tipo
        panel.add(Box.createVerticalStrut(80));

        String[] etiquetas = {"Vida", "Fuerza", "Defensa", "Bendicion", "Victorias"};
        for (String e : etiquetas) {
            JLabel lbl = new JLabel(e, SwingConstants.CENTER);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lbl.setForeground(new Color(255, 200, 50));
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(lbl);
            panel.add(Box.createVerticalStrut(18));
        }

        panel.add(Box.createVerticalGlue());
        return panel;
    }
}