/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author Davide
 */
public class InserisciDj extends HttpServlet {

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
       int idfesta,idutente;
       Timestamp orainiziotime,orafinetime;
       String orainizio,orafine,nome;
       JSONObject obj=new JSONObject();
               idutente=Integer.parseInt(request.getParameter("idutente"));
        idfesta=Integer.parseInt(request.getParameter("idfesta"));
                nome=request.getParameter("nome");
orainizio="1970-01-01 "+request.getParameter("orainizio")+":00.000";
orafine="1970-01-01 "+request.getParameter("orafine")+":00.000";
        String genere=request.getParameter("genere");
orafinetime=Timestamp.valueOf(orafine);
orainiziotime=Timestamp.valueOf(orainizio);



try{
 String query = "INSERT INTO DJFESTA(IDFESTA,NOME,ORAINIZIO,ORAFINE,GENERE) VALUES(?,?,?,?,?)";
            PreparedStatement st = DBManager.db.prepareStatement(query);
            st.setInt(1, idfesta);
            st.setString(2, nome);
            st.setTimestamp(3, orainiziotime);
            st.setTimestamp(4, orafinetime);
            st.setString(5, genere);
            st.executeUpdate();
            st.close();
obj.put("inseriscidj","true");}catch(Exception e){obj.put("inseriscidj","false");}

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