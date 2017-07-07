/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabajofinal;

import java.util.ArrayList;
import java.util.Random;


public class CrossValidation {
    private ArrayList<ArrayList<ArrayList<String>>> entrenamiento;
    private ArrayList<ArrayList<String>> prueba;
    
    public CrossValidation()
    {
        entrenamiento = new ArrayList<>();
        prueba = new ArrayList<>();
    }
    public void division(Dataset dataset, int numero)
    {
        Random rnd = new Random(); int aleatorio;
        ArrayList <String> tmp = new ArrayList<>();
        int instancias = dataset.getInstancias().size();
        int div = dataset.getInstancias().size()/(numero);
        for (int i = 0; i < numero-1; i++) {            
            //System.out.println(div);
            
            getEntrenamiento().add(new ArrayList<>());
            prueba = dataset.getInstancias();
            for (int j = 0; j < div; j++) {                
                aleatorio = (int)(rnd.nextDouble()*(instancias - j ) );                
                tmp = getPrueba().get(aleatorio);
                getPrueba().remove(aleatorio);
                getEntrenamiento().get(i).add(tmp);
            }
            instancias -= div;
            
        }             
        entrenamiento.add(prueba);       
        
        /*
        //Verificacion de datos
        System.out.println(entrenamiento.size());        
        for (int i = 0; i < entrenamiento.size(); i++) {
            System.out.println(i);
            for (int j = 0; j < entrenamiento.get(i).size(); j++) {
                System.out.print(j + " ");
                for (int k = 0; k < entrenamiento.get(i).get(j).size(); k++) {
                    System.out.print(entrenamiento.get(i).get(j).get(k)+ " ");
                }
                System.out.println(" ");
            }            
            System.out.println(" ");
        }
        */
        /*
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

    /**
     * @return the entrenamiento
     */
    public ArrayList<ArrayList<ArrayList<String>>> getEntrenamiento() {
        return entrenamiento;
    }

    /**
     * @return the prueba
     */
    public ArrayList<ArrayList<String>> getPrueba() {
        return prueba;
    }
}
