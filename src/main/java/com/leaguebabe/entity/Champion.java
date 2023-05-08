package com.leaguebabe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Champion {

    @Id
    private String name;
    private String title;
    private String blurb;
    private String profilePictureUrl;
    private String[] tags;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Champion champion = (Champion) o;
        return getName() != null && Objects.equals(getName(), champion.getName());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
