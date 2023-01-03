package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.PopularTag;
import com.epam.esm.util.LocalUtil;
import com.epam.esm.model.Tag;
import com.epam.esm.model.Dto.TagDto;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.ValidatorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The type Tag service implements methods of the Tag interface.
 * The class is annotated as a service, which qualifies it to be automatically created by component-scanning.
 *
 * @author Alexander Pishchala
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {

    private static final Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);

    public static final String TAG_BY_ID_NOT_FOUND_CODE = "code.tag.not_found";
    public static final String TAG_BY_ID_NOT_FOUND = "Tag not found by ID. ID: ";

    private TagDao tagDao;

    private LocalUtil localUtil;

    public TagServiceImpl(TagDao tagDao, LocalUtil localUtil) {
        this.tagDao = tagDao;
        this.localUtil = localUtil;
    }

    @Override
    public boolean save(TagDto tagDto) {
        Tag tag = new Tag(tagDto.getName());
        ValidatorEntity.tag(tag, localUtil);
        List<Tag> tagList = tagDao.findAllTags(showCountTags(), 0);
        if (tagList.stream().noneMatch(x -> x.getName().equalsIgnoreCase(tag.getName()))) {
            return tagDao.create(tag);
        }
        return false;
    }

    @Override
    public boolean delete(int tagId) {
        Optional<Tag> tag = tagDao.findTag(tagId);
        if (tag.isPresent()) {
            return tagDao.delete(tagId);
        } else {
            logger.error(TAG_BY_ID_NOT_FOUND + tagId);
            throw new ServiceException((localUtil.getMessage(TAG_BY_ID_NOT_FOUND_CODE)) + tagId);
        }
    }

    @Override
    public Optional<TagDto> showById(int tagId) {
        Optional<Tag> tag = tagDao.findTag(tagId);
        if (tag.isPresent()) {
            return Optional.of(createTagDtoList(Arrays.asList(tag.get())).get(0));
        }
        return Optional.empty();
    }

    @Override
    public Optional<TagDto> showByName(String name) {
        Optional<Tag> tag = tagDao.findByName(name);
        if (tag.isPresent()) {
            return Optional.of(createTagDtoList(Arrays.asList(tag.get())).get(0));
        }
        return Optional.empty();
    }

    @Override
    public List<TagDto> showTags(int limit, int offset) {
        List<Tag> tagList = tagDao.findAllTags(limit, offset);

        return createTagDtoList(tagList);
    }

    @Override
    public List<TagDto> showByPartName(int limit, int offset, String name) {
        List<Tag> tagList = tagDao.findByPartName(limit, offset, name);

        return createTagDtoList(tagList);
    }

    @Override
    public Optional<PopularTag> showPopularTagByMaxCostOrdersUser() {
        return tagDao.findPopularTagByMaxCostOrdersUser();
    }

    @Override
    public int showCountTags() {
        return tagDao.findCountTags();
    }

    /**
     * Builder list of Tag Dto by list of Tag entity. This is a helper method needed to avoid
     * code duplication.
     *
     * @param tags the list of tags.
     *
     * @return list of Tag Dto.
     */
    private List<TagDto> createTagDtoList(List<Tag> tags) {
        List<TagDto> tagDtoList = new ArrayList<>();
        for (Tag tag : tags) {
            TagDto tagDto = new TagDto();
            tagDto.setTagId(tag.getTagId());
            tagDto.setName(tag.getName());
            tagDtoList.add(tagDto);
        }
        return tagDtoList;
    }
}
