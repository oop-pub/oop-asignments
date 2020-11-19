package solvers;

import fileio.ActionInputData;
import video.Video;
import video.Videos;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class VideoQuerySolver {
    public static String solve(ActionInputData action) {

        List<Video> filteredVideos = null;

        String type;
        if(action.getObjectType().equals("movies")) {
            type = "MOVIE";
        } else {
            type = "SHOW";
        }

        Filter filter = new Filter(
                action.getFilters().get(0).get(0),
                action.getFilters().get(1).get(0)
        );

        Stream<Video> unorderedVideos = Videos.getInstance().getAll().stream()
                .filter(video -> video.getType().equals(type))
                .filter(video -> filter.year == null || video.getReleaseYear() == filter.year)
                .filter(video -> filter.genre == null || video.hasGenre(filter.genre));

        switch (action.getCriteria()) {
            case "ratings":
                if (action.getSortType().equals("desc")) {
                    filteredVideos = unorderedVideos
                            .sorted(Comparator.comparing(Video::getRating).reversed())
                            .limit(action.getNumber())
                            .collect(Collectors.toList());
                } else {
                    filteredVideos = unorderedVideos
                            .sorted(Comparator.comparing(Video::getRating))
                            .limit(action.getNumber())
                            .collect(Collectors.toList());
                }

                filteredVideos = filteredVideos.stream()
                        .filter(video -> video.getRating() != 0)
                        .collect(Collectors.toList());

                break;
            case "favorite":
                if (action.getSortType().equals("desc")) {
                    filteredVideos = unorderedVideos
                            .sorted(Comparator.comparing(Video::getFavorite)
                            .thenComparing(Video::getTitle).reversed())
                            .limit(action.getNumber())
                            .collect(Collectors.toList());
                } else {
                    filteredVideos = unorderedVideos
                            .sorted(Comparator.comparing(Video::getFavorite))
                            .limit(action.getNumber())
                            .collect(Collectors.toList());
                }
                filteredVideos = filteredVideos.stream()
                        .filter(video -> video.getFavorite() != 0)
                        .collect(Collectors.toList());

                break;
            case "longest":
                if (action.getSortType().equals("desc")) {
                    filteredVideos = unorderedVideos
                            .sorted(Comparator.comparing(Video::getDuration).reversed())
                            .limit(action.getNumber())
                            .collect(Collectors.toList());
                } else {
                    filteredVideos = unorderedVideos
                            .sorted(Comparator.comparing(Video::getDuration))
                            .limit(action.getNumber())
                            .collect(Collectors.toList());
                }
                break;
            default:
                if (action.getSortType().equals("desc")) {
                    filteredVideos = unorderedVideos
                            .sorted(Comparator.comparing(Video::getViews).reversed())
                            .limit(action.getNumber())
                            .collect(Collectors.toList());
                } else {
                    filteredVideos = unorderedVideos
                            .sorted(Comparator.comparing(Video::getViews))
                            .limit(action.getNumber())
                            .collect(Collectors.toList());
                }
                filteredVideos = filteredVideos.stream()
                        .filter(video -> video.getViews() != 0)
                        .collect(Collectors.toList());
                break;
        }
        return String.format("Query result: %s", filteredVideos);
    }
}
