package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Post extends BaseModel implements Serializable {

    private String content;
    private String created;
    private String updated;
    private List<Region> regions;
    private Role role;

    public Post(){

    }

    public Post(int id, String content) {
        super.id = id;
        this.content = content;
        this.created = null;
        this.updated = null;
        this.regions = null;
        this.role = Role.MODERATOR;
    }

    public Post(int id, String content, Role role) {
        this.id = id;
        this.content = content;
        this.created = null;
        this.updated = null;
        this.regions = null;
        this.role = role;
    }
}
