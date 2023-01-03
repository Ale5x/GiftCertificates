package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DevelopmentConfig;
import com.epam.esm.dao.TagDao;
import com.epam.esm.model.PopularTag;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DevelopmentConfig.class)
@ActiveProfiles("development")
class TagDaoImplTest {

    @Autowired
    private TagDao tagDao;

    private int limit = 10;
    private int offset = 0;

    @Test
    void create() {
        Tag tag = new Tag("new Tag 28.04");
        int startPosition = tagDao.findCountTags();
        boolean condition = tagDao.create(tag);
        int finishPosition = tagDao.findCountTags();
        assertTrue(condition);
        assertTrue(finishPosition > startPosition);
    }

    @Test
    void findTag() {
        int tagId = 1;
        Optional<Tag> tag = tagDao.findTag(tagId);
        assertTrue(tag.isPresent());
    }

    @Test
    void findAll() {
        List<Tag> tagList = tagDao.findAllTags(limit, offset);
        assertFalse(tagList.isEmpty());
    }

    @Test
    void findByPartName() {
        List<Tag> tagList = tagDao.findByPartName(limit, offset, "Tag");
        assertFalse(tagList.isEmpty());
        assertTrue(tagList.size() > 0);
    }

    @Test
    void findByName() {
        Optional<Tag> existTag = tagDao.findTag(1);
        Optional<Tag> findTag = tagDao.findByName(existTag.get().getName());

        assertTrue(findTag.isPresent());
        assertTrue(existTag.isPresent());
        assertTrue(existTag.get().getName().equals(findTag.get().getName()));
    }

    @Test
    void findCountRecords() {
        int actualCountRows = tagDao.findCountTags();
        assertTrue(actualCountRows > 0);
    }

    @Test
    void findPopularTag() {
        Optional<PopularTag> popularTag = tagDao.findPopularTagByMaxCostOrdersUser();
        System.out.println(popularTag);
        assertTrue(popularTag.isPresent());
    }

    @Test
    void delete() {
        int tagId = 3;
        int startPosition = tagDao.findCountTags();
        boolean condition = tagDao.delete(tagId);
        int finishPosition = tagDao.findCountTags();
        assertTrue(condition);
        assertTrue(startPosition > finishPosition);
    }
}