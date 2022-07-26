package com.webapp.ygsschool.security;

import com.webapp.ygsschool.model.Person;
import com.webapp.ygsschool.model.Roles;
import com.webapp.ygsschool.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WisdomSchoolUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //Taking email and password submitted by user
        String email = authentication.getName();
        String pwd = authentication.getCredentials().toString();

        //Taking email from person database
        Person person = personRepository.findByEmail(email);

        //It will return null if no any person with provided mail id exists
        //Also check if password provided by user is matching with password stored in db(Hash format)
        if (person != null && person.getPersonId() > 0 && passwordEncoder.matches(pwd, person.getPwd())) {
            return new UsernamePasswordAuthenticationToken(
                    //Instead of returning password return null (Spring also by default erases everything)
                    email, null, getGrantedAuthorities(person.getRoles()));
        } else {
            throw new BadCredentialsException("Invalid Credentials!");
        }
    }

    //Taking the respective role and making it granted by using grantedAuthority
    public List<GrantedAuthority> getGrantedAuthorities(Roles roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + roles.getRoleName()));
        return grantedAuthorities;
    }

    //to check which authentication method we are using (here, we will be UsernamePassword Authentication)
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
