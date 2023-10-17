package com.deltaVelorum.coursify.chapitre.entities;

import java.util.Date;
public class Chapitre {
    private int id;
    private String name = "New chapitre";
    private String description = "No description.";
    private Date creationDate = new java.util.Date();
    private ChapitreType type = ChapitreType.Text;

    @Override
    public String toString() {
        return "Chapitre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", type=" + type +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setType(ChapitreType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public ChapitreType getType() {
        return type;
    }
}
