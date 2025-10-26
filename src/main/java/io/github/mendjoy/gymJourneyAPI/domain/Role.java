package io.github.mendjoy.gymJourneyAPI.domain;

import io.github.mendjoy.gymJourneyAPI.domain.enums.RoleName;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleName name;

    public Role() {
    }

    public Role(RoleName name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + name;
    }
}
