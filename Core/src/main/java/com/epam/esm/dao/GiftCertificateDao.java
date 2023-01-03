package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificate;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@link GiftCertificateDao} provides operation with data of database table 'giftCertificate'.
 *
 * @author Alexander Pishchala
 */
public interface GiftCertificateDao {

    /**
     * The method creates new record in database table.
     *
     * @param giftCertificate entity that specifies creation of new records in database table.
     *
     * @return Returns true if the operation was successful.
     */
    boolean create(GiftCertificate giftCertificate);

    /**
     * The method updates new record in database table.
     *
     * @param giftCertificate entity that specifies creation of new records in database table.
     *
     * @return Returns true if the operation was successful.
     */
    boolean update(GiftCertificate giftCertificate);

    /**
     * The method deletes specified record in database table.
     *
     * @param id that will be deleted in database table.
     *
     * @return Returns true if the operation was successful.
     */
    boolean delete(int id);

    /**
     * The method returns specified User by id.
     *
     * @param id id gift certificate.
     *
     * @return the specified Optional Gift certificate by id.
     */
    Optional<GiftCertificate> findGiftCertificate(int id);

    /**
     * The method will return list of Gift certificates.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the specified list of tags.
     */
    List<GiftCertificate> findGiftCertificates(int limit, int offset);

    /**
     * The method will return list of gift certificates by name.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     * @param name the gift certificate name.
     *
     * @return the specified list of Gift certificates by name.
     */
    List<GiftCertificate> findByPartName(int limit, int offset, String name);

    /**
     * The method will return list of gift certificates by tag name.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     * @param tagName tag name.
     *
     * @return the specified list of Gift certificates by tag name.
     */
    List<GiftCertificate> findByTagName(int limit, int offset, String tagName);

    /**
     * The method will return list of certificates sorted by name.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the specified list of Gift certificates sorted by name.
     */
    List<GiftCertificate> findAllBySortName(int limit, int offset);

    /**
     * The method will return list of certificates sorted by name in reverse order.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the specified list of Gift certificates sorted by name in reverse order.
     */
    List<GiftCertificate> findAllBySortNameReverse(int limit, int offset);

    /**
     * The method will return list of certificates sorted by date.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the specified list of Gift certificates sorted by date.
     */
    List<GiftCertificate> findByDate(int limit, int offset);

    /**
     * The method will return list of certificates sorted by date in reverse order.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the specified list of Gift certificates sorted by date in reverse order.
     */
    List<GiftCertificate> findByDateReverse(int limit, int offset);

    /**
     * The method will return list of certificates by order.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     * @param orderId the value of Order id.
     *
     * @return the specified list of Gift certificates by order.
     */
    List<GiftCertificate> findByOrder(int limit, int offset, int orderId);

    /**
     * The method will return the number of rows in the certificate table.
     *
     * @return The number of rows.
     */
    int findCountCertificates();
}