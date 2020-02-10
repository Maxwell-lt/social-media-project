package maxwell_lt.socialmediaproject.dto;

public class LikeResult {
    private final boolean success;
    private final int userLikes;
    private final int totalLikes;

    public LikeResult(boolean success, int userLikes, int totalLikes) {
        this.success = success;
        this.userLikes = userLikes;
        this.totalLikes = totalLikes;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getUserLikes() {
        return userLikes;
    }

    public int getTotalLikes() {
        return totalLikes;
    }
}
