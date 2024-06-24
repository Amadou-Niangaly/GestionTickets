package com.example.GestionTicket.config;

import com.example.GestionTicket.entity.Utilisateur;
import com.example.GestionTicket.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> user = utilisateurRepository.findByNom(username);
        if (user.isPresent()) {
            var userObj= user.get();
            return User.builder()
                    .username(userObj.getNom())
                    .password(userObj.getMotDePasse())
                    .roles(getRoles(userObj))
                    .build();

        }else
        {
            throw new UsernameNotFoundException(username);
        }

    }

    private String[] getRoles(Utilisateur user) {
        if(user.getRole()==null){
            return new  String[]{"USER"} ;
        }
        return user.getRole().split(",");
    }


}
