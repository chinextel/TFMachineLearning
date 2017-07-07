/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabajofinal;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Luis Miguel Fung
 */
public class LeerDataset {
    private Dataset dataset;
    public LeerDataset() {
        dataset = new Dataset();
    }
     public void cargarData(String ruta) throws FileNotFoundException{

        String nomArchivo = ruta;
        FileReader fr = new FileReader(nomArchivo);
        Scanner e = new Scanner( fr );      

        int estado = 1, num = 0, num_instancia = 0;
        String nom_dataset = "";

        ArrayList<ArrayList<String>> data = new ArrayList<>();
        ArrayList<String> nombre = new ArrayList<>();
        ArrayList<ArrayList<String>> atributos =new ArrayList<>();        

        while( e.hasNextLine() ){
            String linea = e.nextLine();
            linea = linea.trim(); 

            //Parte 1: Cubrir el nombre del dataset
            if( estado == 1 && linea.startsWith("@relation") ){
               String[] C = linea.split(" "); 
               nom_dataset = C[1];
               nom_dataset = nom_dataset.trim();
               //System.out.println("Dataset:["+nom_dataset+"]");
               estado = 2;
            }

            //Parte 2: Cubrir las variables aleatorias
            else if( estado == 2 && linea.startsWith("@attribute") ){
                String[] C = linea.split(" "); 
                //System.out.println("Length C: "+C.length);
                String variable = C[1];
                //System.out.println("C2"+C[2]);
                String[] M = C[2].split(",");
                //System.out.println("Variable:["+variable+"]");
                nombre.add(variable);
                atributos.add(new ArrayList<>());
                for( String x : M ){                   
                    x = x.replace("{", "").replace("}","");
                    atributos.get(num).add(x);
                    //System.out.println("M:["+x+"]");
                }
                num++;
                //estado = 3;
            }
            //Parte 3: Leer la data
            else if( estado == 2 && linea.startsWith("@data") ){
               estado = 3;
            }
            //Parte 4: Lectura de las instancias
            else if( estado == 3 ){
               String[] instancia =  linea.split(",");
               data.add(new ArrayList<>());
               //System.out.print("Inst["+num_instancia+"] = ");
               for( String x : instancia ){
                   data.get(num_instancia).add(x);
                   //System.out.print(x+" ");
               }
               //System.out.print("\n");
               num_instancia++;
            }
        }
               /*Verificacion de datos
               System.out.println(nombre.size());
               for (int i = 0; i < atributos.size(); i++)
               {
                    System.out.print(atributos.get(i).size()+ " ");
                    for (int j = 0; j < atributos.get(i).size(); j++)
                    {
                        System.out.print(atributos.get(i).get(j)+" ");
                    }
                    System.out.println("");
               }                            

               System.out.println(data.size());
               for (int i = 0; i < data.size(); i++)
               {                   
                   for (int j = 0; j < data.get(i).size(); j++)
                        System.out.print(data.get(i).get(j)+ " ");
                   System.out.println("");
               }
               */  
        dataset.setInstancias(data);
        dataset.setAtributos(nombre);
        dataset.setTipoAtributos(atributos);
        e.close();
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }
}
