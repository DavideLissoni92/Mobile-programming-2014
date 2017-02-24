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
public class VisualizzaFesta extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */DBManager manager;
   String nome, luogo;
   Timestamp datainizio, datafine,orainizio,orafine;
   int idfesta,idutente;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
         
        PrintWriter out = response.getWriter();
        this.manager = (DBManager) super.getServletContext().getAttribute("dbmanager");
        idfesta=Integer.parseInt(request.getParameter("idfesta"));
        idutente=Integer.parseInt(request.getParameter("idutente"));
      List <DjFesta> djs;
        JSONArray jsonarr = new JSONArray();
        JSONObject obj = new JSONObject();

        try {
 ResultSet rs;
            String query = "SELECT ID,NOME,IDADMIN,DATAINIZIO,DATAFINE,ORAINIZIO,ORAFINE,GENERI,LUOGO,DESCRIZIONE FROM FESTA WHERE ID=?";
            PreparedStatement st = DBManager.db.prepareStatement(query);
            st.setInt(1, idfesta);
            rs = st.executeQuery();
            if(rs.next()){
            obj.put("idfesta", new Integer(rs.getInt(1)));
            obj.put("idutente", new Integer(idutente));
            obj.put("nome", rs.getString(2));
             obj.put("idadmin", new Integer(rs.getInt(3)));
            obj.put("datainizio", rs.getString(4));
            obj.put("datafine", rs.getString(5));
            obj.put("orainizio", rs.getString(6));
            obj.put("orafine", rs.getString(7));
            obj.put("generi", rs.getString(8));
            obj.put("luogo", rs.getString(9));
           obj.put("descrizione", rs.getString(10));
            if(idutente==rs.getInt(3)){
                obj.put("amministratore","true");
            }else{obj.put("amministratore","false");}}
            }catch(Exception e){obj.put("amministratore",e);}
        jsonarr.add(obj);
       djs=this.djfesta(idfesta);
         for(int i=0;i<djs.size();i++){
           JSONObject obj2=new JSONObject();
           obj2.put("iddj", new Integer(djs.get(i).getId()));
           obj2.put("name", djs.get(i).getName());
           obj2.put("orainizio", djs.get(i).getOrainizio());
           obj2.put("orafine", djs.get(i).getOraFine());
           obj2.put("soundcloud", djs.get(i).getCollegamentoSoundcloud());
           jsonarr.add(obj2);
       }
        
     out.print(jsonarr);
        
        
        
        
        
        
    }
    public List<DjFesta> djfesta(int id){
       List <DjFesta> djfesta= new ArrayList<DjFesta>();
       ResultSet rs;
       try{
           String query="SELECT DJFESTA.ID,ORAINIZIO,ORAFINE,DJFESTA.NOME,COLLEGAMENTOCLOUD "
                   + "FROM DJFESTA "
                   + "WHERE IDFESTA=?";
           PreparedStatement st = DBManager.db.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();
            while (rs.next()){
                DjFesta djf=new DjFesta();
                djf.setName(rs.getString(4));
                djf.setid(rs.getInt(1));
                djf.setOraInizio(rs.getString(2));
                djf.setOrafine(rs.getString(3));
                djf.setcollegamentosoundcloud(rs.getString(5));
                djfesta.add(djf);
                
            }
                   rs.close();
                   st.close();
       }catch(Exception e){e.printStackTrace();}
        return djfesta;
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