package cl.dsoto.StudentService.models;

import java.util.List;

/**
 * Created by root on 17-11-21.
 */
public class MyPageRequest {

    private int page;
    private int fetchSize;
    private String sort;
    private String sortDir;
    private String filter;

    public MyPageRequest() {
    }

    public MyPageRequest(int page, int fetchSize, String sort, String sortDir, String filter) {
        this.page = page;
        this.fetchSize = fetchSize;
        this.sort = sort;
        this.sortDir = sortDir;
        this.filter = filter;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
