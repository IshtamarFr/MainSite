package fr.ishtamar.passwords.model.password;


import fr.ishtamar.passwords.model.category.Category;
import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.starter.model.user.UserInfo;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface PasswordService {
    Password getPasswordById(final Long id) throws EntityNotFoundException;
    String calculatePassword(Password password, String secretKey) throws NoSuchAlgorithmException;
    Password createPassword(CreatePasswordRequest request, UserInfo user, Category category);
    List<Password> getPasswordsByUser(UserInfo user);
    void deletePassword(Password password);
    Password modifyPassword(Password oldPassword,CreatePasswordRequest request, Category category);
    Password togglePasswordStatus(Password password, boolean status);
}
