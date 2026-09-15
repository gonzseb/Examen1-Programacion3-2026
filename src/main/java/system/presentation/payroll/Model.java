package system.presentation.payroll;

import system.logic.entities.Empleado;
import system.logic.entities.TipoBono;
import system.logic.entities.TipoDeduccion;
import system.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    private Empleado currentEmpleado;
    private List<Empleado> empleadoList;

    private List<TipoBono> bonosStaging;
    private List<TipoDeduccion> deduccionesStaging;

    public static final String CURRENT_EMPLEADO = "currentEmpleado";
    public static final String EMPLEADO_LIST = "empleadoList";
    public static final String BONOS_STAGING = "bonosStaging";
    public static final String DEDUCCIONES_STAGING = "deduccionesStaging";

    public Model() {
        currentEmpleado = new Empleado();
        empleadoList = new ArrayList<>();
        bonosStaging = new ArrayList<>();
        deduccionesStaging = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT_EMPLEADO);
        firePropertyChange(EMPLEADO_LIST);
        firePropertyChange(BONOS_STAGING);
        firePropertyChange(DEDUCCIONES_STAGING);
    }

    // --- Getters ---
    public Empleado getCurrentEmpleado() { return currentEmpleado; }
    public List<Empleado> getEmpleadoList() { return empleadoList; }
    public List<TipoBono> getBonosStaging() { return bonosStaging; }
    public List<TipoDeduccion> getDeduccionesStaging() { return deduccionesStaging; }

    // --- Setters ---
    public void setCurrentEmpleado(Empleado empleado) {
        this.currentEmpleado = empleado;
        firePropertyChange(CURRENT_EMPLEADO);

        this.bonosStaging = new ArrayList<>(empleado.getBonos());
        this.deduccionesStaging = new ArrayList<>(empleado.getDeducciones());
        firePropertyChange(BONOS_STAGING);
        firePropertyChange(DEDUCCIONES_STAGING);
    }

    public void setEmpleadoList(List<Empleado> list) {
        this.empleadoList = list;
        firePropertyChange(EMPLEADO_LIST);
    }

    public void addBonoToStaging(TipoBono bono) {
        if (!bonosStaging.contains(bono)) bonosStaging.add(bono);
        firePropertyChange(BONOS_STAGING);
    }

    public void addDeduccionToStaging(TipoDeduccion deduccion) {
        if (!deduccionesStaging.contains(deduccion)) deduccionesStaging.add(deduccion);
        firePropertyChange(DEDUCCIONES_STAGING);
    }

    public void removeBonoFromStaging(TipoBono bono) {
        bonosStaging.remove(bono);
        firePropertyChange(BONOS_STAGING);
    }

    public void removeDeduccionFromStaging(TipoDeduccion deduccion) {
        deduccionesStaging.remove(deduccion);
        firePropertyChange(DEDUCCIONES_STAGING);
    }

    public void clearStaging() {
        bonosStaging.clear();
        deduccionesStaging.clear();
        firePropertyChange(BONOS_STAGING);
        firePropertyChange(DEDUCCIONES_STAGING);
    }
}
