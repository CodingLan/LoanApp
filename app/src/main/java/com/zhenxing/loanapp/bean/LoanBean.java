package com.zhenxing.loanapp.bean;

import java.io.Serializable;

public class LoanBean implements Serializable {

    private String title;

    private int rank;
    private double max;
    private int isNew;//1:是新品   0：非新品
    private int isCheckCredit;//1:需查征信   0：不查征信
    private String desp;
    private String imageUrl;
    private String webUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public int getIsCheckCredit() {
        return isCheckCredit;
    }

    public void setIsCheckCredit(int isCheckCredit) {
        this.isCheckCredit = isCheckCredit;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }


}
