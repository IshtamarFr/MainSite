package fr.ishtamar.frozen.model.dishtype;

import fr.ishtamar.starter.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishTypeRepository extends JpaRepository<DishType,Long> {
    List<DishType> findByUser(UserInfo user);
}
