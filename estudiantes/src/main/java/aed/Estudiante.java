package aed;

public class Estudiante implements Comparable<Estudiante>{
   private int id;
   private int[] examen; 
   private double puntaje;

   public Estudiante(int _id, int[] _examen) {
        id = _id;
        examen = _examen;
        puntaje = 0;
   }
}
