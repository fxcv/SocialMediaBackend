package me.springprojects.smbackend.services.verifications;

import lombok.AllArgsConstructor;
import me.springprojects.smbackend.entities.dto.UserDTO;
import me.springprojects.smbackend.exceptions.InvalidUserArgumentException;
import me.springprojects.smbackend.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserServiceVerification {

    private final UserRepository userRepository;

    public void registerUserVerification(UserDTO userDTO){
        usernameVerification(userDTO.getUsername());
        emailVerification(userDTO.getEmail());
        passwordVerification(userDTO.getPassword());
    }

    public void emailVerification(String email){
        String emailValidated = validateUserEmail(email);
        if(emailValidated!=null) throw new InvalidUserArgumentException(emailValidated);
    }

    public void passwordVerification(String password){
        String passwordValidated = validateUserPassword(password);
        if(passwordValidated!=null) throw new InvalidUserArgumentException(passwordValidated);
    }

    public void usernameVerification(String username){
        String usernameValidated = validateUsername(username);
        if(usernameValidated!=null) throw new InvalidUserArgumentException(usernameValidated);
    }

    private String validateUsername(String name){
        if(name==null) return "Please provide a username.";
        boolean isExists = userRepository.checkIfUserExists(name);
        if(isExists) return "This user is already registered";
        if(name.length()<4) return "Please provide username with the minimum length of 4";

        for(char c : name.toCharArray()){
            if(!Character.isLetterOrDigit(c)) return "Username can not contain special characters.";
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
