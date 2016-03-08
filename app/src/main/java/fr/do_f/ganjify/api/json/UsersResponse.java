package fr.do_f.ganjify.api.json;

import java.util.List;

import fr.do_f.ganjify.api.json.object.User;

/**
 * Created by do_f on 26/02/16.
 */
public class UsersResponse {
    private List<User> users;

    public UsersResponse(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
