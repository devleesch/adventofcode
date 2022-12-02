package be.neonweb.aoc.solvers.d02;

import lombok.Data;

@Data
public class Game {

    private Play player1;

    private Play player2;

    public static Game of(String line) {
        Game game = new Game();

        String[] split = line.split(" ");
        game.setPlayer1(Play.of(split[0]));
        game.setPlayer2(Play.of(split[1]));

        return game;
    }

    public static Game of2(String line) {
        Game game = new Game();

        String[] split = line.split(" ");
        Play player1 = Play.of(split[0]);
        game.setPlayer1(player1);
        game.setPlayer2(Play.of2(split[1], player1));

        return game;
    }

    public int points() {
        int point = 0;

        int winner = player2.wins(player1);
        if (winner >= 1) {
            point += 6;
        } else if (winner == 0) {
            point += 3;
        }

        point += player2.getPoint();

        return point;
    }
}
