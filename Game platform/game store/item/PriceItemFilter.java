package bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

import java.math.BigDecimal;

public class PriceItemFilter implements ItemFilter {
    private BigDecimal lowerBound;
    private BigDecimal upperBound;

    public BigDecimal getLowerBound() {
        return lowerBound;
    }

    public BigDecimal getUpperBound() {
        return upperBound;
    }

    public void setLowerBound(BigDecimal lowerBound) {
        this.lowerBound = lowerBound;
    }
    public void setUpperBound(BigDecimal upperBound) {
        this.upperBound = upperBound;
    }

    public PriceItemFilter(BigDecimal lowerBound, BigDecimal upperBound){
        setLowerBound(lowerBound);
        setUpperBound(upperBound);
    }

    @Override
    public boolean matches(StoreItem item) {
        return item.getPrice().compareTo(getLowerBound()) >= 0 && item.getPrice().compareTo(getUpperBound()) <= 0;
    }
}