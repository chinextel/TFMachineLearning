/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabajofinal;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Luis Miguel Fung
 */
public class Knn {
    private  ArrayList<ArrayList<String>> entrenamiento;
    private  ArrayList<ArrayList<String>> prueba;
    private  ArrayList<ArrayList<Integer>> distancia;
    private  ArrayList<ArrayList<Integer>> clase; 
    //la lista verdadero contiene los verdaderos y falsos positivos por clase
    private  ArrayList<ArrayList<Integer>> verdadero; 
    private  ArrayList<Integer> numeroClaseE;
    private  ArrayList<Integer> numeroClaseP;
    private  ArrayList<ArrayList<Integer>> matrizConfusion;
    private  ArrayList<ArrayList<Double>> resultados;
    public Knn ()
    {
        entrenamiento = new ArrayList<>();
        prueba = new ArrayList<>();
        distancia = new ArrayList<>();
        clase = new ArrayList<>();
        verdadero = new ArrayList<>();
        numeroClaseE = new ArrayList<>();
        numeroClaseP = new ArrayList<>();
        matrizConfusion = new ArrayList<>();
        resultados = new ArrayList<>();
    }
    public int HammingBinario(String cadena, String cadena2)
    {
        
        int tamanio = cadena.length(); 
        int tamanio2 = cadena2.length();
        Integer c = 0, c2 = 0;
        int suma = 0, res, aux = 0, j = 1;
        String cad, cad2; char car, car2;
        
        for (int i = 0; i < tamanio; i++) {
            c += (int)cadena.charAt(i);            
        }
        for (int i = 0; i < tamanio2; i++) {
            c2 += (int)cadena2.charAt(i);            
        }
        while (c != 0)
        {
            res = c % 2;
            c /= 2;
            aux += res*j;
            j *= 10;
        }
        j = 1; c = aux; aux = 0;
        while (c2 != 0)
        {
            res = c2 % 2;
            c2 /= 2;
            aux += res*j;
            j *= 10;
        }
        c2 = aux;
        cad = c.toString(); cad2 = c2.toString();
        tamanio = cad.length(); tamanio2 = cad2.length();
        if (tamanio <= tamanio2)
       {
           for (int i = 0; i < tamanio; i++) {           
            car = cad.charAt(i); car2 = cad2.charAt(i);
            if (car != car2)
            {
                suma++;
            }
            //System.out.println(suma);
            }
       }        
       
       else
       {
           for (int i = 0; i < tamanio2; i++) {                
            car = cad.charAt(i); car2 = cad2.charAt(i);
            if (car != car2)
            {
                suma++;
            }
            //System.out.println(suma);
            }
        
       }
        return suma;
    }
    
