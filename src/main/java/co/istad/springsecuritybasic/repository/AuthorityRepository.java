package co.istad.springsecuritybasic.repository;

import co.istad.springsecuritybasic.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority,String> {
   Optional<Authority> findByName(String name);
}
