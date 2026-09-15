package system.logic.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;
import system.logic.entities.utilities.TipoValor;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Rubro {
    @XmlID
    protected String nombre;

    protected TipoValor tipoValor;
    protected double valor;

    protected Rubro() {
        this.nombre = null;
        this.tipoValor = null;
        this.valor = 0.0;
    }

    protected Rubro(String nombre, TipoValor tipoValor, double valor) {
        this.nombre = nombre;
        this.tipoValor = tipoValor;
        this.valor = valor;
    }

    public String getNombre() { return nombre; }
    public TipoValor getTipoValor() { return tipoValor; }
    public double getValor() { return valor; }

    public double calcularMonto(double salarioReferencia) {
        if (tipoValor == TipoValor.PORCENTUAL) {
            return salarioReferencia * (valor / 100.0);
        }
        return valor; // FIJO
    }

    @Override
    public String toString() {
        if (tipoValor == TipoValor.PORCENTUAL) {
            return nombre + " (" + valor + " %)";
        }
        return nombre + " (\u20A1 " + valor + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rubro)) return false;
        return nombre != null && nombre.equals(((Rubro) o).nombre);
    }

    @Override
    public int hashCode() {
        return nombre == null ? 0 : nombre.hashCode();
    }
}
