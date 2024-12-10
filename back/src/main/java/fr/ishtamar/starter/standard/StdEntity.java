package fr.ishtamar.starter.standard;

import fr.ishtamar.starter.model.user.UserInfo;

public interface StdEntity {
    Long getId();
    String getName();
    UserInfo getUser();
}
