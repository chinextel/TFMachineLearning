/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabajofinal;

import java.io.FileNotFoundException;
import java.util.Scanner;


public class TrabajoFinal {

    
    public static void main(String[] args) throws FileNotFoundException {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazGrafica().setVisible(true);
            }
        });
        
        /*
        LeerDataset leer = new LeerDataset();
        String ruta = "D:\\primary.tumor.arff";
        //String ruta = "C:\\machine learning\\primary.tumor.arff";
        leer.cargarData(ruta);
        Scanner sc = new Scanner(System.in);
        int opcion, num, hp;
        
        NaiveBayes nb = new NaiveBayes();
        //Knn knn = new Knn();
        System.out.print("Ingrese la cantidad de divisiones para el dataset: ");
        num = sc.nextInt();
        
        System.out.print("Ingrese el valor para el hiperparametro: ");
        hp = sc.nextInt();
        nb.algoritmo(hp, num, leer.getDataset());
        */
        /*
        System.out.print("Ingrese un k para los vecinos mas cercanos: ");
        int k; k = sc.nextInt();        
        knn.algoritmo(k, num, leer.getDataset());
        */
        
    }
    
}
