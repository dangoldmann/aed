package aed;

public class Estudiante implements Comparable<Estudiante>{
   private int id;
   private int[] examen; 
   private double puntaje;
   private boolean entrego;

   public Estudiante(int _id, int[] _examen) {
        id = _id;
        examen = _examen;
        puntaje = 0;
        entrego = false;
   }

   public Estudiante(Estudiante estudiante) {
      id = estudiante.id();
      examen = estudiante.examen().clone();        // Usamos .clone para que la copia sea por valor y no por referencia
      puntaje = estudiante.puntaje();
      entrego = estudiante.entrego();
   }

   public int id() {
      return this.id;
   }

   public int[] examen() {
      return this.examen;
   }

   public void responderPregunta(int nroPregunta, int respuesta) {
      examen[nroPregunta] = respuesta;
   }
   
   public void actualizarPuntaje(double nuevoPuntaje) {
      puntaje = nuevoPuntaje;
   }

   public double puntaje() {
      return this.puntaje;
   }

   public boolean entrego() {
      return this.entrego;
   }

   public void entregar() {
      entrego = true;
   }

   public int compareTo(Estudiante otro) {
      if (this.entrego != otro.entrego) return this.entrego ? -1 : 1;
      if (this.puntaje != otro.puntaje) return (int) (otro.puntaje - this.puntaje);
      return otro.id - this.id;
   }
}
