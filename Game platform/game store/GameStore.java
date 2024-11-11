package bg.sofia.uni.fmi.mjt.gameplatform.store;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.ItemFilter;

import java.math.BigDecimal;

public class GameStore implements StoreAPI{
    private StoreItem[] availableItems;
    private static boolean usedDiscount;

    public StoreItem[] getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(StoreItem[] availableItems) {
        this.availableItems = availableItems;
    }

    public GameStore(StoreItem[] availableItems){
        setAvailableItems(availableItems);
        usedDiscount = false;
    }

    @Override
    public StoreItem[] findItemByFilters(ItemFilter[] itemFilters) {
        StoreItem[] result = new StoreItem[availableItems.length];
        int index = 0;
        for (StoreItem item : availableItems) {
            boolean matchesAll = true;
            for (ItemFilter filter : itemFilters) {
                if (!filter.matches(item)) {
                    matchesAll = false;
                    break;
                }
            }
            if (matchesAll) {
                result[index++] = item;
            }
        }
        StoreItem[] filteredItems = new StoreItem[index];
        System.arraycopy(result, 0, filteredItems, 0, index);
        return filteredItems;
    }

    @Override
    public void applyDiscount(String promoCode) {
       if(usedDiscount == true)
           return;
        if(promoCode.equals("VAN40")){
            for(StoreItem item: availableItems){
                item.setPrice((item.getPrice().multiply(BigDecimal.valueOf(60)).divide(BigDecimal.valueOf(100))).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            usedDiscount = true;
        }
        else if(promoCode.equals("100YO")){
            for(StoreItem item: availableItems){
                item.setPrice(BigDecimal.valueOf(0).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            usedDiscount = true;
        }

    }

    @Override
    public boolean rateItem(StoreItem item, int rating) {
        if( 1 <= rating && rating <= 5){
            item.rate(rating);
            return true;
        }
        else
            return false;
    }
}
