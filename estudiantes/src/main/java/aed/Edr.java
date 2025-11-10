package aed;
import java.util.ArrayList;

public class Edr {
    private Heap<Estudiante> estudiantesSentados;
    private Heap<Estudiante> estudiantesQueEntregaron;
    private boolean[] copiados;
    private ArrayList<Heap<Estudiante>.HandleHeap> handlesEstudiantes;
    private int dimensionAula;
    private int[] solucionExamen;
    

    public Edr(int LadoAula, int Cant_estudiantes, int[] ExamenCanonico) {       
        handlesEstudiantes = new ArrayList<>(Cant_estudiantes);                                     // O(1)
        copiados = new boolean[Cant_estudiantes];                                                   // O(1)
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
        
        estudiantesSentados = new Heap<Estudiante>(tempEstudiantes);                                       // O(1)
        estudiantesQueEntregaron = new Heap<Estudiante>(new Estudiante[Cant_estudiantes]);                 // O(1)
        handlesEstudiantes = estudiantesSentados.obtenerHandles();                                         // O(E)
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
        ArrayList<Integer> idsVecinos = new ArrayList<Integer>();                                                                   // O(1)
        int[] posicionEstudiante = calcularPosicionPorId(idEstudiante);                                                             // O(1)
        boolean tieneVecinoDerecho = dimensionAula >= posicionEstudiante[1] + 1 && handlesEstudiantes.size() -  1 > idEstudiante;   // O(1)
        boolean tieneVecinoIzquierdo = posicionEstudiante[1] != 0;                                                                  // O(1)
        boolean tieneVecinoAdelante = posicionEstudiante[0] != 0;                                                                   // O(1)

        if (tieneVecinoDerecho) {
            idsVecinos.add(idEstudiante + 1);                                                                                       // O(1)
        }
        if (tieneVecinoIzquierdo) {
            idsVecinos.add(idEstudiante - 1);                                                                                       // O(1)
        }
        if (tieneVecinoAdelante) {
            int estudiantesPorFila = dimensionAula % 2 == 0 ? dimensionAula / 2 : (dimensionAula / 2) + 1;                          // O(1)
            idsVecinos.add(idEstudiante - estudiantesPorFila);                                                                      // O(1)
        }

        return idsVecinos;                                                                                                          // O(1)
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
        int[] respuestasEstudiante = handlesEstudiantes.get(idEstudiante).valor().examen();          // O(1)
        int idVecinoConMasRespuestas = idsVecinos.get(0);                                     // O(1)
        int maxRespuestasQueNoTieneEstudiante = 0;                                                  // O(1)

        for (int i = 0; i < idsVecinos.size(); i++) {                                               // O(1) Esta acotada la cantidad de vecinos que puede tener cualquier estudiante.
            int[] respuestasVecino = handlesEstudiantes.get(idsVecinos.get(i)).valor().examen();    // O(1)
            int respuestasQueNoTiene = 0;                                               // O(1)
            
            for (int j = 0; j < solucionExamen.length; j++) {                           // O(R)
                if (respuestasEstudiante[j] == -1 && respuestasVecino[j] != -1) {       // O(1)
                    respuestasQueNoTiene++;                                             // O(1)
                }
            }

            if (respuestasQueNoTiene > maxRespuestasQueNoTieneEstudiante || (respuestasQueNoTiene == maxRespuestasQueNoTieneEstudiante && idsVecinos.get(i) < idVecinoConMasRespuestas)) {
                idVecinoConMasRespuestas = idsVecinos.get(i);                               // O(1)
                maxRespuestasQueNoTieneEstudiante = respuestasQueNoTiene;                   // O(1)
            }
        }

        return idVecinoConMasRespuestas;                                                    // O(1)
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
        int cantRespuestasCorrectas = 0;                                            // O(1)
        for (int i = 0; i < solucionExamen.length; i++) {                           // O(R)
            if (examen[i] == solucionExamen[i]) {                                   // O(1)
                cantRespuestasCorrectas++;                                          // O(1)
            }
        }
        return (double) cantRespuestasCorrectas / solucionExamen.length * 100;      // O(1)
    }

    public void copiarse(int estudiante) {
        ArrayList<Integer> idsVecinos = obtenerIdsVecinos(estudiante);                                           // O(1)
        int idVecinoConMasRespuestas = obtenerVecinoConMasRespuestas(idsVecinos, estudiante);                    // O(R)
        int[] respuestaACopiarse = obtenerRespuestaACopiarse(idVecinoConMasRespuestas, estudiante);              // O(R)

        Heap<Estudiante>.HandleHeap handle = handlesEstudiantes.get(estudiante);                                 // O(1)
        Estudiante e = handle.valor();                                                                           // O(1)
        e.responderPregunta(respuestaACopiarse[0], respuestaACopiarse[1]);                                       // O(1)
        e.actualizarPuntaje(calcularPuntaje(e.examen()));                                                        // O(R)
        Heap<Estudiante>.HandleHeap nuevoHandle = handle.modificarValor(e);                                      // O(log (E))
        handlesEstudiantes.set(estudiante, nuevoHandle);                                                         // O(1)
    }


//-----------------------------------------------RESOLVER----------------------------------------------------------------




