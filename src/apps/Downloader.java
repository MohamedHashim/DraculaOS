package apps;


import directory.File;
import javafx.concurrent.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;
import org.controlsfx.dialog.*;
import sfe.os.*;
import sfe.os.FileChooser;
import sfe.os.FileSystem;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;


public class Downloader {

    private double angles[] = {90, 180, 270, 360};
    private int currentAngle = 0;
    private double height;
    int id;
    Stage stage;
    ImageView imgView = new ImageView();
    static CPU cpu;
    public Downloader( CPU cpu, int id ) {
        this.cpu=cpu;
        stage = new Stage();
        stage.setTitle("Downloader");
        this.id=id;
        BorderPane border = new BorderPane();
        border.setTop(menuBar());
//        border.setCenter(viewer(fileUrl));
//        border.setBottom(controlsBar());

        stage.setScene(new Scene(createContent()));
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Image Viewer with id :"+id+" Is removed");
                cpu.RemoveProcess(id);
            }
        });
    }
    private Parent createContent() {
        VBox root = new VBox();
        root.setPrefSize(400, 600);

        TextField fieldURL = new TextField();
        root.getChildren().addAll(fieldURL);

        fieldURL.setOnAction(event -> {
            Task<Void> task = new DownloadTask(fieldURL.getText());
            ProgressBar progressBar = new ProgressBar();
            progressBar.setPrefWidth(350);
            progressBar.progressProperty().bind(task.progressProperty());
            root.getChildren().add(progressBar);

            fieldURL.clear();

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        });

        return root;
    }
    private class DownloadTask extends Task<Void> {

        private String url;

        public DownloadTask(String url) {
            this.url = url;
        }

        @Override
        protected Void call() throws Exception {
            String ext = url.substring(url.lastIndexOf("."), url.length());
            URLConnection connection = new URL(url).openConnection();
            long fileLength = connection.getContentLengthLong();

            try (InputStream is = connection.getInputStream();
                 OutputStream os = Files.newOutputStream(Paths.get("downloadedfile" + ext))) {

                long nread = 0L;
                byte[] buf = new byte[8192];
                int n;
                while ((n = is.read(buf)) > 0) {
                    os.write(buf, 0, n);
                    nread += n;
                    updateProgress(nread, fileLength);
                }
            }

            return null;
        }

        @Override
        protected void failed() {
            System.out.println("downloading failed");
        }

        @Override
        protected void succeeded() {
            System.out.println("photo downloaded successfully");
        }
    }
    public MenuBar menuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));

        Menu fileMenu = new Menu("File");
        {
            MenuItem open = new MenuItem("Open...");
            open.setOnAction(event -> { new FileChooser("jpg", "", "open",cpu); stage.close(); } );
            MenuItem close = new MenuItem("Exit");
            close.setOnAction(event -> stage.close());
            fileMenu.getItems().addAll(open, close);
        }

        menuBar.getMenus().addAll(fileMenu);
        return menuBar;
    }

}