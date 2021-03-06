/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class VisualizzaPartecipanti extends HttpServlet {

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
        response.setContentType("java/application");
        PrintWriter out = response.getWriter();
        idutente=Integer.parseInt(request.getParameter("idutente"));
      idfesta=Integer.parseInt(request.getParameter("idfesta"));
      JSONArray jsonarrpreferiti = new JSONArray();
      JSONArray jsonarrutenti = new JSONArray();
       List <Utente> preferiti;
       List <Utente> utenti;
       
       preferiti=this.preferiti(idutente, idfesta);
       for(int i=0;i<preferiti.size();i++){
           JSONObject obj= new JSONObject();
           obj.put("id", new Integer(preferiti.get(i).getId()));
           obj.put("nome",preferiti.get(i).getnome());
           obj.put("cognome",preferiti.get(i).getcognome());
           obj.put("email",preferiti.get(i).getemail());
           obj.put("nickname",preferiti.get(i).getnickname());
           obj.put("ruolo",preferiti.get(i).getruolo());
           jsonarrpreferiti.add(obj);
       }       
       out.print( jsonarrpreferiti);
       
       utenti=this.utentinon(idfesta,idutente);
       for(int j=0;j<utenti.size();j++){
         JSONObject obj= new JSONObject();
           obj.put("id", new Integer(utenti.get(j).getId()));
           obj.put("nome",utenti.get(j).getnome());
           obj.put("cognome",utenti.get(j).getcognome());
           obj.put("email",utenti.get(j).getemail());
           obj.put("nickname",utenti.get(j).getnickname());
           obj.put("ruolo",utenti.get(j).getruolo());
           jsonarrutenti.add(obj);  
       }
       
       out.print(jsonarrutenti);
       
    }
    
    
    
     public List<Utente> preferiti(int idutente,int idfesta){
       List <Utente> preferiti= new ArrayList<Utente>();
       List<Utente>preferitiid=new ArrayList<Utente>();
       ResultSet rs;
       try{
           String query="SELECT IDPREFERITO "
                   + "FROM PREFERITO "
                   + "WHERE IDUTENTE=?";
           PreparedStatement st = DBManager.db.prepareStatement(query);
            st.setInt(1, idutente);
            rs = st.executeQuery();
            while(rs.next()){
            Utente u= new Utente();
            u.setid(rs.getInt(1));
            preferitiid.add(u);
            }
            ResultSet rs1;
             try{
                 for(int i=0;i<preferitiid.size();i++){
           String query1="SELECT UTENTE.ID,UTENTE.NOME,UTENTE.COGNOME,UTENTE.RUOLO,UTENTE.EMAIL,NICKNAME,DESCRIZIONERUOLO "
                   + "FROM UTENTE,UTENTEFESTA "
                   + "WHERE UTENTE.ID=UTENTEFESTA.IDUTENTE AND UTENTE.ID=? AND IDFESTA=?";
           PreparedStatement st1 = DBManager.db.prepareStatement(query1);
            st1.setInt(1, preferitiid.get(i).getId());
            st1.setInt(2, idfesta);
            rs1 = st1.executeQuery();
            if(rs1.next()){
                Utente u2 =new Utente();
                u2.setid(rs1.getInt(1));
                u2.setnome(rs1.getString(2));
                u2.setcognome(rs1.getString(3));
                u2.setruolo(rs1.getString(4));
                u2.setemail(rs1.getString(5));
                u2.setdescrizioneruolo(rs1.getString(7));
                u2.setnickname(rs1.getString(6));
                preferiti.add(u2);
                
            }
                                   }
                 rs.close();
                   st.close();
             
       }catch(Exception e){e.printStackTrace();}
            
            
            
       }catch(Exception e){}
        return preferiti;
       }
     
     
     
      public List<Utente> utentinon(int idfesta,int idutente){
       List <Utente> utenti= new ArrayList<Utente>();
       ResultSet rs;
       try{
           String query="SELECT UTENTE.ID,UTENTE.NOME,UTENTE.COGNOME,UTENTE.RUOLO,UTENTE.EMAIL,NICKNAME,DESCRIZIONERUOLO "
                   + "FROM UTENTE,UTENTEFESTA "
                   + "WHERE UTENTEFESTA.IDUTENTE=UTENTE.ID AND UTENTEFESTA.IDFESTA=? AND UTENTE.ID!=?";
           PreparedStatement st = DBManager.db.prepareStatement(query);
            st.setInt(1, idfesta);
            st.setInt(2, idutente);
            rs = st.executeQuery();
            while(rs.next()){
            Utente u2 =new Utente();
                u2.setid(rs.getInt(1));
                u2.setnome(rs.getString(2));
                u2.setcognome(rs.getString(3));
                u2.setruolo(rs.getString(4));
                u2.setdescrizioneruolo(rs.getString(5));
                u2.setnickname(rs.getString(6));
                u2.setdescrizioneruolo(rs.getString(7));
                utenti.add(u2);
                    }
                 rs.close();
                   st.close();
 
       }catch(Exception e){e.printStackTrace();}
     return utenti;}
    
    
    
    

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