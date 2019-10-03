package com.cepheid.cloud.skel.controller;

import com.cepheid.cloud.skel.dto.DescriptionDto;
import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.repository.DescriptionRepository;
import com.cepheid.cloud.skel.repository.ItemRepository;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@Path("/api/1.0")
@Api()
public class DescriptionController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private DescriptionService descriptionService;

    private final DescriptionRepository repository;

    @Autowired
    public DescriptionController(DescriptionRepository descriptionRepository) {
        repository = descriptionRepository;
    }

    @POST
    @Path("/addDesc/{itemId}/{description}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED)
    public DescriptionDto addDescription(@PathParam("itemId") long itemId, @PathParam("description") String descriptionText) {
        Item item = itemService.getItemById(itemId);
        if (item != null) {
            Description description = new Description(item, descriptionText);
            descriptionService.addDescription(description);
            return new DescriptionDto(itemId, descriptionText);
        } else {
            return null;
        }
    }

    @GET
    @Path("/getDescriptions")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Collection<DescriptionDto> getItems() {
        List<Description> descriptions = descriptionService.getDescriptions();
        List<DescriptionDto> descriptionDtoList = new ArrayList<>();
        for(Description description : descriptions){
            descriptionDtoList.add(new DescriptionDto(description.getItem().getId(), description.getDescription()));
        }
        return descriptionDtoList;
    }
}
