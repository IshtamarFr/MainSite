package fr.ishtamar.starter.model.password;


import fr.ishtamar.starter.model.category.Category;
import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.starter.exceptionhandler.GenericException;
import fr.ishtamar.starter.model.user.UserInfo;

import java.security.NoSuchAlgorithmException;

public interface PasswordService {
    Password getPasswordById(final Long id) throws EntityNotFoundException;
    String calculatePassword(Password password, String secretKey) throws NoSuchAlgorithmException;
    Password createPassword(CreatePasswordRequest request, UserInfo user, Category category);
}
