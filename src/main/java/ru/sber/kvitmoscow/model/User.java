package ru.sber.kvitmoscow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;


@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements GrantedAuthority {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    public User(Integer id, Role role, String name, String password, String email) {
        super(id);
        this.role = role;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    @Override
    public String getAuthority() {
        return getRole().getName();
    }
}
