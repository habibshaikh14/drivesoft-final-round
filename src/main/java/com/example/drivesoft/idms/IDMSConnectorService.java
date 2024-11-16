package com.example.drivesoft.idms;

import com.example.drivesoft.idms.objects.IDMSAccountListResponse;
import com.example.drivesoft.idms.objects.IDMSAuthorizationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

  public IDMSConnectorService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

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
        throw new RuntimeException("Failed to fetch token. Message: " +
                (response != null ? response.getMessage() : "No response"));
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to fetch authentication token: " + e.getMessage(), e);
    }
  }

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
        throw new RuntimeException("Failed to fetch account list. Message: " +
                (response != null ? response.getMessage() : "No response"));
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to fetch account list: " + e.getMessage(), e);
    }

  }
}
