package kr.loplab.gnss05.activities.workmanager;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Worker {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String worker;
    private String name;
    private String user;
    private String password;

    public Worker(String worker, String name, String user, String password) {
        this.worker = worker;
        this.name = name;
        this.user = user;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", worker='" + worker + '\'' +
                ", name='" + name + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
