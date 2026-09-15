package system.data;

import jakarta.xml.bind.annotation.*;
import system.logic.entities.Empleado;
import system.logic.entities.TipoBono;
import system.logic.entities.TipoDeduccion;
import system.logic.entities.utilities.TipoValor;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {

    @XmlElementWrapper(name = "empleados")
    @XmlElement(name = "empleado")
    private List<Empleado> empleados;

    @XmlElementWrapper(name = "catalogoBonos")
    @XmlElement(name = "bono")
    private List<TipoBono> catalogoBonos;

    @XmlElementWrapper(name = "catalogoDeducciones")
    @XmlElement(name = "deduccion")
    private List<TipoDeduccion> catalogoDeducciones;

    public Data() {
        empleados = new ArrayList<>();

        // --- Catálogo de referencia (según ejemplos del enunciado) ---
        catalogoBonos = new ArrayList<>();
        catalogoBonos.add(new TipoBono("Dedicación exclusiva", TipoValor.PORCENTUAL, 35.0));
        catalogoBonos.add(new TipoBono("Grado de Doctorado", TipoValor.FIJO, 30000.0));
        catalogoBonos.add(new TipoBono("Grado de Maestría", TipoValor.FIJO, 20000.0));

        catalogoDeducciones = new ArrayList<>();
        catalogoDeducciones.add(new TipoDeduccion("Régimen de pensiones", TipoValor.PORCENTUAL, 4.33));
        catalogoDeducciones.add(new TipoDeduccion("Seguro de salud", TipoValor.PORCENTUAL, 5.5));
        catalogoDeducciones.add(new TipoDeduccion("Seguro de vida del Magisterio", TipoValor.FIJO, 19970.0));
    }

    public List<Empleado> getEmpleados() { return empleados; }
    public List<TipoBono> getCatalogoBonos() { return catalogoBonos; }
    public List<TipoDeduccion> getCatalogoDeducciones() { return catalogoDeducciones; }
}
