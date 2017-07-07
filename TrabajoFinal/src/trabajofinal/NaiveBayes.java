/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabajofinal;

import java.util.ArrayList;
import java.util.HashMap;



/**
 *
 * @author Luis Miguel Fung
 */
public class NaiveBayes {
    private ArrayList<ArrayList<String>> entrenamiento;
    private ArrayList<ArrayList<String>> prueba;
    private ArrayList<Double> probClase;
    private ArrayList<ArrayList<ArrayList<Double>>> probAtributos;
    private HashMap<Integer,Double> prediccion;
    private ArrayList<ArrayList<Integer>> verdadero;
    private ArrayList<ArrayList<Integer>> matrizConfusion;
    private ArrayList<ArrayList<Double>> resultados;
    
    public NaiveBayes()
    {
        entrenamiento = new ArrayList<>();
        prueba = new ArrayList<>();
        probClase = new ArrayList<>();
        probAtributos = new ArrayList<>();
        prediccion = new HashMap<>();
        verdadero = new ArrayList<>();       
        matrizConfusion = new ArrayList<>();
        resultados = new ArrayList<>();
    }
    
    public double calculoProbClase(int numClase, int tamanio, int tamanio2, int hp,
                                    ArrayList<ArrayList<String>> instancias,
                                    ArrayList<String> tipoAtributo)
    {
        int suma = 0;  double total;
        for (int i = 0; i < instancias.size(); i++) {
                if (instancias.get(i).get(tamanio2-1).equals(tipoAtributo.get(numClase)))
                {
                    suma++;
                } 
            
        }
        total = ((double)suma+hp)/(instancias.size()+ hp*tamanio);
        
        
        return total;
    }
    public void calculoProb(int atributo, int tamanio, int tamanio2, int hp,
            ArrayList<ArrayList<String>> instancias, ArrayList<ArrayList<String>> tipoAtributos,
            ArrayList<ArrayList<ArrayList<Double>>> probAtributos)
            
