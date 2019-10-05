package com.cepheid.cloud.skel;

import com.cepheid.cloud.skel.dto.CreateItemDTO;
import com.cepheid.cloud.skel.dto.ItemDTO;
import com.cepheid.cloud.skel.dto.UpdateItemDTO;
import com.cepheid.cloud.skel.dto.UpdateStateDTO;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

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
    public void shouldUpdateItem() {
        Item item = new Item("item1");
        itemRepository.save(item);

        UpdateItemDTO inputForUpdate = new UpdateItemDTO("updated item1", Item.State.IN_STOCK);
        Builder itemController = getBuilder("/updateItem/{itemId}", item.getId());
        ItemDTO updatedItemResponse = itemController.put(Entity.json(inputForUpdate), new GenericType<ItemDTO>() {
        });

        assertEquals("updated item1", updatedItemResponse.getName());
        assertEquals(Item.State.IN_STOCK, updatedItemResponse.getState());

        Item updatedItemInDb = itemRepository.findById(item.getId()).get();
        assertEquals("updated item1", updatedItemInDb.getName());
        assertEquals(Item.State.IN_STOCK, updatedItemInDb.getState());
    }

    @Test
    public void shouldDeleteItemDescriptionsAfterDeleteTheItem() {

        Item item = itemRepository.save(new Item("item1"));

        Description description1 = descriptionRepository.save(new Description(item, "item1 desc1"));
        Description description2 = descriptionRepository.save(new Description(item, "item1 desc2"));

        Builder itemController = getBuilder("/deleteItem/{itemId}", item.getId());
        itemController.delete();

        assertFalse(descriptionRepository.findById(description1.getId()).isPresent());
        assertFalse(descriptionRepository.findById(description2.getId()).isPresent());
    }

    @Test
    public void shouldGetItemsByItemName() {
        String name = "item";
        Item item1 = itemRepository.save(new Item(name));
        Item item2 = itemRepository.save(new Item(name));
        itemRepository.save(new Item("item3"));

        Builder itemController = getBuilder("/getItemsByName/{name}", name);
        Collection<ItemDTO> responseItems = itemController.get(new GenericType<Collection<ItemDTO>>() {
        });

        List<ItemDTO> expectedItems = new ArrayList<>();
        expectedItems.add(new ItemDTO(item1));
        expectedItems.add(new ItemDTO(item2));

        assertArrayEquals(expectedItems.toArray(), responseItems.toArray());
    }

    @Test
    public void shouldGetItemsByItemDescription() {
        String description = "desc";
        Item item1 = itemRepository.save(new Item("item1"));
        Item item2 = itemRepository.save(new Item("item2"));
        Item item3 = itemRepository.save(new Item("item3"));


        descriptionRepository.save(new Description(item1, description));
        descriptionRepository.save(new Description(item2, description));
        descriptionRepository.save(new Description(item3, "different description"));

        item1 = itemRepository.findById(item1.getId()).get();
        item2 = itemRepository.findById(item2.getId()).get();

        Builder itemController = getBuilder("/getItemsByDescription/{description}", description);
        List<ItemDTO> responseItems = itemController.get(new GenericType<List<ItemDTO>>() {
        });

        List<ItemDTO> expectedItems = new ArrayList<>();
        expectedItems.add(new ItemDTO(item1));
        expectedItems.add(new ItemDTO(item2));

        assertArrayEquals(expectedItems.toArray(), responseItems.toArray());
    }

    @Test
    public void shouldUpdateItemState() {
        Item item = new Item("item1");
        item.setState(Item.State.IN_STOCK);
        itemRepository.save(item);

        UpdateStateDTO updateInput = new UpdateStateDTO(Item.State.OUT_OF_STOCK);
        Builder itemController = getBuilder("/setState/{itemId}", item.getId());
        itemController.put(Entity.json(updateInput));

        Item.State updatedState = itemRepository.findById(item.getId()).get().getState();
        assertEquals(Item.State.OUT_OF_STOCK , updatedState);
    }
}
