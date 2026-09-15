package system.presentation.payroll;

import system.logic.Service;
import system.logic.entities.Empleado;
import system.logic.entities.TipoBono;
import system.logic.entities.TipoDeduccion;

import java.awt.*;
import java.util.ArrayList;

public class Controller {
    private final PlanillaView view;
    private final Model model;

    public Controller(PlanillaView view, Model model) {
        this.view = view;
        this.model = model;

        view.setController(this);
        view.setModel(model);

        view.loadCatalogos(Service.instance().getCatalogoBonos(), Service.instance().getCatalogoDeducciones());

        model.setEmpleadoList(Service.instance().getAllEmpleados());
    }

    public void handleAddBonoToStaging(TipoBono bono) {
        if (bono != null) model.addBonoToStaging(bono);
    }

    public void handleAddDeduccionToStaging(TipoDeduccion deduccion) {
        if (deduccion != null) model.addDeduccionToStaging(deduccion);
    }

    // 3-Agregar empleado
    public void handleCreateEmpleado(String cedula, String nombre, String telefono,
                                      String correo, double salarioBase) throws Exception {
        Service.instance().createEmpleado(cedula, nombre, telefono, correo, salarioBase,
                new ArrayList<>(model.getBonosStaging()), new ArrayList<>(model.getDeduccionesStaging()));

        model.setEmpleadoList(Service.instance().getAllEmpleados());
        model.setCurrentEmpleado(new Empleado());
        model.clearStaging();
    }

    // 5-Modificar empleado editado
    public void handleModifyEmpleado(String cedula, String nombre, String telefono,
                                      String correo, double salarioBase) throws Exception {
        Service.instance().modifyEmpleado(cedula, nombre, telefono, correo, salarioBase,
                new ArrayList<>(model.getBonosStaging()), new ArrayList<>(model.getDeduccionesStaging()));

        model.setEmpleadoList(Service.instance().getAllEmpleados());
        model.setCurrentEmpleado(new Empleado());
        model.clearStaging();
    }

    public void handleClear() {
        model.setCurrentEmpleado(new Empleado());
        model.clearStaging();
    }

    public Component getViewPanel() {
        return view.getPanel();
    }
}
