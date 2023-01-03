package com.epam.esm.validator;

import com.epam.esm.exception.AppRequestException;
import com.epam.esm.exception.ErrorMessage;
import com.epam.esm.util.LocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ValidatorParams {

    private static final String REGEX_NUMBER = "^\\d+$";
    private static final String REGEX_DECIMAL_NUMBER = "^\\d*[.]\\d{0,2}+$";


    private LocalUtil localUtil;

    @Autowired
    public void setLocalUtil(LocalUtil localUtil) {
        this.localUtil = localUtil;
    }

    public void validId(String id) {
        if (!id.matches(REGEX_NUMBER)) {
            throw new  AppRequestException((localUtil.getMessage(ErrorMessage.INVALID_ID_FORMAT)) + id,
                    HttpStatus.BAD_REQUEST);
        }
    }

    public void validPage(String page) {
        if (!page.matches(REGEX_NUMBER)) {
            throw new  AppRequestException((localUtil.getMessage(ErrorMessage.INVALID_PAGE_FORMAT)) + page,
                    HttpStatus.BAD_REQUEST);
        }
    }

    public void validSize(String size) {
        if (!size.matches(REGEX_NUMBER)) {
            throw new  AppRequestException((localUtil.getMessage(ErrorMessage.INVALID_SIZE_FORMAT)) + size,
                    HttpStatus.BAD_REQUEST);
        }
    }

    public void validNumber(String value) {
        if (!value.matches(REGEX_NUMBER)) {
            throw new  AppRequestException((localUtil.getMessage(ErrorMessage.INVALID_NUMBER_FORMAT)) + value,
                    HttpStatus.BAD_REQUEST);
        }
    }

    public void validPrice(String price) {
        if (!price.matches(REGEX_DECIMAL_NUMBER)) {
            throw new  AppRequestException((localUtil.getMessage(ErrorMessage.INVALID_PRICE_FORMAT)) + price,
                    HttpStatus.BAD_REQUEST);
        }
    }
}
