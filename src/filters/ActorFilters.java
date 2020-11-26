package filters;

import actor.ActorsAwards;
import fileio.ActorInputData;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ActorFilters {

    List<ActorInputData> actors;

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
        boolean allWordsInDescription = true;
        for (ActorInputData actor : actors) {
            String description = actor.getCareerDescription();
            for (String word : words) {
                if (!(description.contains(word) || description.contains(word.toUpperCase())
                    || description.contains(word.toLowerCase()))) {
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
