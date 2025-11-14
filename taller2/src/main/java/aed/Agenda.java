package aed;

public class Agenda {
    private Fecha _fecha;
    private ArregloRedimensionableDeRecordatorios _arregloRecordatorios;

    public Agenda(Fecha fechaActual) {
        _fecha = new Fecha(fechaActual);
        _arregloRecordatorios = new ArregloRedimensionableDeRecordatorios();
    }

    public void agregarRecordatorio(Recordatorio recordatorio) {
        _arregloRecordatorios.agregarAtras(recordatorio);
    }

    @Override
    public String toString() {
        String strRecordatorios = "";
        for (int i = 0; i < _arregloRecordatorios.longitud(); i++) {
            Recordatorio recordatorio = _arregloRecordatorios.obtener(i);
            if (!recordatorio.fecha().equals(_fecha)) {
                continue;
            }
            strRecordatorios += recordatorio.toString() + "\n";
        }
        return fechaActual().toString() + "\n" + "=====" + "\n" + strRecordatorios;
    }

    public void incrementarDia() {
        _fecha.incrementarDia();
    }

    public Fecha fechaActual() {
        return new Fecha(_fecha);
    }

}
