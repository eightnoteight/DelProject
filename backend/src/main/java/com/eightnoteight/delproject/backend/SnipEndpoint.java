package com.eightnoteight.delproject.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.NotFoundException;
import com.googlecode.objectify.ObjectifyService;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Objects;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;


@Api(name = "snipapi",
     version = "v1",
     namespace = @ApiNamespace(
             ownerDomain = "backend.delproject.eightnoteight.com",
             ownerName = "backend.delproject.eightnoteight.com",
             packagePath = ""))
public class SnipEndpoint {
    public static final Logger logger = Logger.getLogger(SnipEndpoint.class.getName());
    static {
        ObjectifyService.register(Snip.class);
    }

    public static boolean _check(HttpServletRequest req) {
        return true;
//        return Objects.equals(req.getHeader("XAuth"), "915f4ba8-e5ee-11e5-8a8c-f01faf47f2e8");
    }

    @ApiMethod(name = "create",
               httpMethod = HttpMethod.POST)
    public Snip insert(Snip snip, HttpServletRequest req) {
        if (!_check(req)) {
            snip.setContent("unauthorized!");
            snip.setId((long) -1);
        }
        ofy().save().entity(snip).now();
        logger.info("SnipEndpoint.create: Snip(id=\"" + snip.getId() + "\")");
        return snip;
    }

    @ApiMethod(name = "get",
               httpMethod = HttpMethod.GET)
    public Snip get(@Named("id") Long id, HttpServletRequest req) throws NotFoundException {
        if (!_check(req)) {
            Snip snip = new Snip();
            snip.setContent("unauthorized!");
            snip.setId((long) -1);
        }
        logger.info("SnipEndpoint.get: Snip(id=\"" + id + "\")");
        Snip snip = ofy().load().type(Snip.class).id(id).now();
        if (snip == null) {
            throw new NotFoundException("couldn't find Snip(id=\"" + id +"\")");
        }
        return snip;
    }

    @ApiMethod(name = "delete",
               httpMethod = HttpMethod.DELETE)
    public void delete(@Named("id") Long id, HttpServletRequest req) throws NotFoundException {
        if (!_check(req)) {
            return;
        }
        logger.info("SnipEndpoint.delete: Snip(id=\"" + id + "\")");
        ofy().delete().type(Snip.class).id(id).now();
        // .now() can be removed as delete operation is allowed to happen asynchronously.
        // ofy().delete().type(Snip.class).id(id);
    }

}

