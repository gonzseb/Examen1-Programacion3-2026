package system.logic.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import system.logic.entities.utilities.TipoValor;

@XmlAccessorType(XmlAccessType.FIELD)
public class TipoDeduccion extends Rubro {
    public TipoDeduccion() { super(); }

    public TipoDeduccion(String nombre, TipoValor tipoValor, double valor) {
        super(nombre, tipoValor, valor);
    }
}
