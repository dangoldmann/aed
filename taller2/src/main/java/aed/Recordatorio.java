package aed;

public class Recordatorio {
    private String _mensaje;
    private Fecha _fecha;
    private Horario _horario;

    public Recordatorio(String mensaje, Fecha fecha, Horario horario) {
        _mensaje = mensaje;
        _fecha = new Fecha(fecha);
        _horario = horario;
    }

    public Horario horario() {
        return _horario;
    }

    public Fecha fecha() {
        return _fecha;
    }

    public String mensaje() {
        return _mensaje;
    }

    @Override
    public String toString() {
        return _mensaje + "@" + _fecha.toString() + _horario.toString();
    }

    @Override
    public boolean equals(Object otro) {
        boolean otroEsNull = otro == null;

        if (otroEsNull) {
            return false;
        }

        boolean claseDistinta = otro.getClass() != this.getClass();

        if (claseDistinta) {
            return false;
        }
        
        Recordatorio otroRecordatorio = (Recordatorio) otro;

        return otroRecordatorio._mensaje.equals(_mensaje) && otroRecordatorio._fecha.equals(_fecha) && otroRecordatorio._horario.equals(_horario);
    }

}
