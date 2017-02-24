/*
 * To change this template, choose Tools | Templates
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
 * @author Daniele
 */

public class Registrazione extends HttpServlet {
    DBManager manager;
String nickname,nome,cognome,email,password;
String ctrl="";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
                PrintWriter out = response.getWriter();

        this.manager = (DBManager) super.getServletContext().getAttribute("dbmanager");
        
nickname=request.getParameter("nickname");
nome=request.getParameter("nome");
cognome=request.getParameter("cognome");
email=request.getParameter("email");
password=request.getParameter("password");
int num=0;

ResultSet rs=null;

JSONObject obj = new JSONObject();
  try {
String query1 = "SELECT NICKNAME FROM UTENTE WHERE NICKNAME=?";
            PreparedStatement st1 = manager.db.prepareStatement(query1);
            st1.setString(1, nickname);
           rs= st1.executeQuery();
          if (rs.next()){
num=1;     

          }else{num=2;}

        } catch (SQLException e) {
            e.printStackTrace();
        }
  
        try {

            String query = "INSERT INTO UTENTE(NICKNAME,NOME,COGNOME,EMAIL,PASSWORD) VALUES(?,?,?,?,?)";
            PreparedStatement st = manager.db.prepareStatement(query);
            st.setString(1, nickname);
            st.setString(2, nome);
            st.setString(3, cognome);
            st.setString(4, email);
            st.setString(5, password);
            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } 
        /*

        * 
*/if (num!=1){
  obj.put("registration", "true");}else{
    obj.put("registration", "false");
}
  out.print(obj);
}

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