    public int Hamming(String cadena, String cadena2){
       String aux = cadena;
       String aux2 = cadena2;
       //aux = aux.replace("'","").replace("<","").replace(">","").replace("=","").replace("-","");
       //aux = aux.replace("'","").replace("<","").replace(">","").replace("=","").replace("-","");
       int tamanio = aux.length(); 
       int tamanio2 = aux2.length(); 
       char tmp, tmp2; int suma = 0;
       if (tamanio <= tamanio2)
       {
           for (int i = 0; i < tamanio; i++) {
            tmp = aux.charAt(i); tmp2 = aux2.charAt(i);
            if (tmp != tmp2)
            {
                suma++;
            }
            //System.out.println(suma);
            }
       }        
       
       else
       {
           for (int i = 0; i < tamanio2; i++) {
                tmp = aux.charAt(i); tmp2 = aux2.charAt(i);
            if (tmp != tmp2)
            {
                suma++;
            }
            //System.out.println(suma);
            }
        
       }
       
        return suma;
    }
    public void algoritmo2(int num, int k, Dataset dataset)
    {
        Split split = new Split(); split.division(dataset, num);
        Evaluacion evaluacion = new Evaluacion();
        int suma;
        System.out.println("Prediccion por knn ");
        
        //tamanio es la cantidad de clases que tiene el dataset
        int tamanio = dataset.getTipoAtributos().get(dataset.getTipoAtributos().size()-1).size();
        //tamanio2 es la cantidad de atributos del dataset
        int tamanio2 = dataset.getTipoAtributos().size();
        //inicializamos la matriz de confusion
        for (int i = 0; i < dataset.getTipoAtributos().get(dataset.getAtributos().size()-1).size(); i++) {
            
            getMatrizConfusion().add(new ArrayList<>());
            //System.out.print(i+" ");            
            for (int j = 0; j < dataset.getTipoAtributos().get(dataset.getAtributos().size()-1).size(); j++) {
                getMatrizConfusion().get(i).add(0);
                //System.out.print(matrizConfusion.get(i).get(j) + " ");
            }
            //System.out.println("");
        }
        
        prueba = split.getPrueba();
        entrenamiento = split.getEntrenamiento();
        //inicializamos la lista de listas distancia
        for (int m = 0; m < entrenamiento.size(); m++)
        {
                distancia.add(new ArrayList<>());
                //System.out.print(i+" ");
                for (int j = 0; j < 2;j++)
                {
                    distancia.get(m).add(0); //inicializamos todos los valores en 0
                    //System.out.print(distancia.get(i).get(j) + " ");
                }
                //System.out.println("");
        }
            //inicializamos la lista clase            
        
        for (int m = 0; m < prueba.size();m++)
        {
                    getClase().add(new ArrayList<>());
                    //System.out.print(i+" ");
                    for (int j = 0; j < tamanio; j++) {
                        getClase().get(m).add(0);//inicializamos todos los valores en 0
                        //System.out.print(clase.get(i).get(j)+ " ");
                    }       
                    //System.out.println("");
        }
            //inicializamos la lista verdadero
               
        for (int m = 0; m < tamanio;m++)
        {
                    getVerdadero().add(new ArrayList<>());
                    //System.out.print(i+" ");
                    for (int j = 0; j < 2; j++) {
                        getVerdadero().get(m).add(0);//inicializamos todos los valores en 0
                        //System.out.print(verdadero.get(i).get(j)+ " ");
                    }       
                    //System.out.println("");
        }       
                        
            //reemplazamos las palabras con los numeros correspondientes a la clase
            
       for (int m = 0; m < entrenamiento.size(); m++) {
                    //System.out.print(m+" ");
                    for (int j = 0; j < tamanio; j++) {
                        if (entrenamiento.get(m).get(tamanio2-1).equals(dataset.getTipoAtributos().get(tamanio2-1).get(j)) )                        
                        {
                            numeroClaseE.add(j);
                            break;
                        }                       
                    }
                    //System.out.println(numeroClaseP.get(m));                            
        }
            
        for (int m = 0; m < prueba.size(); m++) {
                    //System.out.print(m+" ");
                    for (int j = 0; j < tamanio; j++) {
                        if (prueba.get(m).get(tamanio2-1).equals(dataset.getTipoAtributos().get(tamanio2-1).get(j)) )                        
                        {
                            numeroClaseP.add(j);
                            break;
                        }                       
                    }
                    //System.out.println(numeroClaseE.get(m));                            
        }
            //System.out.println(i); 
        for (int m = 0; m < prueba.size(); m++) { //hacemos caso por caso
                
                //System.out.println(m); 
                for (int j = 0; j < entrenamiento.size(); j++) //medimos la distancia para cada ejemplo
                {
                    suma = 0; 
                    for (int l = 0; l < prueba.get(0).size()-1; l++)
                    {
                        //suma += Hamming(entrenamiento.get(j).get(l), prueba.get(m).get(l));
                        suma += HammingBinario(entrenamiento.get(j).get(l), prueba.get(m).get(l));
                    }
                    
                    distancia.get(j).set(0,suma); 
                    distancia.get(j).set(1,numeroClaseE.get(j));
                    /*
                    //Verificamos las distancias
                    System.out.println(j+" "+distancia.get(j).get(0)+" "+
                                        distancia.get(j).get(1));
                    */
                }
                
                //ordenamos las distancias de menor a mayor
                
                distancia.forEach(Collections::sort);
                Collections.sort(distancia, (l1, l2) -> l1.get(1).compareTo(l2.get(1)));
           
                
                /*
                //Verificamos el orden
                 for (int j = 0; j < distancia.size(); j++)
                        System.out.println(j+" "+distancia.get(j).get(0)+" "+
                                        distancia.get(j).get(1));
           
                  System.out.println("");                 
                */
                
                    //Aqui la clase para los k mas cercanos son agregados a la lista clase 
                    for (int j = 0; j < k; j++)
                    {               
                            int tmp = getClase().get(m).get(distancia.get(j).get(0));
                            tmp++;
                            getClase().get(m).set(distancia.get(j).get(0),tmp);
                    }
                                                                                                  
                    int mayor = 0; //variable que nos da la cantidad de veces que se repite la clase predicha
                    int indice = 0; //varible que nos da la localizacion de la clase
                    int repeticion = 0; //variable que nos indicara si hay dos clases o 
                                  //mas que son las mas repetidas
                    //Aqui verificamos la clase que se repite mas
                    for (int j = 0; j < getClase().get(0).size(); j++) {
                        if ( mayor < getClase().get(m).get(j) )
                        {
                          mayor = getClase().get(m).get(j);
                          indice = j;
                        }                                
                    }
                
                
                    /*Aqui verificamos si hay dos clases con la misma cantidad,
                     si es asi, ponemos la clase con la menor distancia para la instancia
                    de prueba actual*/
                    
                    for (int j = 0; j < getClase().get(0).size(); j++) {
                        if (mayor == getClase().get(m).get(j) && (repeticion != 2) )
                         repeticion++;
                        if (repeticion == 2)
                        {
                            indice = j;
                            break;
                        }
                    }
                    
                    /*Imprimimos el numero de instancia de prueba con el numero de 
                    repeticiones para la clase mas reiterada*/
                    
                    //System.out.println(m+ " " +mayor + " " + indice);
            
                    
                    int tmp;
                    if (numeroClaseP.get(m) == indice)
                    {
                        tmp = getVerdadero().get(indice).get(0); tmp++;                        
                        getVerdadero().get(indice).set(0, tmp);
                        tmp = getMatrizConfusion().get(indice).get(indice); tmp++;
                        getMatrizConfusion().get(indice).set(indice, tmp);
                    }
                    else
                    {                
                        tmp = getVerdadero().get(indice).get(1); tmp++;                       
                        getVerdadero().get(indice).set(1, tmp);
                        tmp = getMatrizConfusion().get(indice).get(numeroClaseP.get(m)); tmp++;
                        getMatrizConfusion().get(indice).set(numeroClaseP.get(m),tmp);
                     }                                       
                    
        }
                 
                
        /*
        System.out.println("Matriz con las repeticiones para cada clase en cada instancia");
        for (int m = 0; m < clase.size(); m++)
        {
                        System.out.print(m+" ");
                        for (int j = 0; j < tamanio; j++) {
                            System.out.print( clase.get(m).get(j)+ " ");
                        }
                        System.out.println("");
        }
        */        
        /*        
        System.out.println("verdaderos positivos y falsos positivos por clase ");
        for (int m = 0; m < getVerdadero().size(); m++)
        {
                        System.out.print(m+" ");
                        for (int j = 0; j < 2; j++) {
                            System.out.print(getVerdadero().get(m).get(j)+ " ");
                        }
                        System.out.println("");
        }
        */
        /*
        System.out.println("Matriz de confusion"); int instancias = 0;
        for (int m = 0; m < getMatrizConfusion().size(); m++)
        {
                System.out.print(m+" ");
                for (int j = 0; j < getMatrizConfusion().get(0).size(); j++) {
                        System.out.print(getMatrizConfusion().get(m).get(j)+ " ");
                        instancias += getMatrizConfusion().get(m).get(j);
                }
                System.out.println("");
        }
         System.out.println("Instancias: " + instancias);
        */
        
       
        int vp, fp, vn, fn, sum, sum2;  
        for (int i = 0; i< tamanio; i++)
       {
            getResultados().add(new ArrayList<>());
           System.out.println(dataset.getTipoAtributos().get(tamanio2-1).get(i));
           
           vp = verdadero.get(i).get(0);
           fp = verdadero.get(i).get(1);
           sum = 0; sum2 = 0;
           for (int j = 0; j < tamanio; j++) {
               if (i != j)
               {
                   sum += verdadero.get(j).get(0);
                   sum2 += verdadero.get(j).get(1);
               }
           }           
           vn = sum; fn = sum2;
           
           evaluacion.setVp(vp); evaluacion.setFp(fp);
           evaluacion.setVn(vn); evaluacion.setFn(fn);
            getResultados().get(i).add(evaluacion.tasaVP());
            getResultados().get(i).add(evaluacion.tasaFP());
            getResultados().get(i).add(evaluacion.accuracy());
            getResultados().get(i).add(evaluacion.recall());
            getResultados().get(i).add(evaluacion.precision());
            getResultados().get(i).add(evaluacion.fMeasure());
           /*
           System.out.print("TasaVP: " + getResultados().get(i).get(0)+" ");
           System.out.print("TasaFP: " + getResultados().get(i).get(1) +" ");
           System.out.print("accuracy: " + getResultados().get(i).get(2)+"\n");
           System.out.print("Recall: " + getResultados().get(i).get(3)+" ");
           System.out.print("Precision: " + getResultados().get(i).get(4)+" ");
           System.out.print("FMeasure: " + getResultados().get(i).get(5)+"\n");
           */
       }
    }
    
