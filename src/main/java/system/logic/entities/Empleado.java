package system.logic.entities;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Empleado {
    @XmlID
    private String cedula;

    private String nombre;
    private String telefono;
    private String correo;
    private double salarioBase;

    @XmlElementWrapper(name = "bonos")
    @XmlElement(name = "bono")
    @XmlIDREF
    private List<TipoBono> bonos;

    @XmlElementWrapper(name = "deducciones")
    @XmlElement(name = "deduccion")
    @XmlIDREF
    private List<TipoDeduccion> deducciones;

    public Empleado() {
        this.cedula = "";
        this.nombre = "";
        this.telefono = "";
        this.correo = "";
        this.salarioBase = 0.0;
        this.bonos = new ArrayList<>();
        this.deducciones = new ArrayList<>();
    }

    public Empleado(String cedula, String nombre, String telefono, String correo, double salarioBase) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.salarioBase = salarioBase;
        this.bonos = new ArrayList<>();
        this.deducciones = new ArrayList<>();
    }

    // --- Getters básicos ---
    public String getCedula() { return cedula; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public double getSalarioBase() { return salarioBase; }
    public List<TipoBono> getBonos() { return bonos; }
    public List<TipoDeduccion> getDeducciones() { return deducciones; }

    // --- Setters (para "Modificar empleado") ---
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }

    // --- Reglas de negocio: sin bonos/deducciones duplicados ---
    public void addBono(TipoBono bono) {
        if (bono == null) throw new IllegalArgumentException("El bono no puede ser nulo");
        if (!bonos.contains(bono)) bonos.add(bono);
    }

    public void addDeduccion(TipoDeduccion deduccion) {
        if (deduccion == null) throw new IllegalArgumentException("La deducción no puede ser nula");
        if (!deducciones.contains(deduccion)) deducciones.add(deduccion);
    }

    public void reemplazarBonos(List<TipoBono> nuevaLista) {
        bonos.clear();
        for (TipoBono b : nuevaLista) addBono(b);
    }

    public void reemplazarDeducciones(List<TipoDeduccion> nuevaLista) {
        deducciones.clear();
        for (TipoDeduccion d : nuevaLista) addDeduccion(d);
    }

    // --- Cálculos derivados (son MÉTODOS, no campos: no se persisten en el XML) ---
    public double getSalarioBruto() {
        double totalBonos = 0.0;
        for (TipoBono b : bonos) totalBonos += b.calcularMonto(salarioBase);
        return salarioBase + totalBonos;
    }

    public double getSalarioNeto() {
        double bruto = getSalarioBruto();
        double totalDeducciones = 0.0;
        for (TipoDeduccion d : deducciones) totalDeducciones += d.calcularMonto(bruto);
        return bruto - totalDeducciones;
    }
}
