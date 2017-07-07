/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabajofinal;

import java.util.ArrayList;
import java.util.Random;


public class Split {
    private ArrayList<ArrayList<String>> entrenamiento;
    private ArrayList<ArrayList<String>> prueba;
    
    public Split()
    {
        entrenamiento = new ArrayList<>();
        prueba = new ArrayList<>();
    }
    public void division(Dataset dataset, int numero)
    {
        
        int div = dataset.getInstancias().size()*(100-numero)/100;
        
        //System.out.println(div);
        
        entrenamiento = dataset.getInstancias();
        Random rnd = new Random(); int aleatorio;
        ArrayList <String> tmp = new ArrayList<>();
        for (int i = 0; i < div; i++) {
            aleatorio = (int)(rnd.nextDouble()*(339 - i));
            //System.out.println( i+" "+aleatorio);
            tmp = entrenamiento.get(aleatorio);
            entrenamiento.remove(aleatorio);
            prueba.add(tmp);
        }
        /*
        //Verificacion de datos
        System.out.println(entrenamiento.size());        
        for (int i = 0; i < entrenamiento.size(); i++) {
            System.out.print(i+" ");
            for (int j = 0; j < entrenamiento.get(i).size(); j++) {
                System.out.print(entrenamiento.get(i).get(j)+ " ");
            }
            System.out.println(" ");
        }
        System.out.println(prueba.size());
        for (int i = 0; i < prueba.size(); i++) {
            System.out.print(i+" ");
            for (int j = 0; j < prueba.get(i).size(); j++) {
                System.out.print( prueba.get(i).get(j)+ " ");
            }
            System.out.println(" ");
        }
        */
    }

    
    public ArrayList<ArrayList<String>> getEntrenamiento() {
        return entrenamiento;
    }

    
    public void setEntrenamiento(ArrayList<ArrayList<String>> entrenamiento) {
        this.entrenamiento = entrenamiento;
    }

   
    public ArrayList<ArrayList<String>> getPrueba() {
        return prueba;
    }

    void setPrueba(ArrayList<ArrayList<String>> prueba) {
        this.prueba = prueba;
    }
}
