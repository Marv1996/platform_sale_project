package com.sale.repository;

import com.sale.model.CarStatementEntity;
import com.sale.model.StatementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarStatementRepository extends JpaRepository<CarStatementEntity, Integer> {

    List<CarStatementEntity> getByModelAndYear(String model, Integer year);

    List<CarStatementEntity> getByModel(String model);

    List<CarStatementEntity> getByYear(Integer year);

    List<CarStatementEntity> getByCarStatementIdNotAndStatementEntity(
            Integer carStatementId, StatementEntity entity
    );

    @Query(nativeQuery = true, value =
            "select * from car_statements where if(?1 is not null, lower(car_name)like concat('%', lower(?1), '%'), true)"
    )
    List<CarStatementEntity> search(String name);
}
