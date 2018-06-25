package ru.testquest.rest;

import ru.testquest.domain.Element;
import ru.testquest.ejb.AuthManagerBean;
import ru.testquest.ejb.ElementManagerBean;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/element")
public class ElementsRESTService {

    @EJB
    private ElementManagerBean elementManagerBean;
    @EJB
    private AuthManagerBean authManagerBean;

    /*List of Elements*/

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMainElementList(@CookieParam("sessionToken") String sessionToken) {
        /*If sessionToken is exist or not Empty AND complete checking*/
        if (sessionToken != null && !sessionToken.isEmpty() && authManagerBean.checkCookie(sessionToken)) {
            List<Element> elementList = elementManagerBean.getElementList(0);
            return Response.ok(elementList).build();
        }
        return Response.status(401).build();
    }

    /*CrUD Elements*/

    @GET
    @Path("/{elementId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getElementById(@PathParam("elementId") long id,
                                   @CookieParam("sessionToken") String sessionToken) {
        if (sessionToken != null && !sessionToken.isEmpty() && authManagerBean.checkCookie(sessionToken)) {
            Element element = elementManagerBean.getElementById(id);
            if (element == null) {
                return Response.noContent().build();
            }
            return Response.ok(element).build();
        }
        return Response.status(401).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createElement(Element element,
                                  @CookieParam("sessionToken") String sessionToken) {
        if (sessionToken != null && !sessionToken.isEmpty() && authManagerBean.checkCookie(sessionToken)) {
            elementManagerBean.createElement(element);
            return Response.ok(element).build();
        }
        return Response.status(401).build();
    }

    @PUT
    @Path("/{elementId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeElementDataById(Element newData,
                                          @CookieParam("sessionToken") String sessionToken) {
        if (sessionToken != null && !sessionToken.isEmpty() && authManagerBean.checkCookie(sessionToken)) {
            Element element = elementManagerBean.updateElementData(newData);
            if (element == null) {
                return Response.status(204).build();
            }
            return Response.ok(element).build();
        }
        return Response.status(401).build();
    }

    @DELETE
    @Path("/{elementId}")
    public Response deleteElement(@PathParam("elementId") long id,
                                  @CookieParam("sessionToken") String sessionToken) {
        if (sessionToken != null && !sessionToken.isEmpty() && authManagerBean.checkCookie(sessionToken)) {
            if (elementManagerBean.deleteElement(id)) {
                return Response.ok("{\"message\":\"success\"}").build();
            }
            return Response.ok("{\"message\":\"object not found\"}").build();
        }
        return Response.status(401).build();
    }

}
