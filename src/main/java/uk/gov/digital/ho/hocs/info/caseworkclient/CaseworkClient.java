package uk.gov.digital.ho.hocs.info.caseworkclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import uk.gov.digital.ho.hocs.info.RestHelper;
import uk.gov.digital.ho.hocs.info.caseworkclient.dto.GetCaseworkCaseDataResponse;
import uk.gov.digital.ho.hocs.info.caseworkclient.dto.GetStagesResponse;
import uk.gov.digital.ho.hocs.info.exception.EntityNotFoundException;


import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.value;
import static uk.gov.digital.ho.hocs.info.logging.LogEvent.*;

@Slf4j
@Component
public class CaseworkClient {


    private final RestHelper restHelper;
    private final String serviceBaseURL;

    @Autowired
    public CaseworkClient(RestHelper restHelper,
                          @Value("${hocs.case-service}") String caseService) {
        this.restHelper = restHelper;
        this.serviceBaseURL = caseService;
    }

    public GetCaseworkCaseDataResponse getCase(UUID caseUUID) {
        ResponseEntity<GetCaseworkCaseDataResponse> response = restHelper.get(serviceBaseURL, String.format("/case/%s", caseUUID), GetCaseworkCaseDataResponse.class);

        if (response.getStatusCodeValue() == 200) {
            log.info("Got Input for Case: {}", caseUUID);
            return response.getBody();
        } else {
            log.error("Could not get Input; response: %s", response.getStatusCodeValue(), value(EVENT, CASEWORK_CLIENT_FAILURE));
            throw new EntityNotFoundException("Could not get Input; response: %s", response.getStatusCodeValue());

        }
    }

    public GetStagesResponse getCasesForTeam(UUID teamUUID) {
        ResponseEntity<GetStagesResponse> response = restHelper.get(serviceBaseURL, String.format("/team/%s/stage", teamUUID), GetStagesResponse.class);
        if (response.getStatusCodeValue() == 200) {
            log.info("Got Cases for Team: {}", teamUUID);
            return response.getBody();
        } else {
            log.error("Could not get Input; response: %s", response.getStatusCodeValue(), value(EVENT, CASEWORK_CLIENT_FAILURE));
            throw new EntityNotFoundException("Could not get Input; response: %s", response.getStatusCodeValue());
        }
    }
}