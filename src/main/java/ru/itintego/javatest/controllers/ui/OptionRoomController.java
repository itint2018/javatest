package ru.itintego.javatest.controllers.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/options_room")
public class OptionRoomController {

    @RequestMapping
    public String optionsRoom() {
        return "options_room";
    }
}
