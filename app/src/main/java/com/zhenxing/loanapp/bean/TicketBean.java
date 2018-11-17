package com.zhenxing.loanapp.bean;

/**
 * Created by Administrator on 2017/8/29.
 */

public class TicketBean {
    private String ticket;

    //短信验证码错误次数
    private int count;
    //短信错误次数
    private String lastCount;
    //还需要其他规则的验证
    private String pre_verify;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLastCount() {
        return lastCount;
    }

    public void setLastCount(String lastCount) {
        this.lastCount = lastCount;
    }

    public String getPre_verify() {
        return pre_verify;
    }

    public void setPre_verify(String pre_verify) {
        this.pre_verify = pre_verify;
    }
}
