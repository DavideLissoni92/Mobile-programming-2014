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
public class RifiutaInvito extends HttpServlet {

    
    DBManager manager;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("json/application");
        int idutente,idfesta;
        PrintWriter out = response.getWriter();
      idutente=Integer.parseInt(request.getParameter("idutente"));
      idfesta=Integer.parseInt(request.getParameter("idfesta"));
      JSONObject obj=new JSONObject();
      ResultSet rs;
        try {

            String query = "SELECT ID FROM INVITO WHERE IDUTENTE=? AND IDFESTA=?";
            PreparedStatement st = manager.db.prepareStatement(query);
            st.setInt(1, idutente);
            st.setInt(2, idfesta);
            rs = st.executeQuery();
            
              if (rs.next()){
      try {

            String query1 = "UPDATE INVITO SET ACCETTATO=? WHERE IDFESTA=? AND IDUTENTE=?";
            PreparedStatement st1 = DBManager.db.prepareStatement(query1);
            st1.setInt(1, 2);
            st1.setInt(2, idfesta);
            st1.setInt(3, idutente);
            st1.executeUpdate();
            st1.close();
            obj.put("rifiutainvito", "true");
        } catch (SQLException e) {
          obj.put("rifiutainvito", "false");
        }    
      }else{
            try{
            String query2 = "INSERT INTO INVITO(IDUTENTE,IDFESTA,ACCETTATO) VALUES(?,?,?)";
            PreparedStatement st2 = DBManager.db.prepareStatement(query2);
            st2.setInt(1, idutente);
            st2.setInt(2, idfesta);
            st2.setInt(3, 2);
            st2.executeUpdate();
            obj.put("rifiutainvito", "true");
            }catch(Exception e){
                e.printStackTrace();
                obj.put("rifiutainvito", "false");
            }  
            
            
            try{
            String query3 = "DELETE FROM UTENTEFESTA WHERE IDFESTA=? AND IDUTENTE=?";
            PreparedStatement st3 = DBManager.db.prepareStatement(query3);
            st3.setInt(1, idfesta);
            st3.setInt(2, idutente);
            st3.executeUpdate();
            st3.close();

        } catch (SQLException e) {

        }
 
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