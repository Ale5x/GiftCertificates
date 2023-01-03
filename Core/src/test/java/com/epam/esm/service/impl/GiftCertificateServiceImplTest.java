package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.model.Dto.GiftCertificateDto;
import com.epam.esm.model.Dto.TagDto;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @InjectMocks
    private GiftCertificateServiceImpl certificateService;

    @Mock
    private GiftCertificateDao certificateDao;

    @Mock
    private TagDao tagDao;

    private int limit = 10;
    private int offset = 0;
    private String bigString = "ivy (Titus Livius), the great Roman historian, was born at or near Patavium (Padua) in " +
            "64 or 59 BCE; he may have lived mostly in Rome but died at Patavium, in 12 or 17 CE." +
            "\n Livy's only extant work is part of his history of Rome from the foundation of the city to 9 BCE. " +
            "Of its 142 books, we have just 35, and short summaries of all the rest except two. The whole work was, " +
            "long after his death, divided into Decades or series of ten. Books 1–10 we have entire; books 11–20 are " +
            "lost; books 21–45 are entire, except parts of 41 and 43–45. Of the rest only fragments and the summaries " +
            "remain. In splendid style Livy, a man of wide sympathies and proud of Rome's past, presented an uncritical " +
            "but clear and living narrative of the rise of Rome to greatness.";

    private GiftCertificateDto certificateDto = new GiftCertificateDto();
    private GiftCertificate certificate = new GiftCertificate();
    private Tag tag = new Tag();

    private List<GiftCertificate> certificateList = new ArrayList<>();
    private Set<Tag> tags = new HashSet<>();

    @BeforeEach
    void setUp() {
        tag.setName("Name");
        Set<TagDto> tagDtoSet = new HashSet<>();
        tagDtoSet.add(new TagDto());
        certificateDto.setGiftCertificateDtoId(1);
        certificateDto.setName("Certificate");
        certificateDto.setDescription("Description");
        certificateDto.setDuration(11);
        certificateDto.setPrice(BigDecimal.valueOf(5));
        certificateDto.setTags(tagDtoSet);

        Order order = new Order();
        order.setOrderId(1);

        tags.add(tag);

        for (int i = 0; i < 5; i++) {
            certificate.setGiftCertificateId(i + 1);
            certificate.setPrice(BigDecimal.valueOf(5));
            certificate.setDuration(5);
            certificate.setDescription("Description");
            certificate.setName("name");
            certificate.setCreateDate(LocalDateTime.now());
            certificate.setLastUpdateDate(LocalDateTime.now());
            certificate.setTags(tags);
            certificateList.add(certificate);
        }
    }

    @Test
    void create() {
        when(certificateDao.create(any(GiftCertificate.class))).thenReturn(true);
        boolean condition = certificateService.save(certificateDto);
        assertTrue(condition);
    }

    @Test
    void updateRightTest() {
        when(certificateDao.update(any(GiftCertificate.class))).thenReturn(true);
        given(certificateDao.findGiftCertificate(1)).willReturn(Optional.of(certificate));
        given(tagDao.findByName(tag.getName())).willReturn(Optional.of(tag));
        boolean condition = certificateService.update(certificateDto);

        assertTrue(condition);
    }

    @Test
    void updatePrice() {
        when(certificateDao.update(any(GiftCertificate.class))).thenReturn(true);
        given(certificateDao.findGiftCertificate(1)).willReturn(Optional.of(certificate));
        certificateDto.setPrice(BigDecimal.valueOf(5555));
        boolean condition = certificateService.updatePrice(certificateDto);

        assertTrue(condition);
    }

    @Test
    void updateDuration() {
        when(certificateDao.update(any(GiftCertificate.class))).thenReturn(true);
        given(certificateDao.findGiftCertificate(1)).willReturn(Optional.of(certificate));
        certificateDto.setDuration(25);
        boolean condition = certificateService.updateDuration(certificateDto);

        assertTrue(condition);
    }

    @Test
    void showGiftCertificateRightTest() {
        given(certificateDao.findGiftCertificate(1)).willReturn(Optional.of(certificate));
        Optional<GiftCertificateDto> certificateDto = certificateService.showGiftCertificate(1);
        assertTrue(certificateDto.isPresent());
    }

    @Test
    void showGiftCertificateWrongTest() {
        given(certificateDao.findGiftCertificate(1)).willReturn(Optional.empty());
        Optional<GiftCertificateDto> certificateDto = certificateService.showGiftCertificate(1);
        assertTrue(certificateDto.isEmpty());
    }

    @Test
    void showGiftCertificates() {
        given(certificateDao.findGiftCertificates(limit, offset)).willReturn(certificateList);
        List<GiftCertificateDto> certificateList = certificateService.showGiftCertificates(limit, offset);
        assertFalse(certificateList.isEmpty());
        assertTrue(certificateList.size() > 0);
    }

    @Test
    void showByPartNameRightTest() {
        String name = "Auto";
        given(certificateDao.findByPartName(limit, offset, name)).willReturn(certificateList);
        List<GiftCertificateDto> certificateList = certificateService.showByPartName(limit, offset, name);
        assertFalse(certificateList.isEmpty());
        assertTrue(certificateList.size() > 0);
    }

    @Test
    void showByTagName() {
        String tagName = "#Tag 1";
        given(certificateDao.findByTagName(limit, offset, tagName)).willReturn(certificateList);
        List<GiftCertificateDto> certificateList = certificateService.showByTagName(limit, offset,tagName);
        assertFalse(certificateList.isEmpty());
        assertTrue(certificateList.size() > 0);
    }

    @Test
    void showAllBySortName() {
        given(certificateDao.findAllBySortName(limit, offset)).willReturn(certificateList);
        List<GiftCertificateDto> certificateList = certificateService.showAllBySortName(limit, offset);
        assertFalse(certificateList.isEmpty());
        assertTrue(certificateList.size() > 0);
    }

    @Test
    void showAllBySortNameReverse() {
        given(certificateDao.findAllBySortNameReverse(limit, offset)).willReturn(certificateList);
        List<GiftCertificateDto> certificateList = certificateService.showAllBySortNameReverse(limit, offset);
        assertFalse(certificateList.isEmpty());
        assertTrue(certificateList.size() > 0);
    }

    @Test
    void showByDate() {
        given(certificateDao.findByDate(limit, offset)).willReturn(certificateList);
        List<GiftCertificateDto> certificateList = certificateService.showByDate(limit, offset);
        assertFalse(certificateList.isEmpty());
        assertTrue(certificateList.size() > 0);
    }

    @Test
    void showByDateReverse() {
        given(certificateDao.findByDateReverse(limit, offset)).willReturn(certificateList);
        List<GiftCertificateDto> certificateList = certificateService.showByDateReverse(limit, offset);
        assertFalse(certificateList.isEmpty());
        assertTrue(certificateList.size() > 0);
    }

    @Test
    void showByOrder() {
        int orderId = 1;
        given(certificateDao.findByOrder(limit, offset, orderId)).willReturn(certificateList);
        List<GiftCertificateDto> certificateList = certificateService.showByOrder(limit, offset, orderId);
        assertFalse(certificateList.isEmpty());
        assertTrue(certificateList.size() > 0);
    }

    @Test
    void showCountCertificates() {
        given(certificateDao.findCountCertificates()).willReturn(5);
        int countRows = certificateService.showCountCertificates();
        assertTrue(countRows > 0);
    }

    @Test
    void deleteRightTest() {
        given(certificateDao.findGiftCertificate(1)).willReturn(Optional.of(certificate));
        given(certificateDao.delete(1)).willReturn(true);
        boolean condition = certificateService.delete(certificateDto.getGiftCertificateDtoId());
        assertTrue(condition);
    }
}