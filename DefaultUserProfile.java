package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

public class DefaultUserProfile implements UserProfile, Comparable<UserProfile> {
    private String username;
    private Collection<Interest> interests;
    private Collection<UserProfile> friends;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<Interest> getInterests() {
        if (interests == null)
            return Collections.emptyList();
        return Collections.unmodifiableCollection(interests);
    }

    @Override
    public Collection<UserProfile> getFriends() {
        if (friends == null)
            return Collections.emptyList();
        return Collections.unmodifiableCollection(friends);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DefaultUserProfile(String username) {
        setUsername(username);
        this.interests = new HashSet<>();
        this.friends = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultUserProfile that = (DefaultUserProfile) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

    @Override
    public boolean addInterest(Interest interest) {
        if (interest == null)
            throw new IllegalArgumentException("Invalid interest!");
        if (interests == null) {
            interests = new HashSet<Interest>();
        }
        if (interests.contains(interest))
            return false;
        else {
            interests.add(interest);
            return true;
        }
    }

    @Override
    public boolean removeInterest(Interest interest) {
        if (interest == null)
            throw new IllegalArgumentException("Invalid interest!");
        if (interests == null || interests.isEmpty())
            return false;
        if (interests.contains(interest)) {
            interests.remove(interest);
            return true;
        } else
            return false;
    }

    @Override
    public boolean addFriend(UserProfile userProfile) {
        if (userProfile == null || userProfile.getUsername().equals(username))
            throw new IllegalArgumentException("Invalid user profile!");
        if (friends == null)
            friends = new HashSet<>();
        if (isFriend(userProfile)) {
            return false;
        } else {
            friends.add(userProfile);
            userProfile.addFriend(this);
            return true;
        }
    }

    @Override
    public boolean unfriend(UserProfile userProfile) {
        if (userProfile == null)
            throw new IllegalArgumentException("Invalid user!");
        if (friends != null && isFriend(userProfile)) {
            friends.remove(userProfile);
            userProfile.unfriend(this);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isFriend(UserProfile userProfile) {
        if (userProfile == null)
            throw new IllegalArgumentException("Invalid user profile!");
        return friends != null && friends.contains(userProfile);
    }

    @Override
    public int compareTo(UserProfile other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot compare with null");
        }

        int friendCountComparison = Integer.compare(other.getFriends().size(), this.getFriends().size());

        if (friendCountComparison != 0) {
            return friendCountComparison;
        }

        return this.username.compareTo(other.getUsername());
    }
}
