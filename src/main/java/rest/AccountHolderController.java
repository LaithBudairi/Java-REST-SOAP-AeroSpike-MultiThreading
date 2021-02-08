package rest;

import model.AccountHolder;
import service.AccountHolderService;
import service.impl.AccountHolderServiceImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

@Path("/accountHolders")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountHolderController {

    AccountHolderService accountHolderService = new AccountHolderServiceImpl();

    @GET
    public Response getAllAccountHolders() {
        return Response.ok(accountHolderService.getAllAccountHolders()).build();
    }

    @GET
    @Path("{id}")
    public Response getAccountHolderById(@PathParam("id") String id) {
        return Response.ok(accountHolderService.getAccountHolder(id)).build();
    }

    @POST
    public Response createNewBook(AccountHolder accountHolder, @Context UriInfo uriInfo) {
        AccountHolder createdAccount = accountHolderService.createNewAccount(accountHolder);
        String newId = String.valueOf(createdAccount.getId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();

        return Response.created(uri)
                .entity(createdAccount)
                .build();
    }


}