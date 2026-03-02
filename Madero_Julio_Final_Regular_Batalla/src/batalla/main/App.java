package batalla.main;

import batalla.controlador.ConfigControlador;
import batalla.vista.VentanaConfig;
import batalla.vista.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
            ventanaPrincipal.setComenzarListener(e -> {
                ventanaPrincipal.dispose();
                VentanaConfig config = new VentanaConfig();
                new ConfigControlador(config);
                config.setVisible(true);
            });
            ventanaPrincipal.setVisible(true);
        });
    }
}