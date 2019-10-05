package com.cepheid.cloud.skel;

import com.cepheid.cloud.skel.dto.CreateDescriptionDTO;
import com.cepheid.cloud.skel.dto.DescriptionDTO;
import com.cepheid.cloud.skel.dto.UpdateDescriptionDTO;
import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericType;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class DescriptionControllerTest extends TestBase {

    @Test
    public void shouldAddDescriptionForOneItem() {
        Item item = new Item("item1");
        Item persistedItem = itemRepository.save(item);

        Builder descriptionController = getBuilder("/addDescription");
        String expectedDescription = "item1 desc1";
        DescriptionDTO response = descriptionController.post(Entity.json(new CreateDescriptionDTO(persistedItem.getId(), expectedDescription)), new GenericType<DescriptionDTO>() {
        });

        String persistedDescription = descriptionRepository.findAll().get(0).getDescription();

        assertEquals(expectedDescription, response.getDescription());
        assertEquals(expectedDescription, persistedDescription);
    }

    @Test
    public void shouldGetPersistedDescriptionsForSpecificItem() {
        Item item1 = new Item("item1");
        Item persistedItem1 = itemRepository.save(item1);

        Item item2 = new Item("item2");
        itemRepository.save(item2);

        Description description1 = descriptionRepository.save(new Description(item1, "item1 desc1"));
        Description description2 = descriptionRepository.save(new Description(item1, "item1 desc2"));
        descriptionRepository.save(new Description(item2, "item2 desc1"));

        List<DescriptionDTO> expectedDescriptions = new ArrayList<>();
        expectedDescriptions.add(new DescriptionDTO(description1));
        expectedDescriptions.add(new DescriptionDTO(description2));

        Builder descriptionController = getBuilder("/getDescriptions/{itemId}", persistedItem1.getId());
        Collection<DescriptionDTO> actualDescriptions = descriptionController.get(new GenericType<Collection<DescriptionDTO>>() {
        });

        assertArrayEquals(expectedDescriptions.toArray(), actualDescriptions.toArray());
    }

    @Test
    public void shouldUpdateSpecificDescription() {

        //Sets descriptions for item and persists item and descriptions into db

        Item item = itemRepository.save(new Item("item 1"));
        Description description = descriptionRepository.save(new Description(item, "item1 desc1"));

        Builder descriptionController = getBuilder("/updateDescription/{descriptionId}", description.getId());
        DescriptionDTO descriptionResponse = descriptionController.put(Entity.json(new UpdateDescriptionDTO("item1 updated desc")), new GenericType<DescriptionDTO>() {
        });

        assertEquals("item1 updated desc", descriptionResponse.getDescription());
        assertEquals("item1 updated desc", descriptionRepository.findById(description.getId()).get().getDescription());
    }
}
