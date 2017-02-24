/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Daniele
 */
public class HomePartecipo extends HttpServlet {
DBManager manager;
Integer idutente;
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("application/json");
        List <Festa> festep;
        PrintWriter out = response.getWriter();
        idutente=Integer.parseInt(request.getParameter("id"));
       festep=this.festepartecipo(idutente);
       JSONArray jsonarr = new JSONArray();
       
      
       for(int i=0;i<festep.size();i++){
           JSONObject obj=new JSONObject();
           obj.put("id", new Integer(festep.get(i).getid()));
           obj.put("name", festep.get(i).getName());
           obj.put("datainizio", festep.get(i).getdatestart());
           obj.put("datafine", festep.get(i).getdatefinish());
           obj.put("luogo", festep.get(i).getluogo());
           obj.put("orainizio", festep.get(i).gettimestart());
           obj.put("orafine", festep.get(i).gettimefinish());
           jsonarr.add(obj);
       }
       out.print(jsonarr);
    }
    public List<Festa> festepartecipo(int id){
       List <Festa> festepartecipo= new ArrayList<Festa>();
       ResultSet rs;
     //  java.util.Date today = new java.util.Date();
  //Timestamp dataoggi=new java.sql.Timestamp(today.getTime());
  
       try{
           String query="SELECT FESTA.ID,NOME,DATAINIZIO,DATAFINE,LUOGO,ORAINIZIO,ORAFINE "
                   + "FROM FESTA JOIN UTENTEFESTA ON FESTA.ID=IDFESTA "
                   + "WHERE IDUTENTE=? AND DATAFINE>=CURRENT_TIMESTAMP";
           PreparedStatement st = DBManager.db.prepareStatement(query);
            st.setInt(1, idutente);
            //st.setTimestamp(2, dataoggi);
            rs = st.executeQuery();
            while (rs.next()){
                Festa f=new Festa();
                f.setName(rs.getString(2));
                f.setdatefinish(rs.getString(4));
                f.setdatestart(rs.getString(3));
                f.setid(rs.getInt(1));
                f.setluogo(rs.getString(5));
                f.settimefinish(rs.getString(7));
                f.settimestart(rs.getString(6));
                
                festepartecipo.add(f);
                
            }
                   rs.close();
                   st.close();
       }catch(Exception e){e.printStackTrace();}
        return festepartecipo;
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
    try {
        processRequest(request, response);
    } catch (SQLException ex) {
        Logger.getLogger(HomePartecipo.class.getName()).log(Level.SEVERE, null, ex);
    }
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
    try {
        processRequest(request, response);
    } catch (SQLException ex) {
        Logger.getLogger(HomePartecipo.class.getName()).log(Level.SEVERE, null, ex);
    }
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