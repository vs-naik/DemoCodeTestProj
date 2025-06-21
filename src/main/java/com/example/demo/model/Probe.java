package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Probe {
	private Position position;
	private Direction direction;
	private Grid grid;
	private List<Position> visited;

	public Probe(Position position, Direction direction, Grid grid) {
		this.position = position;
		this.direction = direction;
		this.grid = grid;
		this.visited = new ArrayList<>();
		this.visited.add(position);
	}

	public Position getPosition() {
		return position;
	}

	public Direction getDirection() {
		return direction;
	}

	public List<Position> getVisited() {
		return visited;
	}

	public void executeCommands(String commands) {
		for (char command : commands.toCharArray()) {
			switch (command) {
			case 'F' -> {
				Position next = position.move(direction);
				if (grid.isValid(next)) {
					position = next;
					visited.add(next);
				}
			}
			case 'L' -> direction = direction.turnLeft();
			case 'R' -> direction = direction.turnRight();
			default -> throw new IllegalArgumentException("Invalid command: " + command);
			}
		}
	}

}
