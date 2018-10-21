package ru.sber.kvitmoscow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "functions")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Function extends BaseEntity {
    @Column(name = "name")
    private String name;

    public Function(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
