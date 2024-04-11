package com.sale.service;

import com.sale.dto.request.CarStatementRequest;
import com.sale.dto.response.CarStatementResponse;
import com.sale.exceptions.carstatementexceptions.CarStatementApiException;

import java.util.List;

public interface CarStatementService {

    CarStatementResponse create(CarStatementRequest request, String email) throws CarStatementApiException;

    CarStatementResponse get(Integer id) throws CarStatementApiException;

    List<CarStatementResponse> getAll(String model, Integer year);

    CarStatementResponse update(Integer id, CarStatementRequest request, String email) throws CarStatementApiException;

    void delete(Integer id, String email) throws CarStatementApiException;

    List<CarStatementResponse> search(String name);
}
