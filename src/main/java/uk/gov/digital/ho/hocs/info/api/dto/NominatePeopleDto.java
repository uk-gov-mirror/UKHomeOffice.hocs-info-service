package uk.gov.digital.ho.hocs.info.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import uk.gov.digital.ho.hocs.info.domain.model.NominatedContact;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class NominatePeopleDto {

    @JsonProperty("emailAddress")
    String emailAddress;

    public static NominatePeopleDto from(NominatedContact nominatedContact){
        return new NominatePeopleDto(nominatedContact.getEmailAddress());
    }
}
