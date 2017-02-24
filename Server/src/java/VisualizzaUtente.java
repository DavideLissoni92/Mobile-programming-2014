/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
public class VisualizzaUtente extends HttpServlet {

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
   String nome,ruolo,nomeoggetto,descrizioneoggetto,descrizioneruolo,cognome,nickname,mail,preferito;
   int idutente,myid;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        this.manager = (DBManager) super.getServletContext().getAttribute("dbmanager");
        myid=Integer.parseInt(request.getParameter("id"));
        idutente=Integer.parseInt(request.getParameter("idutente"));
        JSONObject obj = new JSONObject();
        ResultSet rs;
           List <Oggetti> ogg;
        
        try {

            String query = "SELECT NOME,COGNOME,NICKNAME,RUOLO,DESCRIZIONERUOLO FROM UTENTE WHERE ID=?";
            PreparedStatement st = manager.db.prepareStatement(query);
            st.setInt(1, idutente);
            rs = st.executeQuery();
            if(rs.next()){
            obj.put("idmy", new Integer(myid));
            obj.put("idutente", new Integer(idutente));
            obj.put("nome", rs.getString(1));
            obj.put("cognome", rs.getString(2));
            obj.put("nickname", rs.getString(3));
            obj.put("ruolo", rs.getString(4));
            obj.put("descrizione ruolo", rs.getString(5));
            }else{obj.put("nome", "errore");}}catch(Exception e){}
    
        try {
ResultSet rs3;
            String query3 = "SELECT ID FROM PREFERITO WHERE IDUTENTE=? AND IDPREFERITO=?";
            PreparedStatement st3 = manager.db.prepareStatement(query3);
            st3.setInt(1, myid);
            st3.setInt(2, idutente);
            rs3 = st3.executeQuery();
            if (rs3.next()){
                obj.put("preferito","true");
            }else{
                obj.put("preferito","false");
            }
    }catch(Exception e){}
    JSONArray jsonarr = new JSONArray();
    jsonarr.add(obj);
    
           ogg=this.oggetti(idutente);
        
         for(int i=0;i<ogg.size();i++){
           JSONObject obj2=new JSONObject();
           obj2.put("idogg", new Integer(ogg.get(i).getId()));
           obj2.put("name", ogg.get(i).getnome());
           obj2.put("descrizione", ogg.get(i).getdescrizione());
           jsonarr.add(obj2);
       }
          
       out.print(jsonarr);
        

         }

    public List<Oggetti> oggetti(int idutente){
       List <Oggetti> oggetti= new ArrayList<Oggetti>();
       ResultSet rs;
       try{
           String query="SELECT ID,NOME,DESCRIZIONE "
                   + "FROM OGGETTO "
                   + "WHERE IDPROPRIETARIO=?";
           PreparedStatement st = DBManager.db.prepareStatement(query);
            st.setInt(1, idutente);
            rs = st.executeQuery();
            while (rs.next()){
                Oggetti ogg=new Oggetti();
                ogg.setid(rs.getInt(1));
                ogg.setidproprietario(idutente);
                ogg.setnome(rs.getString(2));
                ogg.setdescrizione(rs.getString(3));
                oggetti.add(ogg);
                
            }
                   rs.close();
                   st.close();
       }catch(Exception e){e.printStackTrace();}
        return oggetti;
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