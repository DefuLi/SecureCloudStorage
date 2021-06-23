package cn.xjtu.iotlab.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Cert {
    @JsonIgnore
    private Integer id;
    private String cert;
    @JsonIgnore
    private String authoruser;
    private String authorizeduser;
    private int accesstype;
    @JsonIgnore
    private String aes_key;
    @JsonIgnore
    private String rsa_key1;
    @JsonIgnore
    private String rsa_key2;
    @JsonIgnore
    private long opeart_k;
    @JsonIgnore
    private int cescmc_k;
    @JsonIgnore
    private int cescmc_n;

    public Cert() {
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getAuthoruser() {
        return authoruser;
    }

    public void setAuthoruser(String authoruser) {
        this.authoruser = authoruser;
    }

    public String getAuthorizeduser() {
        return authorizeduser;
    }

    public void setAuthorizeduser(String authorizeduser) {
        this.authorizeduser = authorizeduser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAccesstype() {
        return accesstype;
    }

    public void setAccesstype(int accesstype) {
        this.accesstype = accesstype;
    }

    public String getAeskey() {
        return aes_key;
    }

    public void setAeskey(String aeskey) {
        this.aes_key = aeskey;
    }

    public String getRsa_key1() {
        return rsa_key1;
    }

    public void setRsa_key1(String rsa_key1) {
        this.rsa_key1 = rsa_key1;
    }

    public String getRsa_key2() {
        return rsa_key2;
    }

    public void setRsa_key2(String rsa_key2) {
        this.rsa_key2 = rsa_key2;
    }

    public long getOpeart_k() {
        return opeart_k;
    }

    public void setOpeart_k(long opeart_k) {
        this.opeart_k = opeart_k;
    }

    public int getCescmc_k() {
        return cescmc_k;
    }

    public void setCescmc_k(int cescmc_k) {
        this.cescmc_k = cescmc_k;
    }

    public int getCescmc_n() {
        return cescmc_n;
    }

    public void setCescmc_n(int cescmc_n) {
        this.cescmc_n = cescmc_n;
    }
}
