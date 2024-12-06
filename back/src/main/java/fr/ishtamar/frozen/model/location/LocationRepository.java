package fr.ishtamar.frozen.model.location;

import fr.ishtamar.starter.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location,Long> {
    List<Location> findByUser(UserInfo user);
    List<Location> findByNameAndUser(String name,UserInfo user);
}
