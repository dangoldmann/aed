package aed;
import java.util.ArrayList;

public class Edr {
    private Heap<Estudiante> estudiantesSentados;
    private boolean[] copiados;
    private boolean[] entregaron;
    private ArrayList<Heap<Estudiante>.HandleHeap> handlesEstudiantes;
    private int dimensionAula;
    private int[] solucionExamen;
    

    public Edr(int LadoAula, int Cant_estudiantes, int[] ExamenCanonico) {       
        handlesEstudiantes = new ArrayList<>(Cant_estudiantes);                                     // O(1)
        copiados = new boolean[Cant_estudiantes];                                                   // O(1)
        entregaron = new boolean[Cant_estudiantes];                                                 // O(1)
        dimensionAula = LadoAula;                                                                   // O(1)
        solucionExamen = ExamenCanonico;                                                            // O(1)

        Estudiante[] tempEstudiantes = new Estudiante[Cant_estudiantes];
        for (int i = 0; i < Cant_estudiantes; i++) {                                                // O(E)
            int[] examenInicial = new int[ExamenCanonico.length];                                   // O(1)
            for (int j = 0; j < ExamenCanonico.length; j++) {                                       // O(R)
                examenInicial[j] = -1;                                                              // O(1)
            }                                           
            Estudiante e = new Estudiante(i, examenInicial); 
            tempEstudiantes[i] = e;                                                                 // O(1)
        }

        estudiantesSentados = new Heap<Estudiante>(tempEstudiantes);                                                    // O(1)
        handlesEstudiantes = estudiantesSentados.obtenerHandles();                                         // O(1)
    }

//-------------------------------------------------NOTAS--------------------------------------------------------------------------

    public double[] notas(){
        double[] notasEstudiantes = new double[handlesEstudiantes.size()];                  // O(1)

        for (int i = 0; i < handlesEstudiantes.size(); i++) {                               // O(E)
            notasEstudiantes[i] = handlesEstudiantes.get(i).valor().puntaje();              // O(1)
        } 

        return notasEstudiantes;                                                            // O(1)
    }

//------------------------------------------------COPIARSE------------------------------------------------------------------------

    private ArrayList<Integer> obtenerIdsVecinos(int idEstudiante) {
        ArrayList<Integer> idsVecinos = new ArrayList<Integer>();
        int[] posicionEstudiante = calcularPosicionPorId(idEstudiante);
        boolean tieneVecinoDerecho = dimensionAula >= posicionEstudiante[1] + 1 && handlesEstudiantes.size() -  1 > idEstudiante;
        boolean tieneVecinoIzquierdo = posicionEstudiante[1] != 0;
        boolean tieneVecinoAdelante = posicionEstudiante[0] != 0;

        if (tieneVecinoDerecho) {
            idsVecinos.add(idEstudiante + 1);
        }
        if (tieneVecinoIzquierdo) {
            idsVecinos.add(idEstudiante - 1);
        }
        if (tieneVecinoAdelante) {
            int estudiantesPorFila = dimensionAula % 2 == 0 ? dimensionAula / 2 : (dimensionAula / 2) + 1;
            idsVecinos.add(idEstudiante - estudiantesPorFila);
        }

        return idsVecinos;
    }

    private int[] calcularPosicionPorId(int id) {
        int estudiantesPorFila = dimensionAula % 2 == 0 ? dimensionAula / 2 : (dimensionAula / 2) + 1;    // O(1)

        if (estudiantesPorFila == 1) {                                                                    // O(1)
            return new int[]{id, 0};                                                                      // O(1)
        }

        int filaEstudiante = (int) Math.ceil(id / (estudiantesPorFila - 1)) - 1;                          // O(1) 
        int columnaEstudiante = (id - (filaEstudiante * estudiantesPorFila)) * 2;                         // O(1)

        return new int[]{filaEstudiante, columnaEstudiante};                                              // O(1)
    }

    private int obtenerVecinoConMasRespuestas(ArrayList<Integer> idsVecinos, int idEstudiante) {
        int[] respuestasEstudiante = handlesEstudiantes.get(idEstudiante).valor().examen();
        int idVecinoConMasRespuestas = idsVecinos.get(0);
        int maxRespuestasQueNoTieneEstudiante = 0;
        for (int i = 0; i < idsVecinos.size(); i++) {
            int[] respuestasVecino = handlesEstudiantes.get(idsVecinos.get(i)).valor().examen();
            int respuestasQueNoTiene = 0;                                               // O(1)
            
            for (int j = 0; j < solucionExamen.length; j++) {                           // O(R)
                if (respuestasEstudiante[j] == -1 && respuestasVecino[j] != -1) {       // O(1)
                    respuestasQueNoTiene++;                                             // O(1)
                }
            }

            if (respuestasQueNoTiene > maxRespuestasQueNoTieneEstudiante) {
                idVecinoConMasRespuestas = idsVecinos.get(i);
                maxRespuestasQueNoTieneEstudiante = respuestasQueNoTiene;
            }
        }

        return idVecinoConMasRespuestas;
    }

