package Pysk.Enity;

import java.io.Serializable;

public class Reg implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer ID_REG;
    private String NAME;
    private static String LOGIN;
    private String PASSWORD;
    private Boolean ONLINE_REG;

    public Reg(String NAME, String LOGIN, String PASSWORD) {
        this.LOGIN = LOGIN;
        this.NAME = NAME;
        this.PASSWORD = PASSWORD;
    }

    public Reg() {
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public static String getLOGIN() {
        return LOGIN;
    }

    public void setLOGIN(String LOGIN) {
        this.LOGIN = LOGIN;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD=PASSWORD;
    }

    public Integer getID_REG() {
        return ID_REG;
    }

    public void setID_REG(Integer ID_REG) {
        this.ID_REG = ID_REG;
    }

    public Boolean getONLINE_REG() {
        return ONLINE_REG;
    }

    public void setONLINE_REG(Boolean ONLINE_REG) {
        this.ONLINE_REG = ONLINE_REG;
    }


}
