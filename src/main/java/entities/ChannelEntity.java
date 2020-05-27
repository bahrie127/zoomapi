package entities;

import annonations.*;

@Table("channels")
public class ChannelEntity {

    @PrimaryKey
    @Column("id")
    private String id;

    @NotNull
    @Column("name")
    private String name;

    @NotNull
    @Column("type")
    private int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
