package system.presentation.payroll;

import system.logic.entities.TipoBono;
import system.logic.entities.utilities.TipoValor;
import system.presentation.AbstractTableModel;

import java.util.List;

public class BonosAsignadosTableModel extends AbstractTableModel<TipoBono> {
    public BonosAsignadosTableModel(int[] cols, List<TipoBono> rows) {
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
    protected Object getPropetyAt(TipoBono b, int col) {
        switch (cols[col]) {
            case NOMBRE: return b.getNombre();
            case VALOR: return b.getTipoValor() == TipoValor.PORCENTUAL
                    ? b.getValor() + " %"
                    : "\u20A1 " + b.getValor();
            default: return "";
        }
    }
}
