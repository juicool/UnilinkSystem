package controller;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.event.*;
import model.*;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.MainGUI;
import model.Event;
import view.PostListViewCell;

import java.io.*;

import java.util.ArrayList;


public class MainMenuController  implements Serializable{
    @FXML private Label welcomeLabel;
    @FXML private ChoiceBox typeChoiceBox;
    @FXML private ChoiceBox statusChoiceBox;
    @FXML private ChoiceBox creatorChoiceBox;
    @FXML private Button logoutButton;
    @FXML private MenuBar unilinkMenuBar;
    @FXML private ListView allPostsListView;
    String username;

    protected ListProperty<Post> postsListProperty = new SimpleListProperty<>();
    ArrayList<Post> postTypeArrayList = new ArrayList<>();

    private ObservableList<Post> postsObservableList = FXCollections.observableArrayList();
    private ObservableList<Post> postStatusArrayList = FXCollections.observableArrayList();
    private ObservableList<Post> postCreatorArrayList = FXCollections.observableArrayList();

    /**
     * This method is used to initialize choiceboxes, labels and the listview containing all posts
     * Also when the filter values are changed the listview of posts is updated
     * @param username
     * @throws IOException
     */
    @FXML
    public ObservableList<Post> initialize(String username) throws IOException {
        this.username=username;
        welcomeLabel.setText("Welcome " + username);
        postsObservableList = FXCollections.observableArrayList(MainGUI.collectPosts);
        postsListProperty.set(postsObservableList);
        allPostsListView.itemsProperty().bind(postsListProperty);
        allPostsListView.setCellFactory(new Callback<ListView<Post>, ListCell<Post>>() {
            @Override
            public ListCell<Post> call(ListView<Post> postListView) {

                return new PostListViewCell();
            }
        });

        //if post type is changed then update the listview in main window

        typeChoiceBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String oldOne, String newOne) {
                postTypeArrayList.clear();
                for (Post p : postsObservableList) {
                    if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        postTypeArrayList.add(p);
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        postTypeArrayList.add(p);
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        postTypeArrayList.add(p);
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        postTypeArrayList.add(p);
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        postTypeArrayList.add(p);
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        postTypeArrayList.add(p);
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("event") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        if (p instanceof Event) {
                            postTypeArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("event") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        if (p instanceof Event && p.getCreator_id().equalsIgnoreCase(username)) {
                            postTypeArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("event") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        if (p instanceof Event && p.getStatus().equalsIgnoreCase("open")) {
                            postTypeArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("event") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        if (p instanceof Event && p.getStatus().equalsIgnoreCase("closed")) {
                            postTypeArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("event") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        if (p instanceof Event && p.getStatus().equalsIgnoreCase("open") && p.getCreator_id().equalsIgnoreCase(username)) {
                            postTypeArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("event") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        if (p instanceof Event && p.getStatus().equalsIgnoreCase("closed") && p.getCreator_id().equalsIgnoreCase(username)) {
                            postTypeArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("sale") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        if (p instanceof Sale) {
                            postTypeArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("sale") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        if (p instanceof Sale && p.getCreator_id().equalsIgnoreCase(username)) {
                            postTypeArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("sale") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        if (p instanceof Sale && p.getStatus().equalsIgnoreCase("open")) {
                            postTypeArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("sale") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        if (p instanceof Sale && p.getStatus().equalsIgnoreCase("closed")) {
                            postTypeArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("sale") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        if (p instanceof Sale && p.getStatus().equalsIgnoreCase("open") && p.getCreator_id().equalsIgnoreCase(username)) {
                            postTypeArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("sale") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        if (p instanceof Sale && p.getStatus().equalsIgnoreCase("open") && p.getCreator_id().equalsIgnoreCase(username)) {
                            postTypeArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("job") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        if (p instanceof Job) {
                            postTypeArrayList.add(p);
                        }
                    }
                    else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("job") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        if (p instanceof Job  && p.getCreator_id().equalsIgnoreCase(username)) {
                            postTypeArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("job") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        if (p instanceof Job && p.getStatus().equalsIgnoreCase("open")) {
                            postTypeArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("job") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        if (p instanceof Job && p.getStatus().equalsIgnoreCase("closed")) {
                            postTypeArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("job") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        if (p instanceof Job && p.getStatus().equalsIgnoreCase("open") && p.getCreator_id().equalsIgnoreCase(username)) {
                            postTypeArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("job") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        if (p instanceof Job && p.getStatus().equalsIgnoreCase("closed") && p.getCreator_id().equalsIgnoreCase(username)) {
                            postTypeArrayList.add(p);
                        }
                    }
                }
                postsObservableList =FXCollections.observableArrayList(postTypeArrayList);
                    allPostsListView.itemsProperty().bind(postsListProperty);
                    postsListProperty.set(postsObservableList);
                    allPostsListView.setCellFactory(new Callback<ListView<Post>, ListCell<Post>>() {
                        @Override
                        public ListCell<Post> call(ListView<Post> postListView) {
                            return new PostListViewCell();
                        }
                    });

                }
        });
        // if status is changed then update the listview in main window
        statusChoiceBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                postStatusArrayList.clear();
                for(Post p:postsObservableList) {
                    if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        postStatusArrayList.add(p);
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        postStatusArrayList.add(p);
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        postStatusArrayList.add(p);
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        postStatusArrayList.add(p);
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        postStatusArrayList.add(p);
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        postStatusArrayList.add(p);
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("event") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        if (p instanceof Event) {
                            postStatusArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("event") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        if (p instanceof Event && p.getCreator_id().equalsIgnoreCase(username)) {
                            postStatusArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("event") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        if (p instanceof Event && p.getStatus().equalsIgnoreCase("open")) {
                            postStatusArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("event") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        if (p instanceof Event && p.getStatus().equalsIgnoreCase("closed")) {
                            postStatusArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("event") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        if (p instanceof Event && p.getStatus().equalsIgnoreCase("open") && p.getCreator_id().equalsIgnoreCase(username)) {
                            postStatusArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("event") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        if (p instanceof Event && p.getStatus().equalsIgnoreCase("closed") && p.getCreator_id().equalsIgnoreCase(username)) {
                            postStatusArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("sale") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        if (p instanceof Sale) {
                            postStatusArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("sale") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        if (p instanceof Sale && p.getCreator_id().equalsIgnoreCase(username)) {
                            postStatusArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("sale") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        if (p instanceof Sale && p.getStatus().equalsIgnoreCase("open")) {
                            postStatusArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("sale") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        if (p instanceof Sale && p.getStatus().equalsIgnoreCase("closed")) {
                            postStatusArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("sale") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        if (p instanceof Sale && p.getStatus().equalsIgnoreCase("open") && p.getCreator_id().equalsIgnoreCase(username)) {
                            postStatusArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("sale") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        if (p instanceof Sale && p.getStatus().equalsIgnoreCase("closed") && p.getCreator_id().equalsIgnoreCase(username)) {
                            postStatusArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("job") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        if (p instanceof Job) {
                            postStatusArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("job") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        if (p instanceof Job && p.getCreator_id().equalsIgnoreCase(username)) {
                            postStatusArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("job") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        if (p instanceof Job && p.getStatus().equalsIgnoreCase("open")) {
                            postStatusArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("job") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                        if (p instanceof Job && p.getStatus().equalsIgnoreCase("closed")) {
                            postStatusArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("job") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        if (p instanceof Job && p.getStatus().equalsIgnoreCase("open") && p.getCreator_id().equalsIgnoreCase(username)) {
                            postStatusArrayList.add(p);
                        }
                    } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("job") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                        if (p instanceof Job && p.getStatus().equalsIgnoreCase("closed") && p.getCreator_id().equalsIgnoreCase(username)) {
                            postStatusArrayList.add(p);
                        }
                    }
                }
                    postsObservableList=FXCollections.observableArrayList(postStatusArrayList);
                    allPostsListView.itemsProperty().bind(postsListProperty);
                    postsListProperty.set(postsObservableList);
                    allPostsListView.setCellFactory(new Callback<ListView<Post>, ListCell<Post>>() {
                        @Override
                        public ListCell<Post> call(ListView<Post> postListView) {
                            return new PostListViewCell();
                        }
                    });

            }
        });


        // if creator is changed then update listview in main window
        creatorChoiceBox.valueProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue observableValue, Object o, Object t1) {
            postCreatorArrayList.clear();
            for(Post p:postsObservableList) {
                if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                    postCreatorArrayList.add(p);
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                    postCreatorArrayList.add(p);
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                    postCreatorArrayList.add(p);
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                    postCreatorArrayList.add(p);
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                    postCreatorArrayList.add(p);
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                    postCreatorArrayList.add(p);
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("event") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                    if (p instanceof Event) {
                        postCreatorArrayList.add(p);
                    }
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("event") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                    if (p instanceof Event && p.getCreator_id().equalsIgnoreCase(username)) {
                        postCreatorArrayList.add(p);
                    }
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("event") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                    if (p instanceof Event && p.getStatus().equalsIgnoreCase("open")) {
                        postCreatorArrayList.add(p);
                    }
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("event") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                    if (p instanceof Event && p.getStatus().equalsIgnoreCase("closed")) {
                        postCreatorArrayList.add(p);
                    }
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("event") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                    if (p instanceof Event && p.getStatus().equalsIgnoreCase("open") && p.getCreator_id().equalsIgnoreCase(username)) {
                        postCreatorArrayList.add(p);
                    }
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("event") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                    if (p instanceof Event && p.getStatus().equalsIgnoreCase("closed") && p.getCreator_id().equalsIgnoreCase(username)) {
                        postCreatorArrayList.add(p);
                    }
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("sale") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                    if (p instanceof Sale) {
                        postCreatorArrayList.add(p);
                    }

                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("sale") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                    if (p instanceof Sale && p.getCreator_id().equalsIgnoreCase(username)) {
                        postCreatorArrayList.add(p);
                    }
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("sale") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                    if (p instanceof Sale && p.getStatus().equalsIgnoreCase("open")) {
                        postCreatorArrayList.add(p);
                    }
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("sale") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                    if (p instanceof Sale && p.getStatus().equalsIgnoreCase("closed")) {
                        postCreatorArrayList.add(p);
                    }
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("sale") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                    if (p instanceof Sale && p.getStatus().equalsIgnoreCase("open") && p.getCreator_id().equalsIgnoreCase(username)) {
                        postCreatorArrayList.add(p);
                    }
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("sale") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                    if (p instanceof Sale && p.getStatus().equalsIgnoreCase("closed") && p.getCreator_id().equalsIgnoreCase(username)) {
                        postCreatorArrayList.add(p);
                    }
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("job") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                    if (p instanceof Job) {
                        postCreatorArrayList.add(p);
                    }
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("job") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                    if (p instanceof Job && p.getCreator_id().equalsIgnoreCase(username)) {
                        postCreatorArrayList.add(p);
                    }
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("job") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                    if (p instanceof Job && p.getStatus().equalsIgnoreCase("open")) {
                        postCreatorArrayList.add(p);
                    }
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("job") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("all")) {
                    if (p instanceof Job && p.getStatus().equalsIgnoreCase("closed")) {
                        postCreatorArrayList.add(p);
                    }
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("job") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("open") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                    if (p instanceof Job && p.getStatus().equalsIgnoreCase("open") && p.getCreator_id().equalsIgnoreCase(username)) {
                        postCreatorArrayList.add(p);
                    }
                } else if (typeChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("job") && statusChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("closed") && creatorChoiceBox.getValue().toString().toLowerCase().equalsIgnoreCase("my posts")) {
                    if (p instanceof Job && p.getStatus().equalsIgnoreCase("closed") && p.getCreator_id().equalsIgnoreCase(username)) {
                        postCreatorArrayList.add(p);
                    }
                }
            }

            postsObservableList=FXCollections.observableArrayList(postCreatorArrayList);
            allPostsListView.itemsProperty().bind(postsListProperty);
            postsListProperty.set(postsObservableList);
            allPostsListView.setCellFactory(new Callback<ListView<Post>, ListCell<Post>>() {
                @Override
                public ListCell<Post> call(ListView<Post> postListView) {
                    return new PostListViewCell();
                }
            });

        }
        });

        return postsObservableList;
    }

    /**
     * This method is used to jump to newEventView.fxml when new event post button is clicked
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void newEventButtonHandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader newEventLoader = new FXMLLoader();
        newEventLoader.setLocation(getClass().getResource("/view/newEventView.fxml"));
        Parent newEvent = newEventLoader.load();

        Scene newEventWindowScene = new Scene(newEvent);
        NewEventViewController eventPostDetailsController = newEventLoader.getController();
        eventPostDetailsController.initialize(welcomeLabel.getText().substring(8));

        Stage window = new Stage();
        window.setResizable(false);
        window.setScene(newEventWindowScene);
        window.show();
    }

    /**
     * This method is used to jump to newSaleView.fxml when new sale post button is clicked
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void newSaleButtonHandler(ActionEvent actionEvent) throws IOException {
       FXMLLoader newSaleLoader = new FXMLLoader();
        newSaleLoader.setLocation(getClass().getResource("/view/newSaleView.fxml"));
        Parent newSale = newSaleLoader.load();

        Scene newSaleWindowScene = new Scene(newSale);
        NewSaleViewController salePostDetailsController = newSaleLoader.getController();
        salePostDetailsController.initialize(welcomeLabel.getText().substring(8));


        Stage window = new Stage();
        window.setResizable(false);
        window.setScene(newSaleWindowScene);
        window.show();
    }

    /**
     * This method is used to jump to newJobView.fxml when new job post button is clicked
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void newJobButtonHandler(ActionEvent actionEvent) throws IOException {
       FXMLLoader newJobLoader = new FXMLLoader();
        newJobLoader.setLocation(getClass().getResource("/view/newJobView.fxml"));
        Parent newJob = newJobLoader.load();

        Scene newJobWindowScene = new Scene(newJob);
       NewJobViewController jobPostDetailsController = newJobLoader.getController();
       jobPostDetailsController.initialize(welcomeLabel.getText().substring(8));


        Stage window = new Stage();
        window.setResizable(false);
        window.setScene(newJobWindowScene);
        window.show();
    }

    /**
     * This method is used to go back to login window
     * @param actionEvent
     */
    @FXML public void onLogoutPushed(ActionEvent actionEvent) throws IOException {

        LoginController.username = null;
        logoutButton.getScene().getWindow().hide();

        newPostController.information.setTitle("Logout");
        newPostController.information.setHeaderText("you have successfully logged out!");
        newPostController.information.showAndWait();


    }

    /**
     * This method is used to quit the unilink system and to save data to the database
     * @param actionEvent
     */
    @FXML public void onQuitUnlinkPushed(ActionEvent actionEvent){
        System.out.println("Going inside insert row");
        InsertRow.insertRow();
        System.out.println("Insert row executed");
        InsertRow.insertReplyRow();
        newPostController.information.setTitle("Quit Unilink");
        newPostController.information.setHeaderText("System exited. Thank you for using Unilink system!");
        newPostController.information.showAndWait();
        System.exit(0);

    }

    /**
     * This method is used to display developer information by jumping to developerInformation.fxml
     * @param actionEvent
     * @throws IOException
     */
    @FXML public void onDeveloperInformationPushed(ActionEvent actionEvent) throws IOException {
        FXMLLoader developerInformationLoader = new FXMLLoader();
        developerInformationLoader.setLocation(getClass().getResource("/view/developerInformation.fxml"));
        Parent developerInformation = developerInformationLoader.load();

        Scene developerInformationScene = new Scene(developerInformation);


        Stage window = new Stage();
        window.setResizable(false);
        window.setScene(developerInformationScene);
        window.show();

    }

    /**
     * This method is used to export data into export_data.txt and user selects a directory where the exported
     * file needs to be kept
     * @param actionEvent
     * @throws IOException
     */

    @FXML public void onExportClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)unilinkMenuBar.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open Directory");
        File selectedDirectory = directoryChooser.showDialog(stage);
        if(selectedDirectory==null) {
            newPostController.error.setHeaderText("No directory selected!");
        }
        else{
            String directoryPath = selectedDirectory.getAbsolutePath();

            File exportData = new File(directoryPath+"\\export_data.txt");

            FileOutputStream fileOutputStream = new FileOutputStream(exportData);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(MainGUI.collectPosts);
            out.writeObject(MainGUI.replies);

            out.close();
            fileOutputStream.close();
        }
    }

    /**
     * This method is used to import data from a text file that is selected by the user and thus the
     * list view displayed in main window is updated
     * @param actionEvent
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML public void onImportClicked(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Stage stage = (Stage)unilinkMenuBar.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Document (.txt)","*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);
        ArrayList<Post> allPosts = new ArrayList<>();
        ArrayList<Reply> allReplies = new ArrayList<>();
        if(file==null) {
            newPostController.error.setHeaderText("No file selected!");
        }
        else {
            if (file.exists()) {
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                    allPosts = (ArrayList<Post>) in.readObject();
                    allReplies = (ArrayList<Reply>)in.readObject();


                    in.close();
                }catch(Exception e){
                    newPostController.error.setHeaderText("File cannot be read!");
                    newPostController.error.showAndWait();
                }
            } else {
                newPostController.error.setHeaderText("No file found.");
            }

        }
        MainGUI.collectPosts=allPosts;
        MainGUI.replies = allReplies;

        }

    /**
     * this method is used to refresh the list view in main window
     * @param event
     * @throws Exception
     */
    @FXML
    public void onRefresh(ActionEvent event) throws Exception {
       // postsObservableList.clear();
        postsObservableList = initialize(username);
        allPostsListView.itemsProperty().bind(postsListProperty);
        postsListProperty.set(postsObservableList);


    }


}


