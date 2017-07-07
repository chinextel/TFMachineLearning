/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabajofinal;

import java.util.ArrayList;


public class Dataset {
    private ArrayList<String> atributos;
    private ArrayList<ArrayList<String>> tipoAtributos;
    private ArrayList<ArrayList<String>> instancias;
    
    public Dataset (){
         atributos = new ArrayList<>();
         instancias = new ArrayList<>();
         tipoAtributos = new ArrayList<>();
    }

    
    public ArrayList<String> getAtributos() {
        return atributos;
    }

    
    public void setAtributos(ArrayList<String> atributos) {
        this.atributos = atributos;
    }
    
    public ArrayList<ArrayList<String>> getTipoAtributos() {
        return tipoAtributos;
    }
 
    public void setTipoAtributos(ArrayList<ArrayList<String>> tipoAtributos) {
        this.tipoAtributos = tipoAtributos;
    }
    
    public ArrayList<ArrayList<String>> getInstancias() {
        return instancias;
    }

    
    public void setInstancias(ArrayList<ArrayList<String>> instancias) {
        this.instancias = instancias;
    }
}
