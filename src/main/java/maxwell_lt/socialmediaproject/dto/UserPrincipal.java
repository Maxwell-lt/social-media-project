package maxwell_lt.socialmediaproject.dto;

import maxwell_lt.socialmediaproject.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = -4411360649795562701L;
    private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.isHasModeratorPermissions()) {
            authorities.add(new SimpleGrantedAuthority("moderator"));
        }
        if (user.isHasAdminPermissions()) {
            authorities.add(new SimpleGrantedAuthority("admin"));
        }
        authorities.add(new SimpleGrantedAuthority("user"));
        return authorities;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.getIsDeleted();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getIsDeleted();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !user.getIsDeleted();
    }

    @Override
    public boolean isEnabled() {
        return !user.getIsDeleted();
    }
}
