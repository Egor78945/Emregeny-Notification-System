package org.database_service.app.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "register_date")
    private String registerDate;

    public User(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
        this.registerDate = builder.registerDate;
    }

    public static Builder builder() {
        return new User().new Builder();
    }

    public class Builder {
        private Long id;
        private String email;
        private String password;
        private String registerDate;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setRegisterDate(String registerDate) {
            this.registerDate = registerDate;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
