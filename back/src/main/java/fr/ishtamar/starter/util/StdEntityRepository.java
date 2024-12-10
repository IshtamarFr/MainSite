package fr.ishtamar.starter.util;

import fr.ishtamar.starter.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface StdEntityRepository<T extends StdEntity> extends JpaRepository<T,Long> {
    List<T> findByUser(UserInfo user);
    List<T> findByNameAndUser(String name,UserInfo user);
}
