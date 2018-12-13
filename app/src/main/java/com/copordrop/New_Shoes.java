package com.copordrop;

/**
 * Created by MJMJ2 on 19/05/17.
 */
public class New_Shoes {

    private String shoe_name;
    private String shoe_price;
    private String shoe_brand;
    private String shoe_image;
    private String shoe_brand_image;
    private long cop_count;
    private long drop_count;

    public New_Shoes(){

    }

    public New_Shoes(String name,String price, String brand, String image, String shoe_brand_image, long cop_count, long drop_count){
        this.shoe_name = name;
        this.shoe_price = price;
        this.shoe_image = image;
        this.shoe_brand = brand;
        this.shoe_brand_image = shoe_brand_image;
        this.cop_count = cop_count;
        this.drop_count = drop_count;

    }

    public String getShoe_name(){
        return shoe_name;
    }

    public void setShoe_name(String shoe_name) {
        this.shoe_name = shoe_name;
    }

    public void setShoe_price(String shoe_price) {
        this.shoe_price = shoe_price;
    }

    public void setShoe_image(String shoe_image) {
        this.shoe_image = shoe_image;
    }

    public void setShoe_brand(String shoe_brand) {
        this.shoe_brand = shoe_brand;
    }

    public String getShoe_price(){
        return shoe_price;
    }

    public String getShoe_image(){
        return shoe_image;
    }

    public String getShoe_brand(){
        return shoe_brand;
    }

    public String getShoe_brand_image(){
        return shoe_brand_image;
    }

    public void setShoe_brand_image(String shoe_brand_image) {
        this.shoe_brand_image = shoe_brand_image;
    }

    public String getCop_count() {
        return String.valueOf(cop_count);
    }

    public String getDrop_count() {
        return String.valueOf(drop_count);
    }

    public void setCop_count(long cop_count) {
        this.cop_count = cop_count;
    }

    public void setDrop_count(long drop_count) {
        this.drop_count = drop_count;
    }
}
