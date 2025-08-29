package aed;

public class Agenda {
    private Fecha _fecha;

    public Agenda(Fecha fechaActual) {
        _fecha = new Fecha(fechaActual);
    }

    public void agregarRecordatorio(Recordatorio recordatorio) {
        // Implementar
    }

    @Override
    public String toString() {
        // Implementar
        return "";
    }

    public void incrementarDia() {
        // Implementar
    }

    public Fecha fechaActual() {
        return new Fecha(_fecha);
    }

}
