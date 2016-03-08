package fr.do_f.ganjify.api.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by do_f on 26/02/16.
 */
public class StatsPost {

    private String          type;

    @SerializedName("friends")
    private List<String>    ids;
    private String          comment;

    public StatsPost(String type, List<String> ids, String comment) {
        this.type = type;
        this.ids = ids;
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
