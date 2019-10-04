package com.cepheid.cloud.skel;

import com.cepheid.cloud.skel.dto.ItemDTO;
import com.cepheid.cloud.skel.dto.CreateItemDTO;
import com.cepheid.cloud.skel.dto.UpdateItemDTO;
import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class ItemControllerTest extends TestBase {

    @Transactional
    @Test
    public void shouldAddSpecificItem() {
        String itemName1 = "item1";
        String itemName2 = "item2";

        Builder itemController = getBuilder("/addItem");
        ItemDTO item1 = itemController.post(Entity.json(new CreateItemDTO(itemName1)), new GenericType<ItemDTO>() {
        });
        ItemDTO item2 = itemController.post(Entity.json(new CreateItemDTO(itemName2)), new GenericType<ItemDTO>() {
        });

        ItemDTO expectedItem1 = new ItemDTO(itemRepository.getOne(item1.getItemId()));
        ItemDTO expectedItem2 = new ItemDTO(itemRepository.getOne(item2.getItemId()));

        assertEquals(expectedItem1, item1);
        assertEquals(expectedItem2, item2);
    }

    @Test
    public void shouldGetAllItems() {
        Item expectedItem1 = itemRepository.save(new Item("item1"));
        Item expectedItem2 = itemRepository.save(new Item("item2"));
        Item expectedItem3 = itemRepository.save(new Item("item3"));

        List<ItemDTO> expectedItems = new ArrayList<>();
        expectedItems.add(new ItemDTO(expectedItem1));
        expectedItems.add(new ItemDTO(expectedItem2));
        expectedItems.add(new ItemDTO(expectedItem3));

        Builder itemController = getBuilder("/getItems");
        Collection<ItemDTO> items = itemController.get(new GenericType<Collection<ItemDTO>>() {
        });

        assertEquals(expectedItems, items);
    }

    @Test
    public void shouldDeleteSpecificItem() {
        Item item1 = itemRepository.save(new Item("item1"));

        assertEquals(1, itemRepository.findAll().size());

        Builder itemController = getBuilder("/deleteItem/{itemId}", item1.getId());
        Response response = itemController.delete(new GenericType<Response>() {
        });

        assertEquals(200, response.getStatus());
        assertEquals(0, itemRepository.findAll().size());
    }

    @Test
    public void shouldReturn204AfterDeleteRequestWithNonExistItem() {
        itemRepository.save(new Item("item1"));
        itemRepository.save(new Item("item2"));
        itemRepository.save(new Item("item3"));

        Builder itemController = getBuilder("/deleteItem/{itemId}", 4);
        Response response = itemController.delete(new GenericType<Response>() {
        });

        assertEquals(204, response.getStatus());
        assertEquals(3, itemRepository.findAll().size());
    }

    @Test
    public void shouldDeleteAllItemsWithGivenState(){
        Item item1 = new Item("item1");
        Item item2 = new Item("item2");
        Item item3 = new Item("item3");

        item1.setState(Item.State.UNDEFINED);
        item2.setState(Item.State.UNDEFINED);
        item3.setState(Item.State.INVALID);

        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

        Builder itemController = getBuilder("/deleteItems/{state}", "undefined");
        Response response = itemController.delete(new GenericType<Response>(){});

        List<Item> leftItems = new ArrayList<>();
        leftItems.add(item3);

        assertEquals(200, response.getStatus());
        assertEquals(1, itemRepository.findAll().size());
        assertEquals(leftItems, itemRepository.findAll());
    }

    @Test
    public void shouldUpdateItem(){
        Item item = new Item("item1");
        itemRepository.save(item);

        UpdateItemDTO inputForUpdate = new UpdateItemDTO("updated item1", "VALID");
        Builder itemController = getBuilder("/updateItem/{itemId}", item.getId());
        ItemDTO updatedItemResponse = itemController.put(Entity.json(inputForUpdate), new GenericType<ItemDTO>(){});

        assertEquals("updated item1", updatedItemResponse.getName());
        assertEquals("VALID", updatedItemResponse.getState().toString());

        Item updatedItemInDb = itemRepository.findById(item.getId()).get();
        assertEquals("updated item1" , updatedItemInDb.getName());
        assertEquals("VALID" , updatedItemInDb.getState().toString());
    }
}
