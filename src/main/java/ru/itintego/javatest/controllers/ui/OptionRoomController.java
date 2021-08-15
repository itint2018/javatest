package ru.itintego.javatest.controllers.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itintego.javatest.models.OptionsRoom;
import ru.itintego.javatest.repositories.OptionsRoomRepository;

import java.util.List;

@Controller
@RequestMapping("/options_room")
public class OptionRoomController {

    private final OptionsRoomRepository optionsRoomRepository;

    public OptionRoomController(OptionsRoomRepository optionsRoomRepository) {
        this.optionsRoomRepository = optionsRoomRepository;
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

        return modelAndView;
    }

    @RequestMapping("/{id}")
    public ModelAndView editOptionsRooms(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("icon_test");

        return modelAndView;
    }
}
