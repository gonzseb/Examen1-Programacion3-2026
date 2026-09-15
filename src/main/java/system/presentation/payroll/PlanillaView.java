package system.presentation.payroll;

import system.Application;
import system.logic.entities.Empleado;
import system.logic.entities.TipoBono;
import system.logic.entities.TipoDeduccion;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class PlanillaView implements PropertyChangeListener {
    private JPanel panel;
    private JTextField cedulaTextField;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton limpiarButton;
    private JTextField nombreTextField;
    private JTextField telefonoTextField;
    private JTextField correoTextField;
    private JTextField salarioTextField;
    private JComboBox bonosComboBox;
    private JButton agregarBonoButton;
    private JComboBox deduccionesComboBox;
    private JButton agregarDeduccionButton;
    private JTable bonos;
    private JTable deducciones;
    private JTable empleados;

    // -- MVC --
    private Model model;
    private Controller controller;

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void loadCatalogos(List<TipoBono> bonosDisponibles, List<TipoDeduccion> deduccionesDisponibles) {
        DefaultComboBoxModel<TipoBono> bonoModel = new DefaultComboBoxModel<>();
        for (TipoBono b : bonosDisponibles) bonoModel.addElement(b);
        bonosComboBox.setModel(bonoModel);
        bonosComboBox.setSelectedIndex(-1);

        DefaultComboBoxModel<TipoDeduccion> deduccionModel = new DefaultComboBoxModel<>();
        for (TipoDeduccion d : deduccionesDisponibles) deduccionModel.addElement(d);
        deduccionesComboBox.setModel(deduccionModel);
        deduccionesComboBox.setSelectedIndex(-1);
    }

    public PlanillaView() {
        cedulaTextField.setToolTipText("Escriba la cédula");
        nombreTextField.setToolTipText("Escriba el nombre");
        telefonoTextField.setToolTipText("Escriba el teléfono");
        correoTextField.setToolTipText("Escriba el correo");
        salarioTextField.setToolTipText("Escriba el salario base");

        // --- Listeners ---

        agregarBonoButton.addActionListener(e -> {
            TipoBono seleccionado = (TipoBono) bonosComboBox.getSelectedItem();
            if (seleccionado == null) {
                JOptionPane.showMessageDialog(panel, "Seleccione un bono", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controller.handleAddBonoToStaging(seleccionado);
        });

        agregarDeduccionButton.addActionListener(e -> {
            TipoDeduccion seleccionado = (TipoDeduccion) deduccionesComboBox.getSelectedItem();
            if (seleccionado == null) {
                JOptionPane.showMessageDialog(panel, "Seleccione una deducción", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controller.handleAddDeduccionToStaging(seleccionado);
        });

        // Doble clic sobre un rubro ya agregado lo quita de la zona de trabajo
        // (navegación de UI, no lógica de negocio: se llama al Model directamente).
        bonos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = bonos.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        TipoBono b = ((BonosAsignadosTableModel) bonos.getModel()).getRowAt(row);
                        model.removeBonoFromStaging(b);
                    }
                }
            }
        });

        deducciones.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = deducciones.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        TipoDeduccion d = ((DeduccionesAsignadasTableModel) deducciones.getModel()).getRowAt(row);
                        model.removeDeduccionFromStaging(d);
                    }
                }
            }
        });

        agregarButton.addActionListener(e -> {
            if (!validarFormulario()) return;
            try {
                controller.handleCreateEmpleado(
                        cedulaTextField.getText().trim(),
                        nombreTextField.getText().trim(),
                        telefonoTextField.getText().trim(),
                        correoTextField.getText().trim(),
                        Double.parseDouble(salarioTextField.getText().trim())
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        modificarButton.addActionListener(e -> {
            if (model.getCurrentEmpleado().getCedula().isEmpty()) {
                JOptionPane.showMessageDialog(panel,
                        "Seleccione primero un empleado de la lista para modificarlo.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!validarFormulario()) return;
            try {
                controller.handleModifyEmpleado(
                        model.getCurrentEmpleado().getCedula(), // la cédula no se edita
                        nombreTextField.getText().trim(),
                        telefonoTextField.getText().trim(),
                        correoTextField.getText().trim(),
                        Double.parseDouble(salarioTextField.getText().trim())
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        limpiarButton.addActionListener(e -> controller.handleClear());

        // Selección de un empleado en la tabla general -> carga TODO su detalle
        // (requisito 4: incluye bonos y deducciones).
        empleados.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = empleados.getSelectedRow();
                if (selectedRow >= 0) {
                    Empleado seleccionado = ((EmpleadosTableModel) empleados.getModel()).getRowAt(selectedRow);
                    model.setCurrentEmpleado(seleccionado);
                }
            }
        });
    }

    private boolean validarFormulario() {
        StringBuilder errores = new StringBuilder();

        if (cedulaTextField.getText().trim().isEmpty()) {
            cedulaTextField.setBackground(Application.BACKGROUND_ERROR);
            errores.append("La cédula es obligatoria.\n");
        } else {
            cedulaTextField.setBackground(null);
        }

        if (nombreTextField.getText().trim().isEmpty()) {
            nombreTextField.setBackground(Application.BACKGROUND_ERROR);
            errores.append("El nombre es obligatorio.\n");
        } else {
            nombreTextField.setBackground(null);
        }

        if (telefonoTextField.getText().trim().isEmpty()) {
            telefonoTextField.setBackground(Application.BACKGROUND_ERROR);
            errores.append("El teléfono es obligatorio.\n");
        } else {
            telefonoTextField.setBackground(null);
        }

        if (correoTextField.getText().trim().isEmpty()) {
            correoTextField.setBackground(Application.BACKGROUND_ERROR);
            errores.append("El correo es obligatorio.\n");
        } else {
            correoTextField.setBackground(null);
        }

        try {
            double salario = Double.parseDouble(salarioTextField.getText().trim());
            if (salario <= 0) {
                salarioTextField.setBackground(Application.BACKGROUND_ERROR);
                errores.append("El salario debe ser mayor a cero.\n");
            } else {
                salarioTextField.setBackground(null);
            }
        } catch (NumberFormatException ex) {
            salarioTextField.setBackground(Application.BACKGROUND_ERROR);
            errores.append("El salario debe ser un número válido.\n");
        }

        if (!errores.isEmpty()) {
            JOptionPane.showMessageDialog(panel, errores.toString(), "Datos inválidos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public JPanel getPanel() { return panel; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.CURRENT_EMPLEADO:
                Empleado actual = model.getCurrentEmpleado();
                cedulaTextField.setText(actual.getCedula());
                cedulaTextField.setEditable(actual.getCedula().isEmpty()); // solo se digita al crear
                nombreTextField.setText(actual.getNombre());
                telefonoTextField.setText(actual.getTelefono());
                correoTextField.setText(actual.getCorreo());
                salarioTextField.setText(actual.getSalarioBase() == 0.0 ? "" : String.valueOf(actual.getSalarioBase()));
                cedulaTextField.setBackground(null);
                nombreTextField.setBackground(null);
                telefonoTextField.setBackground(null);
                correoTextField.setBackground(null);
                salarioTextField.setBackground(null);
                break;

            case Model.EMPLEADO_LIST:
                int[] empCols = {
                        EmpleadosTableModel.CEDULA,
                        EmpleadosTableModel.NOMBRE,
                        EmpleadosTableModel.TELEFONO,
                        EmpleadosTableModel.CORREO,
                        EmpleadosTableModel.SALARIO_BASE,
                        EmpleadosTableModel.SALARIO_BRUTO,
                        EmpleadosTableModel.SALARIO_NETO
                };
                empleados.setModel(new EmpleadosTableModel(empCols, model.getEmpleadoList()));
                break;

            case Model.BONOS_STAGING:
                int[] bonoCols = { BonosAsignadosTableModel.NOMBRE, BonosAsignadosTableModel.VALOR };
                bonos.setModel(new BonosAsignadosTableModel(bonoCols, model.getBonosStaging()));
                break;

            case Model.DEDUCCIONES_STAGING:
                int[] dedCols = { DeduccionesAsignadasTableModel.NOMBRE, DeduccionesAsignadasTableModel.VALOR };
                deducciones.setModel(new DeduccionesAsignadasTableModel(dedCols, model.getDeduccionesStaging()));
                break;
        }
        this.panel.revalidate();
    }
}