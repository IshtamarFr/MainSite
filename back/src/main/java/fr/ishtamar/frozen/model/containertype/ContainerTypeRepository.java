package fr.ishtamar.frozen.model.containertype;

import fr.ishtamar.starter.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContainerTypeRepository extends JpaRepository<ContainerType,Long> {
    List<ContainerType> findByUser(UserInfo user);
}
