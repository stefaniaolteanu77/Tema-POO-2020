package filters;

import actor.ActorsAwards;
import fileio.ActorInputData;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ActorFilters {

    private final List<ActorInputData> actors;

    public ActorFilters(List<ActorInputData> actors) {
        this.actors = actors;
    }

    public List<ActorInputData> filterByAwards(List<String> awards) {
        List<ActorInputData> filteredActors = new ArrayList<>();
        for (ActorInputData actor : actors) {
            List<ActorsAwards> listAwards = new ArrayList<>(actor.getAwards().keySet());
            List<String> actorAwards =  new ArrayList<>();
            for(ActorsAwards award : listAwards) {
                actorAwards.add(award.name());
            }
            if(actorAwards.containsAll(awards)) {
                filteredActors.add(actor);
            }

        }
        return filteredActors;
    }

    public Integer setNumberOfAwards(ActorInputData actor) {
        Map<ActorsAwards, Integer> actorsAwardsMap = actor.getAwards();
        Integer sum = 0;
        for (Integer key : actorsAwardsMap.values()) {
            sum += key;
        }
        return sum;
    }

    public List<ActorInputData> filterByDescription(List<String> words) {
        List<ActorInputData> filteredActors = new ArrayList<>();
        for (ActorInputData actor : actors) {
            boolean allWordsInDescription = true;
            String description = actor.getCareerDescription();
            List<String> descriptionList = new ArrayList<>(Arrays.asList(description.split("[- ,.]")));
            for (String word : words) {
                boolean wordInDescription = false;
                String capitalised = word.substring(0,1).toUpperCase() + word.substring(1);
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
            if(allWordsInDescription) {
                filteredActors.add(actor);
            }
        }
        return filteredActors;
    }


}
