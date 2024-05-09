package fr.ishtamar.starter.password;


import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;

public interface PasswordService {
    Password getTrucById(final Long id) throws EntityNotFoundException;
}
