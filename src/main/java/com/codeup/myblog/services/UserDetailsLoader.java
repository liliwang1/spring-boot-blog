package com.codeup.myblog.services;

import com.codeup.myblog.models.RoleRepository;
import com.codeup.myblog.models.User;
import com.codeup.myblog.models.UserRepository;
import com.codeup.myblog.models.UserWithRoles;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsLoader implements UserDetailsService {
    private final UserRepository usersDao;
    private final RoleRepository rolesDao;

    public UserDetailsLoader(UserRepository usersDao, RoleRepository rolesDao) {
        this.usersDao = usersDao;
        this.rolesDao = rolesDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersDao.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found for " + username);
        }

        return new UserWithRoles(user, rolesDao.ofUserWith(username));
    }
}
