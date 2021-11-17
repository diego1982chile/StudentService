package cl.dsoto.StudentService.models;

import java.util.List;

/**
 * Created by root on 17-11-21.
 */
public class MyPageResponse {

    private List<Student> items;
    private long totalResults;
    private int limit;

    public MyPageResponse() {
    }

    public MyPageResponse(List<Student> items, long totalResults, int limit) {
        this.items = items;
        this.totalResults = totalResults;
        this.limit = limit;
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

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
