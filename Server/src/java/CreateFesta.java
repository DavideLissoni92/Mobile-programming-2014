import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class CreateFesta extends HttpServlet {

   DBManager manager;
   String nome, luogo,generi;
   Timestamp datainiziotime, datafinetime;
   String datainizio, datafine;
           String orainizio,orafine,descrizione;
   int idadmin;
   int idfesta=0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("application/json");
        JSONObject obj = new JSONObject();
        PrintWriter out = response.getWriter();
        
        this.manager = (DBManager) super.getServletContext().getAttribute("dbmanager");
        
        

nome=request.getParameter("nome");
idadmin=Integer.parseInt(request.getParameter("idadmin"));
datainizio=request.getParameter("datainizio");
datafine=request.getParameter("datafine");
orafine=request.getParameter("orafine");
orainizio=request.getParameter("orainizio");
generi=request.getParameter("generi");
luogo=request.getParameter("luogo");
descrizione=request.getParameter("descrizione");
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
           String query = "INSERT INTO FESTA(NOME,IDADMIN,DATAINIZIO,DATAFINE,LUOGO,ORAINIZIO,ORAFINE,GENERI,DESCRIZIONE) VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = manager.db.prepareStatement(query);
            st.setString(1, nome);
            st.setInt(2, idadmin);
            st.setTimestamp(3, datainiziotime);
            st.setTimestamp(4, datafinetime);
            st.setString(6, orafine);
            st.setString(7, orainizio);
            st.setString(5, luogo);
            st.setString(8, generi);
            st.setString(9, descrizione);
 
            st.executeUpdate();
            st.close();
            
            
          
     
            obj.put("creazione", "true");
            
            
        } catch(Exception e) {
            obj.put("creazione", "false");
            
        }
          try {
ResultSet rs;
            String query1 = "SELECT ID FROM FESTA WHERE NOME=? AND IDADMIN=? AND LUOGO=?";
            PreparedStatement st1 = manager.db.prepareStatement(query1);
            st1.setString(1, nome);
            st1.setString(3,luogo);
            st1.setInt(2, idadmin);
            rs = st1.executeQuery();
            if (rs.next()){
              idfesta=rs.getInt(1);
            }
            }catch(Exception e){}
          obj.put("idfesta",new Integer(idfesta));
        out.print(obj);
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
           Logger.getLogger(CreateFesta.class.getName()).log(Level.SEVERE, null, ex);
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
           Logger.getLogger(CreateFesta.class.getName()).log(Level.SEVERE, null, ex);
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