package fr.do_f.ganjify.api.json.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by do_f on 23/02/16.
 */
public class User
{
    @SerializedName("_id")
    private String          id;
    @SerializedName("__v")
    private int             v;

    private String          email;
    private String          username;
    private String          name;
    private String          provider;
    private List<String>    roles;

    public User(String id, int v, String email, String username, String name, String provider, List<String> roles) {
        this.id = id;
        this.v = v;
        this.email = email;
        this.username = username;
        this.name = name;
        this.provider = provider;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
