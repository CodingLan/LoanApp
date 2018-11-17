package com.zhenxing.loanapp.bean;

import android.text.TextUtils;

import com.zhenxing.loanapp.http.TBPage;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lxq_workspace on 2018/4/3.
 * 广告Bean
 */

public class AdvertisementBean  implements Serializable {

    private long id;

    private int onlyHighAuth;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    private long userId;

    private int pricePrecision;

    public int getPricePrecision() {
        return pricePrecision;
    }

    public void setPricePrecision(int pricePrecision) {
        this.pricePrecision = pricePrecision;
    }

    /**
     * 1.购买 2.出售
     */
    private int type;

    /**
     * 总订单数
     */
    private String orderNum;

    /**
     * 成功的订单数
     */
    private String executeNum;

    /**
     * 价格
     */
    private String price;

    /**
     * 是否已认证 1.是 2.否 3.认证中
     */
    private int isMerchant;

    /**
     * 0:银行卡  1：支付宝   2：微信
     */
    private Integer[] bankType;

    private double minAmount;
    private double maxAmount;
    private String nickName;

    private int currencyId;

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * 广告类型，1：限价广告  2：溢价广告
     */
    private int pricingType;

    private long createDate;

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getPricingType() {
        return pricingType;
    }

    public void setPricingType(int pricingType) {
        this.pricingType = pricingType;
    }

    /**
     * 当前可交易数量
     */
    private String quantity;
    /**
     * 历史累计成交数量
     */
    private double executedQuantity;

    /**
     * 订单金额，//100001:5w以下   100002：5w到20w  100003：20w以上
     */
    private int conditionId;

    private int status;

    /**
     * 状态，1：在线   0：离线
     */
    private int isOnline =1;// MyConstant.USER_OFFLINE;

    public double getExecutedQuantity() {
        return executedQuantity;
    }

    public void setExecutedQuantity(double executedQuantity) {
        this.executedQuantity = executedQuantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 获取未成交金额
     *
     * @return
     */
    public String getUnExecutedAmount() {
        return String.valueOf(Double.parseDouble(quantity) * Double.parseDouble(price));
    }

    public int getOnlyHighAuth() {
        return onlyHighAuth;
    }

    public void setOnlyHighAuth(int onlyHighAuth) {
        this.onlyHighAuth = onlyHighAuth;
    }

    /**
     * 获取总成交金额
     *
     * @return
     */
    public String getAllAmount() {
        return String.valueOf(Double.parseDouble(quantity) * Double.parseDouble(price));
    }

    public int getConditionId() {
        return conditionId;
    }

    public void setConditionId(int conditionId) {
        this.conditionId = conditionId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getExecuteNum() {
        return executeNum;
    }

    public void setExecuteNum(String executeNum) {
        this.executeNum = executeNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * 获取首字母或第一个汉字
     *
     * @return
     */
    public String getFirstSpell() {
        if (!TextUtils.isEmpty(nickName)) {
            return String.valueOf(nickName.charAt(0));
        }
        return "";
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getIsMerchant() {
        return isMerchant;
    }

    public void setIsMerchant(int isMerchant) {
        this.isMerchant = isMerchant;
    }

    public Integer[] getBankType() {
        return bankType;
    }

    public void setBankType(Integer[] bankType) {
        this.bankType = bankType;
    }

    public double getMinAmount() {
        return minAmount;
    }

    /**
     * 支持银行
     *
     * @return
     */
    public boolean isSupportBank() {
        if (bankType != null && bankType.length > 0) {
            for (int i = 0; i < bankType.length; i++) {
                if (bankType[i] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 支持支付宝
     *
     * @return
     */
    public boolean isSupportAliPay() {
        if (bankType != null && bankType.length > 0) {
            for (int i = 0; i < bankType.length; i++) {
                if (bankType[i] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 支持微信付款
     *
     * @return
     */
    public boolean isSupportWeChat() {
        if (bankType != null && bankType.length > 0) {
            for (int i = 0; i < bankType.length; i++) {
                if (bankType[i] == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 限额
     *
     * @return
     */
    public String getLimitCount() {
        return "无限额";
    }

    public void setMinAmount(double minAmount) {
        this.minAmount = minAmount;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public boolean isCanTrade() {
        return true;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

}
