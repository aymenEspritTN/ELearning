package com.deltaVelorum.coursify.chapitre.entities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public File makeTempFileFromContent(String extension)
    {
        File tempFile;
        try {
            tempFile = File.createTempFile(java.util.UUID.randomUUID().toString(), "." + extension);
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return tempFile;
    }

    public ChapitreFile(int _chapitreId) //only this in constructor bc its the only HARD required field.
    {
        chapitreId = _chapitreId;
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
