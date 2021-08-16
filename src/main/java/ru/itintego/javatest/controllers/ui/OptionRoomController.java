package ru.itintego.javatest.controllers.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itintego.javatest.models.OptionsRoom;
import ru.itintego.javatest.repositories.IconRepository;
import ru.itintego.javatest.repositories.OptionsRoomRepository;

import java.util.List;

@Controller
@RequestMapping("/options_room")
public class OptionRoomController {

    private final OptionsRoomRepository optionsRoomRepository;
    private final IconRepository iconRepository;

    public OptionRoomController(OptionsRoomRepository optionsRoomRepository, IconRepository iconRepository) {
        this.optionsRoomRepository = optionsRoomRepository;
        this.iconRepository = iconRepository;
    }

    @RequestMapping
    public ModelAndView optionsRoom() {
        ModelAndView modelAndView = new ModelAndView("options_room");
        List<OptionsRoom> optionsRooms = optionsRoomRepository.findAll();
        modelAndView.addObject("optionsRoom", optionsRooms);
        return modelAndView;
    }

    @RequestMapping("/new")
    public ModelAndView newOptionsRoom() {
        ModelAndView modelAndView = new ModelAndView("icon_test");
        modelAndView.addObject("icon", iconRepository.findAll());
        return modelAndView;
    }

    @RequestMapping("/new/{id}")
    public ModelAndView editOptionsRooms(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("options_room_form");
        modelAndView.addObject("icon", id);
        return modelAndView;
    }
}
