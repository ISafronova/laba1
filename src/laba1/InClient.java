package laba1;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InClient extends Application {

    private static int kol = 0;
    private ArrayList<Phone> arrayList = new ArrayList<Phone>();
    private Client client;
    private void init(final Stage primaryStage) throws RemoteException, NotBoundException {
        System.out.println("Starting...");
        client = new Client();
        client.con(client);
        client.ReadXL();

        final List data1 = client.ReadXML();
        final Group root = new Group();
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 800, 400));
        final ToolBar toolbar = new ToolBar();
        final Button Adding;
        Button Removing;
        final Button Output;
        Button Searching;
        Button Exit;
        toolbar.getItems().add(Adding = new Button("Добавить"));
        toolbar.getItems().add(Removing = new Button("Удалить"));
        toolbar.getItems().add(Output = new Button("Вывести"));
        toolbar.getItems().add(Searching = new Button("Поиск"));
        toolbar.getItems().add(Exit = new Button("Выход"));
        root.getChildren().add(toolbar);
        toolbar.setMinSize(850, 30);
//add
        Adding.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {

                //создаем окно
                javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(800, 400);
                rect.setLayoutX(0);
                rect.setLayoutY(30);
                rect.setFill(Color.WHITE);
                rect.getStyleClass().add("my-rect");

                root.getChildren().addAll(rect);

                //создаем поля для ввода
                final TextField textSurname = new TextField();
                final TextField textCity = new TextField();
                final TextField textCode = new TextField();
                final TextField textNumber = new TextField();

                //создаем подписи полей
                Label label = new Label("Введите фамилию");
                label.setLayoutX(0);
                label.setLayoutY(40);
                root.getChildren().add(label); //омещаем подпись в окно

                Label label1 = new Label("Введите город");
                label1.setLayoutX(0);
                label1.setLayoutY(80);
                root.getChildren().add(label1);

                Label label2 = new Label("Введите код");
                label2.setLayoutX(0);
                label2.setLayoutY(120);
                root.getChildren().add(label2);

                Label label3 = new Label("Введите номер");
                label3.setLayoutX(0);
                label3.setLayoutY(160);
                root.getChildren().add(label3);

                final Button ok = new Button("OK");
                //помещаем поля в окно
                root.getChildren().add(textSurname);
                root.getChildren().add(textCity);
                root.getChildren().add(textCode);
                root.getChildren().add(textNumber);
                root.getChildren().add(ok);

                textSurname.setMaxSize(140, 20);
                textSurname.setLayoutX(110);
                textSurname.setLayoutY(40);

                textCity.setMaxSize(140, 20);
                textCity.setLayoutX(110);
                textCity.setLayoutY(80);

                textCode.setMaxSize(140, 20);
                textCode.setLayoutX(110);
                textCode.setLayoutY(120);

                textNumber.setMaxSize(140, 20);
                textNumber.setLayoutX(110);
                textNumber.setLayoutY(160);

                ok.setLayoutX(210);
                ok.setLayoutY(195);
                //при нажатии кнопки ок
                ok.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        //присваеваем переменным значения полей
                        String surname1 = textSurname.getText();
                        String city1 = textCity.getText();
                        String code1 = textCode.getText();
                        String number1 = textNumber.getText();
                        int i = 0;
                        //заносим сведения в список
                        Phone phone = new Phone(i, surname1, city1, Integer.parseInt(code1), Integer.parseInt(number1));
                        i++;
                        //заносим сведения на сервер
                        client.Addph(phone);
                        System.out.println(surname1 + ' ' + city1 + ' ' + code1 + ' ' + number1);
                        try {
                            client.regAll();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });

                System.out.println("Added!");
            }
        });


