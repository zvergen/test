package ru.testquest.ejb;

import org.apache.commons.codec.digest.DigestUtils;
import ru.testquest.domain.Credentials;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;

@Stateless
public class AuthManagerBean {

    public static final String INVALID_CREDENTIALS = "{\"status\":\"Invalid credentials\"}";

    @PersistenceContext(name = "sample_PU")
    private EntityManager entityManager;

    public Credentials createCredentials(String login, String password) {
        Credentials credentials = new Credentials();
        credentials.setLogin(login);
        credentials.setPassword(password);
        entityManager.persist(credentials);
        return credentials;
    }

    public boolean checkCookie(String sessionToken) {
        TypedQuery<Credentials> query = entityManager.createNamedQuery(Credentials.FIND_USER_COOKIE, Credentials.class);
        query.setParameter("token", sessionToken);
        try {
            Credentials credentials = query.getSingleResult();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String authenticate(String login, String password) {
        TypedQuery<Credentials> query = entityManager.createNamedQuery(Credentials.GET_USER_CREDENTIALS, Credentials.class);
        query.setParameter("login", login);
        query.setParameter("password", password);
        Credentials credentials = null;
        try {
            credentials = query.getSingleResult();
        } catch (Exception e) {
            return "Wrong login/password";
        }
        String sessionToken = createToken(login, password);
        credentials.setToken(sessionToken);
        entityManager.merge(credentials);
        return sessionToken;
    }

    private String createToken(String login, String password) {
        String toEncode = login + password + LocalDateTime.now().getNano();
        return DigestUtils.md5Hex(toEncode);
    }
}
