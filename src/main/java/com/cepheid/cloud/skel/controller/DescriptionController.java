package com.cepheid.cloud.skel.controller;

import com.cepheid.cloud.skel.dto.CreateDescriptionDTO;
import com.cepheid.cloud.skel.dto.DescriptionDTO;
import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.repository.DescriptionRepository;
import com.cepheid.cloud.skel.service.DescriptionService;
import com.cepheid.cloud.skel.service.ItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Component
@Path("/api/1.0")
@Api()
public class DescriptionController {
    private final ItemService itemService;

    private final DescriptionService descriptionService;

    @Autowired
    public DescriptionController(ItemService itemService, DescriptionService descriptionService) {
        this.itemService = itemService;
        this.descriptionService = descriptionService;
    }

    @POST
    @Path("/addDescription")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED)
    public DescriptionDTO addDescription(@RequestBody CreateDescriptionDTO createDescriptionDTO) {
        Item item = itemService.getItemById(createDescriptionDTO.getItemId());
        if (item != null) {
            Description description = new Description(item, createDescriptionDTO.getDescription());
            descriptionService.addDescription(description);
            Set<Description> itemDescriptions = new HashSet<>();
            itemDescriptions.add(description);
            item.setDescriptions(itemDescriptions);
            itemService.updateItem(item);
            return new DescriptionDTO(description);
        } else {
            return null;
        }
    }

    @GET
    @Path("/getDescriptions/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Collection<DescriptionDTO> getDescriptions(@PathParam("itemId") Long itemID) {
        List<Description> descriptions = descriptionService.getDescriptionsByItemId(itemID);
        List<DescriptionDTO> descriptionDtoList = new ArrayList<>();
        for(Description description : descriptions){
            descriptionDtoList.add(new DescriptionDTO(description));
        }
        return descriptionDtoList;
    }
}
