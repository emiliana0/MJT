package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GameBundle implements StoreItem {
    private String title;
    private BigDecimal price;
    private LocalDateTime releaseDate;
    private Game[] games;
    //private int gamesCount;
    static private double rating;
    static private int numberOfReviews;

    @Override
    public String getTitle(){
        return title;
    }

    @Override
    public BigDecimal getPrice() {
        return price.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public double getRating() {
        return rating/numberOfReviews;
    }

    @Override
    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }


    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public void rate(double rating) {
        if (rating >= 1 && rating <= 5) {
            this.rating += rating;
            numberOfReviews++;
        }
    }

    public Game[] getGames() {
        return games;
    }

    public void setGames(Game[] games) {
        this.games = games;
    }

    public GameBundle(String title, BigDecimal price, LocalDateTime releaseDate, Game[] games){
        setTitle(title);
        setPrice(price);
        setReleaseDate(releaseDate);
        setGames(games);
        rating = 0;
        numberOfReviews = 0;
    }
}
