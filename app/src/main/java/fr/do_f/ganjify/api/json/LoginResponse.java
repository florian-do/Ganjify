package fr.do_f.ganjify.api.json;

/**
 * Created by do_f on 22/02/16.
 */
public class LoginResponse {

    private String  token;
    private String  redirect;

    public LoginResponse(String token, String redirect) {
        this.token = token;
        this.redirect = redirect;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    static public class User
    {
        private String  email;
        private String  password;

        public User(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
