package com.codeup.myblog.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50, unique = true)
    private String userName;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date createDate;

    @Column
    private String avatarUrl;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        email = copy.email;
        userName = copy.userName;
        password = copy.password;
    }

    public User(String userName, String email, String password, Date createDate, String avatarUrl, List<Post> posts) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.createDate = createDate;
        this.avatarUrl = avatarUrl;
        this.posts = posts;
    }

    public User(long id, String userName, String email, String password, Date createDate, String avatarUrl, List<Post> posts) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.createDate = createDate;
        this.avatarUrl = avatarUrl;
        this.posts = posts;
    }

    public User(String userName, String email, String password, Date createDate, String avatarUrl) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.createDate = createDate;
        this.avatarUrl = avatarUrl;
    }

    public User(long id, String userName, String email, String password, Date createDate, String avatarUrl) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.createDate = createDate;
        this.avatarUrl = avatarUrl;
    }

    public User() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
