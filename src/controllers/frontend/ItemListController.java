package controllers.frontend;

import DAO.StudentDAO;
import controllers.backend.NotificationsController;
import facade.FacadeFrontend;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import model.Student;
import util.Settings;
import util.Settings.Icons;

/**
 * FXML Controller class
 *
 * @author acmne
 */
public class ItemListController implements Initializable {

    private Student student;
    
    @FXML
    private ImageView imgIcon;
    @FXML
    private Label lblName;
    @FXML
    private Label lblId;
    @FXML
    private Label lblInstituation;
    @FXML
    private BorderPane root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void loadStudent(Student student) {
        Image image = new Image((student.getGenere() == Settings.Genere.MASCULINO)
                    ? Icons.USER_MALE.getIconPath()
                    : Icons.USER_FEMALE.getIconPath());
        this.imgIcon.setImage(image);
        this.lblName.setText(student.getFirstName() + " " + student.getLastName());
        this.lblInstituation.setText(student.getInstitution().getName());
        this.lblId.setText("Matricula: "+ student.getRegistration());
        this.student = student;
    }

    @FXML
    private void show(ActionEvent event) throws IOException {
        FacadeFrontend.getInstance().loadStudent(this.student);
    }

    @FXML
    private void delete(ActionEvent event) {
        try {
            StudentDAO studentDAO = new StudentDAO();
            studentDAO.delete(student);
            NotificationsController.getInstance().sucessNotification("Sucesso!", "Aluno: "+ student + " Foi deletado com sucesso!");
            FacadeFrontend.getInstance().removeItemList(this.root);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ItemListController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ItemListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
