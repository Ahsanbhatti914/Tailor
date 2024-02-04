package com.example.tailor.Models;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Customer implements Serializable {
    private String cid;
    private String id;
    private String name;
    private String number;
    private String kameez;
    private String kameezFrac;
    private String tera;
    private String teraFrac;
    private String bazo;
    private String bazoFrac;
    private String moda;
    private String modaFrac;
    private String chati;
    private String chatiFrac;
    private String fitting;
    private String fittingFrac;
    private String gera;
    private String geraFrac;
    private String gala;
    private String galaFrac;
    private String shalwar;
    private String shalwarFrac;
    private String poncha;
    private String ponchaFrac;
    private String fair;
    private String fairFrac;
    private String asan;
    private String asanFrac;
    private String trouser;
    private String trouserFrac;
    private String trouserPoncha;
    private String trouserPonchaFrac;
    private String trouserFair;
    private String trouserFairFrac;
    private String trouserAsan;
    private String trouserAsanFrac;
    private String collar;
    private String collarWidth;
    private String fracCollarWidth;
    private String collarLength;
    private String fracCollarLength;
    private String kaff;
    private String kaffWidth;
    private String fracKaffWidth;
    private String kaffLength;
    private String fracKaffLength;
    private String salaei;
    private String gheera;
    private String dhaga;
    private String frontPocket;
    private String sidePocket;
    private String shalwarPocket;

    private String note;

    public Customer(){}

    public Customer(String cid, String id, String name, String number, String kameez, String kameezFrac, String tera, String teraFrac, String bazo, String bazoFrac, String moda, String modaFrac, String chati, String chatiFrac, String fitting, String fittingFrac, String gera, String geraFrac, String gala, String galaFrac, String shalwar, String shalwarFrac, String poncha, String ponchaFrac, String fair, String fairFrac, String asan, String asanFrac, String trouser, String trouserFrac, String trouserPoncha, String trouserPonchaFrac, String trouserFair, String trouserFairFrac, String trouserAsan, String trouserAsanFrac, String collar, String collarWidth, String fracCollarWidth, String collarLength, String fracCollarLength, String kaff, String kaffWidth, String fracKaffWidth, String kaffLength, String fracKaffLength, String salaei, String gheera, String dhaga, String frontPocket, String sidePocket, String shalwarPocket,String note) {
        this.cid = cid;
        this.id = id;
        this.name = name;
        this.number = number;
        this.kameez = kameez;
        this.kameezFrac = kameezFrac;
        this.tera = tera;
        this.teraFrac = teraFrac;
        this.bazo = bazo;
        this.bazoFrac = bazoFrac;
        this.moda = moda;
        this.modaFrac = modaFrac;
        this.chati = chati;
        this.chatiFrac = chatiFrac;
        this.fitting = fitting;
        this.fittingFrac = fittingFrac;
        this.gera = gera;
        this.geraFrac = geraFrac;
        this.gala = gala;
        this.galaFrac = galaFrac;
        this.shalwar = shalwar;
        this.shalwarFrac = shalwarFrac;
        this.poncha = poncha;
        this.ponchaFrac = ponchaFrac;
        this.fair = fair;
        this.fairFrac = fairFrac;
        this.asan = asan;
        this.asanFrac = asanFrac;
        this.trouser = trouser;
        this.trouserFrac = trouserFrac;
        this.trouserPoncha = trouserPoncha;
        this.trouserPonchaFrac = trouserPonchaFrac;
        this.trouserFair = trouserFair;
        this.trouserFairFrac = trouserFairFrac;
        this.trouserAsan = trouserAsan;
        this.trouserAsanFrac = trouserAsanFrac;
        this.collar = collar;
        this.collarWidth = collarWidth;
        this.fracCollarWidth = fracCollarWidth;
        this.collarLength = collarLength;
        this.fracCollarLength = fracCollarLength;
        this.kaff = kaff;
        this.kaffWidth = kaffWidth;
        this.fracKaffWidth = fracKaffWidth;
        this.kaffLength = kaffLength;
        this.fracKaffLength = fracKaffLength;
        this.salaei = salaei;
        this.gheera = gheera;
        this.dhaga = dhaga;
        this.frontPocket = frontPocket;
        this.sidePocket = sidePocket;
        this.shalwarPocket = shalwarPocket;
        this.note = note;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getKameez() {
        return kameez;
    }

    public void setKameez(String kameez) {
        this.kameez = kameez;
    }

    public String getKameezFrac() {
        return kameezFrac;
    }

    public void setKameezFrac(String kameezFrac) {
        this.kameezFrac = kameezFrac;
    }

    public String getTera() {
        return tera;
    }

    public void setTera(String tera) {
        this.tera = tera;
    }

    public String getTeraFrac() {
        return teraFrac;
    }

    public void setTeraFrac(String teraFrac) {
        this.teraFrac = teraFrac;
    }

    public String getBazo() {
        return bazo;
    }

    public void setBazo(String bazo) {
        this.bazo = bazo;
    }

    public String getBazoFrac() {
        return bazoFrac;
    }

    public void setBazoFrac(String bazoFrac) {
        this.bazoFrac = bazoFrac;
    }

    public String getModa() {
        return moda;
    }

    public void setModa(String moda) {
        this.moda = moda;
    }

    public String getModaFrac() {
        return modaFrac;
    }

    public void setModaFrac(String modaFrac) {
        this.modaFrac = modaFrac;
    }

    public String getChati() {
        return chati;
    }

    public void setChati(String chati) {
        this.chati = chati;
    }

    public String getChatiFrac() {
        return chatiFrac;
    }

    public void setChatiFrac(String chatiFrac) {
        this.chatiFrac = chatiFrac;
    }

    public String getFitting() {
        return fitting;
    }

    public void setFitting(String fitting) {
        this.fitting = fitting;
    }

    public String getFittingFrac() {
        return fittingFrac;
    }

    public void setFittingFrac(String fittingFrac) {
        this.fittingFrac = fittingFrac;
    }

    public String getGera() {
        return gera;
    }

    public void setGera(String gera) {
        this.gera = gera;
    }

    public String getGeraFrac() {
        return geraFrac;
    }

    public void setGeraFrac(String geraFrac) {
        this.geraFrac = geraFrac;
    }

    public String getGala() {
        return gala;
    }

    public void setGala(String gala) {
        this.gala = gala;
    }

    public String getGalaFrac() {
        return galaFrac;
    }

    public void setGalaFrac(String galaFrac) {
        this.galaFrac = galaFrac;
    }

    public String getShalwar() {
        return shalwar;
    }

    public void setShalwar(String shalwar) {
        this.shalwar = shalwar;
    }

    public String getShalwarFrac() {
        return shalwarFrac;
    }

    public void setShalwarFrac(String shalwarFrac) {
        this.shalwarFrac = shalwarFrac;
    }

    public String getPoncha() {
        return poncha;
    }

    public void setPoncha(String poncha) {
        this.poncha = poncha;
    }

    public String getPonchaFrac() {
        return ponchaFrac;
    }

    public void setPonchaFrac(String ponchaFrac) {
        this.ponchaFrac = ponchaFrac;
    }

    public String getFair() {
        return fair;
    }

    public void setFair(String fair) {
        this.fair = fair;
    }

    public String getFairFrac() {
        return fairFrac;
    }

    public void setFairFrac(String fairFrac) {
        this.fairFrac = fairFrac;
    }

    public String getAsan() {
        return asan;
    }

    public void setAsan(String asan) {
        this.asan = asan;
    }

    public String getAsanFrac() {
        return asanFrac;
    }

    public void setAsanFrac(String asanFrac) {
        this.asanFrac = asanFrac;
    }

    public String getTrouser() {
        return trouser;
    }

    public void setTrouser(String trouser) {
        this.trouser = trouser;
    }

    public String getTrouserFrac() {
        return trouserFrac;
    }

    public void setTrouserFrac(String trouserFrac) {
        this.trouserFrac = trouserFrac;
    }

    public String getTrouserPoncha() {
        return trouserPoncha;
    }

    public void setTrouserPoncha(String trouserPoncha) {
        this.trouserPoncha = trouserPoncha;
    }

    public String getTrouserPonchaFrac() {
        return trouserPonchaFrac;
    }

    public void setTrouserPonchaFrac(String trouserPonchaFrac) {
        this.trouserPonchaFrac = trouserPonchaFrac;
    }

    public String getTrouserFair() {
        return trouserFair;
    }

    public void setTrouserFair(String trouserFair) {
        this.trouserFair = trouserFair;
    }

    public String getTrouserFairFrac() {
        return trouserFairFrac;
    }

    public void setTrouserFairFrac(String trouserFairFrac) {
        this.trouserFairFrac = trouserFairFrac;
    }

    public String getTrouserAsan() {
        return trouserAsan;
    }

    public void setTrouserAsan(String trouserAsan) {
        this.trouserAsan = trouserAsan;
    }

    public String getTrouserAsanFrac() {
        return trouserAsanFrac;
    }

    public void setTrouserAsanFrac(String trouserAsanFrac) {
        this.trouserAsanFrac = trouserAsanFrac;
    }

    public String getCollar() {
        return collar;
    }

    public void setCollar(String collar) {
        this.collar = collar;
    }

    public String getCollarWidth() {
        return collarWidth;
    }

    public void setCollarWidth(String collarWidth) {
        this.collarWidth = collarWidth;
    }

    public String getFracCollarWidth() {
        return fracCollarWidth;
    }

    public void setFracCollarWidth(String fracCollarWidth) {
        this.fracCollarWidth = fracCollarWidth;
    }

    public String getCollarLength() {
        return collarLength;
    }

    public void setCollarLength(String collarLength) {
        this.collarLength = collarLength;
    }

    public String getFracCollarLength() {
        return fracCollarLength;
    }

    public void setFracCollarLength(String fracCollarLength) {
        this.fracCollarLength = fracCollarLength;
    }

    public String getKaff() {
        return kaff;
    }

    public void setKaff(String kaff) {
        this.kaff = kaff;
    }

    public String getKaffWidth() {
        return kaffWidth;
    }

    public void setKaffWidth(String kaffWidth) {
        this.kaffWidth = kaffWidth;
    }

    public String getFracKaffWidth() {
        return fracKaffWidth;
    }

    public void setFracKaffWidth(String fracKaffWidth) {
        this.fracKaffWidth = fracKaffWidth;
    }

    public String getKaffLength() {
        return kaffLength;
    }

    public void setKaffLength(String kaffLength) {
        this.kaffLength = kaffLength;
    }

    public String getFracKaffLength() {
        return fracKaffLength;
    }

    public void setFracKaffLength(String fracKaffLength) {
        this.fracKaffLength = fracKaffLength;
    }

    public String getSalaei() {
        return salaei;
    }

    public void setSalaei(String salaei) {
        this.salaei = salaei;
    }

    public String getGheera() {
        return gheera;
    }

    public void setGheera(String gheera) {
        this.gheera = gheera;
    }

    public String getDhaga() {
        return dhaga;
    }

    public void setDhaga(String dhaga) {
        this.dhaga = dhaga;
    }

    public String getFrontPocket() {
        return frontPocket;
    }

    public void setFrontPocket(String frontPocket) {
        this.frontPocket = frontPocket;
    }

    public String getSidePocket() {
        return sidePocket;
    }

    public void setSidePocket(String sidePocket) {
        this.sidePocket = sidePocket;
    }

    public String getShalwarPocket() {
        return shalwarPocket;
    }

    public void setShalwarPocket(String shalwarPocket) {
        this.shalwarPocket = shalwarPocket;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Customer){
            Customer customer = (Customer) obj;
            return this.cid.equals(customer.cid);
        }
        return false;
    }
}
