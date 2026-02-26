package batalla.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {

    private JButton btnComenzar;
    private JButton btnSalir;

    public VentanaPrincipal() {
        setTitle("Batalla de Héroes y Villanos");
        setSize(600, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal con fondo oscuro
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(new Color(20, 20, 35));

        // ── PANEL CENTRAL ──
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBackground(new Color(20, 20, 35));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(50, 60, 30, 60));

        // Título principal
        JLabel lblTitulo = new JLabel("⚔ Batalla de");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(new Color(255, 200, 50));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitulo2 = new JLabel("Héroes y Villanos");
        lblTitulo2.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo2.setForeground(new Color(255, 200, 50));
        lblTitulo2.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Separador decorativo
        JLabel lblSep = new JLabel("────────────────────────");
        lblSep.setForeground(new Color(80, 80, 120));
        lblSep.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Info del proyecto
        JLabel lblMateria = new JLabel("Proyecto Final — Interfaz Gráfica");
        lblMateria.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMateria.setForeground(new Color(180, 180, 220));
        lblMateria.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblProfe = new JLabel("Docente: Giuliana Dealbera Etchechoury");
        lblProfe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblProfe.setForeground(new Color(140, 140, 180));
        lblProfe.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblAlumno = new JLabel("Alumno: Julio Madero — DNI: 40.974.293");
        lblAlumno.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblAlumno.setForeground(new Color(140, 140, 180));
        lblAlumno.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── PANEL BOTONES ──
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setBackground(new Color(20, 20, 35));

        btnComenzar = new JButton("▶  Comenzar");
        btnComenzar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnComenzar.setBackground(new Color(60, 120, 60));
        btnComenzar.setForeground(Color.WHITE);
        btnComenzar.setFocusPainted(false);
        btnComenzar.setBorderPainted(false);
        btnComenzar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnComenzar.setPreferredSize(new Dimension(160, 42));

        btnSalir = new JButton("✕  Salir");
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalir.setBackground(new Color(120, 40, 40));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setPreferredSize(new Dimension(160, 42));
        btnSalir.addActionListener(e -> System.exit(0));

        panelBotones.add(btnComenzar);
        panelBotones.add(btnSalir);

        // Armar layout
        panelCentro.add(lblTitulo);
        panelCentro.add(lblTitulo2);
        panelCentro.add(Box.createVerticalStrut(20));
        panelCentro.add(lblSep);
        panelCentro.add(Box.createVerticalStrut(20));
        panelCentro.add(lblMateria);
        panelCentro.add(Box.createVerticalStrut(8));
        panelCentro.add(lblProfe);
        panelCentro.add(Box.createVerticalStrut(4));
        panelCentro.add(lblAlumno);
        panelCentro.add(Box.createVerticalStrut(40));
        panelCentro.add(panelBotones);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        // Footer
        JLabel lblFooter = new JLabel("IES 21 — 2025", SwingConstants.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFooter.setForeground(new Color(70, 70, 100));
        lblFooter.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        panelPrincipal.add(lblFooter, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    public void setComenzarListener(ActionListener listener) {
        btnComenzar.addActionListener(listener);
    }
}