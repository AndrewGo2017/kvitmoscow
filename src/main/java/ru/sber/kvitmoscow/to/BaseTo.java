package ru.sber.kvitmoscow.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseTo {
    public Integer id;

    public boolean isNew() {
        return id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseTo baseEntity = (BaseTo) o;
        return id != null && id.equals(baseEntity.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }
}
