package com.sale.controller;

import com.sale.common.constants.ApiConstants;
import com.sale.common.exceptions.ExceptionResponse;
import com.sale.dto.request.UserRequest;
import com.sale.exceptions.userexceptions.UserApiException;
import com.sale.model.UserEntity;
import com.sale.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.BASE_URL + ApiConstants.VERSION + ApiConstants.API_USER)
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Create a user with specified details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRequest.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request was sent to the endpoint", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "409", description = "The requested user already exists", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error occurred while creating the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody UserEntity createUser(
            @RequestBody @Valid UserRequest request
    ) throws UserApiException {
        log.info("Request to save user");
        UserEntity userEntity = userService.create(request);
        log.info("Request to save user is succeeded");
        return userEntity;
    }

    @Operation(summary = "Verify user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User verified",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRequest.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request was sent to the endpoint", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found with given details", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error occurred while creating the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})})
    @PatchMapping
    public @ResponseBody boolean userVerification(
            @RequestParam String email, @RequestParam String verifyCode
    ) throws UserApiException {
        return userService.verifyUser(email, verifyCode);
    }

    @Operation(summary = "Get user for given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRequest.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request was sent to the endpoint", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found with given details", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error occurred while creating the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})})
    @GetMapping("/{id}")
    public @ResponseBody UserEntity get(@PathVariable Integer id) throws UserApiException {
        return userService.get(id);
    }

    @Operation(summary = "Get list of users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRequest.class))}),
            @ApiResponse(responseCode = "500", description = "Error occurred while creating the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})})
    @GetMapping
    public @ResponseBody List<UserEntity> getAll() throws UserApiException {
        return userService.getAll();
    }

    @Operation(summary = "Update user for given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRequest.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request was sent to the endpoint", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found with given details", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error occurred while creating the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})})
    @PutMapping("/{id}")
    public @ResponseBody UserEntity updateUser(
            @PathVariable Integer id, @Valid @RequestBody UserRequest userRequest
    ) throws UserApiException {
        return userService.update(id, userRequest);
    }

    @Operation(summary = "Delete user for given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRequest.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request was sent to the endpoint", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found with given details", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error occurred while creating the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))})})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) throws UserApiException {
        userService.delete(id);
    }
}
