/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rajar
 */
@WebServlet(name = "Parental_login", urlPatterns = {"/Parental_login"})
public class Parental_login extends HttpServlet {

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
        
        String parentEmail = request.getParameter("pemail");
        String parentPassword = request.getParameter("ppwd");
        String childEmail = request.getParameter("cemail");
        String childPassword = request.getParameter("cpwd");

        boolean isAuthenticated = false;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/22r21a05d1", "root", "root");
            String sql = "SELECT * FROM pregistration WHERE (pemail = ? AND ppwd = ?) OR (cemail = ? AND cpwd = ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, parentEmail);
            pst.setString(2, parentPassword);
            pst.setString(3, childEmail);
            pst.setString(4, childPassword);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                isAuthenticated = true;
            }

            if (isAuthenticated) {
                response.sendRedirect("Dashboard.html");
            } 
            else {
                response.sendRedirect("Parental_login.html?error=Invalid+credentials");
            }
            conn.close();
        } catch (Exception e) {
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
        return "Parental_login Servlet";
    }// </editor-fold>

}