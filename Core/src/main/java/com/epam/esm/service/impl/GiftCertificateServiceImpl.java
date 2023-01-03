package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.Dto.TagDto;
import com.epam.esm.util.LocalUtil;
import com.epam.esm.model.Dto.GiftCertificateDto;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.ValidatorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The type GiftCertificate service implements methods of the GiftCertificate interface.
 * The class is annotated as a service, which qualifies it to be automatically created by component-scanning.
 *
 * @author Alexander Pishchala
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final Logger logger = LoggerFactory.getLogger(GiftCertificateServiceImpl.class);

    private final static String ISO_FORMAT_DATE = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String CERTIFICATE_NOT_FOUND = "Certificate not found by ID. ID: ";
    public static final String CERTIFICATE_NOT_FOUND_CODE = "code.certificate.not_found";

    private GiftCertificateDao certificateDao;

    private TagDao tagDao;

    private LocalUtil localUtil;

    /**
     * Instantiates a new GiftCertificate service.
     *
     * @param tagDao the tag repository.
     * @param certificateDao the GiftCertificate repository.
     * @param localUtil the locale to receive error messages according to the user's locale.
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao certificateDao, TagDao tagDao, LocalUtil localUtil) {
        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
        this.localUtil = localUtil;
    }

    @Override
    public boolean save(GiftCertificateDto certificateDto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        ValidatorEntity.certificate(certificateDto, localUtil);
        giftCertificate.setName(certificateDto.getName());
        giftCertificate.setDescription(certificateDto.getDescription());
        giftCertificate.setPrice(certificateDto.getPrice());
        giftCertificate.setDuration(certificateDto.getDuration());
        giftCertificate.setCreateDate(getLocalDateTime());
        giftCertificate.setLastUpdateDate(getLocalDateTime());
        giftCertificate.setTags(getFilterTags(certificateDto.getTags()));
        return certificateDao.create(giftCertificate);
    }

    @Override
    public boolean update(GiftCertificateDto certificateDto) {
        Optional<GiftCertificate> oldCertificate
                = certificateDao.findGiftCertificate(certificateDto.getGiftCertificateDtoId());

        if (oldCertificate.isPresent()) {
            GiftCertificate updateCertificate = new GiftCertificate();

            updateCertificate.setGiftCertificateId(oldCertificate.get().getGiftCertificateId());
            updateCertificate.setName(certificateDto.getName() != "" ?
                    certificateDto.getName() : oldCertificate.get().getName());
            updateCertificate.setDescription(certificateDto.getDescription() != "" ?
                    certificateDto.getDescription() : oldCertificate.get().getDescription());
            updateCertificate.setPrice(certificateDto.getPrice() != null ?
                    certificateDto.getPrice() : oldCertificate.get().getPrice());
            updateCertificate.setDuration(certificateDto.getDuration() != 0 ?
                    certificateDto.getDuration() : oldCertificate.get().getDuration());
            updateCertificate.setCreateDate(oldCertificate.get().getCreateDate());
            updateCertificate.setLastUpdateDate(getLocalDateTime());

            if (certificateDto.getTags() != null) {
                Set<Tag> tags = getFilterTags(certificateDto.getTags());
                updateCertificate.setTags(tags);
            } else {
                updateCertificate.setTags(oldCertificate.get().getTags());
            }
            ValidatorEntity.certificateForUpdate(updateCertificate, localUtil);
            return certificateDao.update(updateCertificate);
            } else {
            logger.error(CERTIFICATE_NOT_FOUND + certificateDto.getGiftCertificateDtoId());
            throw new ServiceException(localUtil.getMessage(CERTIFICATE_NOT_FOUND_CODE) + certificateDto.getGiftCertificateDtoId());
        }
    }

    private LocalDateTime getLocalDateTime() {
        return LocalDateTime.parse(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(ISO_FORMAT_DATE)));
    }

    @Override
    public boolean updatePrice(GiftCertificateDto certificateDto) {
        Optional<GiftCertificate> certificate = certificateDao.findGiftCertificate(certificateDto.getGiftCertificateDtoId());
        if (certificate.isPresent()) {
            certificate.get().setPrice(certificateDto.getPrice());
            certificate.get().setLastUpdateDate(getLocalDateTime());
            ValidatorEntity.certificateForUpdate(certificate.get(), localUtil);
            return certificateDao.update(certificate.get());
        } else {
            logger.error(CERTIFICATE_NOT_FOUND + certificateDto.getGiftCertificateDtoId());
            throw new ServiceException(localUtil.getMessage(CERTIFICATE_NOT_FOUND_CODE) + certificateDto.getGiftCertificateDtoId());
        }
    }

    @Override
    public boolean updateDuration(GiftCertificateDto certificateDto) {
        Optional<GiftCertificate> certificate = certificateDao.findGiftCertificate(certificateDto.getGiftCertificateDtoId());
        if (certificate.isPresent()) {
            certificate.get().setDuration(certificateDto.getDuration());
            certificate.get().setLastUpdateDate(getLocalDateTime());
            ValidatorEntity.certificateForUpdate(certificate.get(), localUtil);
            return certificateDao.update(certificate.get());
        } else {
            logger.error(CERTIFICATE_NOT_FOUND + certificateDto.getGiftCertificateDtoId());
            throw new ServiceException(localUtil.getMessage(CERTIFICATE_NOT_FOUND_CODE) + certificateDto.getGiftCertificateDtoId());
        }
    }

    @Override
    public boolean delete(int id) {
        Optional<GiftCertificate> certificate = certificateDao.findGiftCertificate(id);
        if (certificate.isPresent()) {
            return certificateDao.delete(id);
        } else {
            logger.error(CERTIFICATE_NOT_FOUND + id);
            throw new ServiceException(localUtil.getMessage(CERTIFICATE_NOT_FOUND_CODE) + id);
        }
    }

    @Override
    @Transactional
    public Optional<GiftCertificateDto> showGiftCertificate(int id) {
        Optional<GiftCertificate> certificate = certificateDao.findGiftCertificate(id);
        return certificate.map(value -> builderCertificateDto(Collections.singletonList(value)).get(0));
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> showGiftCertificates(int limit, int offset) {
        List<GiftCertificate> certificateList = certificateDao.findGiftCertificates(limit, offset);
        return builderCertificateDto(certificateList);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> showByPartName(int limit, int offset, String name) {
        List<GiftCertificate> certificateList = certificateDao.findByPartName(limit, offset, name);
        return builderCertificateDto(certificateList);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> showByTagName(int limit, int offset, String tagName) {
        List<GiftCertificate> certificateList = certificateDao.findByTagName(limit, offset, tagName);
        return builderCertificateDto(certificateList);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> showAllBySortName(int limit, int offset) {
        List<GiftCertificate> certificateList = certificateDao.findAllBySortName(limit, offset);
        return builderCertificateDto(certificateList);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> showAllBySortNameReverse(int limit, int offset) {
        List<GiftCertificate> certificateList = certificateDao.findAllBySortNameReverse(limit, offset);
        return builderCertificateDto(certificateList);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> showByDate(int limit, int offset) {
        List<GiftCertificate> certificateList = certificateDao.findByDate(limit, offset);
        return builderCertificateDto(certificateList);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> showByDateReverse(int limit, int offset) {
        List<GiftCertificate> certificateList = certificateDao.findByDateReverse(limit, offset);
        return builderCertificateDto(certificateList);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> showByOrder(int limit, int offset, int orderId) {
        List<GiftCertificate> certificateList = certificateDao.findByOrder(limit, offset, orderId);
        return builderCertificateDto(certificateList);
    }

    @Override
    public int showCountCertificates() {
        return certificateDao.findCountCertificates();
    }

    /**
     * Builder list of Certificates Dto by list of Certificates entity. This is a helper method needed to avoid
     * code duplication.
     *
     * @param certificates the list of certificates.
     *
     * @return list of GiftCertificates Dto.
     */
    private List<GiftCertificateDto> builderCertificateDto(List<GiftCertificate> certificates) {
        List<GiftCertificateDto> certificateDtoList = new ArrayList<>();
        for (GiftCertificate certificate : certificates) {
            GiftCertificateDto certificateDto = new GiftCertificateDto();
            certificateDto.setGiftCertificateDtoId(certificate.getGiftCertificateId());
            certificateDto.setName(certificate.getName());
            certificateDto.setDuration(certificate.getDuration());
            certificateDto.setDescription(certificate.getDescription());
            certificateDto.setPrice(certificate.getPrice());
            certificateDto.setCreateDate(certificate.getCreateDate());
            certificateDto.setLastUpdateDate(certificate.getLastUpdateDate());

            certificateDto.setTags(builderTagDto(certificate.getTags()));
            certificateDtoList.add(certificateDto);
        }
        return certificateDtoList;
    }


    private Set<TagDto> builderTagDto(Set<Tag> tags) {
        Set<TagDto> tagDtos = new HashSet<>();
        for(Tag tag : tags) {
            TagDto tagDto = new TagDto(tag.getTagId(), tag.getName());
            tagDtos.add(tagDto);
        }
        return tagDtos;
    }


    /**
     * Set tag builder. Each tag is checked for presence in the database. If it is present. then its ID will be
     * obtained otherwise it will be considered a new tag.
     *
     * @param stringList the String line of tags.
     *
     * @return Set of tags for GiftCertificate.
     */
    private Set<Tag> getFilterTags(Set<TagDto> stringList) {
        Set<Tag> tags = new HashSet<>();
        for (TagDto tagDto : stringList) {
            Tag tag = new Tag();
            Optional<Tag> tagOptional = tagDao.findByName(tagDto.getName().trim());
            if (tagOptional.isPresent()) {
                tag.setTagId(tagOptional.get().getTagId());
                tag.setName(tagOptional.get().getName());
            } else {
                tag.setName(tagDto.getName().trim());
            }
            tags.add(tag);
        }
        return tags;
    }
}
