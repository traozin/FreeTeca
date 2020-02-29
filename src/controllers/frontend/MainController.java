package controllers.frontend;

import controllers.backend.AudioController;
import facade.FacadeFrontend;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import util.MaskFieldUtil;
import util.Settings.Scenes;
import util.Settings.Slider;

/**
 * FXML Controller class
 *
 * @author acmne
 */
public class MainController implements Initializable {

    @FXML
    private ImageView imageViewSlider;
    @FXML
    private ToggleButton btnAccessbility;
    @FXML
    private Button btnSettings;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblInfo;
    @FXML
    private Label btnRegister;
    @FXML
    private Button btnEntry;

    @FXML
    private VBox homePanel;
    @FXML
    private HBox hBoxGit;
    @FXML
    private Pane paneRoot;

    private boolean activated;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MaskFieldUtil.reproducer(txtEmail);
        this.activated = false;
        this.imageViewSlider.setImage(new Image(Slider.FIRST.getImagePath()));
        this.setAllBinds();
        this.startSlide();
    }

    private void setAllBinds() {
        this.btnAccessbility.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                AudioController.getInstance().setCanReproduce(newValue);
            }
        });
    }

    private void startSlide() {
        this.activated = true;
        new Thread(() -> {
            LinkedList<Image> list = new LinkedList();
            list.add(new Image(Slider.values()[0].getImagePath()));
            list.add(new Image(Slider.values()[1].getImagePath()));
            list.add(new Image(Slider.values()[2].getImagePath()));
            while (activated) {
                for (Image image : list) {
                    Platform.runLater(() -> {
                        this.imageViewSlider.setImage(image);
                    });
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    @FXML
    private void openSettings(ActionEvent event) {
        this.activated = !this.activated;
    }

    @FXML
    private void openRegister(MouseEvent event) {
        this.changeSideBar(Scenes.REGISTER_PERSON);
    }

    public void changeSideBar(Scenes scene) {
        try {
            Parent loadedScreen = FacadeFrontend.getInstance().getScreen(scene);
            Parent removedScreen = (Parent) this.paneRoot.getChildren().remove(0);
            FacadeFrontend.getInstance().addScreen(Scenes.HOME_SIDE, removedScreen);
            this.paneRoot.getChildren().add(loadedScreen);
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void hide(MouseEvent event) {
        Platform.runLater(() -> {
            this.hBoxGit.setVisible(false);
        });
    }

    @FXML
    private void show(MouseEvent event) {
        Platform.runLater(() -> {
            this.hBoxGit.setVisible(true);
        });
    }

}
