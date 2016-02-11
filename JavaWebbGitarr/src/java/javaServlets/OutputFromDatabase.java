/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaServlets;

import GitarrDBModule.Employee;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author johanwallin
 */
@WebServlet(name = "TestDBConnection", urlPatterns = {"/TestDBConnection"})
public class OutputFromDatabase extends HttpServlet {
    /* Om ni behöver lösenordet för databasen så fråga Johan eller Simon. */
    /* Skapar en PersistenceUnit EntityManagerFactory för att kunna utnyttna databasens entitetsklasser. */
    @PersistenceUnit
    EntityManagerFactory emf;
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
        
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Database Connection Example</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OutputFromDatabase at " + request.getContextPath() + "</h1>");
            
            /* 
            Skapar en Employee som använder sig av entitetsklassen Employee och gör en type cast på Employee för att få ett resultat med Employee klassens struktur. 
            Sätter sql frågan till .findAll som är en fördefinerad query som skapats av enitetsklassen Employee (Kan göra egna sql querys i entitetsklasserna om det behövs).
            Använder först getResultList() för att få ut innehållet och sen ta ut första resultatet i listan med .get(0).
            */
            Employee employee = (Employee)emf.createEntityManager().createNamedQuery("Employee.findAll").getResultList().get(0);
            /* Skriver ut employees namn. Se entitetsklassen Employee.java för mer funktioner. */
            out.println("<h2> EMPLOYEE NR 1: " + employee.getEmployeeName() + "</h2>");
            /* För att se innehållet i en tabell i databasen kan du trycka på "Services" tabben och välja 
                anslutningen jdbc:mysql://213.21.86.215:1449/GITARR och gå in i DB GITARR och
                högerklicka på en tabell och sedan "view data".
                
            */
            
            out.println("</body>");
            out.println("</html>");
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
