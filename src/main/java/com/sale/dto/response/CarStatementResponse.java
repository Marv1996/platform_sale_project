package com.sale.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarStatementResponse {
    @JsonIgnore
    private Integer carStatementId;
    @JsonProperty("name")
    private String carName;
    @JsonProperty("model")
    private String model;
    @JsonProperty("year")
    private Integer year;
    @JsonProperty("color")
    private String color;
    @JsonProperty("statementEntity")
    private StatementResponse statementResponse;
}
