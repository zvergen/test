package ru.testquest.domain.rest;

import com.google.gson.Gson;
import ru.testquest.domain.Element;
import ru.testquest.ejb.ElementManagerBean;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/elem")
public class ElementsRESTService {

    @EJB
    private ElementManagerBean elementManagerBean;

    /*Main Elements*/

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMainElementList() {
        String json = new Gson().toJson(elementManagerBean.getAllElementZero());
        return Response.ok(json).build();
    }

    /*CrUD Elements*/

    @GET
    @Path("/edit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getElementById(@QueryParam("elemId") long id) {
        Element element = elementManagerBean.getElementById(id);
        if (element == null) {
            return Response.noContent().build();
        }
        String json = new Gson().toJson(element);
        return Response.ok(json).build();
    }

    @POST
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createElement(Element element) {
        elementManagerBean.createElement(element);
        return Response.accepted(element).build();
    }

    @PUT
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeElementDataById(Element newData) {
        Element element = elementManagerBean.updateElementData(newData);
        if (element == null) {
            return Response.notModified().build();
        }
        String json = new Gson().toJson(element);
        return Response.ok(json).build();
    }

    @DELETE
    @Path("/edit")
    public Response deleteElement(@QueryParam("elemId") long id) {
        if (elementManagerBean.deleteElement(id)) {
            return Response.ok().build();
        }
        return Response.notModified().build();
    }

    /*Child Elements*/

    @GET
    @Path("/childList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getElementListByParentId(@QueryParam("parentId") long id) {
        String json = new Gson().toJson(elementManagerBean.getAllChildElementById(id));
        return Response.ok(json).build();
    }

}