    {    
        probAtributos.add(new ArrayList<>()); int suma, suma2 ;double total;
        
        for (int i = 0; i < tamanio; i++) {
            probAtributos.get(atributo).add(new ArrayList<>());
            for (int j = 0; j < tipoAtributos.get(atributo).size(); j++) {
                suma = 0; suma2 = 0;
                for (int k = 0; k < instancias.size(); k++) {
                    if (instancias.get(k).get(atributo).equals(tipoAtributos.get(atributo).get(j)) &&
                        instancias.get(k).get(tamanio2-1).equals(tipoAtributos.get(tamanio2-1).get(i)) ) {                                           
                        suma++;
                    }
                    if (instancias.get(k).get(tamanio2-1).equals(tipoAtributos.get(tamanio2-1).get(i)) ) {                    
                        suma2++;
                    }
                }
                total = ( ((double)suma/instancias.size())+hp )/
                        ( ((double)suma2/instancias.size())+hp*tipoAtributos.get(atributo).size() );
                probAtributos.get(atributo).get(i).add(total);
            }
        }
    }
    public int prediccionClase(HashMap<Integer,Double> prediccion, int tamanio)
    {
        double probMayor = 0.0; int mayor = 0;
        for (int i = 0; i < tamanio; i++) {
            if (prediccion.get(i) > probMayor)
            {
                probMayor = prediccion.get(i);
                mayor = i; 
            }
        }
        return mayor;
    }
    public void evaluaciones (ArrayList<ArrayList<ArrayList<Double>>> nEvaluacion,
                              int num, int tamanio, int tamanio2, Dataset dataset) 
    {
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
    public void algoritmo(int hp, int num,Dataset dataset)
    {
        CrossValidation cv = new CrossValidation();
        cv.division(dataset, num);
        ArrayList<ArrayList<ArrayList<Double>>> nEvaluacion = new ArrayList<>(); 
        Evaluacion evaluacion = new Evaluacion();
        System.out.println("Prediccion por Naive Bayes");
        
        //tamanio es la cantidad de clases que tiene el dataset
        int tamanio = dataset.getTipoAtributos().get(dataset.getTipoAtributos().size()-1).size();
        //tamanio2 es la cantidad de atributos del dataset
        int tamanio2 = dataset.getTipoAtributos().size();
        double probConjunta;
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
            
            for (int m = 0; m < tamanio; m++) {
                getProbClase().add(calculoProbClase(m, tamanio, tamanio2, hp, 
                        entrenamiento, dataset.getTipoAtributos().get(tamanio2-1) ));
                
            }
            
            /*
            //Verificacion de datos
            double s =0;
            for (int m = 0; m < probClase.size(); m++) {
                s+= probClase.get(m);
            }
            System.out.println(i+" " +"Suma de todas las prob.:" +  s);
            */
            
            for (int m = 0; m < tamanio2-1; m++) {               
                 calculoProb(m, tamanio, tamanio2, hp,
                         entrenamiento, dataset.getTipoAtributos(), getProbAtributos());                
            }
            /*
            //Verificacion de datos
            double s;
            for (int m = 0; m < tamanio2-1; m++) {
                for (int j = 0; j < tamanio; j++) {
                    s = 0;
                    for (int k = 0; k < dataset.getTipoAtributos().get(m).size(); k++) {
                        s += probAtributos.get(m).get(j).get(k);
                    }
                    System.out.println(m+": "+s);
                }
                System.out.println("");
            }
            */
            int predClase; int tmp;
            for (int m = 0; m < prueba.size(); m++) {
                for (int j = 0; j < tamanio; j++) {
                    probConjunta = 1;
                    for (int k = 0; k < tamanio2-1; k++) {
                        for (int l = 0; l < dataset.getTipoAtributos().get(k).size(); l++) {
                            if (prueba.get(m).get(k).equals(dataset.getTipoAtributos().get(k).get(l)) ){
                                probConjunta *= getProbAtributos().get(k).get(j).get(l);
                                break;
                            }
                        }
                    }
                    probConjunta *= getProbClase().get(j);
                    prediccion.put(j,probConjunta);
                }
                
                predClase = prediccionClase(prediccion, tamanio);
                for (int j = 0; j < tamanio; j++) {
                    if (prueba.get(m).get(tamanio2-1).equals(dataset.getTipoAtributos().get(tamanio2-1).get(j)))
                    {
                        if (predClase == j) {
                            tmp = verdadero.get(predClase).get(0); tmp++;
                            verdadero.get(predClase).set(0, tmp);
                            tmp = getMatrizConfusion().get(predClase).get(predClase); tmp++;
                            getMatrizConfusion().get(predClase).set(predClase,tmp);
                        } else {
                            tmp = verdadero.get(predClase).get(1); tmp++;
                            verdadero.get(predClase).set(1, tmp);
                            tmp = getMatrizConfusion().get(predClase).get(j); tmp++;
                            getMatrizConfusion().get(predClase).set(j,tmp);
                        }                            
                        break;
                    }
                }                
                
                prediccion.clear();
            }           
            /*
            System.out.println("verdaderos positivos y falsos positivos por clase ");
                for (int m = 0; m < verdadero.size(); m++)
                {
                        System.out.print(m+" ");
                        for (int j = 0; j < 2; j++) {
                            System.out.print(verdadero.get(m).get(j)+ " ");
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
                    sum = 0; sum2 = 0;
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
            
            getProbClase().clear();
            getProbAtributos().clear();
            verdadero.clear();
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
        evaluaciones(nEvaluacion, num, tamanio, tamanio2, dataset);
    }

    /**
     * @return the probClase
     */
    public ArrayList<Double> getProbClase() {
        return probClase;
    }

    /**
     * @return the probAtributos
     */
    public ArrayList<ArrayList<ArrayList<Double>>> getProbAtributos() {
        return probAtributos;
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
