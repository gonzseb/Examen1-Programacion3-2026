package system.presentation.payroll;

import system.logic.entities.Empleado;
import system.presentation.AbstractTableModel;

import java.util.List;

public class EmpleadosTableModel extends AbstractTableModel<Empleado> {
    public EmpleadosTableModel(int[] cols, List<Empleado> rows) {
        super(cols, rows);
    }

    public static final int CEDULA = 0;
    public static final int NOMBRE = 1;
    public static final int TELEFONO = 2;
    public static final int CORREO = 3;
    public static final int SALARIO_BASE = 4;
    public static final int SALARIO_BRUTO = 5;
    public static final int SALARIO_NETO = 6;

    @Override
    protected void initColNames() {
        colNames = new String[7];
        colNames[CEDULA] = "Cédula";
        colNames[NOMBRE] = "Nombre";
        colNames[TELEFONO] = "Teléfono";
        colNames[CORREO] = "Correo";
        colNames[SALARIO_BASE] = "Sal. Base";
        colNames[SALARIO_BRUTO] = "Sal. Bruto";
        colNames[SALARIO_NETO] = "Sal. Neto";
    }

    @Override
    protected Object getPropetyAt(Empleado e, int col) {
        switch (cols[col]) {
            case CEDULA: return e.getCedula();
            case NOMBRE: return e.getNombre();
            case TELEFONO: return e.getTelefono();
            case CORREO: return e.getCorreo();
            case SALARIO_BASE: return e.getSalarioBase();
            case SALARIO_BRUTO: return e.getSalarioBruto();
            case SALARIO_NETO: return e.getSalarioNeto();
            default: return "";
        }
    }
}
