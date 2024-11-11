package bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

public class TitleItemFilter implements ItemFilter{
    private String title;
    private boolean caseSensitive;

    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public TitleItemFilter(String title, boolean caseSensitive){
        setTitle(title);
        setCaseSensitive(caseSensitive);
    }

    @Override
    public boolean matches(StoreItem item){
        if(caseSensitive == true)
            return item.getTitle().equals(title);
        else
            return item.getTitle().equalsIgnoreCase(title);
    }
}
