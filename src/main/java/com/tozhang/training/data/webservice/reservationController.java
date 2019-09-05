package com.tozhang.training.data.webservice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservations")
public class reservationController {
    @GetMapping("")
    public String getAllGuests() {
        return "reservations";
    }
}
