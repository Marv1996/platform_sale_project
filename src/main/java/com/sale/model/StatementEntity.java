package com.sale.model;

import com.sale.common.constants.DatabaseConstants;
import com.sale.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
@Table(name = DatabaseConstants.STATEMENT_TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
public class StatementEntity {
    @Id
    @Column(name = "statement_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statementId;
    private String title;
    private String description;
    private Integer price;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String location;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatementEntity that = (StatementEntity) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                type == that.type &&
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, price, type, location);
    }
}
