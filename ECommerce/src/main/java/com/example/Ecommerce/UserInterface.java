package com.example.Ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class UserInterface {

    BorderPane createContent(){
        BorderPane root = new BorderPane();
        root.setPrefSize(500,500);
        //root.getChildren().add(loginPage);
        root.setTop(headerBar);
        body = new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);
        //root.setCenter(loginPage);
        productPage = productList.getAllProducts();

        body.getChildren().add(productPage);

        root.setBottom(footerBar);
        return root;
    }

    public UserInterface(){
        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }

    DbConnection connection;

    GridPane loginPage;
    GridPane signInPage;
    HBox headerBar;
    HBox footerBar;
    VBox body;

    Button signIn;
    Label welcomeLabel;
    Customer loggedInCustomer;

    ProductList productList = new ProductList();

    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();

    Button placeOrder = new Button("Place Order");
    VBox productPage;
    private void createLoginPage(){

        Button login = new Button("Login");

        Button signIn = new Button("Sign In");

        Label messageLabel = new Label("Hi");
        Text userNameText = new Text("User Name");
        Text passwordText = new Text("Password");

        TextField userName = new TextField();
        userName.setPromptText("Enter the username");
        PasswordField password = new PasswordField();
        //password.setText("angad01");
        password.setPromptText("Enter the password");

        loginPage = new GridPane();
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10);
        loginPage.setVgap(10);

        loginPage.add(userNameText,0,0);
        loginPage.add(userName,1,0);
        loginPage.add(passwordText,0,1);
        loginPage.add(password,1,1);
        loginPage.add(messageLabel,0,2);

        loginPage.add(login,1,2);


        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = userName.getText();
                String password1 = password.getText();
                Login login = new Login();
                loggedInCustomer = login.customerLogin(name,password1);

                if(loggedInCustomer!=null){
                    messageLabel.setText("Welcome user " + loggedInCustomer.getName());
                    welcomeLabel.setText("Welcome " + loggedInCustomer.getName());
                    headerBar.getChildren().add(welcomeLabel);
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                } else {
                    messageLabel.setText("User does not exist");
                }

            }
        });
    }

    private  void createHeaderBar(){
        TextField searchBar = new TextField("Search");
        Button home = new Button("Home");


        Button searchButton = new Button("Search");
        Button cart = new Button("Cart");
        signIn = new Button("Sign In");
        welcomeLabel = new Label();
        headerBar = new HBox(20);
        headerBar.setStyle("-fx-background-color : grey;");
        headerBar.setPadding(new Insets(10));
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(home,searchBar,searchButton,signIn,cart);

        signIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(loginPage);
                headerBar.getChildren().remove(signIn);
            }
        });



        cart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox prodPage = productList.getProductsInCart(itemsInCart);
                prodPage.setAlignment(Pos.CENTER);
                prodPage.setSpacing(10);
                prodPage.getChildren().add(placeOrder);
                body.getChildren().add(prodPage);
                footerBar.setVisible(false);
            }
        });

        placeOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(itemsInCart==null){
                    showDialog("Please add some product in cart to place order");
                }

                if(loggedInCustomer==null){
                    showDialog("Please login first to place an order");
                    return;
                }

                int count = Order.placeMultipleOrders(loggedInCustomer,itemsInCart);
                if(count!=0){
                    showDialog("Order for "+count+" products has been placed");
                } else {
                    showDialog("Order Failed");
                }
            }
        });

        home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footerBar.setVisible(true);
                if(loggedInCustomer==null && headerBar.getChildren().indexOf(signIn)== -1){
                    headerBar.getChildren().add(signIn);
                }
            }
        });

    }

    private void createFooterBar(){


        Button buyNow = new Button("Buy Now");
        Button addToCart = new Button("Add to cart");
        footerBar = new HBox(20);
        footerBar.setStyle("-fx-background-color : lightblue;");
        footerBar.setPadding(new Insets(10));
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNow,addToCart);

        buyNow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product p = productList.getSelectedProduct();
                if(p==null){
                    showDialog("Please select a product first to place order");
                    return;
                }

                if(loggedInCustomer==null){
                    showDialog("Please login first to place an order");
                    return;
                }

                int count = Order.placeMultipleOrders(loggedInCustomer,itemsInCart);
                if(count!=0){
                    showDialog("Order for "+count+" products has been placed");
                } else {
                    showDialog("Order Failed");
                }

            }
        });

        addToCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product p = productList.getSelectedProduct();
                if(p==null){
                    showDialog("Please select a product first to add it to cart");
                    return;
                }

                itemsInCart.add(p);
                showDialog("Item has been successfully added to cart");
            }
        });
    }

    private void showDialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }
}
