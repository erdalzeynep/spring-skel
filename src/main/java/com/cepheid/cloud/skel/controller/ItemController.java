package com.cepheid.cloud.skel.controller;

import com.cepheid.cloud.skel.dto.ItemDTO;
import com.cepheid.cloud.skel.dto.CreateItemDTO;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.service.ItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public Collection<ItemDTO> getItems() {
        List<Item> items = service.getItems();
        List<ItemDTO> returnedItems = new ArrayList<>();
        for (Item item : items) {
            returnedItems.add(new ItemDTO(item));
        }
        return returnedItems;
    }

    @POST
    @Path("/addItem")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED)
    public ItemDTO addItem(@RequestBody CreateItemDTO itemRequest) {
        Item item = new Item(itemRequest);
        service.addItem(item);
        return new ItemDTO(item);
    }

    @DELETE
    @Path("/deleteItem/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED)
    public Response deleteItem(@PathParam("itemId") long itemId) {
        Item itemToBeDeleted = service.getItemById(itemId);
        if (itemToBeDeleted != null) {
            service.deleteItem(itemToBeDeleted);
            return Response.status(200).entity("Item is deleted").build();
        } else {
            return Response.status(204).build();
        }
    }

    @DELETE
    @Path("/deleteItems/{state}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED)
    public Response deleteItemsByState(@PathParam("state") String state){
        List<Item> itemsToBeDeleted = service.getItemsByState(state);
        if(itemsToBeDeleted.size()!=0){
            service.deleteItems(itemsToBeDeleted);
            return Response.status(200).entity("Items are deleted").build();
        }
        else{
            return Response.status(204).build();
        }
    }
}
