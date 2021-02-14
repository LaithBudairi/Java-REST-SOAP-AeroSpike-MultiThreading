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
        AccountHolder accountHolder = accountHolderService.getAccountHolder(id);
        if(accountHolder == null) {
            return Response.noContent()
                    .build();
        }
        else {
            return Response.ok(accountHolder).build();
        }
    }

    @POST
    public Response createNewAccount(AccountHolder accountHolder, @Context UriInfo uriInfo) {
        AccountHolder createdAccount = accountHolderService.createNewAccount(accountHolder);
        String newId = String.valueOf(createdAccount.getId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
        return Response.created(uri)
                .entity(createdAccount)
                .build();
    }

    @PUT
    @Path("{id}")
    public Response UpdateAccountHolder(AccountHolder accountHolder, @PathParam("id") String id,  @Context UriInfo uriInfo) {
        if (accountHolderService.getAccountHolder(id) == null) {
            return Response.noContent()
                    .build();
        } else {
            accountHolderService.createNewAccount(accountHolder);
            return Response.created(uriInfo.getAbsolutePath())
                    .build();
        }
    }

    @DELETE
    @Path("{id}")
    public boolean deleteAccountById(@PathParam("id") String id) {
        return accountHolderService.deleteAccountById(id);
    }

}