    public void algoritmo(int num, int k, Dataset dataset)
    {                               
        
        ArrayList<ArrayList<ArrayList<Double>>> nEvaluacion = new ArrayList<>();        
        CrossValidation cv = new CrossValidation(); cv.division(dataset, num);                 
        Evaluacion evaluacion = new Evaluacion();                                      
        int suma;  
        System.out.println("Prediccion por knn ");   
        
        //tamanio es la cantidad de clases que tiene el dataset
        int tamanio = dataset.getTipoAtributos().get(dataset.getTipoAtributos().size()-1).size();
        //tamanio2 es la cantidad de atributos del dataset
        int tamanio2 = dataset.getTipoAtributos().size();
        //inicializamos la matriz de confusion
        for (int i = 0; i < dataset.getTipoAtributos().get(dataset.getAtributos().size()-1).size(); i++) {
            
            getMatrizConfusion().add(new ArrayList<>());
            //System.out.print(i+" ");            
            for (int j = 0; j < dataset.getTipoAtributos().get(dataset.getAtributos().size()-1).size(); j++) {
                getMatrizConfusion().get(i).add(0);
                //System.out.print(matrizConfusion.get(i).get(j) + " ");
            }
            //System.out.println("");
        }
        
        prueba = cv.getPrueba();
        
        for (int i = 0; i < num; i++) //hacemos dataset por dataset 
        {
            for (int m = 0; m < num; m++) {
                for (int j = 0; j < cv.getEntrenamiento().get(m).size(); j++) {
                    if ( (num-i-1) != (m) )
                    {
                        entrenamiento.add(cv.getEntrenamiento().get(m).get(j));
                    }
                    else if (i != 0)
                    {
                        prueba = cv.getEntrenamiento().get(m);
                    }
                }                
            }
            
            
            //inicializamos la lista de listas distancia
            for (int m = 0; m < entrenamiento.size(); m++)
            {
                distancia.add(new ArrayList<>());
                //System.out.print(i+" ");
                for (int j = 0; j < 2;j++)
                {
                    distancia.get(m).add(0); //inicializamos todos los valores en 0
                    //System.out.print(distancia.get(i).get(j) + " ");
                }
                //System.out.println("");
            }
            //inicializamos la lista clase            
        
            for (int m = 0; m < prueba.size();m++)
                {
                    getClase().add(new ArrayList<>());
                    //System.out.print(i+" ");
                    for (int j = 0; j < tamanio; j++) {
                        getClase().get(m).add(0);//inicializamos todos los valores en 0
                        //System.out.print(clase.get(i).get(j)+ " ");
                    }       
                    //System.out.println("");
                }
            //inicializamos la lista verdadero
               
            for (int m = 0; m < tamanio;m++)
                {
                    verdadero.add(new ArrayList<>());
                    //System.out.print(i+" ");
                    for (int j = 0; j < 2; j++) {
                        verdadero.get(m).add(0);//inicializamos todos los valores en 0
                        //System.out.print(verdadero.get(i).get(j)+ " ");
                    }       
                    //System.out.println("");
                }       
                        
            //reemplazamos las palabras con los numeros correspondientes a la clase
            
            for (int m = 0; m < entrenamiento.size(); m++) {
                    //System.out.print(m+" ");
                    for (int j = 0; j < tamanio; j++) {
                        if (entrenamiento.get(m).get(tamanio2-1).equals(dataset.getTipoAtributos().get(tamanio2-1).get(j)) )                        
                        {
                            numeroClaseE.add(j);
                            break;
                        }
                       
                    }
                    //System.out.println(numeroClaseP.get(m));                            
            }
            
            for (int m = 0; m < prueba.size(); m++) {
                    //System.out.print(m+" ");
                    for (int j = 0; j < tamanio; j++) {
                        if (prueba.get(m).get(tamanio2-1).equals(dataset.getTipoAtributos().get(tamanio2-1).get(j)) )                        
                        {
                            numeroClaseP.add(j);
                            break;
                        }
                       
                    }
                    //System.out.println(numeroClaseE.get(m));                            
            }
            //System.out.println(i); 
            for (int m = 0; m < prueba.size(); m++) { //hacemos caso por caso
                
                //System.out.println(m); 
                for (int j = 0; j < entrenamiento.size(); j++) //medimos la distancia para cada ejemplo
                {
                    suma = 0; 
                    for (int l = 0; l < prueba.get(0).size()-1; l++)
                    {
                        //suma += Hamming(entrenamiento.get(j).get(l), prueba.get(m).get(l));
                        suma += HammingBinario(entrenamiento.get(j).get(l), prueba.get(m).get(l));
                    }
                    
                    distancia.get(j).set(0,suma); 
                    distancia.get(j).set(1,numeroClaseE.get(j));
                    /*
                    //Verificamos las distancias
                    System.out.println(j+" "+distancia.get(j).get(0)+" "+
                                        distancia.get(j).get(1));
                    */
                }
                
                //ordenamos las distancias de menor a mayor
                
                distancia.forEach(Collections::sort);
                Collections.sort(distancia, (l1, l2) -> l1.get(1).compareTo(l2.get(1)));
           
                
                /*
                //Verificamos el orden
                 for (int j = 0; j < distancia.size(); j++)
                        System.out.println(j+" "+distancia.get(j).get(0)+" "+
                                        distancia.get(j).get(1));
           
                  System.out.println("");                 
                */
                
                    //Aqui la clase para los k mas cercanos son agregados a la lista clase 
                    for (int j = 0; j < k; j++)
                    {               
                            int tmp = getClase().get(m).get(distancia.get(j).get(0));
                            tmp++;
                            getClase().get(m).set(distancia.get(j).get(0),tmp);
                    }
                                                                                                  
                    int mayor = 0; //variable que nos da la cantidad de veces que se repite la clase predicha
                    int indice = 0; //varible que nos da la localizacion de la clase
                    int repeticion = 0; //variable que nos indicara si hay dos clases o 
                                  //mas que son las mas repetidas
                    //Aqui verificamos la clase que se repite mas
                    for (int j = 0; j < getClase().get(0).size(); j++) {
                        if ( mayor < getClase().get(m).get(j) )
                        {
                          mayor = getClase().get(m).get(j);
                          indice = j;
                        }                                
                    }
                
                
                    /*Aqui verificamos si hay dos clases con la misma cantidad,
                     si es asi, ponemos la clase con la menor distancia para la instancia
                    de prueba actual*/
                    
                    for (int j = 0; j < getClase().get(0).size(); j++) {
                        if (mayor == getClase().get(m).get(j) && (repeticion != 2) )
                         repeticion++;
                        if (repeticion == 2)
                        {
                            indice = j;
                            break;
                        }
                    }
                    
                    /*Imprimimos el numero de instancia de prueba con el numero de 
                    repeticiones para la clase mas reiterada*/
                    
                    //System.out.println(m+ " " +mayor + " " + indice);
            
                    
                    int tmp;
                    if (numeroClaseP.get(m) == indice)
                    {
                        tmp = getVerdadero().get(indice).get(0); tmp++;                        
                        getVerdadero().get(indice).set(0, tmp);
                        tmp = getMatrizConfusion().get(indice).get(indice); tmp++;
                        getMatrizConfusion().get(indice).set(indice, tmp);
                    }
                    else
                    {                
                        tmp = getVerdadero().get(indice).get(1); tmp++;                       
                        getVerdadero().get(indice).set(1, tmp);
                        tmp = getMatrizConfusion().get(indice).get(numeroClaseP.get(m)); tmp++;
                        getMatrizConfusion().get(indice).set(numeroClaseP.get(m),tmp);
                     }                                       
                    
            }
                 
                
                /*
                System.out.println("Matriz con las repeticiones para cada clase en cada instancia");
                for (int m = 0; m < clase.size(); m++)
                {
                        System.out.print(m+" ");
                        for (int j = 0; j < tamanio; j++) {
                            System.out.print( clase.get(m).get(j)+ " ");
                        }
                        System.out.println("");
                }
                */
                /*
                System.out.println("verdaderos positivos y falsos positivos por clase ");
                for (int m = 0; m < getVerdadero().size(); m++)
                {
                        System.out.print(m+" ");
                        for (int j = 0; j < 2; j++) {
                            System.out.print(getVerdadero().get(m).get(j)+ " ");
                        }
                        System.out.println("");
                }
                */
                double vp, fp, vn, fn; int sum, sum2;
                
                nEvaluacion.add(new ArrayList<>());
                for (int m = 0; m < tamanio; m++) {
                    nEvaluacion.get(i).add(new ArrayList<>());
                    vp = verdadero.get(m).get(0);
                    fp = verdadero.get(m).get(1);
                    sum = 0; sum2 =0;
                    for (int j = 0; j < tamanio; j++) {
                        if (m != j)
                        {
                            sum += verdadero.get(j).get(0);
                            sum2 += verdadero.get(j).get(1);
                        }
                    }
                    vn = sum; fn = sum2;
                    
                    evaluacion.setVp(vp); evaluacion.setFp(fp);
                    evaluacion.setVn(vn); evaluacion.setFn(fn);
                                        
                    nEvaluacion.get(i).get(m).add(evaluacion.tasaVP());
                    nEvaluacion.get(i).get(m).add(evaluacion.tasaFP());
                    nEvaluacion.get(i).get(m).add(evaluacion.accuracy());
                    nEvaluacion.get(i).get(m).add(evaluacion.recall());
                    nEvaluacion.get(i).get(m).add(evaluacion.precision());
                    nEvaluacion.get(i).get(m).add(evaluacion.fMeasure());
                    /*
                    //Verificacion de datos
                    System.out.println(dataset.getTipoAtributos().get(tamanio2-1).get(m));
                    System.out.print("TasaVP: " + (double)Math.round(nEvaluacion.get(i).get(m).get(0)*100d)/100d+" ");
                    System.out.print("TasaFP: " + (double)Math.round(nEvaluacion.get(i).get(m).get(1)*100d)/100d+" ");
                    System.out.print("accuracy: " + (double)Math.round(nEvaluacion.get(i).get(m).get(2)*100d)/100d+"\n");
                    System.out.print("Recall: " + (double)Math.round(nEvaluacion.get(i).get(m).get(3)*100d)/100d+" ");
                    System.out.print("Precision: " + (double)Math.round(nEvaluacion.get(i).get(m).get(4)*100d)/100d+" ");
                    System.out.print("FMeasure: " + (double)Math.round(nEvaluacion.get(i).get(m).get(5)*100d)/100d+"\n");
                    */
                }
                
                
                distancia.clear();
                getClase().clear();
                verdadero.clear();
                numeroClaseE.clear();
                numeroClaseP.clear();
                entrenamiento.clear();
        }
        
        /*
        int instancias = 0;
        System.out.println("Matriz de confusion");
        for (int m = 0; m < getMatrizConfusion().size(); m++)
        {
                System.out.print(m+" ");
                for (int j = 0; j < getMatrizConfusion().get(0).size(); j++) {
                        System.out.print(getMatrizConfusion().get(m).get(j)+ " "); 
                        instancias += getMatrizConfusion().get(m).get(j);
                }
                System.out.println("");
        }
         System.out.println("Instancias: " + instancias);
         */
        ArrayList<ArrayList<Double>> promedio = new ArrayList<>(); double aux = 0;         
        for (int i = 0; i < tamanio; i++) {
            promedio.add(new ArrayList<>());
            for (int j = 0; j < 6; j++) {
                promedio.get(i).add(0.0);
            }
            
        }
       
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < tamanio; j++) {
                for (int l = 0; l < 6; l++) {
                    aux = promedio.get(j).get(l);
                    aux += nEvaluacion.get(i).get(j).get(l);
                    promedio.get(j).set(l,aux);
                }
            }
        }
        for (int i = 0; i < tamanio; i++) {
            getResultados().add(new ArrayList<>());
            for (int j = 0; j < 6; j++) {
                aux = promedio.get(i).get(j);
                aux /= 10;
                promedio.get(i).set(j,aux);
            }
             getResultados().get(i).add(promedio.get(i).get(0));
             getResultados().get(i).add(promedio.get(i).get(1));
             getResultados().get(i).add(promedio.get(i).get(2));
             getResultados().get(i).add(promedio.get(i).get(3));
             getResultados().get(i).add(promedio.get(i).get(4));
             getResultados().get(i).add(promedio.get(i).get(5));
             /*
             System.out.println(dataset.getTipoAtributos().get(tamanio2-1).get(i));
             System.out.print("TasaVP: " + getResultados().get(i).get(0)+" ");
             System.out.print("TasaFP: " + getResultados().get(i).get(1)+" ");
             System.out.print("accuracy: " + getResultados().get(i).get(2)+"\n");
             System.out.print("Recall: " + getResultados().get(i).get(3)+" ");
             System.out.print("Precision: " + getResultados().get(i).get(4)+" ");
             System.out.print("FMeasure: " + getResultados().get(i).get(5)+"\n");
             */
        }
        
       
        
    }

   
    public ArrayList<ArrayList<Integer>> getVerdadero() {
        return verdadero;
    }

    /**
     * @return the clase
     */
    public ArrayList<ArrayList<Integer>> getClase() {
        return clase;
    }

    /**
     * @return the matrizConfusion
     */
    public ArrayList<ArrayList<Integer>> getMatrizConfusion() {
        return matrizConfusion;
    }

    /**
     * @return the resultados
     */
    public ArrayList<ArrayList<Double>> getResultados() {
        return resultados;
    }
}
