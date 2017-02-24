/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Davide
 */
public class Utente {
   private int id;
   private String nickname,nome,cognome,email,password,ruolo,descrizioneruolo;
   
    public void setid(int id) {
 this.id = id;}
    public int getId() {

        return id;

    }
    
    public void setnickname(String nickname) {
 this.nickname = nickname;}
    public String getnickname() {

        return nickname;

    } 
    public void setnome(String nome) {
 this.nome = nome;}
    public String getnome() {

        return nome;

    } public void setcognome(String cognome) {
 this.cognome = cognome;}
    public String getcognome() {

        return cognome;

    }
    public void setemail(String email) {
 this.email = email;}
    public String getemail() {

        return email;

    } 
    public void setpassword(String password) {
 this.password = password;}
    public String getpassword() {

        return password;

    } 
   public void setruolo(String ruolo) {
 this.ruolo = ruolo;}
    public String getruolo() {

        return ruolo;

    } 
       public void setdescrizioneruolo(String descrizioneruolo) {
 this.descrizioneruolo = descrizioneruolo;}
    public String getdescrizioneruolo() {

        return descrizioneruolo;
    }
}