package cl.dsoto.StudentService.models;

import java.util.List;

/**
 * Created by dsoto on 17-11-21.
 */
/**
 * Clase que representa a la solicitud de una página
 */
public class MyPageRequest {

    /**
     * N° de página
     */
    private int page;

    /**
     * Cantidad de registros de una página
     */
    private int fetchSize;

    /**
     * Campo atributo del alumno de ordenamiento
     */
    private String sort;

    /**
     * Campo atributo del alumno de ordenamiento
     */
    private String sortDir;

    /**
     * Cadena de filtro
     */
    private String filter;

    /**
     * Constructor vacío para la deserialización del objeto
     */
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
