package com.epam.esm.controller;

import com.epam.esm.exception.AppRequestException;
import com.epam.esm.exception.ErrorMessage;
import com.epam.esm.util.LocalUtil;
import com.epam.esm.model.Dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.ValidatorParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The type GiftCertificate Controller is a controller which operates requests from clients and generates response in
 * representational forms. Information exchanging are in a JSON forms.
 *
 * @author Alexander Pishchala
 */
@RestController
@CrossOrigin({"http://localhost:3000", "http://localhost:8080"})
public class CertificateController extends PaginationHelper {

    private GiftCertificateService certificateService;
    private LocalUtil localUtil;
    private ValidatorParams validator;

    /**
     * Instantiates a new CertificateController.
     *
     * @param certificateService the certificate service
     * @param localUtil the Local.
     * @param validator the validator values.
     */
    @Autowired
    public CertificateController(GiftCertificateService certificateService,
                                 LocalUtil localUtil,
                                 ValidatorParams validator) {
        this.certificateService = certificateService;
        this.localUtil = localUtil;
        this.validator = validator;
    }

    /**
     * Create a Certificate.
     *
     * @param certificate the certificateDto entity.
     *
     * @return the HttpStatus.
     */
    @PostMapping(value = PathPageConstant.CERTIFICATE_CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> createCertificate(@RequestBody GiftCertificateDto certificate) {
        certificateService.save(certificate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Update a Certificate by id.
     *
     * @param certificate the certificateDto entity.
//     * @param certificateId the certificateDto id.
     *
     * @return the HttpStatus.
     */
    @PostMapping(value = PathPageConstant.CERTIFICATE_UPDATE)
    public ResponseEntity<HttpStatus> update(@RequestBody GiftCertificateDto certificate) {
        certificateService.update(certificate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update the price a Certificate by id.
     *
     * @param price the Certificate price.
     * @param certificateId the certificateDto id.
     *
     * @return the HttpStatus.
     */
    @PutMapping(value = PathPageConstant.CERTIFICATE_UPDATE_PRICE)
    public ResponseEntity<HttpStatus> updatePrice(@RequestParam(ConstantController.PRICE) String price,
                                                  @RequestParam(ConstantController.ID) String certificateId) {
        validator.validId(certificateId);
        validator.validPrice(price);
        GiftCertificateDto certificate = new GiftCertificateDto();
        certificate.setGiftCertificateDtoId(Integer.parseInt(certificateId));
        certificate.setPrice(BigDecimal.valueOf(Double.parseDouble(price)));
        certificateService.updatePrice(certificate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update the duration a Certificate by id.
     *
     * @param duration the Certificate duration.
     * @param certificateId the certificateDto id.
     *
     * @return the HttpStatus.
     */
    @PutMapping(value = PathPageConstant.CERTIFICATE_UPDATE_DURATION)
    public ResponseEntity<HttpStatus> updateDuration(@RequestParam(ConstantController.DURATION) String duration,
                                                     @RequestParam(ConstantController.ID) String certificateId) {
        validator.validNumber(duration);
        validator.validId(certificateId);
        GiftCertificateDto certificate = new GiftCertificateDto();
        certificate.setGiftCertificateDtoId(Integer.parseInt(certificateId));
        certificate.setDuration(Integer.parseInt(duration));
        certificateService.updateDuration(certificate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deleting certificates.
     *
     * @param certificateId the certificate id.
     *
     * @return the HttpStatus.
     */
    @DeleteMapping(value = PathPageConstant.CERTIFICATE_DELETE)
    public ResponseEntity<HttpStatus> deleteCertificate(@RequestParam(ConstantController.ID) String certificateId) {
        validator.validId(certificateId);
        certificateService.delete(Integer.parseInt(certificateId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Getting certificates by id.
     *
     * @param certificateId the certificate id.
     *
     * @return the certificates by id.
     */
    @GetMapping(value= PathPageConstant.CERTIFICATE_GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GiftCertificateDto getCertificate(@RequestParam(ConstantController.ID) String certificateId) {
        validator.validId(certificateId);
        return certificateService.showGiftCertificate(Integer.parseInt(certificateId)).orElseThrow(()
                -> new AppRequestException((localUtil.getMessage(ErrorMessage.CERTIFICATE_NOT_FOUND)) + certificateId,
                HttpStatus.NOT_FOUND));
    }

    /**
     * Getting certificates.
     *
     * @param page the page being viewed.
     * @param size the number of elements per page.
     *
     * @return the list of certificates.
     */
    @GetMapping(value = PathPageConstant.CERTIFICATE_GET_ALL)
    public CollectionModel<GiftCertificateDto> getCertificates(@RequestParam(ConstantController.PAGE) String page,
                                                               @RequestParam(ConstantController.SIZE) String size){
        validator.validPage(page);
        validator.validSize(size);
        Link previousLink = linkTo(methodOn(CertificateController.class)
                .getCertificates(getPreviousPage(page), size))
                .withRel(ConstantController.PREVIOUS_LINK).withType(ConstantController.METHOD_GET);
        Link nextLink = linkTo(methodOn(CertificateController.class)
                .getCertificates(getNextPage(page), size))
                .withRel(ConstantController.NEXT_LINK).withType(ConstantController.METHOD_GET);
        List<GiftCertificateDto> certificateList = certificateService.showGiftCertificates(Integer.parseInt(size),
                getOffset(page, size));
        return CollectionModel.of(certificateList, previousLink, nextLink);
    }

    /**
     * Getting certificates.
     *
     * @param page the page being viewed.
     * @param size the number of elements per page.
     * @param name the part name of Certificate.
     *
     * @return the list of certificates by part name.
     */
    @GetMapping(value = PathPageConstant.CERTIFICATE_GET_BY_PART_NAME)
    public CollectionModel<GiftCertificateDto> getCertificatesByPartName(@RequestParam(ConstantController.PAGE) String page,
                                                                         @RequestParam(ConstantController.SIZE) String size,
                                                                         @RequestParam(ConstantController.NAME) String name){
        validator.validPage(page);
        validator.validSize(size);
        Link previousLink = linkTo(methodOn(CertificateController.class)
                .getCertificatesByPartName(getPreviousPage(page), size, name))
                .withRel(ConstantController.PREVIOUS_LINK).withType(ConstantController.METHOD_GET);
        Link nextLink = linkTo(methodOn(CertificateController.class)
                .getCertificatesByPartName(getNextPage(page), size, name))
                .withRel(ConstantController.NEXT_LINK).withType(ConstantController.METHOD_GET);
        List<GiftCertificateDto> certificateList = certificateService.showByPartName(Integer.parseInt(size),
                getOffset(page, size), name);

        return CollectionModel.of(certificateList, previousLink, nextLink);
    }

    @GetMapping(value = "store/certificate/getCountCertificatesByPartName")
    public ResponseEntity<Integer> getCountCertificatesByPartName(@RequestParam(ConstantController.PAGE) String page,
                                                                         @RequestParam(ConstantController.SIZE) String size,
                                                                         @RequestParam(ConstantController.NAME) String name){
        validator.validPage(page);
        validator.validSize(size);

        return new ResponseEntity<>(certificateService.showByPartName(Integer.parseInt(size),
                getOffset(page, size), name).size(), HttpStatus.OK);
    }

    /**
     * Getting certificates.
     *
     * @param page the page being viewed.
     * @param size the number of elements per page.
     * @param name the tag name from a Certificate.
     *
     * @return the list of certificates by tag name.
     */
    @GetMapping(value = PathPageConstant.CERTIFICATE_GET_BY_TAG_NAME)
    public CollectionModel<GiftCertificateDto> getCertificatesByTagName(@RequestParam(ConstantController.PAGE) String page,
                                                                        @RequestParam(ConstantController.SIZE) String size,
                                                                        @RequestParam(ConstantController.NAME) String name){
        validator.validPage(page);
        validator.validSize(size);
        Link previousLink = linkTo(methodOn(CertificateController.class)
                .getCertificatesByTagName(getPreviousPage(page), size, name))
                .withRel(ConstantController.PREVIOUS_LINK).withType(ConstantController.METHOD_GET);
        Link nextLink = linkTo(methodOn(CertificateController.class)
                .getCertificatesByTagName(getNextPage(page), size, name))
                .withRel(ConstantController.NEXT_LINK).withType(ConstantController.METHOD_GET);
        List<GiftCertificateDto> certificateList = certificateService.showByTagName(Integer.parseInt(size),
                getOffset(page, size), name);

        return CollectionModel.of(certificateList, previousLink, nextLink);
    }

    /**
     * Sorting certificates by name.
     *
     * @param page the page being viewed.
     * @param size the number of elements per page.
     *
     * @return the sorted list of certificates by name.
     */
    @GetMapping(value = PathPageConstant.CERTIFICATE_GET_ALL_SORT_NAME)
    public CollectionModel<GiftCertificateDto> getAllBySortName(@RequestParam(ConstantController.PAGE) String page,
                                                                @RequestParam(ConstantController.SIZE) String size){
        validator.validPage(page);
        validator.validSize(size);
        Link previousLink = linkTo(methodOn(CertificateController.class)
                .getAllBySortName(getPreviousPage(page), size))
                .withRel(ConstantController.PREVIOUS_LINK).withType(ConstantController.METHOD_GET);
        Link nextLink = linkTo(methodOn(CertificateController.class)
                .getAllBySortName(getNextPage(page), size))
                .withRel(ConstantController.NEXT_LINK).withType(ConstantController.METHOD_GET);
        List<GiftCertificateDto> certificateList = certificateService.showAllBySortName(Integer.parseInt(size),
                getOffset(page, size));

        return CollectionModel.of(certificateList, previousLink, nextLink);
    }

    /**
     * Sorting certificates by reverse name.
     *
     * @param page the page being viewed.
     * @param size the number of elements per page.
     *
     * @return the sorted list of certificates by reverse name.
     */
    @GetMapping(value = PathPageConstant.CERTIFICATE_GET_ALL_SORT_REVERSE_NAME)
    public CollectionModel<GiftCertificateDto> getAllBySortReverseName(@RequestParam(ConstantController.PAGE) String page,
                                                                       @RequestParam(ConstantController.SIZE) String size){
        validator.validPage(page);
        validator.validSize(size);
        Link previousLink = linkTo(methodOn(CertificateController.class)
                .getAllBySortReverseName(getPreviousPage(page), size))
                .withRel(ConstantController.PREVIOUS_LINK).withType(ConstantController.METHOD_GET);
        Link nextLink = linkTo(methodOn(CertificateController.class)
                .getAllBySortReverseName(getNextPage(page), size))
                .withRel(ConstantController.NEXT_LINK).withType(ConstantController.METHOD_GET);
        List<GiftCertificateDto> certificateList = certificateService.showAllBySortNameReverse(Integer.parseInt(size),
                getOffset(page, size));

        return CollectionModel.of(certificateList, previousLink, nextLink);
    }

    /**
     * Sorting certificates by date.
     *
     * @param page the page being viewed.
     * @param size the number of elements per page.
     *
     * @return the sorted list of certificates by date.
     */
    @GetMapping(value = PathPageConstant.CERTIFICATE_GET_ALL_SORT_DATE)
    public CollectionModel<GiftCertificateDto> getAllBySortDate(@RequestParam(ConstantController.PAGE) String page,
                                                                @RequestParam(ConstantController.SIZE) String size){
        validator.validPage(page);
        validator.validSize(size);
        Link previousLink = linkTo(methodOn(CertificateController.class)
                .getAllBySortDate(getPreviousPage(page), size))
                .withRel(ConstantController.PREVIOUS_LINK).withType(ConstantController.METHOD_GET);
        Link nextLink = linkTo(methodOn(CertificateController.class)
                .getAllBySortDate(getNextPage(page), size))
                .withRel(ConstantController.NEXT_LINK).withType(ConstantController.METHOD_GET);
        List<GiftCertificateDto> certificateList = certificateService.showByDate(Integer.parseInt(size),
                getOffset(page, size));

        return CollectionModel.of(certificateList, previousLink, nextLink);
    }

    /**
     * Sorting certificates by reverse date.
     *
     * @param page the page being viewed.
     * @param size the number of elements per page.
     *
     * @return the sorted list of certificates by reverse date.
     */
    @GetMapping(value = PathPageConstant.CERTIFICATE_GET_ALL_SORT_REVERSE_DATE)
    public CollectionModel<GiftCertificateDto> getAllBySortReverseDate(@RequestParam(ConstantController.PAGE) String page,
                                                                       @RequestParam(ConstantController.SIZE) String size){
        validator.validPage(page);
        validator.validSize(size);
        Link previousLink = linkTo(methodOn(CertificateController.class)
                .getAllBySortReverseDate(getPreviousPage(page), size))
                .withRel(ConstantController.PREVIOUS_LINK).withType(ConstantController.METHOD_GET);
        Link nextLink = linkTo(methodOn(CertificateController.class)
                .getAllBySortReverseDate(getNextPage(page), size))
                .withRel(ConstantController.NEXT_LINK).withType(ConstantController.METHOD_GET);
        List<GiftCertificateDto> certificateList = certificateService.showByDateReverse(Integer.parseInt(size),
                getOffset(page, size));

        return CollectionModel.of(certificateList, previousLink, nextLink);
    }

    /**
     * Getting certificates by the order id.
     *
     * @param page the page being viewed.
     * @param size the number of elements per page.
     * @param id the order id.
     *
     * @return the list of certificates by order id.
     */
    @GetMapping(value = PathPageConstant.CERTIFICATE_GET_BY_ORDER)
    public CollectionModel<GiftCertificateDto> getAllByOrder(@RequestParam(ConstantController.PAGE) String page,
                                                             @RequestParam(ConstantController.SIZE) String size,
                                                             @RequestParam(ConstantController.ID) String id){
        validator.validPage(page);
        validator.validSize(size);
        validator.validId(id);
        Link previousLink = linkTo(methodOn(CertificateController.class)
                .getAllByOrder(getPreviousPage(page), size, id))
                .withRel(ConstantController.PREVIOUS_LINK).withType(ConstantController.METHOD_GET);
        Link nextLink = linkTo(methodOn(CertificateController.class)
                .getAllByOrder(getNextPage(page), size, id))
                .withRel(ConstantController.NEXT_LINK).withType(ConstantController.METHOD_GET);
        List<GiftCertificateDto> certificateList = certificateService.showByOrder(Integer.parseInt(size),
                getOffset(page, size), Integer.parseInt(id));

        return CollectionModel.of(certificateList, previousLink, nextLink);
    }

    /**
     * Getting count certificates.
     *
     * @return number of certificates.
     */
    @GetMapping(value = PathPageConstant.CERTIFICATE_GET_COUNT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Integer> getCountCertificates() {
        return new ResponseEntity<>(certificateService.showCountCertificates(), HttpStatus.OK);
    }
}
