package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.model.Dto.TagDto;
import com.epam.esm.model.PopularTag;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagDaoImpl tagDao;

    private Tag tag = new Tag();
    private List<Tag> tagList = new ArrayList<>();

    private int limit = 10;
    private int offset = 0;

    @BeforeEach
    void setUp() {
        tag.setName("#Tag ");
        tag.setTagId(1);

        for (int i = 0; i < 5; i++) {
            tag.setTagId(i + 1);
            tagList.add(tag);
        }
    }

    @Test
    void save() {
        when(tagDao.create(any(Tag.class))).thenReturn(true);
        TagDto tag = new TagDto(1, "#Tag 31");
        boolean condition = tagService.save(tag);
        assertTrue(condition);
    }

    @Test
    void deleteRightTest() {
        int id = 1;
        given(tagDao.findTag(id)).willReturn(Optional.of(tag));
        given(tagDao.delete(id)).willReturn(true);
        boolean condition = tagService.delete(id);
        assertTrue(condition);
    }

    @Test
    void showById() {
        int tagId = 1;
        given(tagDao.findTag(tagId)).willReturn(Optional.of(tag));
        Optional<TagDto> tag = tagService.showById(tagId);
        assertTrue(tag.isPresent());
    }

    @Test
    void showByName() {
        String name = "#Tag 3";
        given(tagDao.findByName(name)).willReturn(Optional.of(tag));
        Optional<TagDto> tag = tagService.showByName(name);
        assertTrue(tag.isPresent());
    }

    @Test
    void showTags() {
        given(tagDao.findAllTags(limit, offset)).willReturn(tagList);
        List<TagDto> tags = tagService.showTags(limit, offset);
        assertFalse(tags.isEmpty());
        assertTrue(tags.size() > 0);
    }

    @Test
    void showByPartName() {
        String name = "Tag";
        given(tagDao.findByPartName(limit, offset, name)).willReturn(tagList);
        List<TagDto> tagList = tagService.showByPartName(limit, offset, name);
        assertFalse(tagList.isEmpty());
        assertTrue(tagList.size() > 0);
    }

    @Test
    void showPopularTagByMaxCostOrdersUser() {
        given(tagDao.findPopularTagByMaxCostOrdersUser()).willReturn(Optional.of(new PopularTag()));
        Optional<PopularTag> tag = tagService.showPopularTagByMaxCostOrdersUser();
        assertTrue(tag.isPresent());
    }

    @Test
    void showCountTags() {
        given(tagDao.findCountTags()).willReturn(5);
        int countTags = tagService.showCountTags();
        assertTrue(countTags > 0);
    }
}