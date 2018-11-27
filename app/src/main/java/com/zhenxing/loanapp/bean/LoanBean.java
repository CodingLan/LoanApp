package com.zhenxing.loanapp.bean;

import java.io.Serializable;

public class LoanBean implements Serializable {

    public static final int isTrue = 1;

    /**
     * 产品名称
     */
    private String title;

    /**
     * 排名
     */
    private int rank;
    /**
     * 利率
     */
    private double rate;

    /**
     * 是否自动审批,1:自动  0：非自动审批
     */
    private int isAutoApproval;
    /**
     * 最高可借
     */
    private double max;
    /**
     * 是否是新品，1:是新品   0：非新品
     */
    private int isNew;

    /**
     * 是否大额低息，1:是   0：否
     */
    private int isMaxLowFee;

    /**
     * 是否查征信，1:需查征信   0：不查征信
     */
    private int isCheckCredit;
    /**
     * 描述
     */
    private String desp;
    /**
     * 图片地址
     */
    private String imageUrl;
    /**
     * 跳转网页地址
     */
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

    public double getRate() {

        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }



    public int getIsAutoApproval() {
        return isAutoApproval;
    }

    public void setIsAutoApproval(int isAutoApproval) {
        this.isAutoApproval = isAutoApproval;
    }

    public int getIsMaxLowFee() {
        return isMaxLowFee;
    }

    public void setIsMaxLowFee(int isMaxLowFee) {
        this.isMaxLowFee = isMaxLowFee;
    }
}
