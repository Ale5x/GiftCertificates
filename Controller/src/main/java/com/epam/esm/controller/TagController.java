package com.epam.esm.controller;

import com.epam.esm.exception.AppRequestException;
import com.epam.esm.exception.ErrorMessage;
import com.epam.esm.model.PopularTag;
import com.epam.esm.util.LocalUtil;
import com.epam.esm.model.Dto.TagDto;
import com.epam.esm.validator.ValidatorParams;
import org.springframework.hateoas.Link;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.controller.ConstantController.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The type Tag Controller is a controller which operates requests from clients and generates response in
 * representational forms. Information exchanging are in a JSON forms.
 *
 * @author Alexander Pishchala
 */
@RestController
@CrossOrigin({"http://localhost:3000", "http://localhost:8080"})
public class TagController extends PaginationHelper {

    private ValidatorParams validator;

    private TagService tagService;

    private LocalUtil localUtil;

    /**
     * Instantiates a new TagController.
     *
     * @param tagService the tag service
     * @param localUtil the Local.
     * @param validator the validator values.
     */
    @Autowired
    public TagController(TagService tagService, LocalUtil localUtil, ValidatorParams validator) {
        this.tagService = tagService;
        this.localUtil = localUtil;
        this.validator = validator;
    }

    /**
     * Create a Tag.
     *
     * @param tagDto the body of tagDto.
     *
     * @return the HttpStatus.
     */
    @PostMapping(value = PathPageConstant.TAG_CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> createTag(@RequestBody TagDto tagDto) {
        if (tagService.save(tagDto)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Getting a tag by id.
     *
     * @param id the tag id.
     *
     * @return the tag by id.
     */
    @GetMapping(value = PathPageConstant.TAG_GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TagDto getTag(@RequestParam(ConstantController.ID) String id) {
        validator.validId(id);
        Optional<TagDto> tag = tagService.showById(Integer.parseInt(id));
        return tag.orElseThrow(() -> new AppRequestException((localUtil.getMessage(ErrorMessage.TAG_BY_ID_NOT_FOUND)) + id,
                HttpStatus.NOT_FOUND));
    }

    /**
     * Deleting a tag.
     *
     * @param id the tag id.
     *
     * @return the HttpStatus.
     */
    @DeleteMapping(value = PathPageConstant.TAG_DELETE)
    public ResponseEntity<HttpStatus> delete(@RequestParam(ConstantController.ID) String id) {
        validator.validId(id);
        tagService.delete(Integer.parseInt(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Getting tags.
     *
     * @param page the page being viewed.
     * @param size the number of elements per page.
     *
     * @return the list of tags.
     */
    @GetMapping(value = PathPageConstant.TAG_GET_ALL)
    public CollectionModel<TagDto> getTags(@RequestParam(PAGE) String page, @RequestParam(SIZE) String size){
        validator.validPage(page);
        validator.validSize(size);
        Link previousLink = linkTo(methodOn(TagController.class)
                .getTags(getPreviousPage(page), size))
                .withRel(ConstantController.PREVIOUS_LINK).withType(ConstantController.METHOD_GET);
        Link nextLink = linkTo(methodOn(TagController.class)
                .getTags(getNextPage(page), size))
                .withRel(ConstantController.NEXT_LINK).withType(ConstantController.METHOD_GET);
        List<TagDto> tagDtos = tagService.showTags(Integer.parseInt(size), getOffset(page, size));

        return CollectionModel.of(tagDtos, previousLink, nextLink);
    }

    /**
     * Getting tags by part name.
     *
     * @param page the page being viewed.
     * @param size the number of elements per page.
     *
     * @return the list of tags.
     */
    @GetMapping(value = PathPageConstant.TAG_GET_BY_PART_NAME)
    public CollectionModel<TagDto> getTagsByPartName(@RequestParam(ConstantController.PAGE) String page,
                                                     @RequestParam(ConstantController.SIZE) String size,
                                                     @RequestParam(ConstantController.NAME) String name) {
        validator.validPage(page);
        validator.validSize(size);
        Link previousLink = linkTo(methodOn(TagController.class)
                .getTagsByPartName(getPreviousPage(page), size, name))
                .withRel(ConstantController.PREVIOUS_LINK).withType(ConstantController.METHOD_GET);
        Link nextLink = linkTo((methodOn(TagController.class)
                .getTagsByPartName(getNextPage(page), size, name)))
                .withRel(ConstantController.NEXT_LINK).withType(ConstantController.METHOD_GET);
        List<TagDto> tagDtoList = tagService.showByPartName(Integer.parseInt(size), getOffset(page, size), name);

        return CollectionModel.of(tagDtoList, previousLink, nextLink);
    }

    /**
     * Getting a tag by name.
     *
     * @param name the tag name.
     *
     * @return the tag by name.
     */
    @GetMapping(value = PathPageConstant.TAG_GET_NAME, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TagDto getTagByName(@RequestParam(ConstantController.NAME) String name) {
        Optional<TagDto> tag = tagService.showByName(name);
        return tag.orElseThrow(() -> new AppRequestException(
                (localUtil.getMessage(ErrorMessage.TAG_BY_NAME_NOT_FOUND)) + name, HttpStatus.NOT_FOUND));
    }

    /**
     * Getting the user's popular tag with the maximum cost of all orders.
     *
     * @return the message the user's popular tag.
     */
    @GetMapping(value = PathPageConstant.TAG_GET_POPULAR_TAG, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PopularTag getPopularTag() {
        Optional<PopularTag> popularTag = tagService.showPopularTagByMaxCostOrdersUser();
        return popularTag.orElseThrow(() -> new AppRequestException(localUtil.getMessage(ErrorMessage.TAG_NOT_FOUND),
                HttpStatus.NOT_FOUND));
    }

    /**
     * Getting count tags.
     *
     * @return the message count.
     */
    @GetMapping(value = PathPageConstant.TAG_GET_COUNT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Integer> getCountTag() {
        return new ResponseEntity<>(tagService.showCountTags(), HttpStatus.OK);
    }
}
