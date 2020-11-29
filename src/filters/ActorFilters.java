package filters;

import actor.ActorsAwards;
import fileio.ActorInputData;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class ActorFilters {

    private final List<ActorInputData> actors;

    public ActorFilters(final List<ActorInputData> actors) {
        this.actors = actors;
    }

    /**
     *
     * @param awards the list of award by which we fikter
     * @return list of filtered actors by awards
     */
    public List<ActorInputData> filterByAwards(final List<String> awards) {
        List<ActorInputData> filteredActors = new ArrayList<>();
        for (ActorInputData actor : actors) {
            List<ActorsAwards> listAwards = new ArrayList<>(actor.getAwards().keySet());
            List<String> actorAwards = new ArrayList<>();
            for (ActorsAwards award : listAwards) {
                actorAwards.add(award.name());
            }
            if (actorAwards.containsAll(awards)) {
                filteredActors.add(actor);
            }

        }
        return filteredActors;
    }

    /**
     *
     * @param actor the actor for which we want the number of awards
     * @return total number of awards
     */
    public Integer setNumberOfAwards(final ActorInputData actor) {
        Map<ActorsAwards, Integer> actorsAwardsMap = actor.getAwards();
        Integer sum = 0;
        for (Integer key : actorsAwardsMap.values()) {
            sum += key;
        }
        return sum;
    }

    /**
     *
     * @param words the list of words we want to find in the actors'
     *              descriptions
     * @return list of filtered actors by description
     */
    public List<ActorInputData> filterByDescription(final List<String> words) {
        List<ActorInputData> filteredActors = new ArrayList<>();
        for (ActorInputData actor : actors) {
            boolean allWordsInDescription = true;
            String description = actor.getCareerDescription();
            List<String> descriptionList =
                    new ArrayList<>(Arrays.asList(description.split("[- ,.]")));
            for (String word : words) {
                boolean wordInDescription = false;
                String capitalised = word.substring(0, 1).toUpperCase() + word.substring(1);
                for (String descriptionWord : descriptionList) {
                    if (descriptionWord.equals(word) || descriptionWord.equals(capitalised)) {
                        wordInDescription = true;
                        break;
                    }
                }
                if (!wordInDescription) {
                    allWordsInDescription = false;
                    break;
                }
            }
            if (allWordsInDescription) {
                filteredActors.add(actor);
            }
        }
        return filteredActors;
    }


}
