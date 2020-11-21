package Clases;
import java.util.Queue;


import java.util.LinkedList;

public class Procesador{
    public Procesador(){
        quantum = 10;
        _current = this;
    }

    private static Procesador _current;
    
    public static Procesador Current(){
        if(_current == null){
            System.out.println(colores.ANSI_RED + "Procesador es vacio." + colores.ANSI_RESET);
        }

        return _current;
    }
    
    Queue<PCB> scheduler = new LinkedList<>();
    private int quantum;

    public void addProceso (PCB newProceso){
        scheduler.add(newProceso);
        System.out.println(colores.ANSI_GREEN + "El " + newProceso.toString() + " fue añadido al scheduler." + colores.ANSI_RESET);
    }

    public boolean ejecutarProximoProceso(){
        // Si esta vacio, esta mal no deberia en esta iteracion.
        if(scheduler.isEmpty()){
            System.out.print(colores.ANSI_RED + "FATAL: El scheduler esta vacio." + colores.ANSI_RESET);
            return true;
        }

        // Conseguir el proximo proceso en el scheduler.
        PCB procesoActual = Procesador.Current().getNextProceso();
        boolean terminoProceso = false;

        // Si esta bloqueado, no ejecutar y avisar.
        if(!procesoActual.enEstado("Bloqueado")){
            terminoProceso = procesoActual.ejecutar();
        } else {
            System.out.println(colores.ANSI_YELLOW + "El " + procesoActual + " sigue bloqueado."  + colores.ANSI_RESET);
            System.out.println(colores.ANSI_YELLOW + "El " + procesoActual.getRecurso() + " sigue en uso."  + colores.ANSI_RESET);
        }

        // Si no termino el proceso, agregarlo de nuevo al scheduler
        if(!terminoProceso) {
            // Si esta en ejecucion, antes de agregarlo al scheduler lo pasamos a listo.
            if(procesoActual.enEstado("EnEjecucion")){
                procesoActual.cambiarEstado("Listo");
            }
            
            System.out.println(colores.ANSI_YELLOW + "El " + procesoActual + " aun no ha terminado. Quedo en la linea " + procesoActual.getLinea() + colores.ANSI_RESET);
            // Se agrega el proceso.
            addProceso(procesoActual);
        } else {
            System.out.println(colores.ANSI_GREEN + "El " + procesoActual + " ha terminado su ejecucion."  + colores.ANSI_RESET);
        }
       
        // Retornar si terminamos
        return scheduler.isEmpty();
    }

    public PCB getNextProceso(){
        PCB ret = scheduler.remove();
        System.out.println(colores.ANSI_GREEN + "Se le da el Procesador a " + ret.toString() + colores.ANSI_RESET);
        return ret;
    }

    public int getQuantum(){
        return quantum;
    }

    public void esperar(RCB recurso){
        
    }
}