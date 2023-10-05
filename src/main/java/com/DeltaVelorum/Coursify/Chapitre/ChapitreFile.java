package com.DeltaVelorum.Coursify.Chapitre;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class ChapitreFile {
    private int id;
    private String name;
    private byte[] content;
    private int chapitreId;

    public void setContentFromPath(String filePath)
    {
        try
        {
            content = Files.readAllBytes(Path.of(filePath));
        }catch (Exception ex)
        {
            System.err.println("setContentFromPath Error: " + ex.getMessage());
        }
    }

    @Override
    public String toString() {
        return "ChapitreFile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content=" + Arrays.toString(content) +
                ", chapitreId=" + chapitreId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getChapitreId() {
        return chapitreId;
    }

    public void setChapitreId(int chapitreId) {
        this.chapitreId = chapitreId;
    }
}
