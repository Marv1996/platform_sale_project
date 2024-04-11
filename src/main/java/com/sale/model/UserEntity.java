package com.sale.model;

import com.sale.common.constants.DatabaseConstants;
import com.sale.enums.Role;
import com.sale.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = DatabaseConstants.USER_TABLE_NAME)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String name;
    @Column(name = "last_name")
    private String surname;
    private String email;
    private String password;
    @Column(name = "verification_code")
    private String verifyCode;
    @Column(name = "reset_token")
    private String resetToken;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_statement_mapping",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "car_statement_id")
    )
    private Set<CarStatementEntity> carStatements;

    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
