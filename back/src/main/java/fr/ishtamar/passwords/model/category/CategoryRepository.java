package fr.ishtamar.passwords.model.category;

import fr.ishtamar.starter.model.user.UserInfo;
import fr.ishtamar.starter.standard.StdEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends StdEntityRepository<Category> {
    List<Category> findByNameAndUser(String name, UserInfo user);
    List<Category> findByUser(UserInfo user);
}
