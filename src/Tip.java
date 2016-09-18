package com.u4bi.tipmoaproject;

/**
 * Created by student on 2016-05-13.
 */
public class Tip {

    private  int tip_id;
    private String reg;
    private String regdate;
    private int tipPrice;
    private int tipio;

    public int getTip_id() {
        return tip_id;
    }

    public void setTip_id(int tip_id) {
        this.tip_id = tip_id;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public int getTipPrice() {
        return tipPrice;
    }

    public void setTipPrice(int tipPrice) {
        this.tipPrice = tipPrice;
    }

    public int getTipio() {
        return tipio;
    }

    public void setTipio(int tipio) {
        this.tipio = tipio;
    }
}
