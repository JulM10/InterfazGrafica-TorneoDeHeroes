package batalla.vista;

import batalla.modelo.Personaje.TipoPersonaje;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VentanaConfig extends JFrame {

    private JTextField txtNombre;
    private JTextField txtApodo;
    private JComboBox<String> cmbTipo;
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnIniciar;
    private JButton btnSalir;
    private JButton btnReporte;
    private JButton btnComparar;
    private JButton btnTorneo;

    private DefaultTableModel modeloTabla;
    private JTable tablaPersonajes;

    private JLabel lblHeroeSeleccionado;
    private JLabel lblVillanoSeleccionado;

    public VentanaConfig() {
        setTitle("Configuración de Batalla");
        setSize(650, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(20, 20, 35));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // ── TÍTULO ──
        JLabel lblTitulo = new JLabel("⚔ Configuración de Batalla", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(255, 200, 50));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // ── PANEL IZQUIERDO: registro ──
        JPanel panelRegistro = new JPanel();
        panelRegistro.setLayout(new BoxLayout(panelRegistro, BoxLayout.Y_AXIS));
        panelRegistro.setBackground(new Color(30, 30, 50));
        panelRegistro.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 120)),
                "Registrar Personaje",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 12),
                new Color(180, 180, 220)));

        txtNombre = crearCampo();
        txtApodo = crearCampo();
        cmbTipo = new JComboBox<>(new String[] { "Heroe", "Villano" });
        cmbTipo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbTipo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        cmbTipo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? new Color(60, 80, 140) : new Color(40, 40, 65));
                setForeground(Color.WHITE);
                setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
                return this;
            }
        });

        btnAgregar = crearBoton("+ Agregar", new Color(60, 120, 60));
        btnEliminar = crearBoton("✕ Eliminar seleccionado", new Color(120, 40, 40));

        panelRegistro.add(Box.createVerticalStrut(8));
        panelRegistro.add(crearLabel("Nombre:"));
        panelRegistro.add(Box.createVerticalStrut(4));
        panelRegistro.add(txtNombre);
        panelRegistro.add(Box.createVerticalStrut(8));
        panelRegistro.add(crearLabel("Apodo:"));
        panelRegistro.add(Box.createVerticalStrut(4));
        panelRegistro.add(txtApodo);
        panelRegistro.add(Box.createVerticalStrut(8));
        panelRegistro.add(crearLabel("Tipo:"));
        panelRegistro.add(Box.createVerticalStrut(4));
        panelRegistro.add(cmbTipo);
        panelRegistro.add(Box.createVerticalStrut(16));
        panelRegistro.add(btnAgregar);
        panelRegistro.add(Box.createVerticalStrut(6));
        panelRegistro.add(btnEliminar);
        panelRegistro.add(Box.createVerticalGlue());

        // ── PANEL DERECHO: tabla ──
        JPanel panelTabla = new JPanel(new BorderLayout(0, 8));
        panelTabla.setBackground(new Color(30, 30, 50));
        panelTabla.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 120)),
                "Personajes registrados",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 12),
                new Color(180, 180, 220)));

        modeloTabla = new DefaultTableModel(
                new String[] { "Nombre", "Apodo", "Tipo", "Vida", "Fuerza", "Defensa" }, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tablaPersonajes = new JTable(modeloTabla);
        tablaPersonajes.setBackground(new Color(25, 25, 45));
        tablaPersonajes.setForeground(new Color(200, 200, 220));
        tablaPersonajes.setSelectionBackground(new Color(60, 80, 140));
        tablaPersonajes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaPersonajes.getTableHeader().setBackground(new Color(40, 40, 70));
        tablaPersonajes.getTableHeader().setForeground(new Color(255, 200, 50));
        tablaPersonajes.setRowHeight(26);
        tablaPersonajes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // listener de clic en tabla ──
        tablaPersonajes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tablaPersonajes.getSelectedRow();
                if (fila >= 0) {
                    String tipo = (String) modeloTabla.getValueAt(fila, 2);
                    String nombre = (String) modeloTabla.getValueAt(fila, 0);
                    String apodo = (String) modeloTabla.getValueAt(fila, 1);
                    if (tipo.equals("Heroe")) {
                        lblHeroeSeleccionado.setText("⚔ Heroe: " + nombre + " (" + apodo + ")");
                        lblHeroeSeleccionado.setForeground(new Color(100, 180, 255));
                    } else {
                        lblVillanoSeleccionado.setText("💀 Villano: " + nombre + " (" + apodo + ")");
                        lblVillanoSeleccionado.setForeground(new Color(255, 100, 100));
                    }
                }
            }
        });

        // panel de seleccionados ──
        lblHeroeSeleccionado = new JLabel("⚔ Heroe: ninguno seleccionado");
        lblHeroeSeleccionado.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblHeroeSeleccionado.setForeground(new Color(100, 100, 140));

        lblVillanoSeleccionado = new JLabel("💀 Villano: ninguno seleccionado");
        lblVillanoSeleccionado.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblVillanoSeleccionado.setForeground(new Color(100, 100, 140));

        JPanel panelSeleccion = new JPanel(new GridLayout(2, 1, 0, 4));
        panelSeleccion.setBackground(new Color(30, 30, 50));
        panelSeleccion.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        panelSeleccion.add(lblHeroeSeleccionado);
        panelSeleccion.add(lblVillanoSeleccionado);

        JLabel lblInfo = new JLabel("Clic en una fila para seleccionar combatientes");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(120, 120, 160));

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBackground(new Color(30, 30, 50));
        panelSur.add(panelSeleccion, BorderLayout.CENTER);
        panelSur.add(lblInfo, BorderLayout.SOUTH);

        panelTabla.add(new JScrollPane(tablaPersonajes), BorderLayout.CENTER);
        panelTabla.add(panelSur, BorderLayout.SOUTH);

        // ── SPLIT ──
        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, panelRegistro, panelTabla);
        split.setDividerLocation(240);
        split.setEnabled(false);
        split.setBackground(new Color(20, 20, 35));
        panelPrincipal.add(split, BorderLayout.CENTER);

        // ── BOTONES INFERIORES ──
        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBottom.setBackground(new Color(20, 20, 35));
        panelBottom.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        btnIniciar = crearBoton("▶  Iniciar Batalla", new Color(60, 120, 60));
        btnIniciar.setPreferredSize(new Dimension(160, 38));
        btnSalir = crearBoton("✕  Salir", new Color(80, 40, 40));
        btnSalir.setPreferredSize(new Dimension(100, 38));
        btnSalir.addActionListener(e -> System.exit(0));

        btnReporte = crearBoton("📊 Ver Reporte", new Color(60, 60, 140));
        btnReporte.setPreferredSize(new Dimension(140, 38));

        btnComparar = crearBoton("⚖ Comparar", new Color(80, 60, 120));
        btnComparar.setPreferredSize(new Dimension(130, 38));

        btnTorneo = crearBoton("🏆 Torneo", new Color(120, 80, 20));
        btnTorneo.setPreferredSize(new Dimension(120, 38));

        panelBottom.add(btnSalir);
        panelBottom.add(btnReporte);
        panelBottom.add(btnComparar);
        panelBottom.add(btnTorneo);
        panelBottom.add(btnIniciar);
        panelPrincipal.add(panelBottom, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    // ── HELPERS de UI ──
    private JTextField crearCampo() {
        JTextField campo = new JTextField();
        campo.setBackground(new Color(40, 40, 65));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 120)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        return campo;
    }

    private JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(new Color(180, 180, 220));
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
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
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        return btn;
    }

    // ── MÉTODOS para el controlador ──
    public void setAgregarListener(ActionListener l) {
        btnAgregar.addActionListener(l);
    }

    public void setEliminarListener(ActionListener l) {
        btnEliminar.addActionListener(l);
    }

    public void setIniciarListener(ActionListener l) {
        btnIniciar.addActionListener(l);
    }

    public String getNombre() {
        return txtNombre.getText().trim();
    }

    public String getApodo() {
        return txtApodo.getText().trim();
    }

    public TipoPersonaje getTipo() {
        return cmbTipo.getSelectedItem().equals("Heroe")
                ? TipoPersonaje.HEROE
                : TipoPersonaje.VILLANO;
    }

    public void agregarFilaTabla(String nombre, String apodo, String tipo,
            int vida, int fuerza, int defensa) {
        modeloTabla.addRow(new Object[] { nombre, apodo, tipo, vida, fuerza, defensa });
    }

    public int getFilaSeleccionada() {
        return tablaPersonajes.getSelectedRow();
    }

    public void eliminarFilaTabla(int fila) {
        modeloTabla.removeRow(fila);
    }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtApodo.setText("");
        cmbTipo.setSelectedIndex(0);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // ── NUEVO: obtener apodos seleccionados ──
    public String getApodoHeroeSeleccionado() {
        String texto = lblHeroeSeleccionado.getText();
        if (texto.contains("(")) {
            return texto.substring(texto.indexOf("(") + 1, texto.indexOf(")"));
        }
        return null;
    }

    public String getApodoVillanoSeleccionado() {
        String texto = lblVillanoSeleccionado.getText();
        if (texto.contains("(")) {
            return texto.substring(texto.indexOf("(") + 1, texto.indexOf(")"));
        }
        return null;
    }

    public void setReporteListener(java.awt.event.ActionListener l) {
        btnReporte.addActionListener(l);
    }

    public void setCompararListener(ActionListener l) {
        btnComparar.addActionListener(l);
    }

    public void setTorneoListener(ActionListener l) {
        btnTorneo.addActionListener(l);
    }

    public List<String> getApodosEnTabla() {
        List<String> apodos = new ArrayList<>();
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            apodos.add((String) modeloTabla.getValueAt(i, 1));
        }
        return apodos;
    }
}