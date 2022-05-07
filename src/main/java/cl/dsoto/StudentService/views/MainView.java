package cl.dsoto.StudentService.views;

import cl.dsoto.StudentService.models.Student;
import cl.dsoto.StudentService.services.StudentService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Created by root on 22-04-22.
 */
@Route("")
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {

    private StudentService studentService;

    private Grid<Student> grid = new Grid<>(Student.class);

    private TextField filter = new TextField();

    private StudentFormView studentFormView;

    public MainView(StudentService studentService) {
        this.studentService = studentService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        filter.setPlaceholder("Filter by name...");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> updateList());

        studentFormView = new StudentFormView();
        studentFormView.addListener(ContactForm.SaveEvent.class, this::saveContact); 1
        studentFormView.addListener(ContactForm.DeleteEvent.class, this::deleteContact); 2
        studentFormView.addListener(ContactForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, studentFormView);
        content.addClassName("content");
        content.setSizeFull();

        add(filter, content);

        closeEditor();

        updateList();
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("rut", "name", "birth", "gender");
        grid.asSingleSelect().addValueChangeListener(event -> editContact(event.getValue()));
    }

    private void updateList() {
        //grid.setItems(studentService.getAll());

        grid.setItems(query -> {

            Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("rut").ascending());

            // Si viene especificado el ordenamiento, setear esta información en la búsqueda
            if(!query.getSortOrders().isEmpty()) {

                String sorted = query.getSortOrders().get(0).getSorted();
                String direction = query.getSortOrders().get(0).getDirection().name();

                if(direction.equalsIgnoreCase("ascending")) {
                    pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by(sorted).ascending());
                }
                else {
                    pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by(sorted).descending());
                }
            }

            if(filter.getValue().equals("")) {
                return studentService.getStudentsPaginated(pageable).stream();
            }
            else {
                return studentService.getStudentsPaginated(pageable, filter.getValue()).stream();
            }

        });
    }

    public void editContact(Student student) {

        if (student == null) {
            closeEditor();
        } else {
            studentFormView.setStudent(student);
            studentFormView.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        studentFormView.setStudent(null);
        studentFormView.setVisible(false);
        removeClassName("editing");
    }

}

