package fr.ishtamar.starter.password;


import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;

public interface PasswordService {
    Password getPasswordById(final Long id) throws EntityNotFoundException;
}
