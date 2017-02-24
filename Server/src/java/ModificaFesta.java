import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author Daniele
 */
public class ModificaFesta extends HttpServlet {

    
    
    String nome, luogo,genere;
   Timestamp datainiziotime, datafinetime;
   
           String orainizio,orafine,datainizio,datafine;
   int idadmin,idfesta;
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
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
               
        

nome=request.getParameter("nome");
idadmin=Integer.parseInt(request.getParameter("idadmin"));
idfesta=Integer.parseInt(request.getParameter("idfesta"));
datainizio=request.getParameter("datainizio");
datafine=request.getParameter("datafine");
orafine=request.getParameter("orafine");
orainizio=request.getParameter("orainizio");
luogo=request.getParameter("luogo");
genere=request.getParameter("genere");
JSONObject obj=new JSONObject();


java.text.SimpleDateFormat dateformat1 = new java.text.SimpleDateFormat("dd-MM-yyyy");
java.text.SimpleDateFormat dateformat2= new java.text.SimpleDateFormat("yyyy-MM-dd");
       try {
           datafine=dateformat2.format(dateformat1.parse(datafine))+" 00:00:00.000";
           datainizio=dateformat2.format(dateformat1.parse(datainizio))+" 00:00:00.000";
       } catch (ParseException ex) {
           Logger.getLogger(CreateFesta.class.getName()).log(Level.SEVERE, null, ex);
       }
datainiziotime=Timestamp.valueOf(datainizio);
       datafinetime=Timestamp.valueOf(datafine);







        try {
            /* TODO output your page here. You may use following sample code. */
           String query = "UPDATE FESTA SET NOME=?,DATAINIZIO=?,DATAFINE=?,LUOGO=?,ORAINIZIO=?,ORAFINE=?,ULTIMAMODIFICA=CURRENT_TIMESTAMP,GENERI=? WHERE ID=?";
            PreparedStatement st = DBManager.db.prepareStatement(query);
            st.setString(1, nome);
            st.setTimestamp(2, datainiziotime);
            st.setTimestamp(3, datafinetime);
            st.setString(5, orainizio);
            st.setString(6, orafine);
            st.setString(4, luogo);
                        st.setInt(8, idfesta);
                        st.setString(7, genere);
            st.executeUpdate();
            st.close();
            obj.put("modificafesta", "true");
        } catch (SQLException ex) {
            obj.put("modificafesta", "false");
            Logger.getLogger(ModificaFesta.class.getName()).log(Level.SEVERE, null, ex);
        } finally {            
            out.close();
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