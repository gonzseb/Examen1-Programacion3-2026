package system.logic;

import system.data.Data;
import system.data.XmlPersister;
import system.logic.entities.Empleado;
import system.logic.entities.TipoBono;
import system.logic.entities.TipoDeduccion;

import java.util.List;

public class Service {
    private static Service theInstance;

    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    private Data data;

    private Service() {
        try {
            data = XmlPersister.instance().load();
        } catch (Exception e) {
            data = new Data();
        }
    }

    public void stop() {
        try {
            XmlPersister.instance().store(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // --- Catálogos (fijos, solo lectura desde la UI) ---
    public List<TipoBono> getCatalogoBonos() { return data.getCatalogoBonos(); }
    public List<TipoDeduccion> getCatalogoDeducciones() { return data.getCatalogoDeducciones(); }

    // --- Empleados ---
    public List<Empleado> getAllEmpleados() { return data.getEmpleados(); }

    // 3-Agregar empleado
    public void createEmpleado(String cedula, String nombre, String telefono, String correo,
                                double salarioBase, List<TipoBono> bonos, List<TipoDeduccion> deducciones) throws Exception {

        if (cedula == null || cedula.isEmpty()) throw new Exception("La cédula es obligatoria");
        if (nombre == null || nombre.isEmpty()) throw new Exception("El nombre es obligatorio");
        if (telefono == null || telefono.isEmpty()) throw new Exception("El teléfono es obligatorio");
        if (correo == null || correo.isEmpty()) throw new Exception("El correo es obligatorio");
        if (salarioBase <= 0) throw new Exception("El salario base debe ser mayor a cero");

        boolean existe = data.getEmpleados().stream()
                .anyMatch(e -> e.getCedula().equals(cedula));
        if (existe) throw new Exception("Ya existe un empleado con esa cédula");

        Empleado nuevo = new Empleado(cedula, nombre, telefono, correo, salarioBase);

        if (bonos != null) for (TipoBono b : bonos) nuevo.addBono(b);
        if (deducciones != null) for (TipoDeduccion d : deducciones) nuevo.addDeduccion(d);

        data.getEmpleados().add(nuevo);
    }

    // 4 y 5 - Editar / Modificar empleado
    public void modifyEmpleado(String cedula, String nombre, String telefono, String correo,
                                double salarioBase, List<TipoBono> bonos, List<TipoDeduccion> deducciones) throws Exception {

        if (nombre == null || nombre.isEmpty()) throw new Exception("El nombre es obligatorio");
        if (telefono == null || telefono.isEmpty()) throw new Exception("El teléfono es obligatorio");
        if (correo == null || correo.isEmpty()) throw new Exception("El correo es obligatorio");
        if (salarioBase <= 0) throw new Exception("El salario base debe ser mayor a cero");

        Empleado almacenado = data.getEmpleados().stream()
                .filter(e -> e.getCedula().equals(cedula))
                .findFirst()
                .orElseThrow(() -> new Exception("El empleado no existe"));

        almacenado.setNombre(nombre);
        almacenado.setTelefono(telefono);
        almacenado.setCorreo(correo);
        almacenado.setSalarioBase(salarioBase);

        if (bonos != null) almacenado.reemplazarBonos(bonos);
        if (deducciones != null) almacenado.reemplazarDeducciones(deducciones);
    }
}
