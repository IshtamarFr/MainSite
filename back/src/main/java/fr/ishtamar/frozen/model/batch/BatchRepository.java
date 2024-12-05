package fr.ishtamar.frozen.model.batch;

import fr.ishtamar.frozen.model.food.Food;
import fr.ishtamar.starter.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch,Long> {
    List<Batch> findByUser(UserInfo user);
    List<Batch> findByFood(Food food);
}
