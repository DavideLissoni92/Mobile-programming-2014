
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniele
 */
public class Festa {
 
    private String username;
    private String password;
    private String datestart;
    private String datefinish;
    private String timestart;
    private String timefinish;
    private String luogo;
    private int id;
    //funzione che ritorna l'username

    public String getName() {

        return username;

    }
    //funzione che setta l'username

    public void setName(String username) {

        this.username = username;

    }
    //funzione che ritorna la password

       //funzione che ritorna l'id

    public int getid() {

        return id;

    }
    //funzione che setta l'id

    public void setid(int id) {

        this.id = id;

    }   
   public String getdatestart() {

        return datestart;

    }
    //funzione che setta l'id

    public void setdatestart(String datestart) {

        this.datestart = datestart;

    }   

public String getluogo() {

        return luogo;

    }
    //funzione che setta l'id

    public void setluogo(String luogo) {

        this.luogo = luogo;

    }   
public String getdatefinish() {

        return datefinish;

    }
    //funzione che setta l'id

    public void setdatefinish(String datefinish) {

        this.datefinish = datefinish;

    }   
    public String gettimestart() {

        return timestart;

    }
    //funzione che setta l'id

    public void settimestart(String timestart) {

        this.timestart = timestart;

    }   
    public String gettimefinish() {

        return timefinish;

    }
    //funzione che setta l'id

    public void settimefinish(String timefinish) {

        this.timefinish = timefinish;

    }   
}
