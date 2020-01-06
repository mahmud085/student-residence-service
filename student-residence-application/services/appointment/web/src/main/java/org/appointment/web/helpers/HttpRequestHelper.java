package org.appointment.web.helpers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import org.appointment.common.Messages;
import org.appointment.common.exceptions.UnauthorizedException;
import org.appointment.service.models.Contract;
import org.appointment.web.Constants;
import org.appointment.web.models.User;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class HttpRequestHelper {
    private static final Client client = ClientBuilder.newClient(new ClientConfig(
            new JacksonJaxbJsonProvider().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    ));

    public static User validateAccessToken(String accessToken) throws Exception {
        try {
            String resourcePath = String.format("%s/%s/accessToken/{accessToken}/validation", ConfigHelper.getAuthServiceUrl(), Constants.AUTH_SERVICE_AUTHENTICATION_RESOURCE_PATH);

            Response response = client.target(resourcePath)
                    .resolveTemplate("accessToken", accessToken)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get();

            if (response.getStatus() == 200) {
                return response.readEntity(User.class);
            }

            return null;
        } catch (ProcessingException e) {
            throw new Exception("Error validating access token.");
        }
    }

    public static Contract getContract(String contractId, String authHeaderValue) throws Exception {
        try {
            String resourcePath = String.format("%s/%s/{contractId}", ConfigHelper.getContractServiceUrl(), Constants.CONTRACT_SERVICE_CONTRACT_RESOURCE_PATH);

            Response response = client.target(resourcePath)
                    .resolveTemplate("contractId", contractId)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", authHeaderValue)
                    .get();

            if (response.getStatus() == 200) {
                return response.readEntity(Contract.class);
            }

            if (response.getStatus() == 401) {
                throw new UnauthorizedException(Messages.UNAUTHORIZED_USER_FOR_CONTRACT_FOR_APPOINTMENT_CREATION);
            }

            return null;
        } catch (ProcessingException e) {
            throw new Exception("Error retrieving contract.");
        }
    }
}
