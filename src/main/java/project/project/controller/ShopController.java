package project.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.project.model.entity.PC;
import project.project.service.impl.PcServiceImpl;

import java.util.List;

@Controller
public class ShopController {
    private final PcServiceImpl pcServiceImpl;

    public ShopController(PcServiceImpl pcServiceImpl) {
        this.pcServiceImpl = pcServiceImpl;
    }

    @GetMapping("/shop")
    public String viewShop(Model model) {
        List<PC> pcs = pcServiceImpl.getAllPCs();
        model.addAttribute("pcs", pcs);
        return "shop";
    }
}
