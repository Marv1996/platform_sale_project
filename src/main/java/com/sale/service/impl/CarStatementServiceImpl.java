package com.sale.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sale.dto.request.CarStatementRequest;
import com.sale.dto.request.StatementRequest;
import com.sale.dto.response.CarStatementResponse;
import com.sale.exceptions.carstatementexceptions.CarStatementApiException;
import com.sale.exceptions.carstatementexceptions.CarStatementNotFoundException;
import com.sale.model.CarStatementEntity;
import com.sale.model.StatementEntity;
import com.sale.model.UserEntity;
import com.sale.repository.CarStatementRepository;
import com.sale.repository.StatementRepository;
import com.sale.repository.UserRepository;
import com.sale.service.CarStatementService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.sale.common.constants.ExceptionMessageConstants.*;

@Service
@Slf4j
public class CarStatementServiceImpl implements CarStatementService {

    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private CarStatementRepository carStatementRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CarStatementResponse create(CarStatementRequest request, String email) throws CarStatementApiException {
        StatementRequest.validatePrice(request.getPrice());
        CarStatementRequest.validateYear(request.getYear());
        StatementEntity statementEntity = StatementRequest.toStatementEntity(request);
        CarStatementEntity carStatementEntity = CarStatementRequest.toCarStatementEntity(request);

        List<StatementEntity> statementEntities = statementRepository
                .findAll()
                .stream()
                .filter(se -> se.equals(statementEntity))
                .toList();

        if (!statementEntities.isEmpty()) {
            carStatementEntity.setStatementEntity(statementEntities.get(0));
        } else {
            statementEntity.setStatementId(0);
            StatementEntity newStatement;
            try {
                newStatement = statementRepository.save(statementEntity);
            } catch (Exception ex) {
                throw new CarStatementApiException(CREATING_STATEMENT_PROBLEM);
            }
            carStatementEntity.setStatementEntity(newStatement);
        }
        carStatementEntity.setCarStatementId(0);

        try {
            UserEntity userEntity = userRepository.getByEmail(email);
            Set<CarStatementEntity> carStatements = userEntity.getCarStatements();
            carStatements.add(carStatementEntity);
            userRepository.save(userEntity);
        } catch (Exception ex) {
            throw new CarStatementApiException(CREATING_STATEMENT_PROBLEM);
        }
        return new ObjectMapper().convertValue(carStatementEntity, CarStatementResponse.class);
    }

    @Override
    public CarStatementResponse get(Integer id) throws CarStatementApiException {
        Optional<CarStatementEntity> carStatementEntity;
        try {
            carStatementEntity = carStatementRepository.findById(id);
        } catch (Exception ex) {
            throw new CarStatementApiException(GETTING_STATEMENT_PROBLEM);
        }
        if (carStatementEntity.isEmpty()) {
            throw new CarStatementNotFoundException(STATEMENT_PROBLEM);
        }
        return new ObjectMapper().convertValue(carStatementEntity.get(), CarStatementResponse.class);
    }

    @Override
    public List<CarStatementResponse> getAll(String model, Integer year) {
        List<CarStatementEntity> carStatementEntities;
        if (!StringUtils.isEmpty(model) && year != null && !StringUtils.isEmpty(year + "")) {
            carStatementEntities = carStatementRepository.getByModelAndYear(model, year);
        } else if (!StringUtils.isEmpty(model)) {
            carStatementEntities = carStatementRepository.getByModel(model);
        } else if (year != null && !StringUtils.isEmpty(year + "")) {
            carStatementEntities = carStatementRepository.getByYear(year);
        } else {
            carStatementEntities = carStatementRepository.findAll();
        }
        return new ObjectMapper().convertValue(
                carStatementEntities, new TypeReference<>() {
                }
        );
    }

