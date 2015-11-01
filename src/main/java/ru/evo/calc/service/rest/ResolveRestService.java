package ru.evo.calc.service.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Dima on 28.10.2015.
 */
@Path("/resolve")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ResolveRestService {

    @GET()
    public String getWork(@QueryParam("expression") String expression) {
        return "Work!";
    }
}
