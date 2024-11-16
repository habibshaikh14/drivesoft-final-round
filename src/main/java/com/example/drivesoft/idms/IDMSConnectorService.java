package com.example.drivesoft.idms;

import com.example.drivesoft.idms.exception.IDMSAccountListException;
import com.example.drivesoft.idms.exception.IDMSAuthenticationException;
import com.example.drivesoft.idms.objects.IDMSAccountListResponse;
import com.example.drivesoft.idms.objects.IDMSAuthorizationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The {@code IDMSConnectorService} class interacts with the IDMS (Identity Management System) API
 * to handle authentication and fetch account data. It uses {@link RestTemplate} to make HTTP requests
 * to the IDMS API endpoints and manages responses related to user authentication and account retrieval.
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @Service} - This class is marked as a service component in Spring's context, enabling it to be injected into other components.</li>
 * </ul>
 *
 * @since 1.0
 */
@Service
public class IDMSConnectorService {

  private final RestTemplate restTemplate;

  @Value("${idms.base.url}")
  private String baseUrl;

  @Value("${idms.username}")
  private String idmsUsername;

  @Value("${idms.password}")
  private String idmsPassword;

  @Value("${idms.institution.id}")
  private int institutionID;

  @Value("${idms.layout.id}")
  private int layoutID;

  @Value("${idms.account.status}")
  private String accountStatus;

  @Value("${idms.page.number}")
  private int pageNumber;

  /**
   * Constructs an instance of {@code IDMSConnectorService}.
   *
   * @param restTemplate the {@link RestTemplate} bean used to make HTTP requests
   */
  public IDMSConnectorService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * Retrieves the authentication token required for further API requests to the IDMS system.
   * This method makes a GET request to the IDMS authentication API with provided credentials
   * and returns the authentication token if the request is successful.
   *
   * @return the authentication token as a string
   * @throws IDMSAuthenticationException if the authentication fails
   */
  private String getAuthToken() {
    String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/api/authenticate/GetUserAuthorizationToken")
            .queryParam("username", idmsUsername)
            .queryParam("password", idmsPassword)
            .queryParam("InstitutionID", institutionID)
            .toUriString();

    try {
      IDMSAuthorizationResponse response = restTemplate.getForObject(url, IDMSAuthorizationResponse.class);
      if (response != null && response.getStatus() == 200) {
        return response.getToken();
      } else {
        throw new IDMSAuthenticationException("Failed to fetch token. Message: " +
                (response != null ? response.getMessage() : "No response from IDMS"));
      }
    } catch (Exception e) {
      throw new IDMSAuthenticationException("Error during authentication: " + e.getMessage(), e);
    }
  }

  /**
   * Fetches the account list from the IDMS system by making an authenticated API request.
   * This method uses the authentication token retrieved from {@link #getAuthToken()} to
   * fetch a list of accounts based on the specified query parameters.
   *
   * @return the {@link IDMSAccountListResponse} containing the account data
   * @throws IDMSAccountListException if the account list retrieval fails
   */
  public IDMSAccountListResponse getAccountList() {
    String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/api/Account/GetAccountList")
            .queryParam("Token", getAuthToken())
            .queryParam("LayoutID", layoutID)
            .queryParam("AccountStatus", accountStatus)
            .queryParam("InstitutionID", institutionID)
            .queryParam("PageNumber", pageNumber)
            .toUriString();

    try {
      IDMSAccountListResponse response = restTemplate.getForObject(url, IDMSAccountListResponse.class);
      if (response != null && Integer.parseInt(response.getStatus()) == 200) {
        return response;
      } else {
        throw new IDMSAccountListException("Failed to fetch account list. Message: " +
                (response != null ? response.getMessage() : "No response from IDMS"));
      }
    } catch (Exception e) {
      throw new IDMSAccountListException("Error during account list fetch: " + e.getMessage(), e);
    }
  }
}
