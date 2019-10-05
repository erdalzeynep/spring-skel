package com.cepheid.cloud.skel.controller;

import com.cepheid.cloud.skel.dto.CreateItemDTO;
import com.cepheid.cloud.skel.dto.ItemDTO;
import com.cepheid.cloud.skel.dto.UpdateItemDTO;
import com.cepheid.cloud.skel.dto.UpdateStateDTO;
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
import java.util.Set;

// curl http:/localhost:9443/app/api/1.0/items

@Component
@Path("/api/1.0")
@Api()
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GET
    @Path("/getItems")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Collection<ItemDTO> getItems() {
        List<Item> items = itemService.getItems();
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
        Item item = new Item(itemRequest.getName());
        itemService.addItem(item);
        return new ItemDTO(item);
    }

    @DELETE
    @Path("/deleteItem/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED)
    public Response deleteItem(@PathParam("itemId") long itemId) {
        Item itemToBeDeleted = itemService.getItemById(itemId);
        if (itemToBeDeleted != null) {
            itemService.deleteItem(itemToBeDeleted);
            return Response.status(200).entity("Item is deleted").build();
        } else {
            return Response.status(204).build();
        }
    }

    @PUT
    @Path("/updateItem/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED)
    public ItemDTO updateItem(@PathParam("itemId") long itemId, @RequestBody UpdateItemDTO updateItemBody) {
        Item item = itemService.getItemById(itemId);
        if (item != null) {
            item.setName(updateItemBody.getName());
            item.setState(updateItemBody.getState());
            Item updatedItem = itemService.updateItem(item);
            return new ItemDTO(updatedItem);
        } else {
            return null;
        }
    }

    @GET
    @Path("/getItemsByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Collection<ItemDTO> getItemsByGivenName(@PathParam("name") String name) {
        List<Item> items = itemService.getItemsByName(name);
        List<ItemDTO> returnedItems = new ArrayList<>();
        for (Item item : items) {
            returnedItems.add(new ItemDTO(item));
        }
        return returnedItems;
    }

    @PUT
    @Path("/setState/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED)
    public ItemDTO updateItemState(@PathParam("itemId") long itemId, @RequestBody UpdateStateDTO updateBody) {
        Item item = itemService.getItemById(itemId);
        if (item != null) {
            item.setState(updateBody.getState());
            Item updatedItem = itemService.updateItem(item);
            return new ItemDTO(updatedItem);
        } else {
            return null;
        }
    }

    @GET
    @Path("/searchItems/byName")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Collection<ItemDTO> searchItemsByName(@QueryParam("search-text") String searchText) {
        List<Item> items = itemService.getItemsByNameContaining(searchText);
        List<ItemDTO> returnedItems = new ArrayList<>();
        for (Item item : items) {
            returnedItems.add(new ItemDTO(item));
        }
        return returnedItems;
    }

    @GET
    @Path("/searchItems/byDescription")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Collection<ItemDTO> searchItemsByDescription(@QueryParam("search-text") String searchText) {
        Set<Item> items = itemService.getItemsByDescriptionContaining(searchText);
        List<ItemDTO> returnedItems = new ArrayList<>();
        for (Item item : items) {
            returnedItems.add(new ItemDTO(item));
        }
        return returnedItems;
    }
}
