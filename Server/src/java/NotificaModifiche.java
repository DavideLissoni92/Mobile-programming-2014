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
public class NotificaModifiche extends HttpServlet {

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
int idfesta=Integer.parseInt(request.getParameter("idfesta"));       
     JSONArray jsonarr = new JSONArray();
    try {
ResultSet rs;
            String query = "SELECT UTENTE.ID FROM UTENTEFESTA,FESTA,UTENTE WHERE IDUTENTE=UTENTE.ID AND FESTA.ID=IDFESTA AND IDUTENTE=? AND IDFESTA=? AND ULTIMAMODIFICA>=ULTIMOLOGIN";
            PreparedStatement st = DBManager.db.prepareStatement(query);
            st.setInt(1,idutente);
            st.setInt(2,idfesta);
            
            rs = st.executeQuery();
    while(rs.next()){
    JSONObject obj= new JSONObject();
    obj.put("notificamodificaidutente", new Integer(rs.getInt(query)));
    jsonarr.add(obj);
    }
    
    
    
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