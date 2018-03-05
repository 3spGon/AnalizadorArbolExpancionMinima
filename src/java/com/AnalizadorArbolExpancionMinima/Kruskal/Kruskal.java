/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AnalizadorArbolExpancionMinima.Kruskal;

import com.AnalizadorArbolExpancionMinima.objects.Conexiones;
import com.AnalizadorArbolExpancionMinima.objects.Resultado;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Kruskal {

    private static String txt;
    private int Total;
    private static List<Conexiones> conexiones;

    public Kruskal(int V, int E) {
        this.V = V;
        this.E = E;

    }

    public void funcion(List<Conexiones> arbolExpancion) {
        for (int i = 0; i < E; ++i) {
            Conexiones con = arbolExpancion.get(i);
//                System.out.println("empiezo el for");
            arista[i] = new Edge();
            arista[i].origen = con.getNodo1();
            arista[i].destino = con.getNodo2();
            arista[i].peso = con.getPesoArista();
            //arista[ i ] = new Arista( sc.nextInt() , );
        }

    }

    public Resultado fun(List<Conexiones> arbolExpancion) {
        conexiones = new LinkedList<>();
        for (Conexiones cone : arbolExpancion) {
            conexiones.add(cone);
        }

        for (int i = 0; i < E; ++i) {
            Conexiones con = arbolExpancion.get(i);
//                System.out.println("empiezo el for");
            arista[i] = new Edge();
            arista[i].origen = con.getNodo1();
            arista[i].destino = con.getNodo2();
            arista[i].peso = con.getPesoArista();
            //arista[ i ] = new Arista( sc.nextInt() , );
        }
        Resultado krus = KruskalMST();
        return krus;
    }

    static final int MAX = 1005;  //maximo numero de vértices

    ///UNION-FIND
    static int padre[] = new int[MAX];  //Este arreglo contiene el padre del i-esimo nodo

    //Método de inicialización
    static void MakeSet(int n) {
        for (int i = 1; i <= n; ++i) {
            padre[i] = i;
        }
    }

    //Método para encontrar la raiz del vértice actual X
    static int Find(int x) {
        return (x == padre[x]) ? x : (padre[x] = Find(padre[x]));
    }

    //Método para unir 2 componentes conexas
    static void Union(int x, int y) {
        padre[Find(x)] = Find(y);
    }

    //Método que me determina si 2 vértices estan o no en la misma componente conexa
    static boolean sameComponent(int x, int y) {
        if (Find(x) == Find(y)) {
            return true;
        }
        return false;
    }
    ///FIN UNION-FIND

    static int V, E;      //numero de vertices y aristas
    //Estructura arista( edge )

    static class Edge implements Comparator<Edge> {

        int origen;     //Vértice origen
        int destino;    //Vértice destino
        int peso;       //Peso entre el vértice origen y destino

        Edge() {
        }
        //Comparador por peso, me servira al momento de ordenar lo realizara en orden ascendente
        //Ordenar de forma descendente para obtener el arbol de expansion maxima

        @Override
        public int compare(Edge e1, Edge e2) {
            //return e2.peso - e1.peso; //Arbol de expansion maxima
            return e1.peso - e2.peso;   //Arbol de expansion minima
        }
    };

    static Edge arista[] = new Edge[MAX];      //Arreglo de aristas para el uso en kruskal
    static Edge MST[] = new Edge[MAX];     //Arreglo de aristas del MST encontrado

    static Resultado KruskalMST() {
        Resultado respuesta = new Resultado();
        int origen, destino, peso;
        int total = 0;          //Peso total del MST
        int numAristas = 0;     //Numero de Aristas del MST

        MakeSet(V);           //Inicializamos cada componente
        Arrays.sort(arista, 0, E, new Edge());    //Ordenamos las aristas por su comparador

        for (int i = 0; i < E; ++i) {     //Recorremos las aristas ya ordenadas por peso
            origen = arista[i].origen;    //Vértice origen de la arista actual
            destino = arista[i].destino;  //Vértice destino de la arista actual
            peso = arista[i].peso;        //Peso de la arista actual

            //Verificamos si estan o no en la misma componente conexa
            if (!sameComponent(origen, destino)) {  //Evito ciclos
                total += peso;              //Incremento el peso total del MST
                MST[numAristas++] = arista[i];  //Agrego al MST la arista actual
                Union(origen, destino);  //Union de ambas componentes en una sola
            }
        }

        //Si el MST encontrado no posee todos los vértices mostramos mensaje de error
        //Para saber si contiene o no todos los vértices basta con que el numero
        //de aristas sea igual al numero de vertices - 1
        if (V - 1 != numAristas) {
            System.out.println("No existe MST valido para el grafo ingresado, el grafo debe ser conexo.");
            respuesta.setRespuesta("error");
            return respuesta;
        } else {
            System.out.println("-----El MST encontrado contiene las siguientes aristas-----");
            txt = "graph { \n";
            String nuevoArbol = "graph { ";

            for (Conexiones con : conexiones) {
                int n=0;
                for (int i = 0; i < numAristas; ++i) {
                    System.out.printf("( %d , %d ) : %d\n", MST[i].origen, MST[i].destino, MST[i].peso); //( vertice u , vertice v ) : peso
                    if (con.getNodo1() == MST[i].origen && con.getNodo2() == MST[i].destino && con.getPesoArista() == MST[i].peso) {
                        nuevoArbol = nuevoArbol + " " + con.getNodo1() + " -- " + con.getNodo2() + " [label=\"" + con.getPesoArista() + "\", color=\"red\"]; \n";
                        n++;
                    }
                }
                if(n==0){
                nuevoArbol = nuevoArbol + " " + con.getNodo1() + " -- " + con.getNodo2() + " [label=\"" + con.getPesoArista() + "\"]; \n";
                }
            }
            nuevoArbol = nuevoArbol + "}";

            respuesta.setArbolFin(nuevoArbol);
            respuesta.setTotal(total);
            System.out.printf("El costo minimo de todas las aristas del MST es : %d\n", total);
            respuesta.setRespuesta("ok");
        }
        return respuesta;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

}
