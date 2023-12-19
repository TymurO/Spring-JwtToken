package com.example.webappovcharenkolab5.wrapper;

import com.example.webappovcharenkolab5.models.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class RoleWrapper implements GrantedAuthority {

    private Role role;

    @Override
    public String getAuthority() {
        return role.getName();
    }
}
