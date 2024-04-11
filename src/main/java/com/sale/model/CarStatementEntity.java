package com.sale.model;

import com.sale.common.constants.DatabaseConstants;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
@Table(name = DatabaseConstants.CAR_STATEMENT_TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
public class CarStatementEntity {
    @Id
    @Column(name = "car_statement_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carStatementId;
    @Column(name = "car_name")
    private String name;
    private String model;
    private Integer year;
    private String color;
    @OneToOne
    @JoinColumn(name = "statement_id", referencedColumnName = "statement_id")
    private StatementEntity statementEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarStatementEntity that = (CarStatementEntity) o;
        return Objects.equals(carStatementId, that.carStatementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carStatementId);
    }
}
