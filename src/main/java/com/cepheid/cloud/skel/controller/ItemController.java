package com.cepheid.cloud.skel.controller;

import com.cepheid.cloud.skel.dto.ItemDTO;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.repository.ItemRepository;
import com.cepheid.cloud.skel.service.ItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

// curl http:/localhost:9443/app/api/1.0/items

@Component
@Path("/api/1.0")
@Api()
public class ItemController {
    private final ItemService service;

    @Autowired
    public ItemController(ItemService service) {
        this.service = service;
    }

    @GET
    @Path("/getItems")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Collection<Item> getItems() {
       return  service.getItems();
    }

    @POST
    @Path("/addItem")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED)
    public ItemDTO addItem(@RequestBody Item item) {
       Item addedItem = service.addItem(item);
       return new ItemDTO(addedItem.getId(), addedItem.getName());
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
