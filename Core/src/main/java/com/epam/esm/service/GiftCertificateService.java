package com.epam.esm.service;

import com.epam.esm.model.Dto.GiftCertificateDto;

import java.util.List;
import java.util.Optional;

/**
 * The interface GiftCertificate service contains methods for business logic with GiftCertificate.
 *
 * @author Alexander Pishchala
 */
public interface GiftCertificateService {

    /**
     * Create a GiftCertificate.
     *
     * @param giftCertificateDto the entity GiftCertificateDto.
     *
     * @return returns a boolean result
     */
    boolean save(GiftCertificateDto giftCertificateDto);

    /**
     * Update Certificate.
     *
     * @param giftCertificateDto the GiftCertificate.
     *
     * @return @return returns a boolean result.
     */
    boolean update(GiftCertificateDto giftCertificateDto);

    /**
     * Certificate price update.
     *
     * @param giftCertificateDto the GiftCertificate.
     *
     * @return @return returns a boolean result.
     */
    boolean updatePrice(GiftCertificateDto giftCertificateDto);

    /**
     * Certificate duration update.
     *
     * @param giftCertificateDto the GiftCertificate.
     *
     * @return @return returns a boolean result.
     */
    boolean updateDuration(GiftCertificateDto giftCertificateDto);

    /**
     * Delete a certificate.
     *
     * @param id the GiftCertificate id.
     *
     * @return returns a boolean result
     */
    boolean delete(int id);

    /**
     * Show Certificate by id.
     *
     * @param id the GiftCertificate id.
     *
     * @return the specified Optional Gift certificate by id.
     */
    Optional<GiftCertificateDto> showGiftCertificate(int id);

    /**
     * The method will return list of Gift certificates.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the specified list of tags.
     */
    List<GiftCertificateDto> showGiftCertificates(int limit, int offset);

    /**
     * The method will return list of gift certificates by name.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     * @param name the gift certificate name.
     *
     * @return the specified list of Gift certificates by name.
     */
    List<GiftCertificateDto> showByPartName(int limit, int offset, String name);

    /**
     * The method will return list of gift certificates by tag name.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     * @param tagName the Tag name.
     *
     * @return the specified list of Gift certificates by tag name.
     */
    List<GiftCertificateDto> showByTagName(int limit, int offset, String tagName);

    /**
     * The method will return list of certificates sorted by name.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the specified list of Gift certificates sorted by name.
     */
    List<GiftCertificateDto> showAllBySortName(int limit, int offset);

    /**
     * The method will return list of certificates sorted by name in reverse order.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the specified list of Gift certificates sorted by name in reverse order.
     */
    List<GiftCertificateDto> showAllBySortNameReverse(int limit, int offset);

    /**
     * The method will return list of certificates sorted by date.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the specified list of Gift certificates sorted by date.
     */
    List<GiftCertificateDto> showByDate(int limit, int offset);

    /**
     * The method will return list of certificates sorted by date in reverse order.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the specified list of Gift certificates sorted by date in reverse order.
     */
    List<GiftCertificateDto> showByDateReverse(int limit, int offset);

    /**
     * The method will return list of certificates by order.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     * @param orderId the value of Order id.
     *
     * @return the specified list of Gift certificates by order.
     */
    List<GiftCertificateDto> showByOrder(int limit, int offset, int orderId);

    /**
     * The method will return the number of rows in the certificate table.
     *
     * @return The number of rows.
     */
    int showCountCertificates();
}
