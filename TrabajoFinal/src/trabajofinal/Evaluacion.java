/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabajofinal;


public class Evaluacion {
    private double vp;
    private double fp;
    private double vn;
    private double fn;
    
    public Evaluacion()  {
        vp = 0;
        fp = 0;
        vn = 0;
        fn = 0;
        
    }
    public double accuracy (){   
        if (vp != 0 || fp != 0)
            return (getVp() + getVn())/(getVp()+getFp()+getVn()+getFn());
        else
            return 0;
    }
    public double recall (){
        if (vp != 0 || fp != 0)
            return getVp()/(getVp()+getFn());
        else 
            return 0;
    }
    public double precision() {
        if (vp != 0 || fp != 0)
            return getVp()/(getVp()+getFp());
        else
            return 0;
    }
    public double fMeasure(){
        if (vp != 0 )
            return ( 2*(vp/(vp+fn))*(vp/(vp+fp)) )/( (vp/(vp+fn))+(vp/(vp+fp)) );            
        else
            return 0;
    }
    public double tasaVP (){
        if (vp != 0 )
            return vp/(vp+fn);
        else
            return 0;
    }
    public double tasaFP () {
        if (fp != 0 )
            return fp/(fp+vn);
        else
            return 0;
    }

    /**
     * @return the vp
     */
    public double getVp() {
        return vp;
    }

    /**
     * @param vp the vp to set
     */
    public void setVp(double vp) {
        this.vp = vp;
    }

    /**
     * @return the fp
     */
    public double getFp() {
        return fp;
    }

    /**
     * @param fp the fp to set
     */
    public void setFp(double fp) {
        this.fp = fp;
    }

    /**
     * @return the vn
     */
    public double getVn() {
        return vn;
    }

    /**
     * @param vn the vn to set
     */
    public void setVn(double vn) {
        this.vn = vn;
    }

    /**
     * @return the fn
     */
    public double getFn() {
        return fn;
    }

    /**
     * @param fn the fn to set
     */
    public void setFn(double fn) {
        this.fn = fn;
    }
}
