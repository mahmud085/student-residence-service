package org.authentication.web.authorization;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

public class CorsFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) {
        responseContext.getHeaders().putSingle(
                "Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().putSingle(
                "Access-Control-Allow-Headers", "*");
        responseContext.getHeaders().putSingle(
                "Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().putSingle(
                "Access-Control-Allow-Methods", "*");
    }
}
