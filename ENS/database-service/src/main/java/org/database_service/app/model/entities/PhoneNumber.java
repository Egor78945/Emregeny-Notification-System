package org.database_service.app.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "phone_numbers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "number")
    private String number;
    @Column(name = "user_id")
    private Long user_id;

    public PhoneNumber(String number, Long user_id) {
        this.number = number;
        this.user_id = user_id;
    }
}
