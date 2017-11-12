//package dkjconstruction;
//
//import java.net.URL;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.beans.value.ObservableValue;
//import javafx.concurrent.Worker.State;
//import javafx.scene.Scene;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Region;
//import javafx.scene.paint.Color;
//import javafx.scene.web.WebEngine;
//import javafx.scene.web.WebView;
//import javafx.stage.Stage;
//import netscape.javascript.JSObject;
//
//public class ChatClient extends Application {
//    private Scene scene;
//
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//    @Override
//    public void start(Stage primaryStage) {
//    primaryStage.setTitle("title");
//    scene = new Scene(new Browser(), 640, 480,Color.web("#ffffff"));
//
//    primaryStage.setScene(scene);
//
//    primaryStage.show();
//    }
//}
//
//class Browser extends Region {
//
//
//    HBox toolbar;
//
//    WebView webView = new WebView();
//    WebEngine webEngine = webView.getEngine();
//
//    public Browser() {
//
//
//        final URL url = getClass().getResource("g.html");
//        webEngine.load(url.toExternalForm());
//        webEngine.getLoadWorker().stateProperty().addListener((ObservableValue<? extends State> ov, State oldState, State newState) -> {
//            if (newState == State.SUCCEEDED) {
//                JSObject win = (JSObject) webEngine.executeScript("window");
//                win.setMember("app", new JavaApp());
//            } 
//        });
//
//
//        getChildren().add(webView);
//    }
//}
//
//class JavaApp {
//  public void exit() {
//    Platform.exit();
//  }
//}
