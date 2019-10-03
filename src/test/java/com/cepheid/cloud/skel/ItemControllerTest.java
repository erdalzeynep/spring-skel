package com.cepheid.cloud.skel;

import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.repository.ItemRepository;
import com.cepheid.cloud.skel.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericType;
import java.util.Collection;

@RunWith(SpringRunner.class)
public class ItemControllerTest extends TestBase {

    @Autowired
    ItemRepository repository;

    @Autowired
    ItemService itemService;

    @Test
    public void shouldReturnListOfItems() {

        Builder itemController = getBuilder("/getItems");
        Collection<Item> items = itemController.get(new GenericType<Collection<Item>>() {
        });
    }
}