    @Override
    public CarStatementResponse update(
            Integer id, CarStatementRequest request, String email
    ) throws CarStatementApiException {
        log.info("Start updating");
        StatementRequest.validatePrice(request.getPrice());
        CarStatementRequest.validateYear(request.getYear());

        Optional<CarStatementEntity> carStatementEntities;
        try {
            carStatementEntities = carStatementRepository.findById(id);
        } catch (Exception ex) {
            throw new CarStatementApiException(UPDATING_STATEMENT_PROBLEM);
        }
        if (carStatementEntities.isEmpty()) {
            throw new CarStatementNotFoundException(STATEMENT_PROBLEM);
        }

        StatementEntity statementEntity = StatementRequest.toStatementEntity(request);
        CarStatementEntity carStatementEntity = CarStatementRequest.toCarStatementEntity(request);

        List<StatementEntity> statementEntities = statementRepository
                .findAll()
                .stream()
                .filter(se -> se.equals(statementEntity))
                .toList();

        if (!statementEntities.isEmpty()) {
            carStatementEntity.setStatementEntity(statementEntities.get(0));
        } else {
            statementEntity.setStatementId(carStatementEntities.get().getStatementEntity().getStatementId());
            StatementEntity newStatement;
            try {
                newStatement = statementRepository.save(statementEntity);
            } catch (Exception ex) {
                throw new CarStatementApiException(CREATING_STATEMENT_PROBLEM);
            }
            carStatementEntity.setStatementEntity(newStatement);
        }
        carStatementEntity.setCarStatementId(id);
        UserEntity userEntity = userRepository.getByEmail(email);
        Set<CarStatementEntity> carStatements = userEntity.getCarStatements();
        for (CarStatementEntity next : carStatements) {
            if (!next.equals(carStatementEntity)) {
                carStatements.add(carStatementEntity);
            }
        }
        userEntity.setCarStatements(carStatements);
        CarStatementEntity newCarStatement;
        try {
            userRepository.save(userEntity);
            newCarStatement = carStatementRepository.save(carStatementEntity);
        } catch (Exception ex) {
            throw new CarStatementApiException(CREATING_STATEMENT_PROBLEM);
        }
        log.info("End updating ");
        return new ObjectMapper().convertValue(newCarStatement, CarStatementResponse.class);
    }

    @Override
    public void delete(Integer id, String email) throws CarStatementApiException {
        Optional<CarStatementEntity> carStatementEntities;
        try {
            carStatementEntities = carStatementRepository.findById(id);
        } catch (Exception ex) {
            throw new CarStatementApiException(DELETING_STATEMENT_PROBLEM);
        }
        if (carStatementEntities.isEmpty()) {
            throw new CarStatementNotFoundException(STATEMENT_PROBLEM);
        }

        CarStatementEntity carStatementEntity = carStatementEntities.get();
        StatementEntity statementEntity = carStatementEntity.getStatementEntity();

        UserEntity userEntity = userRepository.getByEmail(email);
        Set<CarStatementEntity> carStatements = userEntity.getCarStatements();

        carStatements.removeIf(next -> next.equals(carStatementEntity));
        userEntity.setCarStatements(carStatements);
        try {
            userRepository.save(userEntity);
            List<CarStatementEntity> ignoreCases =
                    carStatementRepository.getByCarStatementIdNotAndStatementEntity(
                            id, statementEntity
                    );
            if (ignoreCases.isEmpty()) {
                statementRepository.deleteById(statementEntity.getStatementId());
            }
            carStatementRepository.deleteById(id);
        } catch (Exception ex) {
            throw new CarStatementApiException(DELETING_STATEMENT_PROBLEM);
        }
    }

    @Override
    public List<CarStatementResponse> search(String name) throws CarStatementApiException {
        List<CarStatementEntity> entities;
        try {
            entities = carStatementRepository.search(name);
        } catch (Exception ex) {
            throw new CarStatementApiException(SEARCHING_STATEMENT_PROBLEM);
        }
        return new ObjectMapper().convertValue(entities, new TypeReference<>() {
        });
    }
}
