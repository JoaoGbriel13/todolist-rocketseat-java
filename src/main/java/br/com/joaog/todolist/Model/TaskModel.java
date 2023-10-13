package br.com.joaog.todolist.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "tb_tasks")
public class TaskModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID ID;
    private UUID userID;
    private String description;
    @Column(length = 50)
    private String title;
    private LocalDateTime startedAt;
    private LocalDateTime endAt;
    private String priority;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public TaskModel(UUID ID, String description, String title, LocalDateTime startedAt, LocalDateTime endAt, String priority, LocalDateTime createdAt) {
        this.ID = ID;
        this.description = description;
        this.title = title;
        this.startedAt = startedAt;
        this.endAt = endAt;
        this.priority = priority;
        this.createdAt = createdAt;
    }

    public TaskModel() {
    }

    public UUID getID() {
        return ID;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws Exception {
        if (title.length() > 50){
            throw new Exception("O campo deve conter até 50 caracteres!");
        }
        this.title = title;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "ID=" + ID +
                ", userID=" + userID +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", startedAt=" + startedAt +
                ", endAt=" + endAt +
                ", priority='" + priority + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
