
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RecommendationRunner implements Recommender {

    @Override
    public ArrayList<String> getItemsToRate() throws IOException {
        ArrayList<String> chosenMovies = new ArrayList<>();
        ArrayList<String> sampleMoviesList = MovieDatabase.filterBy(new TrueFilter());

        for (int i = 0; chosenMovies.size() < 15; i++) {
            Random seed = new Random();
            int randomInt = seed.nextInt(sampleMoviesList.size());
            String movieId = sampleMoviesList.get(randomInt);
            if (!sampleMoviesList.contains(movieId)) {
                chosenMovies.add(movieId);
            }
        }
        return chosenMovies;
    }

    @Override
    public void printRecommendationsFor(String webRaterID) throws IOException {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        FourthRatings fr = new FourthRatings();

        ArrayList<Rating> recommendedMovies = fr.getSimilarRatings(webRaterID, 20, 5);
        int recommendedMoviesLength = recommendedMovies.size();
        if (recommendedMoviesLength == 0) {
            System.out.println("<h2>, I'm so sorry, there is no recommendations for you at this moment. Try with another rating.<h2>");
        } else {
            ArrayList<String> moviesToRate = this.getItemsToRate();
            ArrayList<Rating> outputIds = new ArrayList<>();
            int repeatedItems = 0;
            for (int i = 0; outputIds.size() + repeatedItems != recommendedMoviesLength && outputIds.size() < 11; i++) {
                if (!moviesToRate.contains(recommendedMovies.get(i).getItem())) {
                    outputIds.add(recommendedMovies.get(i));
                } else {
                    repeatedItems++;
                }
            }
            System.out.println("outid size = " + outputIds.size());


            System.out.println("<style>");
            System.out.println("h2,h3{");
            System.out.println("  text-align: center;");
            System.out.println("  height: 50px;");
            System.out.println("  line-height: 50px;");
            System.out.println("  font-family: Arial, Helvetica, sans- serif;");
            System.out.println("  background-color: black;");
            System.out.println("   color:  #ff6600 }");

            System.out.println(" table {");
            System.out.println("   border-collapse: collapse;");
            System.out.println("   margin: auto;}");
            System.out.println("table, th, td {");
            System.out.println("    border: 2px solid white;");
            System.out.println("    font-size: 15px;");

            System.out.println("    padding: 2px 6px 2px 6px; }");
            System.out.println(" td img{");
            System.out.println("    display: block;");
            System.out.println("    margin-left: auto;");
            System.out.println("    margin-right: auto; }");
            System.out.println("th {");
            System.out.println("    height: 40px;");
            System.out.println("    font-size: 18px;");

            System.out.println("  background-color: black;");
            System.out.println(" color: white;");
            System.out.println("text-align: center; }");

            System.out.println(" tr:nth-child(even) {");
            System.out.println("     background-color: #f2f2f2; }");
            System.out.println("  tr:nth-child(odd) {");
            System.out.println("background-color: #cccccc; }");
            System.out.println(" tr:hover {");
            System.out.println(" background-color: #666666; ");
            System.out.println("  color:white;}");

            System.out.println("table td:first-child {");
            System.out.println(" text-align: center; }");

            System.out.println(" tr {");
            System.out.println(" font-family: Arial, Helvetica, sans-serif; }");
            System.out.println(".rating{");
            System.out.println("    color:#ff6600;");
            System.out.println("    padding: 0px 10px;");
            System.out.println("   font-weight: bold; }");
            System.out.println("</style>");


            System.out.println("<h2>Wei Xu Brings Best Movies for You! Enjoy!^^</h2>");
            System.out.println("<table id = \"rater\">");
            System.out.println("<tr>");
            System.out.println("<th>Rank</th>");

            System.out.println("<th>Poster</th>");
            System.out.println("<th>Title & Rating</th>");
            System.out.println("<th>Genre</th>");
            System.out.println("<th>Country</th>");
            System.out.println("</tr>");


            int rank = 1;
            for (Rating i : outputIds) {
                System.out.println("<tr><td>" + rank + "</td>" +

                        "<td><img src = \"" + MovieDatabase.getPoster(i.getItem()) + "\" width=\"50\" height=\"70\"></td> " +
                        "<td>" + MovieDatabase.getYear(i.getItem()) + "&ensp;&ensp; <a href=\"https://www.imdb.com/title/tt" +
                        i.getItem() + "\">" + MovieDatabase.getTitle(i.getItem()) + "</a><br><div class = \"rating\">&starf; &ensp;&ensp;&ensp;"
                        + String.format("%.1f", i.getValue()) + "/10</td>" +
                        "<td>" + MovieDatabase.getGenres(i.getItem()) + "</td>" +
                        "<td>" + MovieDatabase.getCountry(i.getItem()) + "</td>" +
                        "</tr> ");
                rank++;
            }
        }
        System.out.println("</table>");
        System.out.println("<h3>*The rank of movies is based on other raters who have the most similar rating to yours. Enjoy!^^</h3>");

    }

        public static void main(String[] args) throws IOException {
            RecommendationRunner a = new RecommendationRunner();
            a.getItemsToRate();
            a.printRecommendationsFor("65");

        }
}
