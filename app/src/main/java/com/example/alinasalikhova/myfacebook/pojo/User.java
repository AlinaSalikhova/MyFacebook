package com.example.alinasalikhova.myfacebook.pojo;

import java.util.Objects;

public class User {
    private int id;
    private String imageUrl;
    private String name;
    private String nick;
    private int friendsCount;

    public User(int id,
                String imageUrl,
                String name,
                String nick,
                int friendsCount) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.nick = nick;
        this.friendsCount = friendsCount;
    }
    public long getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getNick() {
        return nick;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (friendsCount != user.friendsCount) return false;
        if (imageUrl != null ? !imageUrl.equals(user.imageUrl) : user.imageUrl != null)
            return false;
        if (!name.equals(user.name)) return false;
        if (!nick.equals(user.nick)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + name.hashCode();
        result = 31 * result + nick.hashCode();
        result = 31 * result + friendsCount;
        return result;
    }
}