package cl.dsoto.StudentService.models;

import java.util.List;

/**
 * Created by dsoto on 17-11-21.
 */

/**
 * Clase que representa a la respuesta de una página
 */
public class CustomPageResponse {

    /**
     * Los registros de la página
     */
    private List<Student> students;


    private long totalItems;

    private int totalPages;

    private int currentPage;

    /**
     * Constructor vacío para la deserialización del objeto
     */
    public CustomPageResponse() {
    }

    public CustomPageResponse(List<Student> students, long totalItems, int totalPages, int currentPage) {
        this.students = students;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
