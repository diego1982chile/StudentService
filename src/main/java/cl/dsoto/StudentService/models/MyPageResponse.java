package cl.dsoto.StudentService.models;

import java.util.List;

/**
 * Created by dsoto on 17-11-21.
 */
/**
 * Clase que representa a la respuesta de una página
 */
public class MyPageResponse {

    /**
     * Los registros de la página
     */
    private List<Student> items;

    /**
     * Cantidad total de registros
     */
    private long totalResults;

    /**
     * Cantidad de registros de la página
     */
    private int limit;

    /**
     * Constructor vacío para la deserialización del objeto
     */
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
