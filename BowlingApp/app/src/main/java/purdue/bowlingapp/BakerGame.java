package purdue.bowlingapp;

import java.util.ArrayList;

/**
 * Created by Jimmy on 10/17/2017.
 */

public class BakerGame extends Game {

        ArrayList<Player> players;

        public BakerGame(ArrayList<Frame> frames, TenthFrame tenth, ArrayList<Player> players) {
            super(frames, tenth);
            this.players = players;
        }

        public BakerGame(Game game, ArrayList<Player> players) {
            super(game.getFrames(), game.getTenth());
            this.players = players;
        }

        //TODO: Add score to group if it exists
        //TODO: Add stats to each individual player
}
