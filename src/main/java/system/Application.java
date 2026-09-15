package system;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import system.logic.Service;
import system.presentation.payroll.Controller;
import system.presentation.payroll.Model;
import system.presentation.payroll.PlanillaView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class Application {

    public static final Color BACKGROUND_ERROR = new Color(255, 102, 102);

    public static void main(String[] args) {
        // Ensure Look and Feel is set early
        try {
            FlatMacDarkLaf.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf. Falling back to default L&F.");
        }

        // Run Swing creation on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            // Standard MVC Initialization
            PlanillaView view = new PlanillaView();
            Model model = new Model();
            Controller controller = new Controller(view, model);

            // Window Setup
            JFrame window = new JFrame("Sistema de Gestión de Planilla");
            window.setSize(1150, 750);
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            window.setLocationRelativeTo(null);

            // Icon Setup
            try {
                URL iconURL = Application.class.getResource("/images/appLogo.png");
                if (iconURL != null) {
                    window.setIconImage(new ImageIcon(iconURL).getImage());
                }
            } catch (Exception e) {
                System.err.println("Error loading application icon: " + e.getMessage());
            }

            // Let the controller provide the view container if possible,
            // or use standard view retrieval safely.
            JTabbedPane tabs = new JTabbedPane();
            tabs.addTab("Empleados", controller.getViewPanel()); // Delegating through controller keeps Application independent of View structure

            window.setContentPane(tabs);
            window.setVisible(true);

            // Lifecycle management
            window.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    Service.instance().stop();
                }
            });
        });
    }
}
