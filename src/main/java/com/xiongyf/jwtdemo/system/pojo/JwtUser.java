package com.xiongyf.jwtdemo.system.pojo;

public class JwtUser {

    private String id;
    private String username;
    private String nickname;
    private String[] auths;

    public JwtUser() {
    }

    public JwtUser(String id, String username, String nickname, String[] auths) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.auths = auths;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAuths(String[] auths) {
        this.auths = auths;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String[] getAuths() {
        return auths;
    }

}
