package fr.do_f.ganjify.api.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import fr.do_f.ganjify.api.json.object.User;

/**
 * Created by do_f on 23/02/16.
 */
public class StatsResponse
{
    @SerializedName("_id")
    private String      id;

    @SerializedName("__v")
    private int         v;

    private User        user;
    private String      type;
    private String      comment;
    private List<User>  friends;
    private String      created;

    public StatsResponse(String id, int v, User user, String type, String comment, List<User> friends, String created) {
        this.id = id;
        this.v = v;
        this.user = user;
        this.type = type;
        this.comment = comment;
        this.friends = friends;
        this.created = created;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
