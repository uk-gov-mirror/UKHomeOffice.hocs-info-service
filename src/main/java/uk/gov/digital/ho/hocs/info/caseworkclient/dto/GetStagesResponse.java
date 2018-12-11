package uk.gov.digital.ho.hocs.info.caseworkclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetStagesResponse {

    @JsonProperty("stages")
    private Set<StageDto> stages;
}