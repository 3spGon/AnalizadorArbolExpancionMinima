/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AnalizadorArbolExpancionMinima.servlets;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author NESPINOZA
 */
public class ImagenServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("image/svg");
        response.setHeader("cache-control", "no-cache"); 

        try {

            //obteniendo el archivo d eimagen.
            String file = request.getParameter("file");
            System.out.println(file + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            //en la siguiente linea debe pintar una direccion en su computadora
            BufferedInputStream in = new BufferedInputStream(new FileInputStream("C:\\Users\\NESPINOZA\\Documents\\" + file));
            System.out.println(in + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            // obteniendo el contenido d ela imagen.
            byte[] bytes = new byte[in.available()];

            in.read(bytes);
            in.close();

            // escribiendo el contenido d ela imagen  a la respuesta.
            response.getOutputStream().write(bytes);

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
