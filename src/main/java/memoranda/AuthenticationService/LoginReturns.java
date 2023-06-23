package memoranda.AuthenticationService;

/**
 * @Author Ryan Dinaro
 * All possible states of login requests. String representation is a short description of the state
 */
public enum LoginReturns {
    LOGIN_SUCCESSFUL("User has successfully logged in"),
    USER_NOT_FOUND("User information not found"),
    INCORRECT_PASSWORD("The password does not match"),
    CREATED_ACCOUNT_INFO("User account has been successfully created"),
    USERNAME_TAKEN("This username has already been taken"),
    PASSWORD_CHANGED("The password has successfully been changed");
    private final String stateOfLoginRequest;

    /**
     * @param state - Description of state
     */
    LoginReturns(String state) {
        stateOfLoginRequest = state;
    }

    /**
     * @return - the description of the state
     */
    @Override
    public String toString(){
        return stateOfLoginRequest;
    }
}
