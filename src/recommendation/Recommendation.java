package recommendation;

import fileio.Input;
import fileio.UserInputData;
import fileio.ActionInputData;
import utils.WriterHelper;

import java.io.IOException;

public final class Recommendation {
    private final WriterHelper writerHelper;
    private final Input input;

    public Recommendation(final WriterHelper writerHelper, final Input input) {
        this.writerHelper = writerHelper;
        this.input = input;
    }

    /**
     * Applies recommendation based on the type of the action
     * @param action the action to be done
     * @throws IOException in case the result cannot be written to output
     */
    public void applyRecommendation(final ActionInputData action)
            throws IOException {

        if (action.getActionType().equals("recommendation")) {
            for (UserInputData user : input.getUsers()) {
                if (user.getUsername().equals(action.getUsername())) {
                    switch (action.getType()) {
                        case "standard":
                            StandardVideo standardVideo = new StandardVideo(user);
                            standardVideo.applyStandardVideo(input, action, writerHelper);
                            break;
                        case "best_unseen":
                            BestUnseenVideo bestUnseenVideo = new BestUnseenVideo(user);
                            bestUnseenVideo.applyBestUnseen(input, action, writerHelper);
                            break;
                        case "search":
                            SearchVideo searchVideo = new SearchVideo(user);
                            searchVideo.applySearchVideo(input, action, writerHelper);
                            break;
                        case "favorite":
                            FavoriteVideo favoriteVideo = new FavoriteVideo(user);
                            favoriteVideo.applyFavoriteVideo(input, action, writerHelper);
                            break;
                        case "popular":
                            PopularVideo popularVideo = new PopularVideo(user);
                            popularVideo.applyPopularVideo(input, action, writerHelper);
                            break;
                        default:
                            break;

                    }
                    break;
                }
            }
        }
    }
}
