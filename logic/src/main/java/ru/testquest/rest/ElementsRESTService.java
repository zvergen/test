package ru.testquest.rest;

import ru.testquest.domain.Element;
import ru.testquest.ejb.AuthManagerBean;
import ru.testquest.ejb.ElementManagerBean;
import ru.testquest.filter.Secured;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/element")
public class ElementsRESTService {

    @EJB
    private ElementManagerBean elementManagerBean;

    /*List of Elements*/

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMainElementList(@CookieParam("sessionToken") String sessionToken) {
        /*If sessionToken is exist or not Empty AND complete checking*/
        List<Element> elementList = elementManagerBean.getElementList(0);
        return Response.ok(elementList).build();
    }

    /*CrUD Elements*/

    @GET
    @Secured
    @Path("/{elementId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getElementById(@PathParam("elementId") long id,
                                   @CookieParam("sessionToken") String sessionToken) {
        Element element = elementManagerBean.getElementById(id);
        if (element == null) {
            return Response.noContent().build();
        }
        return Response.ok(element).build();
    }

    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createElement(Element element,
                                  @CookieParam("sessionToken") String sessionToken) {
        elementManagerBean.createElement(element);
        return Response.ok(element).build();
    }

    @PUT
    @Secured
    @Path("/{elementId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeElementDataById(Element newData,
                                          @CookieParam("sessionToken") String sessionToken) {
        Element element = elementManagerBean.updateElementData(newData);
        if (element == null) {
            return Response.status(204).build();
        }
        return Response.ok(element).build();
    }

    @DELETE
    @Secured
    @Path("/{elementId}")
    public Response deleteElement(@PathParam("elementId") long id,
                                  @CookieParam("sessionToken") String sessionToken) {
        if (elementManagerBean.deleteElement(id)) {
            return Response.ok("{\"message\":\"success\"}").build();
        }
        return Response.ok("{\"message\":\"object not found\"}").build();
    }

}
