package ru.testquest.rest;

import ru.testquest.ejb.AuthManagerBean;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

@Path("/auth")
public class AuthRESTService {

    @EJB
    private AuthManagerBean authManagerBean;

    @GET
    @Path("/echo")
    public Response echo(@QueryParam("message") String message,
                         @CookieParam("sessionToken") String sessionToken) {
        if (sessionToken == null || sessionToken.isEmpty()) {
            return Response.status(401).build();
        }
        if (authManagerBean.checkCookie(sessionToken)) {
            String msg = "{\"message\":\"" + message + "\",\"time\":\"" + LocalDateTime.now().toString() + "\"}";
            return Response.ok(msg).build();
        }
        return Response.status(401).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("login") String login,
                                     @FormParam("password") String password) {
        String result = authManagerBean.authenticate(login, password);
        String responseData = null;
        if (result.equals("Wrong login/password")) {
            responseData = "{\"status\":\"" + result + "\"}";
            return Response.ok().entity(responseData).build();
        }
        responseData = "{\"status\":\"success\",\"sessionToken\":\"" + result + "\"}";
        return Response.ok().entity(responseData).build();
    }
}
