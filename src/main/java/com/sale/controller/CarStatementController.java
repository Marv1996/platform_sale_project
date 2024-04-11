package com.sale.controller;


import com.sale.common.constants.ApiConstants;
import com.sale.dto.request.CarStatementRequest;
import com.sale.dto.response.CarStatementResponse;
import com.sale.exceptions.carstatementexceptions.CarStatementApiException;
import com.sale.service.CarStatementService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(ApiConstants.BASE_URL + ApiConstants.VERSION + ApiConstants.API_CAR_STATEMENT)
@Slf4j
public class CarStatementController {

    @Autowired
    private CarStatementService carStatementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody CarStatementResponse createStatement(
            @Valid @RequestBody CarStatementRequest request, Principal principal
    ) throws CarStatementApiException {
        log.info("Create");
        String email = principal.getName();
        return carStatementService.create(request, email);
    }

    @GetMapping("/{id}")
    public @ResponseBody CarStatementResponse get(@PathVariable Integer id) throws CarStatementApiException {
        return carStatementService.get(id);
    }

    @GetMapping
    public @ResponseBody List<CarStatementResponse> getAll(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer year
    ) {
        return carStatementService.getAll(model, year);
    }

    @PutMapping("/{id}")
    public @ResponseBody CarStatementResponse update(
            @PathVariable Integer id, @RequestBody CarStatementRequest request, Principal principal
    ) throws CarStatementApiException {
        log.info("Update");
        String email = principal.getName();
        return carStatementService.update(id, request, email);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody void delete(@PathVariable Integer id, Principal principal) throws CarStatementApiException {
        String email = principal.getName();
        carStatementService.delete(id, email);
    }

    @GetMapping("/search")
    public @ResponseBody List<CarStatementResponse> search(@RequestParam String name) {
        return carStatementService.search(name);
    }
}
