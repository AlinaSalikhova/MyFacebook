package com.example.alinasalikhova.myfacebook.pojo;

public class Tweet {
    private User user;
    private String id;
    private String creationDate;
    private String text;
    private String imageUrl;

    public Tweet(User user, String id, String creationDate, String text, String imageUrl) {
        this.user = user;
        this.id = id;
        this.creationDate = creationDate;
        this.text = text;
        this.imageUrl = imageUrl;
    }

    public User getUser() {
        return user;
    }

    public String getId() {
        return id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getText() {
        return text;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tweet tweet = (Tweet) o;

        if (!user.equals(tweet.user)) return false;
        if (!id.equals(tweet.id)) return false;
        if (!creationDate.equals(tweet.creationDate)) return false;
        if (!text.equals(tweet.text)) return false;
        return imageUrl != null ? imageUrl.equals(tweet.imageUrl) : tweet.imageUrl == null;
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        return result;
    }
}