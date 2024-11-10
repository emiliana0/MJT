package bg.sofia.uni.fmi.mjt.socialnetwork.post;

import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class SocialFeedPost implements Post {
    private UserProfile author;
    private String content;
    private String uniqueId;
    private LocalDateTime publishingTime;
    private Map<ReactionType, Set<UserProfile>> reactions;

    @Override
    public UserProfile getAuthor() {
        return author;
    }

    public void setAuthor(UserProfile author) {
        this.author = author;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public LocalDateTime getPublishedOn() {
        return publishingTime;
    }

    public void setPublishingTime(LocalDateTime publishingTime) {
        this.publishingTime = publishingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocialFeedPost that = (SocialFeedPost) o;
        return Objects.equals(uniqueId, that.uniqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uniqueId);
    }

    public SocialFeedPost(UserProfile author, String content) {
        setAuthor(author);
        setContent(content);
        setPublishingTime(LocalDateTime.now());
        this.uniqueId = UUID.randomUUID().toString();
        this.reactions = new HashMap<>();
    }

    @Override
    public boolean addReaction(UserProfile userProfile, ReactionType reactionType) {
        if (userProfile == null || reactionType == null)
            throw new IllegalArgumentException("Invalid argument!");
        if (reactions == null) {
            reactions = new HashMap<>();
        }
        boolean removed = false;
        if (removeReaction(userProfile)) {
            removed = true;
        }

        Set<UserProfile> usersWithReaction = reactions.get(reactionType);
        if (usersWithReaction == null) {
            usersWithReaction = new HashSet<>();
            reactions.put(reactionType, usersWithReaction);
        }

        usersWithReaction.add(userProfile);

        return !removed;
    }

    @Override
    public boolean removeReaction(UserProfile userProfile) {
        if (userProfile == null)
            throw new IllegalArgumentException("Invalid user!");
        if (reactions == null) {
            return false;
        }
        for (Map.Entry<ReactionType, Set<UserProfile>> entry : reactions.entrySet()) {
            Set<UserProfile> userSet = entry.getValue();
            if (userSet.remove(userProfile)) {
                if (userSet.isEmpty()) {
                    reactions.remove(entry.getKey());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<ReactionType, Set<UserProfile>> getAllReactions() {
        if (reactions == null)
            return Collections.emptyMap();
        return Collections.unmodifiableMap(reactions);
    }

    @Override
    public int getReactionCount(ReactionType reactionType) {
        if (reactionType == null) {
            throw new IllegalArgumentException("Invalid reaction type!");
        }
        if (reactions == null)
            return 0;
        if (reactions.containsKey(reactionType)) {
            if (reactions.get(reactionType) == null)
                return 0;
            return reactions.get(reactionType).size();
        }
        return 0;
    }

    @Override
    public int totalReactionsCount() {
        if (reactions == null)
            return 0;
        int count = 0;
        for (Set<UserProfile> userSet : reactions.values()) {
            count += userSet.size();
        }
        return count;
    }
}
