package com.sale.dto.request;

import com.sale.enums.Type;
import com.sale.exceptions.statementexceptions.StatementBadRequestException;
import com.sale.model.StatementEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public abstract class StatementRequest {

    private static final String TITLE_NULL_MSG = "Statement title can't be null or empty";
    private static final String TITLE_REGEX = "^[a-zA-Z0-9 -//.]+";
    private static final String TITLE_MSG = "Title permitted characters: a-z, A-z, 0-9, spase, minus, dot";
    private static final int TITLE_MIN_SIZE = 5;
    private static final int TITLE_MAX_SIZE = 56;
    private static final String TITLE_SIZE_MSG =
            "Title must contain " + TITLE_MIN_SIZE + " - " + TITLE_MAX_SIZE + " characters";


    @NotEmpty(message = TITLE_NULL_MSG)
    @Pattern(regexp = TITLE_REGEX, message = TITLE_MSG)
    @Schema(description = "The title of statement", example = "title")
    @Length(message = TITLE_SIZE_MSG, min = TITLE_MIN_SIZE, max = TITLE_MAX_SIZE)
    private String title;
    private String description;
    private Integer price;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String location;

    public static StatementEntity toStatementEntity(StatementRequest request) {
        StatementEntity statementEntity = new StatementEntity();
        statementEntity.setTitle(request.getTitle());
        statementEntity.setDescription(request.getDescription());
        statementEntity.setPrice(request.getPrice());
        statementEntity.setType(request.getType());
        statementEntity.setLocation(request.getLocation());
        return statementEntity;
    }

    public static void validatePrice(Integer price) {
        try {
            Integer.parseInt(price.toString());
        } catch (Exception ex) {
            throw new StatementBadRequestException("Price is invalid");
        }
        if (price < 0) {
            throw new StatementBadRequestException("Price of product can't be negative");
        }
    }
}
