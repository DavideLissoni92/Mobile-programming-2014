import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
public class Autenticazione extends HttpServlet {

    DBManager manager;
    String nickname, password;
    Integer output;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
                    PrintWriter out = response.getWriter();
        this.manager = (DBManager) super.getServletContext().getAttribute("dbmanager");

        nickname = request.getParameter("nickname");
        password = request.getParameter("password");
        ResultSet rs;
        try {

            String query = "SELECT ID FROM UTENTE WHERE NICKNAME=? AND PASSWORD=?";
            PreparedStatement st = manager.db.prepareStatement(query);
            st.setString(1, nickname);
            st.setString(2, password);
            rs = st.executeQuery();

            JSONObject obj = new JSONObject();
if(rs.next()){
    try{
        String query1 = "UPDATE UTENTE SET ULTIMOLOGIN=CURRENT_TIMESTAMP WHERE ID=?";
            PreparedStatement st1 = DBManager.db.prepareStatement(query1);
            st1.setInt(1, rs.getInt(1));
            
            st1.executeUpdate();
    }catch(Exception e){}
    obj.put("id", new Integer(rs.getInt(1)));
}else{obj.put("id", new Integer(-1));}
            
     
out.print(obj);


        } catch (SQLException e) {
            e.printStackTrace();
        }

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