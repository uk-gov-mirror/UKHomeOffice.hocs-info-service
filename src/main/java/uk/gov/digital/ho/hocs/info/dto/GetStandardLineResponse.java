package uk.gov.digital.ho.hocs.info.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.gov.digital.ho.hocs.info.entities.StandardLine;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetStandardLineResponse {

    @JsonProperty("standardLine")
    List<StandardLineDto> standardLineDtos;

    public static GetStandardLineResponse from(List<StandardLine> standardLines) {
        List<StandardLineDto> standardLineDtos = standardLines.stream().map(StandardLineDto::from).collect(Collectors.toList());
        return new GetStandardLineResponse(standardLineDtos);
    }
}