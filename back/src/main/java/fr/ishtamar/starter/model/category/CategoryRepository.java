package fr.ishtamar.starter.model.category;

import fr.ishtamar.starter.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByNameAndUser(String name, UserInfo user);
    List<Category> findByUser(UserInfo user);
}
