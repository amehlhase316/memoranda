package memoranda.AuthenticationService;

public enum LoginReturns {
    LOGIN_SUCCESSFUL("User has successfully logged in"),
    USER_NOT_FOUND("User information not found, try again or create a new account"),
    INCORRECT_PASSWORD("The password does not match"),
    CREATED_ACCOUNT_INFO("User account has been successfully created"),
    USERNAME_TAKEN("This username has already been taken"),
    PASSWORD_CHANGED("The password has successfully been changed");
    private String statement;
    LoginReturns(String returnStatement) {
        statement = returnStatement;
    }
    @Override
    public String toString(){
        return statement;
    }
}
