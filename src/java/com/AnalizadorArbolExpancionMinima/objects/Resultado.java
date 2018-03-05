/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AnalizadorArbolExpancionMinima.objects;

import java.util.List;

/**
 *
 * @author NESPINOZA
 */
public class Resultado {
    
    private String respuesta;
    private String  arbolFin;
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getArbolFin() {
        return arbolFin;
    }

    public void setArbolFin(String arbolFin) {
        this.arbolFin = arbolFin;
    }
    
    
    
}