    private int[] obtenerRespuestaACopiarse(int idVecinoConMasRespuestas, int idEstudiante) {
        int[] respuestasEstudiante = handlesEstudiantes.get(idEstudiante).valor().examen();
        int[] respuestasVecino = handlesEstudiantes.get(idVecinoConMasRespuestas).valor().examen();
        int[] res = new int[2];
        
        for (int i = 0; i < solucionExamen.length; i++) {
            if (respuestasEstudiante[i] == -1 && respuestasVecino[i] != -1) {
                res[0] = i;
                res[1] = respuestasVecino[i];
            }
        }
        
        return res;
    }

    private double calcularPuntaje(int[] examen) {
        int cantRespuestasCorrectas = 0;
        for (int i = 0; i < solucionExamen.length; i++) {
            if (examen[i] == solucionExamen[i]) {
                cantRespuestasCorrectas++;
            }
        }
        return (double) cantRespuestasCorrectas / solucionExamen.length * 100;
    }

    public void copiarse(int estudiante) {
        ArrayList<Integer> idsVecinos = obtenerIdsVecinos(estudiante);                                           // O(1)
        int idVecinoConMasRespuestas = obtenerVecinoConMasRespuestas(idsVecinos, estudiante);       // O(R)
        int[] respuestaACopiarse = obtenerRespuestaACopiarse(idVecinoConMasRespuestas, estudiante);   // O(R)

        Heap<Estudiante>.HandleHeap handle = handlesEstudiantes.get(estudiante);
        Estudiante e = handle.valor();
        e.responderPregunta(respuestaACopiarse[0], respuestaACopiarse[1]);
        e.actualizarPuntaje(calcularPuntaje(e.examen()));
        Heap<Estudiante>.HandleHeap nuevoHandle = handle.modificarValor(e);
        handlesEstudiantes.set(estudiante, nuevoHandle);
    }


//-----------------------------------------------RESOLVER----------------------------------------------------------------




    public void resolver(int estudiante, int NroEjercicio, int res) {
        Heap<Estudiante>.HandleHeap handle = handlesEstudiantes.get(estudiante);
        Estudiante e = handle.valor();
        e.responderPregunta(NroEjercicio, res);
        e.actualizarPuntaje(calcularPuntaje(e.examen()));
        Heap<Estudiante>.HandleHeap nuevoHandle = handle.modificarValor(e);
        handlesEstudiantes.set(estudiante, nuevoHandle);
    }



//------------------------------------------------CONSULTAR DARK WEB-------------------------------------------------------

    public void consultarDarkWeb(int n, int[] examenDW) {
        Estudiante[] tempEstudiantes =  new Estudiante[n];
        for (int i = 0; i < n; i++) {                               // O(k)
            Estudiante e = estudiantesSentados.desencolar();                // O(log(E))
            for (int j = 0; j < examenDW.length; j++) {             // O(R)
                e.responderPregunta(j, examenDW[j]);
            }
            e.actualizarPuntaje(calcularPuntaje(e.examen()));
            tempEstudiantes[i] = e;                                 // O(1)
        }

        for (int i = 0; i < n; i++) {                               // O(k)
            Estudiante e = tempEstudiantes[i];                      // O(1)
            Heap<Estudiante>.HandleHeap handle = estudiantesSentados.encolar(e);             // O(log(E))
            handlesEstudiantes.set(e.id(), handle);                                          // O(1)
        }
    }
 

//-------------------------------------------------ENTREGAR-------------------------------------------------------------

    public void entregar(int estudiante) {
        throw new UnsupportedOperationException("Sin implementar");
    }

//-----------------------------------------------------CORREGIR---------------------------------------------------------

    public NotaFinal[] corregir() {
        for (int i = 0; i < copiados.length; i++) {                                 // O(E)
            // Estudiante e = estudiantesQueEntregaron.desencolar();                   // O(log(E))
            
        }
        return new NotaFinal[0];
    }

//-------------------------------------------------------CHEQUEAR COPIAS-------------------------------------------------

    public int[] chequearCopias() {
        int[][] tablaRespuestas = new int[solucionExamen.length][10];
        return new int[0];
    }
}
