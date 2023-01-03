package com.epam.esm.model.Dto;

import org.springframework.hateoas.RepresentationModel;

/**
 * The type Tag Dto. The Dto class. It is used to transfer data between layers.
 *
 * @author Alexander Pishchala
 */
public class TagDto extends RepresentationModel<TagDto> {

    private int tagId;
    private String name;

    /**
     * Instantiates a new Tag.
     */
    public TagDto() {
    }

    /**
     * Instantiates a new Tag.
     *
     * @param name the tag name.
     */
    public TagDto(int tagId, String name) {
        this.name = name;
        this.tagId = tagId;
    }

    /**
     * Getting the tag id.
     *
     * @return the tag id.
     */
    public int getTagId() {
        return tagId;
    }

    /**
     * Setting the tag id.
     *
     */
    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    /**
     * Getting the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setting the tag name.
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TagDto tagDto = (TagDto) o;

        if (tagId != tagDto.tagId) return false;
        return name != null ? name.equals(tagDto.name) : tagDto.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + tagId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder buildString = new StringBuilder();
        buildString.append("TagDto{tagId=").append(tagId)
                .append(", name='").append(name).append('}');
        return  buildString.toString();
    }
}
