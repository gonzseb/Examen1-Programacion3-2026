# Sistema de Gestión de Planilla

Solución del Examen 1 — Programación III (2026-02), construida replicando 1:1 la
arquitectura del proyecto guía `Examen1-Programacion3-master` (3 capas + MVC en
la capa de presentación, persistencia XML con JAXB).

## Arquitectura

```
src/main/java/system/
├── Application.java
├── data/
│   ├── Data.java                 → raíz JAXB: empleados + catálogo de bonos/deducciones
│   └── XmlPersister.java         → singleton load()/store() (idéntico al guía)
├── logic/
│   ├── Service.java               → único punto de entrada de la Vista a la lógica
│   └── entities/
│       ├── Rubro.java             → clase abstracta (bono/deducción): nombre, tipoValor, valor
│       ├── TipoBono.java
│       ├── TipoDeduccion.java
│       ├── Empleado.java          → cédula, nombre, teléfono, correo, salarioBase, bonos, deducciones
│       └── utilities/
│           └── TipoValor.java     → enum PORCENTUAL, FIJO
└── presentation/
    ├── AbstractModel.java         → idéntico al guía (Observer/PropertyChangeSupport)
    ├── AbstractTableModel.java    → idéntico al guía (TableModel genérico)
    └── payroll/
        ├── Model.java             → estado observable de la Vista
        ├── Controller.java        → único que llama a Service
        ├── EmpleadosTableModel.java
        ├── BonosAsignadosTableModel.java
        ├── DeduccionesAsignadasTableModel.java
        └── PlanillaView.java      → Vista Swing (layout manual, lista para migrar a .form)
```

## Fórmulas de negocio

- `salarioBruto = salarioBase + Σ bono.calcularMonto(salarioBase)`
- `salarioNeto  = salarioBruto − Σ deduccion.calcularMonto(salarioBruto)`

Verificado con el ejemplo de PEDRO del enunciado (salario base ₡1.000.000, bono
Dedicación Exclusiva 35% + Grado de Maestría ₡20.000, deducción Régimen de
pensiones 4,33% + Seguro de vida del Magisterio ₡19.970):

- Salario Bruto = 1.370.000
- Salario Neto  = 1.290.709

Ambos números coinciden exactamente con la captura de pantalla del enunciado.

## Cómo correrlo

```
mvn compile exec:java -Dexec.mainClass=system.Application
```

o ejecutar `Application.main` desde IntelliJ. `data.xml` debe estar en el
directorio de trabajo (mismo nivel del proyecto).

## Migración a Swing UI Designer

`PlanillaView.java` está escrita en Java puro para poder correrla y validarla
tal cual, sin depender de IntelliJ. Para migrarla al plugin de GUI Designer:

1. Crear `PlanillaView.form` con `bind-to-class="system.presentation.payroll.PlanillaView"`.
2. Arrastrar los componentes usando **exactamente los mismos nombres de binding**
   ya declarados como atributos de la clase: `cedulaField`, `nombreField`,
   `telefonoField`, `correoField`, `salarioField`, `bonosComboBox`,
   `agregarBonoButton`, `bonosAsignadosTable`, `deduccionesComboBox`,
   `agregarDeduccionButton`, `deduccionesAsignadasTable`, `agregarButton`,
   `modificarButton`, `limpiarButton`, `empleadosTable`, `planillaPanel`.
3. Borrar el bloque de construcción manual entre los comentarios
   `// --- LAYOUT ... --- // --- FIN LAYOUT ---` (eso lo genera el `.form`).
   Todo lo demás (listeners, `propertyChange`, `loadCatalogos`) se mantiene igual.

## Nota sobre la validación de este código

Este proyecto fue compilado y ejecutado localmente contra la lógica de dominio
(clases `Empleado`, `Rubro`, `TipoBono`, `TipoDeduccion`) para confirmar que los
cálculos de salario bruto/neto arrojan los mismos valores que la captura del
enunciado. No se pudo ejecutar una compilación Maven completa (con JAXB y
FlatLaf reales) fuera de un entorno con acceso a Maven Central, así que se
recomienda compilar una vez dentro de IntelliJ antes de la migración a `.form`
para confirmar que no aparecen errores adicionales dependientes del entorno.
