package ru.testquest.domain;

import javax.persistence.*;

@Entity
@Table(name = "t_credentials")
@NamedQueries({
        @NamedQuery(name = Credentials.GET_USER_CREDENTIALS,
                query = "SELECT c FROM Credentials c WHERE c.login = :login AND c.password = :password"),
        @NamedQuery(name = Credentials.FIND_USER_COOKIE,
                query = "SELECT c FROM Credentials c WHERE c.token = :token")
})
public class Credentials {

    public static final String GET_USER_CREDENTIALS = "Credentials.getUserCredentials";
    public static final String FIND_USER_COOKIE = "Credentials.findUserCookie";

    @Id
    private long id;
    private String login;
    private String password;
    private String token;

    public Credentials() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