//search
        Searching.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(800, 400);
                rect.setLayoutX(0);
                rect.setLayoutY(30);
                rect.setFill(Color.WHITE);
                rect.getStyleClass().add("my-rect");
                root.getChildren().addAll(rect);

                //объекты класса Button
                Button SurnameB;
                Button CityB;
                Button CodeB;
                Button NumberB;
                //поле для ввода
                final TextField text = new TextField();
                //создаем кнопки
                root.getChildren().add(SurnameB = new Button("Фамилия"));
                root.getChildren().add(CityB = new Button("Город"));
                root.getChildren().add(CodeB = new Button("Код"));
                root.getChildren().add(NumberB = new Button("Номер"));
                //создаем поле
                root.getChildren().add(text);
                //размещение в окне
                SurnameB.setLayoutX(0);
                SurnameB.setLayoutY(90);
                CityB.setLayoutX(80);
                CityB.setLayoutY(90);
                CodeB.setLayoutX(145);
                CodeB.setLayoutY(90);
                NumberB.setLayoutX(195);
                NumberB.setLayoutY(90);
                text.setMinSize(150, 20);
                text.setLayoutX(0);
                text.setLayoutY(60);

                //при нажатии кнопки Фамилия
                SurnameB.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        kol = 1;
                        String field = text.getText();
                        ArrayList<Phone> arrayList = new ArrayList<Phone>();
                        //поиск
                        try {
                            arrayList = client.Search(field, kol);
                        } catch (RemoteException ex) {
                            Logger.getLogger(InClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.println(arrayList);

                        if (arrayList != null) {
                            ObservableList<Phone> data = FXCollections.observableArrayList(arrayList);

                            //создаем таблицу
                            TableColumn surnameCol = new TableColumn();
                            surnameCol.setText("Фамилия");
                            surnameCol.setMinWidth(70);
                            surnameCol.setCellValueFactory(new PropertyValueFactory("surname"));
                            TableColumn cityCol = new TableColumn();
                            cityCol.setText("Город");
                            cityCol.setMinWidth(70);
                            cityCol.setCellValueFactory(new PropertyValueFactory("city"));
                            TableColumn codeCol = new TableColumn();
                            codeCol.setMinWidth(70);
                            codeCol.setText("Код");
                            codeCol.setCellValueFactory(new PropertyValueFactory("code"));
                            TableColumn numberCol = new TableColumn();
                            numberCol.setText("Номер");
                            numberCol.setMinWidth(70);
                            numberCol.setCellValueFactory(new PropertyValueFactory("number"));

                            TableView tableView = new TableView();
                            tableView.setLayoutX(0);
                            tableView.setLayoutY(150);
                            tableView.setItems(data);
                            tableView.getColumns().addAll(surnameCol, cityCol, codeCol, numberCol);
                            root.getChildren().add(tableView);
                            arrayList = null;
                        }

                    }
                });
                NumberB.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        kol = 2;
                        String field = text.getText();
                        ArrayList<Phone> arrayList = new ArrayList<Phone>();
                        try {
                            arrayList = client.Search(field, kol);
                        } catch (RemoteException ex) {
                            Logger.getLogger(InClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (arrayList != null) {
                            final ObservableList<Phone> data = FXCollections.observableArrayList(arrayList);
                            TableColumn surnameCol = new TableColumn();
                            surnameCol.setText("Фамилия");
                            surnameCol.setMinWidth(70);
                            surnameCol.setCellValueFactory(new PropertyValueFactory("surname"));
                            TableColumn cityCol = new TableColumn();
                            cityCol.setText("Город");
                            cityCol.setMinWidth(70);
                            cityCol.setCellValueFactory(new PropertyValueFactory("city"));
                            TableColumn codeCol = new TableColumn();
                            codeCol.setMinWidth(70);
                            codeCol.setText("Код");
                            codeCol.setCellValueFactory(new PropertyValueFactory("code"));
                            TableColumn numberCol = new TableColumn();
                            numberCol.setText("Номер");
                            numberCol.setMinWidth(70);
                            numberCol.setCellValueFactory(new PropertyValueFactory("number"));

                            TableView tableView = new TableView();
                            tableView.setLayoutX(0);
                            tableView.setLayoutY(150);
                            tableView.setItems(data);
                            tableView.getColumns().addAll(surnameCol, cityCol, codeCol, numberCol);
                            root.getChildren().add(tableView);
                            arrayList = null;
                        }
                    }
                });
                CodeB.setOnAction(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent event) {
                        try {
                            kol = 3;
                            String field = text.getText();
                            ArrayList<Phone> arrayList = new ArrayList<Phone>();
                            arrayList = client.Search(field, kol);
                            if (arrayList != null) {
                                final ObservableList<Phone> data = FXCollections.observableArrayList(arrayList);
                                TableColumn surnameCol = new TableColumn();
                                surnameCol.setText("Фамилия");
                                surnameCol.setMinWidth(70);
                                surnameCol.setCellValueFactory(new PropertyValueFactory("surname"));
                                TableColumn cityCol = new TableColumn();
                                cityCol.setText("Город");
                                cityCol.setMinWidth(70);
                                cityCol.setCellValueFactory(new PropertyValueFactory("city"));
                                TableColumn codeCol = new TableColumn();
                                codeCol.setMinWidth(70);
                                codeCol.setText("Код");
                                codeCol.setCellValueFactory(new PropertyValueFactory("code"));
                                TableColumn numberCol = new TableColumn();
                                numberCol.setText("Номер");
                                numberCol.setMinWidth(70);
                                numberCol.setCellValueFactory(new PropertyValueFactory("number"));

                                TableView tableView = new TableView();
                                tableView.setLayoutX(0);
                                tableView.setLayoutY(150);
                                tableView.setItems(data);
                                tableView.getColumns().addAll(surnameCol, cityCol, codeCol, numberCol);
                                root.getChildren().add(tableView);
                            }

                        } catch (RemoteException ex) {
                            Logger.getLogger(InClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                CityB.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        try {
                            kol = 4;
                            String field = text.getText();
                            ArrayList<Phone> arrayList = new ArrayList<Phone>();
                            arrayList = client.Search(field, kol);
                            if (arrayList != null) {
                                final ObservableList<Phone> data = FXCollections.observableArrayList(arrayList);
                                TableColumn surnameCol = new TableColumn();
                                surnameCol.setText("Фамилия");
                                surnameCol.setMinWidth(70);
                                surnameCol.setCellValueFactory(new PropertyValueFactory("surname"));
                                TableColumn cityCol = new TableColumn();
                                cityCol.setText("Город");
                                cityCol.setMinWidth(70);
                                cityCol.setCellValueFactory(new PropertyValueFactory("city"));
                                TableColumn codeCol = new TableColumn();
                                codeCol.setMinWidth(70);
                                codeCol.setText("Код");
                                codeCol.setCellValueFactory(new PropertyValueFactory("code"));
                                TableColumn numberCol = new TableColumn();
                                numberCol.setText("Номер");
                                numberCol.setMinWidth(70);
                                numberCol.setCellValueFactory(new PropertyValueFactory("number"));

                                TableView tableView = new TableView();
                                tableView.setLayoutX(0);
                                tableView.setLayoutY(150);
                                tableView.setItems(data);
                                tableView.getColumns().addAll(surnameCol, cityCol, codeCol, numberCol);
                                root.getChildren().add(tableView); //добавление на экран
                            }

                        } catch (RemoteException ex) {
                            Logger.getLogger(InClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        });
        Removing.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(800, 400);
                rect.setLayoutX(0);
                rect.setLayoutY(30);
                rect.setFill(Color.WHITE);
                rect.getStyleClass().add("my-rect");
                root.getChildren().addAll(rect);

                Label label = new Label("Введите номер удаляемого элемента");
                label.setLayoutX(0);
                label.setLayoutY(40);
                root.getChildren().add(label);

                final TextField text = new TextField();
                final Button ok = new Button("OK");
                root.getChildren().add(text);
                root.getChildren().add(ok);

                text.setMinSize(250, 20);
                text.setLayoutX(0);
                text.setLayoutY(60);
                ok.setLayoutX(255);
                ok.setLayoutY(60);
                ok.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        int num = Integer.parseInt(text.getText());
                        try {
                            if (client.DeleteData(num)) {
                                System.out.println("Deleted!");
                                javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(800, 400);
                                rect.setLayoutX(0);
                                rect.setLayoutY(100);
                                rect.setFill(Color.WHITE);
                                rect.getStyleClass().add("my-rect");
                                root.getChildren().addAll(rect);
                                Label labe2 = new Label("Удален");
                                labe2.setLayoutX(0);
                                labe2.setLayoutY(100);

                                root.getChildren().add(labe2);
                            } else {
                                javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(800, 400);
                                rect.setLayoutX(0);
                                rect.setLayoutY(100);
                                rect.setFill(Color.WHITE);
                                rect.getStyleClass().add("my-rect");
                                root.getChildren().addAll(rect);
                                Label labe2 = new Label("Не удален");

                                labe2.setLayoutX(0);
                                labe2.setLayoutY(100);
                                root.getChildren().add(labe2);
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(InClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            client.regAll();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }

        });
//pechat
        Output.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(800, 400);
                rect.setLayoutX(0);
                rect.setLayoutY(30);
                rect.setFill(Color.WHITE);
                rect.getStyleClass().add("my-rect");

                root.getChildren().addAll(rect);
                ArrayList<Phone> data1 = new ArrayList<Phone>();
                try {
                    data1 = (ArrayList<Phone>) client.ReadXML();
                } catch (RemoteException ex) {
                    Logger.getLogger(laba1.InClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                final  ObservableList<Phone> data = FXCollections.observableArrayList(data1);
                try {
                    client.update();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                TableColumn surnameCol = new TableColumn();
                surnameCol.setText("Фамилия");
                surnameCol.setMinWidth(70);
                surnameCol.setCellValueFactory(new PropertyValueFactory("surname"));
                TableColumn cityCol = new TableColumn();
                cityCol.setText("Город");
                cityCol.setMinWidth(70);
                cityCol.setCellValueFactory(new PropertyValueFactory("city"));
                TableColumn codeCol = new TableColumn();
                codeCol.setText("Код");
                codeCol.setMinWidth(70);
                codeCol.setCellValueFactory(new PropertyValueFactory("code"));
                TableColumn numberCol = new TableColumn();
                numberCol.setText("Номер");
                numberCol.setMinWidth(70);
                numberCol.setCellValueFactory(new PropertyValueFactory("number"));

                final TableView tableView = new TableView();
                tableView.setLayoutX(0);
                tableView.setLayoutY(50);
                tableView.setItems(client.data);
                ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
                Runnable pinger = new Runnable() {
                    public void run() {

                        try {
                            client.update();
                            tableView.setItems(client.print());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ses.scheduleAtFixedRate(pinger, 1, 1, TimeUnit.SECONDS);
                tableView.getColumns().addAll(surnameCol, cityCol, codeCol, numberCol);
                root.getChildren().add(tableView);
            }
        });
//vihod
        Exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    client.DeleteKol();
                } catch (RemoteException ex) {
                    Logger.getLogger(laba1.InClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    client.aregistry(client);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                System.out.println("Exit!");
                System.exit(0);
            }
        });
    }
//=========================================================================================================================
    public double getSampleWidth() {
        return 800;
    }
    public double getSampleHeight() {
        return 400;
    }
    public void update() throws RemoteException{
        System.out.println("up");
    }
    public static class Phone1 {
        private StringProperty surname;
        private StringProperty city;
        private StringProperty code;
        private StringProperty number;
        private Phone1(String surname, String city, int code,int number) {
            this.surname = new SimpleStringProperty(surname);
            this.city = new SimpleStringProperty(city);
            this.code = new SimpleStringProperty(Integer.toString(code));
            this.number = new SimpleStringProperty(Integer.toString(number));
        }
        public StringProperty surnameProperty() {
            return surname;
        }
        public StringProperty cityProperty() {
            return city;
        }
        public StringProperty codeProperty() {
            return code;
        }
        public StringProperty numberProperty() {
            return number;
        }
    }

    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.setTitle("Каталог");
        primaryStage.show();
    }
    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
