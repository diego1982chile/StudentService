package cl.dsoto.StudentService.views;

import cl.dsoto.StudentService.models.Student;
import cl.dsoto.StudentService.services.StudentService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

/**
 * Created by root on 22-04-22.
 */
public class StudentFormView extends FormLayout {

    private Student student;

    TextField rut = new TextField("Rut");
    TextField name = new TextField("Name");
    DatePicker birth = new DatePicker("Birth");
    ComboBox<String> gender = new ComboBox<>("Gender");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    // Other fields omitted
    Binder<Student> binder = new BeanValidationBinder<>(Student.class);

    public StudentFormView() {
        addClassName("contact-form");
        binder.bindInstanceFields(this);
        gender.setItems(Arrays.asList("F","M"));
        //company.setItemLabelGenerator(Company::getName);
        add(rut,
            name,
            birth,
            gender,
            createButtonsLayout());
    }



    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, student)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this, student)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setStudent(Student student) {
        this.student = student;
        binder.readBean(student);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(student);
            fireEvent(new SaveEvent(this, student));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class StudentFormEvent extends ComponentEvent<StudentFormView> {
        private Student student;

        protected StudentFormEvent(StudentFormView source, Student student) {
            super(source, false);
            this.student = student;
        }

        public Student getStudent() {
            return student;
        }

    }

    public static class SaveEvent extends StudentFormEvent {
        SaveEvent(StudentFormView source, Student student) {
            super(source, student);
        }
    }

    public static class DeleteEvent extends StudentFormEvent {
        DeleteEvent(StudentFormView source, Student student) {
            super(source, student);
        }
    }

    public static class CloseEvent extends StudentFormEvent {
        CloseEvent(StudentFormView source, Student student) {
            super(source, student);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}

