package fr.ishtamar.starter.password;


import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;

import java.security.NoSuchAlgorithmException;

public interface PasswordService {
    Password getPasswordById(final Long id) throws EntityNotFoundException;
    String calculatePassword(Password password, String secretKey) throws NoSuchAlgorithmException;
}
