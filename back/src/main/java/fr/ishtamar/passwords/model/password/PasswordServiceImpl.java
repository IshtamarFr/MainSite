package fr.ishtamar.passwords.model.password;

import fr.ishtamar.passwords.model.category.Category;
import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.starter.model.user.UserInfo;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class PasswordServiceImpl implements PasswordService {
    private final PasswordRepository repository;

    private static final String DIGITS = "0123456789";
    private static final String LOWERCASES = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SYMBOLS = "!@#$%^&*()-+={}[]|:;',<.>/?_";
    private static final String ALL_CHARS = DIGITS + LOWERCASES + UPPERCASES + SYMBOLS;

    private static final int PASSWORD_LENGTH = 64; // Adjust as needed
    private static final SecureRandom random = new SecureRandom();

    public PasswordServiceImpl(PasswordRepository repository){
        this.repository = repository;
    }

    @Override
    public Password getPasswordById(final Long id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(Password.class,"id",id.toString()));
    }

    @Override
    public String calculatePassword(Password password, String secretKey) throws NoSuchAlgorithmException {
        String candidate=password.getPasswordPrefix()
                +sha512(secretKey+password.getPasswordKey()+password.getPasswordPrefix());
        return candidate.substring(0,Math.min(password.getPasswordLength().intValue(),candidate.length()));
    }

    private String sha512(String message) throws NoSuchAlgorithmException {
        return Base64.getEncoder().encodeToString(
                MessageDigest.getInstance("SHA-512").digest(message.getBytes()));
    }

    public static String generatePassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        // Ensure at least one character from each group
        password.append(getRandomCharacter(DIGITS));
        password.append(getRandomCharacter(LOWERCASES));
        password.append(getRandomCharacter(UPPERCASES));
        password.append(getRandomCharacter(SYMBOLS));

        // Fill remaining characters randomly from the entire character set
        for (int i = password.length(); i < PASSWORD_LENGTH; i++) {
            password.append(getRandomCharacter(ALL_CHARS));
        }

        // Shuffle the characters for enhanced security
        shuffleCharacters(password);

        return password.toString();
    }

    public static String generatePrefix() {
        return String.valueOf(getRandomCharacter(LOWERCASES)) +
                getRandomCharacter(DIGITS) +
                getRandomCharacter(SYMBOLS) +
                getRandomCharacter(UPPERCASES);
    }

    private static char getRandomCharacter(String characterSet) {
        int randomIndex = random.nextInt(characterSet.length());
        return characterSet.charAt(randomIndex);
    }

    private static void shuffleCharacters(StringBuilder sb) {
        for (int i = 0; i < sb.length(); i++) {
            int swapIndex = random.nextInt(sb.length());
            char temp = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(swapIndex));
            sb.setCharAt(swapIndex, temp);
        }
    }

    @Override
    public Password createPassword(CreatePasswordRequest request, UserInfo user, Category category) {
        Password password= Password.builder()
                .passwordKey(generatePassword())
                .siteName(request.getSiteName())
                .category(category)
                .active(true)
                .build();

        if (request.getSiteLogin()!=null && !request.getSiteLogin().isEmpty() ) password.setSiteLogin(request.getSiteLogin());
        if (request.getSiteAddress()!=null && !request.getSiteAddress().isEmpty() ) password.setSiteAddress(request.getSiteAddress());
        if (request.getDescription()!=null && !request.getDescription().isEmpty() ) password.setDescription(request.getDescription());
        password.setPasswordLength(
                request.getPasswordLength()==null || request.getPasswordLength().isEmpty() ?
                        64L: Long.parseLong(request.getPasswordLength()));
        password.setPasswordPrefix(
                request.getPasswordPrefix()==null || request.getPasswordPrefix().isEmpty() ?
                        generatePrefix(): request.getPasswordPrefix());

        return repository.save(password);
    }

    @Override
    public List<Password> getPasswordsByUser(UserInfo user) {
        return repository.findAll()
                .stream()
                .filter(password-> Objects.equals(password.getCategory().getUser(),user))
                .toList();
    }

    @Override
    public void deletePassword(Password password) {
        repository.delete(password);
    }

    @Override
    public Password modifyPassword(Password oldPassword, CreatePasswordRequest request, Category category) {
        Password password= Password.builder()
                .id(oldPassword.getId())
                .passwordKey(oldPassword.getPasswordKey())
                .siteName(request.getSiteName())
                .category(category)
                .active(oldPassword.isActive())
                .build();

        if (request.getSiteLogin()!=null && !request.getSiteLogin().isEmpty() ) password.setSiteLogin(request.getSiteLogin());
        if (request.getSiteAddress()!=null && !request.getSiteAddress().isEmpty() ) password.setSiteAddress(request.getSiteAddress());
        if (request.getDescription()!=null && !request.getDescription().isEmpty() ) password.setDescription(request.getDescription());
        password.setPasswordLength(
                request.getPasswordLength()==null || request.getPasswordLength().isEmpty() ?
                        oldPassword.getPasswordLength(): Long.parseLong(request.getPasswordLength()));
        password.setPasswordPrefix(
                request.getPasswordPrefix()==null || request.getPasswordPrefix().isEmpty() ?
                        oldPassword.getPasswordPrefix(): request.getPasswordPrefix());

        return repository.save(password);
    }

    @Override
    public Password togglePasswordStatus(Password password, boolean status) {
        password.setActive(status);
        return repository.save(password);
    }
}
