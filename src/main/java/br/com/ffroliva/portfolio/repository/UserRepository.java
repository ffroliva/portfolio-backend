package br.com.ffroliva.portfolio.repository;

import br.com.ffroliva.portfolio.model.User;
import br.com.ffroliva.portfolio.model.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    @Query( value="SELECT user FROM User user JOIN FETCH user.userRoles userRole " +
            "WHERE user.username = :usernameOrEmail OR user.email = :usernameOrEmail")
    Optional<User> findByUsernameOrEmail(@Param("usernameOrEmail")String usernameOrEmail);

    Optional<List<User>> findByUserRoles(UserRole userRole);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
