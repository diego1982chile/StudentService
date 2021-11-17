package cl.dsoto.StudentService.models;

import java.util.List;

/**
 * Created by root on 17-11-21.
 */
public class CustomPage {

    private List<Student> items;
    private long totalResults;
    private int count;
    private boolean hasMore;
    private int limit;
    private int offSet;

    public CustomPage() {
    }

    public CustomPage(List<Student> items, long totalResults, int count, boolean hasMore, int limit, int offSet) {
        this.items = items;
        this.totalResults = totalResults;
        this.count = count;
        this.hasMore = hasMore;
        this.limit = limit;
        this.offSet = offSet;
    }

    public List<Student> getItems() {
        return items;
    }

    public void setItems(List<Student> items) {
        this.items = items;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffSet() {
        return offSet;
    }

    public void setOffSet(int offSet) {
        this.offSet = offSet;
    }
}
