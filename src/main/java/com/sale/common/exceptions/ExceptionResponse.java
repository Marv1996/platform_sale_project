package com.sale.common.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Exception response object
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
@JsonInclude(Include.NON_NULL)
public class ExceptionResponse {

    /**
     * message explaining the application error
     */
    private String message;

    /**
     * application error code
     */
    private String errorCode;

    /**
     * application error detail
     */
    private String errorDetail;

    /**
     * in of the used which information is duplicated
     */
    private Integer conflictingId;

    /**
     * List of error details
     */
    private List<String> errorDetails;

    /**
     * Constructor
     *
     * @param errorMessage error message
     * @param errorCode    error code
     */
    public ExceptionResponse(String errorMessage, String errorCode) {
        this.message = errorMessage;
        this.errorCode = errorCode;
    }

    /**
     * Constructor
     *
     * @param errorMessage error message
     * @param errorCode    error code
     * @param errorDetail  error detail (single message)
     */
    public ExceptionResponse(String errorMessage, String errorCode, String errorDetail) {
        this.message = errorMessage;
        this.errorCode = errorCode;
        this.errorDetail = errorDetail;
    }

    /**
     * Constructor
     *
     * @param errorMessage error message
     * @param errorCode    error code
     * @param errorDetails list of error detail messages
     */
    public ExceptionResponse(String errorMessage, String errorCode, List<String> errorDetails) {
        this.message = errorMessage;
        this.errorCode = errorCode;
        this.errorDetails = errorDetails;
    }

    /**
     * Constructor
     *
     * @param errorMessage  error message
     * @param errorCode     error code
     * @param conflictingId UUID of a conflicting object
     */
    public ExceptionResponse(String errorMessage, String errorCode, Integer conflictingId) {
        this.message = errorMessage;
        this.errorCode = errorCode;
        this.conflictingId = conflictingId;
    }
}
