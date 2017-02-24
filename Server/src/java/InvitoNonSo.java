/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author Davide
 */
public class InvitoNonSo extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     
    DBManager manager;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idutente,idfesta;
        response.setContentType("json/application");
        PrintWriter out = response.getWriter();
         idutente=Integer.parseInt(request.getParameter("idutente"));
      idfesta=Integer.parseInt(request.getParameter("idfesta"));
      JSONObject obj=new JSONObject();
      ResultSet rs;
    try {

            String query = "SELECT ID FROM UTENTEFESTA WHERE IDUTENTE=? AND IDFESTA=?";
            PreparedStatement st = manager.db.prepareStatement(query);
            st.setInt(1, idutente);
            st.setInt(2, idfesta);
            rs=st.executeQuery();
      
              if (rs.next()){
      
try{
            String query2 = "INSERT INTO INVITO(IDUTENTE,IDFESTA,ACCETTATO) VALUES(?,?,?)";
            PreparedStatement st2 = DBManager.db.prepareStatement(query2);
            st2.setInt(1, idutente);
            st2.setInt(2, idfesta);
            st2.setInt(3, 1);
            st2.executeUpdate();
           }catch(Exception e){
                e.printStackTrace();
                
            } 
            try {

            String query4 = "DELETE FROM UTENTEFESTA WHERE IDFESTA=? AND IDUTENTE=?";
            PreparedStatement st4 = DBManager.db.prepareStatement(query4);
            st4.setInt(1, idfesta);
            st4.setInt(2, idutente);
            st4.executeUpdate();
            st4.close();
obj.put("invitononso", "true");
        } catch (SQLException e) {
obj.put("invitononso", "false");
        }
      }else{
                  try{
              String query1 = "UPDATE INVITO SET ACCETTATO=? WHERE IDFESTA=? AND IDUTENTE=?";
              
            PreparedStatement st1 = DBManager.db.prepareStatement(query1);
            st1.setInt(1, 1);
            st1.setInt(2, idfesta);
            st1.setInt(3, idutente);
            st1.executeUpdate();
            st1.close();
            obj.put("invitononso", "true");
        } catch (SQLException e) {
          obj.put("invitononso", "false");
        }    
               }} catch (SQLException e) {}
    out.print(obj);
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