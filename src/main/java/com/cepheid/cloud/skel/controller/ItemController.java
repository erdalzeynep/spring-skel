package com.cepheid.cloud.skel.controller;

import java.util.Collection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.cepheid.cloud.skel.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cepheid.cloud.skel.model.Item;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

// curl http:/localhost:9443/app/api/1.0/items

@Component
@Path("/api/1.0")
@Api()
public class ItemController {
    @Autowired
    private ItemService service;

    @GET
    @Path("/getItems")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Collection<Item> getItems() {
        return service.getItems();
    }

    @POST
    @Path("/addItem")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Long addItem(@RequestBody Item item) {
        Item returnItem = service.addItem(item);
        return returnItem.getId();
    }

    @DELETE
    @Path("/removeItem/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean removeItem(@PathParam("itemId") long itemId) {
        service.deleteItem(itemId);
        return true;
    }
}
