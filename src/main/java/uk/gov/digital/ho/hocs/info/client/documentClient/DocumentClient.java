package uk.gov.digital.ho.hocs.info.client.documentClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import uk.gov.digital.ho.hocs.info.application.RestHelper;
import uk.gov.digital.ho.hocs.info.client.documentClient.dto.CreateManagedDocumentResponse;
import uk.gov.digital.ho.hocs.info.client.documentClient.dto.CreateManagedDocumentRequest;
import uk.gov.digital.ho.hocs.info.client.documentClient.dto.ProcessDocumentRequest;
import uk.gov.digital.ho.hocs.info.client.documentClient.model.ManagedDocumentType;
import uk.gov.digital.ho.hocs.info.domain.exception.ApplicationExceptions;

import java.util.UUID;

@Slf4j
@Component
public class DocumentClient {

    private final String documentQueue;
    private final ProducerTemplate producerTemplate;
    private final ObjectMapper objectMapper;
    private final RestHelper restHelper;
    private final String serviceBaseURL;

    @Autowired
    public DocumentClient(RestHelper restHelper,
                          @Value("${hocs.document-service}") String documentService,
                          ProducerTemplate producerTemplate,
                          @Value("${docs.queue}") String documentQueue,
                          ObjectMapper objectMapper) {
        this.restHelper = restHelper;
        this.serviceBaseURL = documentService;

        this.producerTemplate = producerTemplate;
        this.documentQueue = documentQueue;
        this.objectMapper = objectMapper;
    }

    public UUID createDocument(UUID referenceUUID, String displayName, ManagedDocumentType type) throws ApplicationExceptions.EntityCreationException {
        CreateManagedDocumentRequest request = new CreateManagedDocumentRequest(displayName, type, referenceUUID);
        ResponseEntity<CreateManagedDocumentResponse> response = restHelper.post(serviceBaseURL, "/document", request, CreateManagedDocumentResponse.class);
        if (response.getStatusCodeValue() == 200) {
            log.info("Created Managed Document {}, Reference {}", response.getBody().getUuid(), referenceUUID);
            return response.getBody().getUuid();
        } else {
            throw new ApplicationExceptions.EntityCreationException("Could not create Managed Document; response: %s", response.getStatusCodeValue());
        }
    }

    public void processDocument(ManagedDocumentType type, UUID documentUUID, String fileLocation) throws ApplicationExceptions.EntityCreationException {
        ProcessDocumentRequest request = new ProcessDocumentRequest(documentUUID, fileLocation);

        try {
            producerTemplate.sendBody(documentQueue, objectMapper.writeValueAsString(request));
            log.info("Processed Document {}", documentUUID);
        } catch (JsonProcessingException e) {
            throw new ApplicationExceptions.EntityCreationException("Could not process Document: %s", e.toString());
        }
    }

    public void deleteDocument(UUID documentUUID) throws ApplicationExceptions.EntityCreationException {
        ResponseEntity<Void> response = restHelper.delete(serviceBaseURL, String.format("/document/%s", documentUUID), Void.class);

        if(response.getStatusCodeValue() == 200) {
            log.info("Deleted Document {}", documentUUID);
        } else {
            throw new ApplicationExceptions.EntityCreationException("Could not delete Document; response: %s", response.getStatusCodeValue());
        }
    }
}
