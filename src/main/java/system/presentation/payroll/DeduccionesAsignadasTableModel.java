package system.presentation.payroll;

import system.logic.entities.TipoDeduccion;
import system.logic.entities.utilities.TipoValor;
import system.presentation.AbstractTableModel;

import java.util.List;

public class DeduccionesAsignadasTableModel extends AbstractTableModel<TipoDeduccion> {
    public DeduccionesAsignadasTableModel(int[] cols, List<TipoDeduccion> rows) {
        super(cols, rows);
    }

    public static final int NOMBRE = 0;
    public static final int VALOR = 1;

    @Override
    protected void initColNames() {
        colNames = new String[2];
        colNames[NOMBRE] = "Nombre";
        colNames[VALOR] = "Valor";
    }

    @Override
    protected Object getPropetyAt(TipoDeduccion d, int col) {
        switch (cols[col]) {
            case NOMBRE: return d.getNombre();
            case VALOR: return d.getTipoValor() == TipoValor.PORCENTUAL
                    ? d.getValor() + " %"
                    : "\u20A1 " + d.getValor();
            default: return "";
        }
    }
}
