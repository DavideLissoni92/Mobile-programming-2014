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
import org.json.simple.JSONObject;

/**
 *
 * @author Davide
 */
public class ModificaUtente extends HttpServlet {

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
        String password,nome,cognome,nickname,email,ruolo,descrizioneruolo;
        String controllo;
        JSONObject obj=new JSONObject();
        int idutente;
       controllo=request.getParameter("vecchiapass");
       password=request.getParameter("password");
       nome=request.getParameter("nome");
       cognome=request.getParameter("cognome");
       nickname=request.getParameter("nickname");
       email=request.getParameter("email");
       idutente=Integer.parseInt(request.getParameter("idutente"));
       
       try{
            ResultSet rs1;     
           String query1="SELECT NOME "
                   + "FROM UTENTE "
                   + "WHERE ID=? AND PASSWORD=?";
           PreparedStatement st1 = DBManager.db.prepareStatement(query1);
            st1.setInt(1,idutente);
            st1.setString(2,controllo);
       rs1=st1.executeQuery();
       
       if(rs1.next()){
           try{
            String query = "UPDATE UTENTE SET NICKNAME=?,NOME=?,COGNOME=?,EMAIL=?,PASSWORD=? WHERE ID=?";
                PreparedStatement st = DBManager.db.prepareStatement(query);
                st.setString(1,nickname);
                st.setString(2,nome);
                st.setString(3,cognome);
                st.setString(4,email);
                st.setString(5,password);
                st.setInt(6, idutente);

                st.executeUpdate();
                obj.put("modifica", "true");
           }catch(Exception e){obj.put("modifica", "false");}
           
       }else{
           obj.put("modifica", "falsepassword");
       }
       
       
            }catch(Exception e){}
       
       
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