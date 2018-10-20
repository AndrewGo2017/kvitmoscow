package ru.sber.kvitmoscow.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class UserTo extends BaseTo{

    private Integer role;

    private String name;

    private String password;

    private String email;

    public UserTo(Integer id, Integer role, String name, String password, String email) {
        super(id);
        this.role = role;
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
