/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AnalizadorArbolExpancionMinima.beans;

import com.AnalizadorArbolExpancionMinima.Kruskal.Kruskal;
import com.AnalizadorArbolExpancionMinima.objects.Conexiones;
import com.AnalizadorArbolExpancionMinima.objects.Resultado;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author NESPINOZA
 */
public class AnalizadroArbolExpancionMinimaBean implements Serializable {

    /**
     * Creates a new instance of AnalizadroArbolExpancionMinimaBean
     */
    private int nodos;
    private int aristas;
    private List<Conexiones> arbolExpancion = new LinkedList<>();
    private int sizeArbol;
    private String verConexiones;
    private int nodo1;
    private int nodo2;
    private int pesoArista;
    private String file;
    private Timer timer;
    private boolean genera;
    private String file2;
    private String graf1 = "graf.txt";
    private String graf2 = "graf2.txt";
    private int minimoPesoArbol;

    public AnalizadroArbolExpancionMinimaBean() {
        this.nodo1 = 0;
        this.nodo2 = 0;
        this.pesoArista = 0;
        this.verConexiones = "n";
        this.sizeArbol = 0;
        this.nodos = 0;
        this.aristas = 0;
        this.arbolExpancion = new LinkedList<>();
        int numero = (int) (Math.random() * 1000) + 1;
        int numero2 = (int) (Math.random() * 1000) + 2;
        file = "graf" + numero + ".svg";
        file2 = "graf" + numero2 + ".svg";
        genera = false;
        this.minimoPesoArbol = 0;

    }

    public void nuevoArbol() {
        if (nodos > 1) {
            if (this.nodos != 0 && this.aristas != 0) {
                this.sizeArbol = this.aristas;
                if (sizeArbol != 0) {
                    verConexiones = "s";
                }
            }
        } else {
            FacesMessage msg = new FacesMessage("debe ingresar 2 o mas nodos", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        System.out.println("nodos: " + nodos + " - aristas: " + aristas + " - sizeArbol: " + sizeArbol);
    }

    public void dot() {

        Conexiones con = new Conexiones();
        con.setNodo1(nodo1);
        con.setNodo2(nodo2);
        con.setPesoArista(pesoArista);
        arbolExpancion.add(con);
        nodo1 = 0;
        nodo2 = 0;
        pesoArista = 0;
        System.out.println("arbolExpancion size: " + arbolExpancion.size());
        sizeArbol = sizeArbol - 1;
        System.out.println("sizeArbol : " + sizeArbol);

    }

    public void analizaArbol() {

        String texto = "";
        texto = "graph { \n";
        for (Conexiones con : arbolExpancion) {
            texto = texto + " " + con.getNodo1() + " -- " + con.getNodo2() + " [label=\"" + con.getPesoArista() + "\"]; \n";
        }
        texto = texto + "}";
        System.out.println(texto);
        creaImagenGrafo(texto, file, graf1);
        RequestContext contextoRequest = RequestContext.getCurrentInstance();
        contextoRequest.update("segundo:diagrama");
        genera = true;
        System.out.println(genera);

        Kruskal kruskal = new Kruskal(nodos, aristas);

        Resultado res = kruskal.fun(arbolExpancion);
        if (res.getRespuesta().equals("ok")) {
            creaImagenGrafo(res.getArbolFin(), file2, graf2);
            minimoPesoArbol = res.getTotal();
            FacesMessage msg = new FacesMessage("OK !", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else if (res.getRespuesta().equals("error")) {
            FacesMessage msg = new FacesMessage("No existe MST valido para el grafo ingresado, el grafo debe ser conexo.", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void creaImagenGrafo(String texto, String file, String graf) {
        int n = 0;
        //en la siguiente linea debe pintar una direccion en su computadora
        String ruta = "C:\\Users\\NESPINOZA\\Documents\\NetBeansProjects\\AnalizadorArbolExpancionMinima\\web\\" + graf;
        File archivo = new File(ruta);
        BufferedWriter bw;

        try {
            if (archivo.exists()) {
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write(texto);
            } else {
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write(texto);
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("No se puede crear el archivo " + e);
        }

        try {

            //aca va la direccion donde esta instalado su libreria Graphviz
            String doPath = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\sfdp.exe";
            //en la siguiente linea debe pintar una direccion en su computadora
            String fileInputPath = "C:\\Users\\NESPINOZA\\Documents\\NetBeansProjects\\AnalizadorArbolExpancionMinima\\web\\" + graf;
            String fileOuputPath = "C:\\Users\\NESPINOZA\\Documents\\NetBeansProjects\\AnalizadorArbolExpancionMinima\\web\\" + file;
            String tParam = "-Tsvg";
            String tOParam = "-o";
            String[] cmd = new String[5];
            cmd[0] = doPath;
            cmd[1] = tParam;
            cmd[2] = fileInputPath;
            cmd[3] = tOParam;
            cmd[4] = fileOuputPath;

            Process p = Runtime.getRuntime().exec(cmd);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getNodo1() {
        return nodo1;
    }

    public void setNodo1(int nodo1) {
        this.nodo1 = nodo1;
    }

    public int getNodo2() {
        return nodo2;
    }

    public void setNodo2(int nodo2) {
        this.nodo2 = nodo2;
    }

    public int getPesoArista() {
        return pesoArista;
    }

    public void setPesoArista(int pesoArista) {
        this.pesoArista = pesoArista;
    }

    public int getSizeArbol() {
        return sizeArbol;
    }

    public void setSizeArbol(int sizeArbol) {
        this.sizeArbol = sizeArbol;
    }

    public int getNodos() {
        return nodos;
    }

    public void setNodos(int nodos) {
        this.nodos = nodos;
    }

    public int getAristas() {
        return aristas;
    }

    public void setAristas(int aristas) {
        this.aristas = aristas;
    }

    public List<Conexiones> getArbolExpancion() {
        return arbolExpancion;
    }

    public void setArbolExpancion(List<Conexiones> arbolExpancion) {
        this.arbolExpancion = arbolExpancion;
    }

    public String getVerConexiones() {
        return verConexiones;
    }

    public void setVerConexiones(String verConexiones) {
        this.verConexiones = verConexiones;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public boolean isGenera() {
        return genera;
    }

    public void setGenera(boolean genera) {
        this.genera = genera;
    }

    public String getFile2() {
        return file2;
    }

    public void setFile2(String file2) {
        this.file2 = file2;
    }

    public String getGraf1() {
        return graf1;
    }

    public void setGraf1(String graf1) {
        this.graf1 = graf1;
    }

    public String getGraf2() {
        return graf2;
    }

    public void setGraf2(String graf2) {
        this.graf2 = graf2;
    }

    public int getMinimoPesoArbol() {
        return minimoPesoArbol;
    }

    public void setMinimoPesoArbol(int minimoPesoArbol) {
        this.minimoPesoArbol = minimoPesoArbol;
    }

}
