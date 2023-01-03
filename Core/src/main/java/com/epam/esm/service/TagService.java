package com.epam.esm.service;

import com.epam.esm.model.Dto.TagDto;
import com.epam.esm.model.PopularTag;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag service contains methods for business logic with tag.
 *
 * @author Alexander Pishchala
 */
public interface TagService {

    /**
     * Create a tag.
     *
     * @param tagDto the entity TagDto.
     *
     * @return returns a boolean result
     */
    boolean save(TagDto tagDto);

    /**
     * Delete a tag.
     *
     * @param tagId the id
     *
     * @return returns a boolean result
     */
    boolean delete(int tagId);

    /**
     * Show tag by id.
     *
     * @param tagId the Tag id.
     *
     * @return the specified Optional Tag by id.
     */
    Optional<TagDto> showById(int tagId);

    /**
     * Show tag by name.
     *
     * @param name the Tag name.
     *
     * @return the specified Optional Tag by name.
     */
    Optional<TagDto> showByName(String name);

    /**
     * Show all list of tags.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the list of tags.
     */
    List<TagDto> showTags(int limit, int offset);

    /**
     * Show list of tags by a part name.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the list of tags.
     */
    List<TagDto> showByPartName(int limit, int offset, String name);

    /**
     * The method will return the user's popular tag with the maximum cost of all orders.
     *
     * @return String message.
     */
    Optional<PopularTag> showPopularTagByMaxCostOrdersUser();

    /**
     * The method will return the number of rows in the tag table.
     *
     * @return The number of rows.
     */
    int showCountTags();
}
