package com.azstudio.storeapp.Models;

public class Product {

    int product_id;
    String product_name;
    int rate;
    String imagea;
    String imageb;
    String imagec;
    String title;
    int original_rate;
    String sleeve;
    String fabric;
    String neck_type;
    String pattern;
    String m;
    String l;
    String xl;
    String xxl;
    String xxxl;
    String details;

    public Product(String product_name, int rate, String imagea, String imageb, String imagec, String title, int original_rate, String sleeve, String fabric, String neck_type, String pattern, String m, String l, String xl, String xxl, String xxxl, String details) {
        this.product_name = product_name;
        this.rate = rate;
        this.imagea = imagea;
        this.imageb = imageb;
        this.imagec = imagec;
        this.title = title;
        this.original_rate = original_rate;
        this.sleeve = sleeve;
        this.fabric = fabric;
        this.neck_type = neck_type;
        this.pattern = pattern;
        this.m = m;
        this.l = l;
        this.xl = xl;
        this.xxl = xxl;
        this.xxxl = xxxl;
        this.details = details;
    }

    public Product() {

    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getImagea() {
        return imagea;
    }

    public void setImagea(String imagea) {
        this.imagea = imagea;
    }

    public String getImageb() {
        return imageb;
    }

    public void setImageb(String imageb) {
        this.imageb = imageb;
    }

    public String getImagec() {
        return imagec;
    }

    public void setImagec(String imagec) {
        this.imagec = imagec;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOriginal_rate() {
        return original_rate;
    }

    public void setOriginal_rate(int original_rate) {
        this.original_rate = original_rate;
    }

    public String getSleeve() {
        return sleeve;
    }

    public void setSleeve(String sleeve) {
        this.sleeve = sleeve;
    }

    public String getFabric() {
        return fabric;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public String getNeck_type() {
        return neck_type;
    }

    public void setNeck_type(String neck_type) {
        this.neck_type = neck_type;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getXl() {
        return xl;
    }

    public void setXl(String xl) {
        this.xl = xl;
    }

    public String getXxl() {
        return xxl;
    }

    public void setXxl(String xxl) {
        this.xxl = xxl;
    }

    public String getXxxl() {
        return xxxl;
    }

    public void setXxxl(String xxxl) {
        this.xxxl = xxxl;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
