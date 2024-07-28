package org.database_service.app.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "mail")
    private String mail;
    @Column(name = "user_id")
    private Long user_id;

    public Mail(String mail, Long user_id) {
        this.mail = mail;
        this.user_id = user_id;
    }
}
