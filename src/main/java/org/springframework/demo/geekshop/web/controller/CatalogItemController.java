package org.springframework.demo.geekshop.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.demo.geekshop.domain.CatalogBrand;
import org.springframework.demo.geekshop.domain.CatalogItem;
import org.springframework.demo.geekshop.domain.CatalogType;
import org.springframework.demo.geekshop.repository.CatalogBrandRepository;
import org.springframework.demo.geekshop.repository.CatalogItemRepository;
import org.springframework.demo.geekshop.repository.CatalogTypeRepository;
import org.springframework.demo.geekshop.web.converter.BigDecimalEditor;
import org.springframework.demo.geekshop.web.converter.CatalogBrandEditor;
import org.springframework.demo.geekshop.web.converter.CatalogTypeEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/catalogitems")
public class CatalogItemController {
    private CatalogBrandRepository catalogBrandRepository;
    private CatalogTypeRepository catalogTypeRepository;
    private CatalogItemRepository catalogItemRepository;

    public CatalogItemController(CatalogBrandRepository catalogBrandRepository, CatalogTypeRepository catalogTypeRepository, CatalogItemRepository catalogItemRepository) {
        this.catalogBrandRepository = catalogBrandRepository;
        this.catalogTypeRepository = catalogTypeRepository;
        this.catalogItemRepository = catalogItemRepository;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(CatalogType.class, new CatalogTypeEditor(catalogTypeRepository));
        binder.registerCustomEditor(CatalogBrand.class, new CatalogBrandEditor(catalogBrandRepository));
        binder.registerCustomEditor(BigDecimal.class, new BigDecimalEditor());
    }

    @GetMapping
    public String getAllCatalogBrands(Model model) {
        model.addAttribute("catalogItemList",
                catalogItemRepository.findAll());
        return "/catalogitem/list";
    }

    @PostMapping(value = "/save")
    public String saveNewCatalogItem(HttpServletRequest servletRequest, @ModelAttribute("catalogItem") CatalogItem catalogItem) {
        try {
            MultipartFile picFile = catalogItem.getPicture();
            File fileDest = new File(servletRequest.getServletContext().getRealPath("/WEB-INF/item_images"), picFile.getOriginalFilename());
            log.info("Image for catalog item is stored in the location : {}", fileDest.getAbsolutePath());
            picFile.transferTo(fileDest);
            catalogItem.setPictureUrl(picFile.getOriginalFilename());
        } catch (Exception ex) {
            log.info("Error in uploading file : {}", ex);
        }
        log.info("CatalogItem to be save : {}", catalogItem);
        catalogItemRepository.save(catalogItem);

        return "redirect:/catalogitems";
    }

    @GetMapping(value = "/add")
    public ModelAndView addNewCatalogItem() {
        ModelAndView modelAndView = new ModelAndView("/catalogitem/addNew",
                "command", new CatalogItem());
        return modelAndView;
    }

    @GetMapping(value = "/view/{id}")
    public String getCatalogItemById(@PathVariable long id, Model model) {
        model.addAttribute("catalogitem", catalogItemRepository.findById(id).get());
        log.info("The catalogitem : {}", catalogItemRepository.findById(id).get());
        return "/catalogitem/catalogitem";
    }

    @GetMapping(value = "/display")
    public String displayCatalogItemById(Model model) {
        model.addAttribute("catalogItemList", catalogItemRepository.findAll());
        return "displayCatalogItems";
    }

    @GetMapping(value = "/edit/{id}")
    public ModelAndView editCatalogItem(@PathVariable long id, Model model) {
        ModelAndView modelAndView = new ModelAndView("/catalogitem/catalogitem",
                "command", catalogItemRepository.findById(id).get());
        return modelAndView;
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteCatalogItemById(@PathVariable long id) {
        log.info("The catalogItem to be deleted: {}", id);
        catalogItemRepository.deleteById(id);
        return "redirect:/catalogitems";
    }

    @ModelAttribute("catalogTypeList")
    public List<CatalogType> getCatalogTypeList() {
        return catalogTypeRepository.findAll();
    }

    @ModelAttribute("catalogBrandList")
    public List<CatalogBrand> getCatalogBrandList() {
        return catalogBrandRepository.findAll();
    }

    @GetMapping(value = "/image/{pictureUrl}")
    public void getCatalogImageAsByteArray(@PathVariable String pictureUrl, HttpServletRequest servletRequest, HttpServletResponse response) throws IOException {
        InputStream in = servletRequest.getServletContext().getResourceAsStream("/WEB-INF/item_images/" + pictureUrl);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }
}
