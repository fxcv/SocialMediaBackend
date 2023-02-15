package me.springprojects.smbackend.services.verifications;

import lombok.AllArgsConstructor;
import me.springprojects.smbackend.entities.User;
import me.springprojects.smbackend.entities.dto.UserDTO;
import me.springprojects.smbackend.exceptions.InvalidUserArgumentException;
import me.springprojects.smbackend.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserServiceVerification {

    private final UserRepository userRepository;

    public void registerUserVerification(UserDTO userDTO){
        String name = userDTO.getName();
        String lastname = userDTO.getLastname();
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        String nameValidated = validateUserName(name);
        String lastnameValidated = validateUserLastname(lastname);
        String emailValidated = validateUserEmail(email);
        String passwordValidated = validateUserPassword(password);
        if(nameValidated!=null) throw new InvalidUserArgumentException(nameValidated);
        else if(lastnameValidated!=null) throw new InvalidUserArgumentException(lastnameValidated);
        else if(emailValidated!=null) throw new InvalidUserArgumentException(emailValidated);
        else if(passwordValidated!=null) throw new InvalidUserArgumentException(passwordValidated);
    }

    public void changeEmailVerification(String email){
        String emailValidated = validateUserEmail(email);
        if(emailValidated!=null) throw new InvalidUserArgumentException(emailValidated);
    }

    public void changePasswordVerification(String password){
        String passwordValidated = validateUserPassword(password);
        if(passwordValidated!=null) throw new InvalidUserArgumentException(passwordValidated);
    }

    public void verificateUserExistence(Optional<User> userOptional){
        if(userOptional.isEmpty()) throw new InvalidUserArgumentException("User does not exist.");
    }

    private String validateUserName(String name){
        if(name==null) return "Please provide a name.";
        if(name.length()<3) return "Please provide a correct name.";

        for(char c : name.toCharArray()){
            if(!Character.isLetter(c)) return "Only letters in name.";
            else if(!name.startsWith(c + "") && c == Character.toUpperCase(c)) return "Name should not contain uppercase characters except first character";
            else if(name.startsWith(c + "") && c == Character.toLowerCase(c)) return "Name should start with a capital letter";
        }

        return null;
    }

    private String validateUserLastname(String lastname){
        if(lastname==null) return "Please provide a lastname.";
        if(lastname.length()<3) return "Please provide a correct lastname.";

        for(char c : lastname.toCharArray()){
            if(!Character.isLetter(c)) return "Lastname should contain only letters.";
            else if(!lastname.startsWith(c + "") && c == Character.toUpperCase(c)) return "Lastname should not contain uppercase characters except first character";
            else if(lastname.startsWith(c + "") && c == Character.toLowerCase(c)) return "Lastname should start with a capital letter";
        }

        return null;
    }

    private String validateUserEmail(String email){
        if(email==null) return "Please provide an email.";
        boolean isExists = userRepository.checkIfEmailExists(email);
        if(isExists) return "This email is already registered.";

        String holder = "Please provide a correct email.";
        if(email.length()<5 || !email.contains("@") || !email.contains(".") || email.indexOf(".")<email.indexOf("@")) return holder;
        int count = 0;

        for(char c : email.toCharArray()){
            if(!Character.isLetterOrDigit(c) && count>=2) return holder;
            else if(c == '@' || c == '.') ++count;
            else if(Character.isWhitespace(c)) return holder;
        }

        String middlePart = email.substring(email.indexOf("@") + 1, email.indexOf("."));
        String endPart = email.substring(email.indexOf(".") + 1);
        String startPart = email.substring(0, email.indexOf("@"));
        if(startPart.length()<3 || middlePart.length()<3 || endPart.length()<2) return holder;

        return null;
    }

    private String validateUserPassword(String password){
        if(password==null) return "Please provide a password.";
        if(password.length()<8) return "Passwords must have the minimum length of 8";
        int upperCaseCount = 0;
        int specialCharacterCount = 0;
        int digitsCount = 0;

        for(char c : password.toCharArray()){
            if(Character.isDigit(c)) ++digitsCount;
            else if(Character.isUpperCase(c)) ++upperCaseCount;
            else if(!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) ++specialCharacterCount;
        }

        if(upperCaseCount<1) return "Password should contain at least one uppercase character.";
        else if(specialCharacterCount<1) return "Password should contain at least one special character.";
        else if(digitsCount<1) return "Password should contain at least one digit.";

        return null;
    }
}