    public void resolver(int estudiante, int NroEjercicio, int res) {
        Heap<Estudiante>.HandleHeap handle = handlesEstudiantes.get(estudiante);                // O(1)
        Estudiante e = handle.valor();                                                          // O(1)
        e.responderPregunta(NroEjercicio, res);                                                 // O(1)
        e.actualizarPuntaje(calcularPuntaje(e.examen()));                                       // O(1)
        Heap<Estudiante>.HandleHeap nuevoHandle = handle.modificarValor(e);                     // O(log(E))
        handlesEstudiantes.set(estudiante, nuevoHandle);                                        // O(1)
    }



//------------------------------------------------CONSULTAR DARK WEB-------------------------------------------------------

    public void consultarDarkWeb(int n, int[] examenDW) {
        Estudiante[] tempEstudiantes =  new Estudiante[n];                                  // O(1)
        for (int i = 0; i < n; i++) {                                                       // O(k)
            Estudiante e = estudiantesSentados.desencolar();                                // O(log(E))
            for (int j = 0; j < examenDW.length; j++) {                                     // O(R)
                e.responderPregunta(j, examenDW[j]);                                        // O(1)
            }
            e.actualizarPuntaje(calcularPuntaje(e.examen()));                               // O(1)
            tempEstudiantes[i] = e;                                                         // O(1)
        }

        for (int i = 0; i < n; i++) {                                                       // O(k)
            Estudiante e = tempEstudiantes[i];                                              // O(1)
            Heap<Estudiante>.HandleHeap handle = estudiantesSentados.encolar(e);            // O(log(E))
            handlesEstudiantes.set(e.id(), handle);                                         // O(1)
        }
    }
 

//-------------------------------------------------ENTREGAR-------------------------------------------------------------

    public void entregar(int estudiante) {
        throw new UnsupportedOperationException("Sin implementar");
    }

//-----------------------------------------------------CORREGIR---------------------------------------------------------

    public NotaFinal[] corregir() {
        ArrayList<NotaFinal> notas = new ArrayList<>();
        for (int i = 0; i < handlesEstudiantes.size(); i++) {                       // O(E)
            Estudiante e = estudiantesQueEntregaron.desencolar();                   // O(log(E))
            if (copiados[e.id()]) continue;                                         // O(1)
            NotaFinal nota = new NotaFinal(e.puntaje(), e.id());                    // O(1)
            notas.add(nota);                                                        // O(1)
        }

        NotaFinal[] res = new NotaFinal[notas.size()];                              // O(1)
        for (int i = 0; i < notas.size(); i++) {                                    // O(E)
            res[i] = notas.get(i);                                                  // O(1)
        }

        return res;                                                                 // O(1)
    }

//-------------------------------------------------------CHEQUEAR COPIAS-------------------------------------------------

    public int[][] armarTablaRespuestas() {
        int[][] tablaRespuestas = new int[solucionExamen.length][10];       // O(1)

        for (int i = 0; i < handlesEstudiantes.size(); i++) {               // O(E)
            int[] examen = handlesEstudiantes.get(i).valor().examen();      // O(1)
            for (int j = 0; j < examen.length; j++) {                       // O(R)
                int respuesta = examen[j];                                  // O(1)
                if (respuesta == -1) continue;                              // O(1)
                tablaRespuestas[j][examen[j]] += 1;                         // O(1)
            }
        }

        return tablaRespuestas;                                             // O(1)
    }

    public boolean esCopion(int[] examen, int[][] tablaRespuestas) {
        boolean esCopion = true;                                                                                    // O(1)

        for (int i = 0; i < examen.length; i++) {                                                                   // O(R)
            int respuesta = examen[i];                                                                              // O(1)
            if (respuesta == -1) continue;
            boolean esRespuestaSospechosa = tablaRespuestas[i][examen[i]] - 1 >= handlesEstudiantes.size() * 0.25;  // O(1)
            if (!esRespuestaSospechosa) {                                                                           // O(1)
                esCopion = false;                                                                                   // O(1)
                break;                                                                                              // O(1)
            }
        }

        return esCopion;                                                                                            // O(1)
    }

    public int[] chequearCopias() {
        int[][] tablaRespuestas = armarTablaRespuestas();                   // O(E * R)

        ArrayList<Integer> copiones = new ArrayList<>();                    // O(1)

        for (int i = 0; i < handlesEstudiantes.size(); i++) {               // O(E)
            int[] examen = handlesEstudiantes.get(i).valor().examen();      // O(1)
    
            if (esCopion(examen, tablaRespuestas)) {                        // O(R)
                copiones.add(i);                                            // O(1)
                copiados[i] = true;                                         // O(1)
            }         
        }
        
        int[] res = new int[copiones.size()];                               // O(1)
        for (int i = 0; i < copiones.size(); i++) {                         // O(E)
            res[i] = copiones.get(i);                                       // O(1)
        }

        return res;
    }
}
