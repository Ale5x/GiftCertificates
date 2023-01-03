package com.epam.esm.controller;

/**
 * Helper class for working with list of items pages.
 *
 * @author Alexander Pishchala
 */
public abstract class PaginationHelper {


    /**
     * Method for determining the starting position of a list of items.
     *
     * @param page the page of items.
     * @param size the number of items per page.
     *
     * @return starting position of the list.
     */
    public int getOffset(String page, String size) {
        int offset = Integer.parseInt(page) * Integer.parseInt(size) - Integer.parseInt(size);
        return offset;
    }

    /**
     * Method for getting the next page number.
     *
     * @param page the page of items.
     *
     * @return next page number.
     */
    public String getNextPage(String page) {
        int pageNumber = Integer.parseInt(page);
        return ++pageNumber + "";
    }

    /**
     * Method for getting the previous page number.
     *
     * @param page the page of items.
     *
     * @return previous page number.
     */
    public String getPreviousPage(String page) {
        int pageNumber = Integer.parseInt(page);
        if (pageNumber <= 1) {
            return "1";
        }
        return --pageNumber + "";
    }
}
