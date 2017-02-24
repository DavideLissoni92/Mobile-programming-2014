import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
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
public class AccettaInvito extends HttpServlet {

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
      try {

            String query = "DELETE FROM INVITO WHERE IDFESTA=? AND IDUTENTE=?";
            PreparedStatement st = DBManager.db.prepareStatement(query);
            st.setInt(1, idfesta);
            st.setInt(2, idutente);
            st.executeUpdate();
            st.close();

        } catch (SQLException e) {

        }
       try{
            String query = "INSERT INTO UTENTEFESTA(IDUTENTE,IDFESTA) VALUES(?,?)";
            PreparedStatement st = DBManager.db.prepareStatement(query);
            st.setInt(1, idutente);
            st.setInt(2, idfesta);
            st.executeUpdate();
            obj.put("accettainvito", "true");
            }catch(Exception e){
                e.printStackTrace();
                obj.put("accettainvito", "false");
            }
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