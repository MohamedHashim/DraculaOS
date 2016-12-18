package sfe.os;

import apps.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main extends Application {

    Stage mainStage;
    public static FileSystem fileSystem;
    static CPU cpu = new CPU();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        primaryStage.setTitle("Desktop");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setScene(desktopScene());
        fileSystem = new FileSystem(cpu);
        primaryStage.show();
    }

    private Scene desktopScene() {
        BorderPane desktop = new BorderPane();

        desktop.setBackground(new Background(new BackgroundImage(
                new Image("res/gravity.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

        desktop.setBottom(taskBar());

        return new Scene(desktop);
    }

    private BorderPane taskBar() {
        BorderPane taskBar = new BorderPane();
        taskBar.setCenter(apps());
        taskBar.setRight(clock());
        taskBar.setLeft(turnOff());

        return taskBar;
    }

    private HBox apps() {
        HBox appsBar = new HBox(10);
        Label fileExplorer = new Label(null, new ImageView("res/FileExplorer.png"));
        fileExplorer.setAlignment(Pos.CENTER);
        fileExplorer.setOnMouseEntered(event1 -> {
            fileExplorer.setTranslateY(-6.0);
        });
        fileExplorer.setOnMouseExited(event1 -> {
            fileExplorer.setTranslateY(0);
        });
        fileExplorer.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                if(event.getClickCount() == 1) {
                    new Explorer(cpu);
                }
            }
        });

        Label imageViewerApp = new Label(null, new ImageView("res/ImageViewer.png"));
        imageViewerApp.setAlignment(Pos.CENTER);
        imageViewerApp.setOnMouseEntered(event1 -> {
            imageViewerApp.setTranslateY(-6.0);
        });
        imageViewerApp.setOnMouseExited(event1 -> {
            imageViewerApp.setTranslateY(0);
        });
        imageViewerApp.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                if(event.getClickCount() == 1) {
                    Process p=new Process("Image viewer");
                    cpu.addProcess(p);
                    cpu.RR_Schedule();
                    new ImageViewer(null,p.getId(), cpu);
                }
            }
        });
        Label memoApp = new Label(null, new ImageView("res/Memo.png"));
        memoApp.setAlignment(Pos.CENTER);
        memoApp.setOnMouseEntered(event1 -> {
            memoApp.setTranslateY(-6.0);
        });
        memoApp.setOnMouseExited(event1 -> {
            memoApp.setTranslateY(0);
        });
        memoApp.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                if(event.getClickCount() == 1) {
                    Process p = new Process("Memo");
                    cpu.addProcess(p);
                    if(cpu.list.size()==1){
                        cpu.RR_Schedule();
                    }
                    new Memo(null, p.getId(), cpu);
                }
            }
        });
        Label musicPlayerApp = new Label(null, new ImageView("res/MusicPlayer.png"));
        musicPlayerApp.setAlignment(Pos.CENTER);
        musicPlayerApp.setOnMouseEntered(event1 -> {
            musicPlayerApp.setTranslateY(-6.0);
        });
        musicPlayerApp.setOnMouseExited(event1 -> {
            musicPlayerApp.setTranslateY(0);
        });
        musicPlayerApp.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                if(event.getClickCount() == 1) {
                    Process p = new Process("MusicPlayer");
                    cpu.addProcess(p);
                    if(cpu.list.size()==1){
                        cpu.RR_Schedule();
                    }
                    new FXMediaPlayer(null, p.getId(), cpu);
                }
            }
        });

        Label videoPlayerApp = new Label(null, new ImageView("res/VideoPlayer.png"));
        videoPlayerApp.setAlignment(Pos.CENTER);
        videoPlayerApp.setOnMouseEntered(event1 -> {
            videoPlayerApp.setTranslateY(-6.0);
        });
        videoPlayerApp.setOnMouseExited(event1 -> {
            videoPlayerApp.setTranslateY(0);
        });
        videoPlayerApp.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                if(event.getClickCount() == 1) {
                    Process p1 = new Process("MediaPlayer");
                    cpu.addProcess(p1);
                    new FXMediaPlayer(null, p1.getId(), cpu);
                }
            }
        });

        Label browserApp = new Label(null, new ImageView("res/Space.png"));
        browserApp.setAlignment(Pos.CENTER);
        browserApp.setOnMouseEntered(event1 -> {
            browserApp.setTranslateY(-6.0);
        });
        browserApp.setOnMouseExited(event1 -> {
            browserApp.setTranslateY(0);
        });
        browserApp.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                if(event.getClickCount() == 1) {
                    System.out.println("Opening the WebBrowser...");
                    Process p = new Process("WebBrowser");
                    cpu.addProcess(p);
                    if(cpu.list.size()==1){
                        cpu.RR_Schedule();
                    }
                    new WebBrowser(WebBrowser.defaultUrl, p.getId(), cpu);
                }
            }
        });

        Label calculator = new Label(null, new ImageView("res/Calculator.png"));
        calculator.setAlignment(Pos.CENTER);
        calculator.setOnMouseEntered(event1 -> {
            calculator.setTranslateY(-6.0);


        });
        calculator.setOnMouseExited(event1 -> {
            calculator.setTranslateY(0);

        });
        calculator.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {

                if(event.getClickCount() == 1) {
                    Process p = new Process("Calculator");
                    cpu.addProcess(p);
                    if(cpu.list.size()==1){
                        cpu.RR_Schedule();
                    }
                    new Calc(cpu,p.getId());

                }
            }
        });

        Label xo = new Label(null, new ImageView("res/xo.png"));
        xo.setAlignment(Pos.CENTER);
        xo.setOnMouseEntered(event1 -> {
            xo.setScaleX(1);
            xo.setScaleY(1);
        });
        xo.setOnMouseExited(event1 -> {
            xo.setScaleX(1);
            xo.setScaleY(1);
        });
        xo.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                if(event.getClickCount() == 1) {
                    Process p = new Process("xo");
                    cpu.addProcess(p);
                    if(cpu.list.size()==1){
                        cpu.RR_Schedule();
                    }
                    new TicTacToe(cpu, p.getId());

                }
            }
        });

        Label downloader = new Label(null, new ImageView("res/Threads.png"));
        downloader.setAlignment(Pos.CENTER);
        downloader.setOnMouseEntered(event1 -> {
            downloader.setTranslateY(-6.0);
        });
        downloader.setOnMouseExited(event1 -> {
            downloader.setTranslateY(0);
        });
        downloader.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                if(event.getClickCount() == 1) {
                    Process p = new Process("downloader");
                    cpu.addProcess(p);
                    if(cpu.list.size()==1){
                        cpu.RR_Schedule();
                    }
                    new Downloader(cpu, p.getId());

                }
            }
        });





        appsBar.getChildren().addAll(fileExplorer, imageViewerApp, musicPlayerApp, browserApp, calculator,downloader);
        appsBar.setBackground(new Background(new BackgroundFill(Color.web("#000000", 0), new CornerRadii(5), new Insets(0, 350, 0, 350))));
        appsBar.setPadding(new Insets(5, 0, 5, 0));
        appsBar.setTranslateY(-350);
        appsBar.setAlignment(Pos.CENTER);
        return appsBar;
    }

    private HBox turnOff() {
        ImageView turnOff = new ImageView("res/powerOff.png");
        HBox box = new HBox(turnOff);
        turnOff.setStyle("-fx-opacity: 0.4;");
        box.setOnMouseExited(event -> turnOff.setStyle("-fx-opacity: 0.4;"));
        box.setOnMouseEntered(event -> turnOff.setStyle("-fx-opacity: 1;"));
        box.setOnMouseClicked(event -> {
            fileSystem.store();
            System.exit(0);
        });
        box.setBackground(new Background(new BackgroundFill(Color.web("#000000", 0), new CornerRadii(5), new Insets(5, 5, 0, 5))));
        box.setPadding(new Insets(0, 0, 20, 40));
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    private HBox clock() {
        Label label = new Label();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), actionEvent -> {
                    Calendar time = Calendar.getInstance();
                    String hourString = StringUtilities.pad(2, ' ', time.get(Calendar.HOUR) == 0 ? "12" : time.get(Calendar.HOUR) + "");
                    String minuteString = StringUtilities.pad(2, '0', time.get(Calendar.MINUTE) + "");
                    String secondString = StringUtilities.pad(2, '0', time.get(Calendar.SECOND) + "");
                    String ampmString = time.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
                    label.setText(hourString + ":" + minuteString + ":" + secondString + " " + ampmString + "\n" + new SimpleDateFormat("dd-MM-yyyy").format(time.getTime()));
                }),
                new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        label.setStyle("-fx-font-size: 14; -fx-text-fill: white; -fx-text-alignment: center; ");

        HBox box = new HBox(label);
        box.setBackground(new Background(new BackgroundFill(Color.web("#000000", 0), new CornerRadii(5), new Insets(2, 2, 2, 2))));
        box.setPadding(new Insets(5, 35, 5, 5));
        box.setAlignment(Pos.CENTER_RIGHT);

        return box;
    }

    static class StringUtilities {

        public static String pad(int fieldWidth, char padChar, String s) {
            StringBuilder sb = new StringBuilder();
            for (int i = s.length(); i < fieldWidth; i++) {
                sb.append(padChar);
            }
            sb.append(s);

            return sb.toString();
        }

    }

}