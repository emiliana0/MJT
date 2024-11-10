package bg.sofia.uni.fmi.mjt.socialnetwork;

import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.Post;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.SocialFeedPost;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.Interest;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SocialNetworkImpl implements SocialNetwork {
    private Set<UserProfile> registeredUsers;
    private Collection<Post> allPosts;

    @Override
    public void registerUser(UserProfile userProfile) throws UserRegistrationException {
        if (userProfile == null)
            throw new IllegalArgumentException("Invalid user!");
        if (registeredUsers == null)
            registeredUsers = new HashSet<>();
        if (registeredUsers.contains(userProfile))
            throw new UserRegistrationException("User already registered!");
        registeredUsers.add(userProfile);
    }

    @Override
    public Set<UserProfile> getAllUsers() {
        if (registeredUsers == null)
            return Collections.emptySet();
        return Collections.unmodifiableSet(registeredUsers);
    }

    @Override
    public Post post(UserProfile userProfile, String content) throws UserRegistrationException {
        if (userProfile == null || content == null || content.isEmpty())
            throw new IllegalArgumentException("Invalid argument");
        if (registeredUsers == null || !registeredUsers.contains(userProfile)) {
            throw new UserRegistrationException("User profile not registered!");
        }
        Post res = new SocialFeedPost(userProfile, content);
        if (allPosts == null) {
            allPosts = new HashSet<>();
        }
        allPosts.add(res);
        return res;
    }

    @Override
    public Collection<Post> getPosts() {
        if (allPosts == null)
            return null;
        return Collections.unmodifiableCollection(allPosts);
    }

    @Override
    public Set<UserProfile> getReachedUsers(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post cannot be null");
        }

        UserProfile author = post.getAuthor();
        Set<UserProfile> reachedUsers = new HashSet<>();

        Set<UserProfile> networkFriends = getAllConnectedFriends(author);

        for (UserProfile user : networkFriends) {
            if (hasCommonInterest(author, user)) {
                reachedUsers.add(user);
            }
        }

        return reachedUsers;
    }

    private boolean hasCommonInterest(UserProfile user1, UserProfile user2) {
        for (Interest interest : user1.getInterests()) {
            if (user2.getInterests().contains(interest)) {
                return true;
            }
        }
        return false;
    }

    private Set<UserProfile> getAllConnectedFriends(UserProfile user) {
        Set<UserProfile> visited = new HashSet<>();
        exploreNetwork(user, visited);
        return visited;
    }

    private void exploreNetwork(UserProfile user, Set<UserProfile> visited) {
        if (!visited.contains(user)) {
            visited.add(user);
            for (UserProfile friend : user.getFriends()) {
                exploreNetwork(friend, visited);
            }
        }
    }

    @Override
    public Set<UserProfile> getMutualFriends(UserProfile userProfile1, UserProfile userProfile2)
            throws UserRegistrationException {
        if (userProfile1 == null || userProfile2 == null) {
            throw new IllegalArgumentException("Invalid user!");
        }
        if (registeredUsers == null || !registeredUsers.contains(userProfile1) ||
                !registeredUsers.contains(userProfile2)) {
            throw new UserRegistrationException("User not registered!");
        }
        if (userProfile1.getFriends() == null || userProfile2.getFriends() == null)
            return new HashSet<>();
        Set<UserProfile> mutualFriends = new HashSet<>(userProfile1.getFriends());
        mutualFriends.retainAll(userProfile2.getFriends());

        return mutualFriends;
    }

    @Override
    public SortedSet<UserProfile> getAllProfilesSortedByFriendsCount() {
        if (registeredUsers == null)
            return Collections.unmodifiableSortedSet(new TreeSet<>());
        return Collections.unmodifiableSortedSet(new TreeSet<>(registeredUsers));
    }
}
