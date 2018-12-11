package uk.gov.digital.ho.hocs.info;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uk.gov.digital.ho.hocs.info.dto.TeamDeleteActiveParentTopicsDto;
import uk.gov.digital.ho.hocs.info.exception.EntityNotFoundException;
import uk.gov.digital.ho.hocs.info.exception.TeamDeleteException;
import uk.gov.digital.ho.hocs.info.bulkupdate.BulkImportException;
import uk.gov.digital.ho.hocs.info.security.KeycloakException;

import static net.logstash.logback.argument.StructuredArguments.value;
import static org.springframework.http.HttpStatus.*;
import static uk.gov.digital.ho.hocs.info.logging.LogEvent.*;

@ControllerAdvice
@Slf4j
public class RestResponseExceptionHandler {

    @ExceptionHandler(KeycloakException.class)
    public ResponseEntity handle(KeycloakException e) {
        log.error("Keycloak exception: {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handle(EntityNotFoundException e) {
        log.error("Keycloak exception: {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(BulkImportException.class)
    public ResponseEntity handle(BulkImportException e) {
        log.error("BulkImportException: {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TeamDeleteException.class)
    public ResponseEntity<TeamDeleteActiveParentTopicsDto> handle(TeamDeleteException e) {
        log.error("Exception: {}", e.getMessage(), value(EVENT, TEAM_DELETED_FAILURE));
        return new ResponseEntity<>(e.getTeamDeleteActiveParentTopicsDto() ,PRECONDITION_FAILED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handle(Exception e) {
        log.error("Exception: {}", e.getMessage(), value(EVENT, UNCAUGHT_EXCEPTION));
        return new ResponseEntity<>(e.getMessage(), INTERNAL_SERVER_ERROR);
    }
}