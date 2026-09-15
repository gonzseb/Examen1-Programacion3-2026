package system.logic.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import system.logic.entities.utilities.TipoValor;

@XmlAccessorType(XmlAccessType.FIELD)
public class TipoBono extends Rubro {
    public TipoBono() { super(); }

    public TipoBono(String nombre, TipoValor tipoValor, double valor) {
        super(nombre, tipoValor, valor);
    }
}
