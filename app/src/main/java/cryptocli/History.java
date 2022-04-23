package cryptocli;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.Callable;

import com.mitchtalmadge.asciidata.graph.ASCIIGraph;

import models.PriceSnapshot;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import util.CoinService;
import views.CoinView;

@Command(name="history",
    description = "Show price history of a cryptocurrency"
)
public class History implements Callable<Integer> {

    @Option(names={"-d", "--days"}, required = true)
    Integer numDaysHistory;

    @Option(names={"-g", "--graph"})
    boolean graphIt;

    @ParentCommand
    Crypto crypto;

    private CoinService coinService = CoinService.getInstance();

    public Integer call() throws IOException, InterruptedException {
        Currency cur = crypto.denominations.get(0); // just use first denomination
        for (String coin : crypto.coinNames) {
            List<PriceSnapshot> priceHistory = coinService.getCoinHistory(coin, cur, numDaysHistory);
            CoinView.displayPriceHistory(priceHistory, cur);
            if (graphIt) {
                final int GRAPH_X_MAX = 50;
                int every = 1;
                if(priceHistory.size() > GRAPH_X_MAX) {
                    every = priceHistory.size() / GRAPH_X_MAX; // scale x axis down
                }
                List<Double> prices = new ArrayList<>();
                for (int i = 0; i < priceHistory.size(); i+=every) { // compress x axis
                    prices.add(priceHistory.get(i).price);
                }
                double[] arr = new double[prices.size()];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = prices.get(i);
                }
                String graph = ASCIIGraph.fromSeries(arr)
                    .withNumRows(10)
                    .withTickFormat(new DecimalFormat("###,###"))
                    .plot();
                System.out.println(graph);
            }
        }
        return 0;
    }
    
}
