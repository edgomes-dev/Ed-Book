package com.edgomesdev;

import io.quarkus.oidc.client.reactive.filter.OidcClientRequestReactiveFilter;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@RegisterProvider(OidcClientRequestReactiveFilter.class)
@Path("/")
public interface RestClientWithOidcClientFilter {
    @GET
    @Produces("text/plain")
    @Path("userName")
    Uni<String> getUserName();

    @GET
    @Produces("text/plain")
    @Path("adminName")
    Uni<String> getAdminName();
}
