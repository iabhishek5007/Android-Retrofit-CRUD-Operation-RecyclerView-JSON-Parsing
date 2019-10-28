package com.ontrack.retrofit3;


//Post instead of ExampleItem
import com.google.gson.annotations.SerializedName;

public class Post {

    private int userId;

    private String id;

    private String name;

    private String ignition;

    private String image;

    @SerializedName("body")
    private String text;

    public Post(String id, String name, String image, String ignition) {
        this.id = id;
        this.name = name;
        this.ignition = ignition;
        this.image = image;
    }

    public String getId() {
        return id;
    }

  //  public Integer getId() {
    //    return id;
   // }

    public String getName() {
        return name;
    }

    public String getIgnition() {
        return ignition;
    }

    public String getImage(){
        return image;
    }
}
