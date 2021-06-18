package cn.xjtu.iotlab.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Cert {
    @JsonIgnore
    private Integer id;
    private String cert;
    @JsonIgnore
    private String authoruser;
    private String authorizeduser;
    private String accesstype;

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

    public String getAccesstype() {
        return accesstype;
    }

    public void setAccesstype(String accesstype) {
        this.accesstype = accesstype;
    }
}
