package com.copordrop;

/**
 * Created by MJMJ2 on 20/05/17.
 */
public class Comments_Shoe {

    private String comment;
    private String users_name;
    private long time;
    private String timestamp;
    private int like_count;

    TimeElasped t = new TimeElasped();
    long millis = System.currentTimeMillis();

    public Comments_Shoe(){

    }

    public Comments_Shoe(String comment, String users_name, String timestamp, long time,int like_count){
        this.comment = comment;
        this.users_name = users_name;
        this.time = time;
        this.timestamp = timestamp;
        this.like_count = like_count;

    }

    public String getComment() {
        return comment;
    }

    public String getUsers_name() {
        return users_name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUsers_name(String users_name) {
        this.users_name = users_name;
    }

    public long getTime() {
        return time;
    }

    public String getTimestamp() {
        return t.convertLongDateToAgoString(getTime(),millis);
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }
}
