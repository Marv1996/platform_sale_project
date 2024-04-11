package com.sale.dto.request;

import com.sale.enums.Role;
import com.sale.exceptions.carstatementexceptions.CarStatementBadRequestException;
import com.sale.model.CarStatementEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CarStatementRequest extends StatementRequest {
    private String carName;
    private String model;
    private Integer year;
    private String color;

    public static void validateYear(Integer year) {
        int localDate = LocalDate.now().getYear();
        if (year < 1900 || year > localDate) {
            throw new CarStatementBadRequestException("Wrong year");
        }
    }

    public static CarStatementEntity toCarStatementEntity(CarStatementRequest request) {
        CarStatementEntity carStatementEntity = new CarStatementEntity();
        carStatementEntity.setName(request.getCarName());
        carStatementEntity.setModel(request.getModel());
        carStatementEntity.setYear(request.getYear());
        carStatementEntity.setColor(request.getColor());
        return carStatementEntity;
    }
}
