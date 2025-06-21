package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Direction;
import com.example.demo.model.Grid;
import com.example.demo.model.Position;
import com.example.demo.model.Probe;

@RestController
@RequestMapping("/probe")
public class ProbeController {

    private final Grid grid = new Grid(5, 5, List.of(new Position(1, 1)));
    private final Probe probe = new Probe(new Position(0, 0), Direction.NORTH, grid);

   @PostMapping("/command")
    public ResponseEntity<?> command(@RequestBody Map<String, String> payload) {
        String commands = payload.get("commands");

        if (commands == null || commands.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Commands must not be empty"));
        }

        try {
            probe.executeCommands(commands);
            return ResponseEntity.ok(Map.of(
                    "position", probe.getPosition(),
                    "direction", probe.getDirection(),
                    "visited", probe.getVisited()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

}
