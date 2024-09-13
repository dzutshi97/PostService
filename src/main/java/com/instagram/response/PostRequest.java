package com.instagram.response;

import com.instagram.models.Location;
import com.instagram.models.User;
import lombok.Data;

@Data
public class PostRequest {

    private String description;
    private Location location;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
