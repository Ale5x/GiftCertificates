package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DevelopmentConfig;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DevelopmentConfig.class)
@ActiveProfiles("development")
class GiftCertificateDaoImplTest {

    @Autowired
    private GiftCertificateDao certificateDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private OrderDao orderDao;

    private GiftCertificate certificate = new GiftCertificate();
    private int limit = 10;
    private int offset = 0;

    @BeforeEach
    public void init() {
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        certificate.setName("Gift Test 112");
        certificate.setDescription("Description Test");
        certificate.setPrice(new BigDecimal(3));
        certificate.setDuration(10);
        Set<Tag> tagList = new HashSet<>();
//        tagList.add(new Tag(3, "#Tag 3"));
//        tagList.add(new Tag(2, "#Tag 2"));


        Tag tag1 = new Tag(3, "#Tag 3");
        Tag tag2 = new Tag(2, "#Tag 2");

        certificate.addTag(tag1);
        certificate.addTag(tag2);

    }

    @Test
    void create() {
        int startPosition = certificateDao.findCountCertificates();

        boolean condition  = certificateDao.create(certificate);
        int finishPosition = certificateDao.findCountCertificates();

        assertTrue(condition);
        assertTrue(finishPosition > startPosition);
    }

    @Test
    void update() {
        int certificateId = 1;
        String name = "New name";
        String description = "New Description";
        Optional<GiftCertificate> certificate = certificateDao.findGiftCertificate(certificateId);
        assertTrue(certificate.isPresent());
        GiftCertificate updateCertificate = certificate.get();
        updateCertificate.setName(name);
        updateCertificate.setDescription(description);

        boolean condition = certificateDao.update(updateCertificate);
        Optional<GiftCertificate> afterUpdateCertificate = certificateDao.findGiftCertificate(certificateId);
        assertTrue(condition);
        assertFalse(certificate.get().equals(afterUpdateCertificate));
        assertTrue(name.equals(certificate.get().getName()));
        assertTrue(description.equals(certificate.get().getDescription()));
        assertTrue(name.equals(afterUpdateCertificate.get().getName()));
        assertTrue(description.equals(afterUpdateCertificate.get().getDescription()));
    }

    @Test
    void findGiftCertificate() {
        int id = 2;
        Optional<GiftCertificate> giftCertificate = certificateDao.findGiftCertificate(id);
        assertTrue(giftCertificate.isPresent());
    }

    @Test
    void findGiftCertificates() {
        List<GiftCertificate> certificates = new ArrayList<>();
        certificates = certificateDao.findGiftCertificates(limit, offset);
        assertFalse(certificates.isEmpty());
        assertTrue(certificates.size() > 0);
        assertTrue(certificates.get(0).equals(certificates.get(0)));
    }

    @Test
    void findByPartName() {
        int id = 1;
        Optional<GiftCertificate> certificate = certificateDao.findGiftCertificate(id);
        List<GiftCertificate> giftCertificateList = certificateDao
                .findByPartName(limit, offset, certificate.get().getName());

        assertFalse(giftCertificateList.isEmpty());
        assertTrue(giftCertificateList.size() > 0);
        assertTrue(giftCertificateList.stream().anyMatch(x -> x.getGiftCertificateId() == id));
    }

    @Test
    void findByTagName() {
        int id = 1;
        Optional<Tag> tag = tagDao.findTag(id);
        List<GiftCertificate> certificateList = certificateDao.findByTagName(limit, offset, tag.get().getName());
        assertFalse(certificateList.isEmpty());
        assertTrue(certificateList.size() > 0);
//        assertTrue(certificateList.stream().anyMatch(x -> x.getTagList()
//                .stream().anyMatch(g -> g.getTagId() == id)));
    }

    @Test
    void findAllBySortName() {
        List<GiftCertificate> certificateList = certificateDao.findGiftCertificates(limit, offset);

        certificateList.sort(Comparator.comparing(GiftCertificate::getName));

        List<GiftCertificate> sortCertificateList = certificateDao.findAllBySortName(limit, offset);

        assertTrue(certificateList.get(0).getGiftCertificateId() == sortCertificateList.get(0).getGiftCertificateId());
        assertTrue(certificateList.get(1).getGiftCertificateId() == sortCertificateList.get(1).getGiftCertificateId());
    }

    @Test
    void findAllBySortNameReverse() {
        List<GiftCertificate> certificateList = certificateDao.findGiftCertificates(limit, offset);

        certificateList.sort(Comparator.comparing(GiftCertificate::getName));
        Collections.reverse(certificateList);

        List<GiftCertificate> sortCertificateList = certificateDao.findAllBySortNameReverse(limit, offset);

        assertTrue(certificateList.get(0).getGiftCertificateId() == sortCertificateList.get(0).getGiftCertificateId());
        assertTrue(certificateList.get(1).getGiftCertificateId() == sortCertificateList.get(1).getGiftCertificateId());
    }

    @Test
    void findByDate() {
        List<GiftCertificate> certificateList = certificateDao.findGiftCertificates(limit, offset);

        certificateList.sort(Comparator.comparing(GiftCertificate::getCreateDate));

        List<GiftCertificate> sortedCertificateByDate = certificateDao.findByDate(limit, offset);

        assertTrue(certificateList.get(0).getGiftCertificateId() == sortedCertificateByDate.get(0).getGiftCertificateId());
        assertTrue(certificateList.get(1).getGiftCertificateId() == sortedCertificateByDate.get(1).getGiftCertificateId());
    }

    @Test
    void findByDateReverse() {
        List<GiftCertificate> certificateList = certificateDao.findGiftCertificates(limit, offset);

        certificateList.sort(Comparator.comparing(GiftCertificate::getCreateDate));
        Collections.reverse(certificateList);

        List<GiftCertificate> sortCertificateList = certificateDao.findByDateReverse(limit, offset);

        assertTrue(certificateList.get(0).getGiftCertificateId() == sortCertificateList.get(0).getGiftCertificateId());
        assertTrue(certificateList.get(1).getGiftCertificateId() == sortCertificateList.get(1).getGiftCertificateId());
    }

    @Test
    void findByOrder() {
        int id = 1;
        Optional<Order> order = orderDao.findOrder(id);
        List<GiftCertificate> certificateList = certificateDao.findByOrder(limit, offset, order.get().getOrderId());
        assertFalse(certificateList.isEmpty());
        assertTrue(certificateList.size() > 0);
//        assertTrue(certificateList.stream().anyMatch(x -> x.getOrder().getOrderId() == id));
    }

    @Test
    void findCountRecords() {
        long countRows = certificateDao.findCountCertificates();
        assertTrue(countRows > 0);
    }

    @Test
    void delete() {
        int startPosition = certificateDao.findCountCertificates();
        int id = 3;
        boolean condition = certificateDao.delete(id);
        int finishPosition = certificateDao.findCountCertificates();
        assertTrue(condition);
        assertTrue(startPosition > finishPosition);
    }
}