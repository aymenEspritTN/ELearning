package com.deltaVelorum.coursify.course.entities;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hassa
 */

import java.util.Date;
import java.util.List;

public class Course {
     private long courseId;
    private String title;
    private String description;
    private float price;
    private String image;
    private boolean isActive;
    private Date updatedAt;
    private Date createdAt;
    private List<String> courseContent;
    private long instructorId;
    private String instructorName;
     //   private List<Review> reviews; // Collection of reviews associated with the course

    public Course(long courseId, String title, String description, float price, String image, boolean isActive, Date updatedAt, Date createdAt, List<String> courseContent, long instructorId, String instructorName) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.isActive = isActive;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.courseContent = courseContent;
        this.instructorId = instructorId;
        this.instructorName = instructorName;
    }
    public Course( String title, String description, float price, String image, boolean isActive, Date updatedAt, Date createdAt, List<String> courseContent,  String instructorName) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.isActive = isActive;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.courseContent = courseContent;
        this.instructorName = instructorName;
    }
 public Course( String title, String description, float price, String image, boolean isActive, Date updatedAt, Date createdAt, List<String> courseContent, long instructorId, String instructorName) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.isActive = isActive;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.courseContent = courseContent;
        this.instructorId = instructorId;
        this.instructorName = instructorName;
    }

    public Course(long aLong, String string, String string0, float aFloat, String string1, boolean aBoolean, java.sql.Date date, java.sql.Date date0, List object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Course(String title, String description, float price, String image, boolean isActive, Date createdAt, List<String> courseContent, String instructorName) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.courseContent = courseContent;
        this.instructorName = instructorName;
    }

    public Course() {

    }

    public Course(String s, String s1, float v) {
    }
    // Getters and Setters for all fields

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getCourseContent() {
        return courseContent;
    }

    public void setCourseContent(List<String> courseContent) {
        this.courseContent = courseContent;
    }

    public long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(long instructorId) {
        this.instructorId = instructorId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", isActive=" + isActive +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                ", courseContent=" + courseContent +
                ", instructorId=" + instructorId +
                ", instructorName='" + instructorName + '\'' +
                '}';
    }
    
private float averageRating;  // Assuming this is the property for average rating

    // ... other methods ...

    // Setter method for averageRating
    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }
    
    // Getter method for averageRating (optional)
    public float getAverageRating() {
        return averageRating;
    }
}
