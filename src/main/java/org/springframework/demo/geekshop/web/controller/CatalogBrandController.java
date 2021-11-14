package org.springframework.demo.geekshop.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.demo.geekshop.domain.CatalogBrand;
import org.springframework.demo.geekshop.repository.CatalogBrandRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/catalogbrands")
public class CatalogBrandController {
    private CatalogBrandRepository catalogBrandRepository;

    public CatalogBrandController(CatalogBrandRepository catalogBrandRepository) {
        this.catalogBrandRepository = catalogBrandRepository;
    }

    @GetMapping
    public String getAllCatalogBrands(Model model) {
        model.addAttribute("catalogBrandList",
                catalogBrandRepository.findAll());
        return "/catalogbrand/catalogbrands";
    }

    @PostMapping(value = "/save")
    public String saveNewCatalogBrand(CatalogBrand catalogBrand) {
        log.info("CatalogBrand to be save : {}", catalogBrand);
        catalogBrandRepository.save(catalogBrand);

        return "redirect:/catalogbrands";
    }

    @GetMapping(value = "/add")
    public String addNewCatalogBrand() {
        return "/catalogbrand/addCatalogBrand";
    }

    @GetMapping(value = "/show/{id}")
    public String getCatalogBrandById(@PathVariable long id, Model model) {
        model.addAttribute("catalogbrand", catalogBrandRepository.findById(id).get());
        log.info("The catalogbrand : {}", catalogBrandRepository.findById(id).get());
        return "/catalogbrand/catalogbrand";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteBrandById(@PathVariable long id) {
        log.info("The catalogbrandId to be delted: {}", id);
        catalogBrandRepository.deleteById(id);
        return "redirect:/catalogbrands";
    }

    @GetMapping(value = "/edit/{id}")
    public String editCatalogBrand(@PathVariable long id, Model model) {
        model.addAttribute("catalogbrand", catalogBrandRepository.findById(id).get());
        return "/catalogbrand/catalogbrand";
    }
}
