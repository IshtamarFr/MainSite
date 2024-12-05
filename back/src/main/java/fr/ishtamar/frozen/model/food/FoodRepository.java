package fr.ishtamar.frozen.model.food;

import fr.ishtamar.starter.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Long> {
    List<Food> findByUser(UserInfo user);
}
