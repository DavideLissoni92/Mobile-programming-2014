/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Davide
 */
public class NotificheInvito extends HttpServlet {

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
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
       int idutente=Integer.parseInt(request.getParameter("idutente"));
     JSONArray jsonarr = new JSONArray();
    try {
ResultSet rs;
            String query = "SELECT IDFESTA FROM UTENTE,INVITO WHERE IDUTENTE=UTENTE.ID AND DATAINVITO>=ULTIMOLOGIN AND IDUTENTE=?";
            PreparedStatement st = DBManager.db.prepareStatement(query);
            st.setInt(1,idutente);
            rs = st.executeQuery();
            
            while(rs.next()){
            
            try{
                ResultSet rs1;
                String query1 = "SELECT NOME FROM FESTA WHERE ID=?";
            PreparedStatement st1 = DBManager.db.prepareStatement(query1);
            st1.setInt(1,rs.getInt(1));
            rs1 = st1.executeQuery();
 if(rs1.next()){
 
            JSONObject obj= new JSONObject();
obj.put("nomefesta", rs1.getString(1));
jsonarr.add(obj);
 
 
 
 }
            }catch(Exception e){}
            
            
            
            }
       
            
            
           
    }catch(Exception e){}
    
    try{
                String query2 = "UPDATE UTENTE SET ULTIMOLOGIN=CURRENT_TIMESTAMP WHERE ID=?";
            PreparedStatement st2 = DBManager.db.prepareStatement(query2);
            st2.setInt(1,idutente);
            st2.executeUpdate();
            }catch(Exception e){}
    out.print(jsonarr);
    
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