package pl.zajavka.infrastructure.security;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUserName(@Length(min = 5) String userName);
}
