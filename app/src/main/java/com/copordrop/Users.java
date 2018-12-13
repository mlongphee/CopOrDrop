package com.copordrop;

/**
 * Created by MJMJ2 on 20/05/17.
 */
public class Users {

    public String first_name;
    public String last_name;
    public boolean first_time;
    public String email;

    public Users(){

    }

    public Users(String name, boolean first_time, String email, String first_name, String last_name){
        this.first_name = first_name;
        this.last_name = last_name;
        this.first_time = first_time;
        this.email = email;
    }

    public void setFirst_time(boolean first){
        this.first_time = first;
    }

    public boolean isFirst_time() {
        return first_time;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}