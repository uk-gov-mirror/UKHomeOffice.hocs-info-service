package uk.gov.digital.ho.hocs.info.caseworkclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StageDto {

//    @JsonProperty("uuid")
//    private UUID uuid;

//    @JsonProperty("created")
//    private LocalDateTime created;
//
//    @JsonProperty("stageType")
//    private String stageType;
//
//    @JsonProperty("deadline")
//    private LocalDate deadline;

//    @JsonProperty("status")
//    private String status;

    @JsonProperty("caseUUID")
    private UUID caseUUID;

//    @JsonProperty("teamUUID")
//    private UUID teamUUID;

//    @JsonProperty("userUUID")
//    private UUID userUUID;

//    @JsonProperty("caseReference")
//    private String caseReference;

//    @JsonProperty("caseType")
//    private String caseDataType;

//    @JsonRawValue
//    private String data;

}