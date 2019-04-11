package KSR.Classification;

import KSR.Basic.ClassifyArticle;
import KSR.Basic.PreparedArticle;
import KSR.Features.FeaturesService;
import KSR.Metrics.IMetric;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class KNNService {
    FeaturesService featuresService;
    IMetric metric;
    Integer k;
    public ArrayList<ClassifyArticle> classifiedArticles;

    public KNNService(FeaturesService featuresService, IMetric metric, Integer k) {
        this.featuresService = featuresService;
        this.metric = metric;
        this.k = k;
        this.classifiedArticles = new ArrayList<>();
    }

    public void InitKnn(ArrayList<PreparedArticle> articles) {
        for (PreparedArticle art : articles) {
            classifiedArticles.add(new ClassifyArticle(art.tags.get(0), art.tags.get(0), featuresService.GetFeaturesVector(art)));
        }
    }

    public String ClassifyArticle(PreparedArticle article) {
        ClassifyArticle classifyArticle = new ClassifyArticle(article.tags.get(0), "", featuresService.GetFeaturesVector(article));

//        Map<ClassifyArticle, Double> friends  = new HashMap<>();
//        //ArrayList<ClassifyArticle> friends = new ArrayList<>();
//        ArrayList<ClassifyArticle> sortedFriends = new ArrayList<>();
//
//        for (ClassifyArticle article : this.classifiedArticles) {
//
//        }
//
//
//        Map<String, Double> neighbors = new HashMap<>();
//
//        for (ClassifyArticle art : this.classifiedArticles) {
//            neighbors.put(art.predictedTag, (metric.CalculateDistance(art.featuresVector, classifyArticle.featuresVector)));
//        }
//
//        // Get sorted and limited collection of neighbors
//        Map<String, Double> orderedNeighbors = neighbors.asMap().entrySet()
//                .stream()
//                .limit(k)
//                .collect(
//                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new)
//                );
//
//
//        // Get all distinct tags
//        ArrayList<String> distrinctTags = new ArrayList<>();
//        ArrayList<String> allTags = new ArrayList<>();
//        for (Map.Entry me : orderedNeighbors.entrySet()) {
//            allTags.add((String) me.getKey());
//        }
//        distrinctTags = allTags.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
//
//        // Init map with distinct tags and 0
//        Map<String, Integer> distinctNeighborsCount = new HashMap<>();
//        for (String tag : distrinctTags) {
//            distinctNeighborsCount.put(tag, 0);
//        }
//
//        // Get map with distinct tags and their count
//        for (Map.Entry me1 : orderedNeighbors.entrySet()) {
//            for (Map.Entry me2 : distinctNeighborsCount.entrySet()) {
//                if (me1.getKey() == me2.getKey()) {
//                    Integer c = (Integer) me2.getValue();
//                    c += 1;
//                    distinctNeighborsCount.replace((String) me2.getKey(), c);
//                }
//            }
//        }
//        distinctNeighborsCount = distinctNeighborsCount.entrySet()
//                .stream()
//                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
//                .limit(k)
//                .collect(
//                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new)
//                );
//
//
//        classifyArticle.predictedTag = distinctNeighborsCount.entrySet().stream().findFirst().get().getKey();
//        classifiedArticles.add(classifyArticle);
        return classifyArticle.predictedTag;
    }
}
