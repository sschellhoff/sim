import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

class Sim {
    public static void runRandomGame(final int boardSize) {
        // create game court
		Graph court = new Graph(boardSize);

        // make list with all possible moves
        ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
        for(int i = 0; i < boardSize; i++) {
            for(int j = i + 1; j < boardSize; j++) {
                possibleMoves.add(new int[] {i, j});
            }
        }

        // to get current player value by index
        EdgeValue[] playerValues = new EdgeValue[]{EdgeValue.ONE, EdgeValue.TWO};

        // index for current player
        int currentPlayer = 0;

        // number of players
        final int numberOfPlayers = 2;

        // is the game running or done
        boolean gameRunning = true;

        // random number generator
        Random random = new Random();

        try {
            // gameloop
            do {
                // are there possible moves left?
                if(possibleMoves.isEmpty()) {
                    System.out.println("noone lost");
                    gameRunning = false;
                } else {
                    // get next move
                    int moveIndex = random.nextInt(possibleMoves.size());
                    // swap wanted move and last move in list for cheaper removement
                    Collections.swap(possibleMoves, moveIndex, possibleMoves.size()-1);
                    int[] move = possibleMoves.remove(possibleMoves.size()-1);
                    
                    System.out.println("start to make move(" + move[0] + ", " + move[1] + ") of player " + (currentPlayer + 1));
                    if(court.makeMove(move[0], move[1], playerValues[currentPlayer])) {

                        // game goes on, so it is the next players turn
                        currentPlayer = (currentPlayer + 1) % numberOfPlayers;
                    } else {

                        // game is lost
                        System.out.println("Player " +  (currentPlayer + 1) + " lost the game");
                        gameRunning = false;
                    }
                }
            } while(gameRunning);
        } catch(InvalidMoveException ime) {
            System.out.println("there was an invalid move: " + ime.getMessage());
        }
    }

	public static void main(String[] args) {

        // run game with 6 nodes and random moves
        Sim.runRandomGame(6);
	}
}
