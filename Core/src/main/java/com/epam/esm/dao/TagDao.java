package com.epam.esm.dao;

import com.epam.esm.model.PopularTag;
import com.epam.esm.model.Tag;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@link TagDao} provides operation with data of database table 'tags'.
 *
 * @author Alexander Pishchala
 */
public interface TagDao {

    /**
     * The method creates new record in database table.
     *
     * @param tag entity that specifies creation of new records in database table.
     * @return Returns true if the operation was successful.
     */
    boolean create(Tag tag);

    /**
     * The method returns specified Tag by id.
     *
     * @param tagId id tag.
     * @return specified Optional Tag by id.
     */
    Optional<Tag> findTag(int tagId);

    /**
     * The method deletes specified record in database table.
     *
     * @param tagId that will be deleted in database table.
     * @return Returns true if the operation was successful.
     */
    boolean delete(int tagId);

    /**
     * The method will return list Tags.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the specified list of tags.
     */
    List<Tag> findAllTags(int limit, int offset);

    /**
     * The method will return list of Tags by name.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     * @param name the part tag name.
     *
     * @return the specified list of tags by part name.
     */
    List<Tag> findByPartName(int limit, int offset, String name);

    /**
     * The method returns specified Tag by name.
     *
     * @param name The tag name
     *             .
     * @return specified Optional Tag by name.
     */
    Optional<Tag> findByName(String name);

    /**
     * The method will return the user's popular tag with the maximum cost of all orders.
     *
     * @return String message.
     */
    Optional<PopularTag> findPopularTagByMaxCostOrdersUser();

    /**
     * The method will return the number of rows in the Tag table.
     *
     * @return The number of rows.
     */
    int findCountTags();
}
