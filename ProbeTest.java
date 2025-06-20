package com.example.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProbeTest {

    private Grid grid;

    @BeforeEach
    void setUp() {
        // 5x5 grid with (1,1) as a blocked position
        grid = new Grid(5, 5, List.of(new Position(1, 1)));
    }

    @Test
    void testInitialPositionAndDirection() {
        Probe probe = new Probe(new Position(0, 0), Direction.NORTH, grid);
        assertEquals(new Position(0, 0), probe.getPosition());
        assertEquals(Direction.NORTH, probe.getDirection());
        assertEquals(List.of(new Position(0, 0)), probe.getVisited());
    }

    @Test
    void testMoveForwardIntoFreeCell() {
        Probe probe = new Probe(new Position(0, 0), Direction.NORTH, grid);
        probe.executeCommands("F");

        assertEquals(new Position(0, 1), probe.getPosition());
        assertEquals(Direction.NORTH, probe.getDirection());
        assertEquals(List.of(new Position(0, 0), new Position(0, 1)), probe.getVisited());
    }

    @Test
    void testAvoidBlockedCell() {
        Probe probe = new Probe(new Position(1, 0), Direction.NORTH, grid);
        probe.executeCommands("F"); // (1,1) is blocked

        assertEquals(new Position(1, 0), probe.getPosition());
        assertEquals(Direction.NORTH, probe.getDirection());
        assertEquals(List.of(new Position(1, 0)), probe.getVisited()); // no move
    }

    @Test
    void testTurningLeftAndRight() {
        Probe probe = new Probe(new Position(0, 0), Direction.NORTH, grid);
        probe.executeCommands("R"); // EAST
        assertEquals(Direction.EAST, probe.getDirection());

        probe.executeCommands("R"); // SOUTH
        assertEquals(Direction.SOUTH, probe.getDirection());

        probe.executeCommands("L"); // EAST
        assertEquals(Direction.EAST, probe.getDirection());

        probe.executeCommands("L"); // NORTH
        assertEquals(Direction.NORTH, probe.getDirection());
    }

    @Test
    void testMultipleCommands() {
        Probe probe = new Probe(new Position(0, 0), Direction.NORTH, grid);
        probe.executeCommands("FFRFF");

        assertEquals(new Position(2, 2), probe.getPosition());
        assertEquals(Direction.EAST, probe.getDirection());

        List<Position> expectedVisited = List.of(
                new Position(0, 0),
                new Position(0, 1),
                new Position(0, 2),
                new Position(1, 2),
                new Position(2, 2)
        );
        assertEquals(expectedVisited, probe.getVisited());
    }

    @Test
    void testInvalidCommandThrowsException() {
        Probe probe = new Probe(new Position(0, 0), Direction.NORTH, grid);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> probe.executeCommands("FX"));

        assertEquals("Invalid command: X", exception.getMessage());
    }
}
