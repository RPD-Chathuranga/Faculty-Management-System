package authentication;

public class LoginController {

    private LoginService loginService;

    public LoginController() {
        loginService = new LoginService();
    }

    public boolean login(String username, String password) {
        return loginService.authenticate(username, password);
    }
}