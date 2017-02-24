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
public class HomeInviti extends HttpServlet {
DBManager manager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       response.setContentType("json/application");
        Integer idutente;
      PrintWriter out = response.getWriter();
        
        List <Festa> feste = new ArrayList<Festa>();
              JSONArray jsonarr = new JSONArray();
        idutente=Integer.parseInt(request.getParameter("idutente"));
        try {
          
           ResultSet rs;
            String query = "SELECT FESTA.ID,NOME,DATAINIZIO,DATAFINE,LUOGO,ORAINIZIO,ORAFINE "
                    + "FROM INVITO JOIN FESTA ON FESTA.ID=IDFESTA WHERE IDUTENTE=? AND ACCETTATO=? AND DATAFINE>=CURRENT_TIMESTAMP";
            PreparedStatement st = DBManager.db.prepareStatement(query);
            st.setInt(1, idutente);
            st.setInt(2, 1);
           
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
                feste.add(f);
                
            }
                   rs.close();
                   st.close();
        }            
            catch(Exception e){
                
            }
        
        
        for(int i=0;i<feste.size();i++){
           JSONObject obj=new JSONObject();
           obj.put("id", new Integer(feste.get(i).getid()));
           obj.put("name", feste.get(i).getName());
           obj.put("datainizio", feste.get(i).getdatestart());
           obj.put("datafine", feste.get(i).getdatefinish());
           obj.put("luogo", feste.get(i).getluogo());
           obj.put("orainizio", feste.get(i).gettimestart());
           obj.put("orafine", feste.get(i).gettimefinish());
           jsonarr.add(obj);
       }
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