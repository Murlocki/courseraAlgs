import edu.princeton.cs.algs4.StdRandom;
public class PercolationStats {
    private final double[] thresholds;
    private final int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if( n <= 0 || trials <=0 ) throw new IllegalArgumentException();
        this.trials = trials;
        thresholds = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            int openSites = 0;
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    openSites++;
                }
            }
            thresholds[i] = (double) openSites / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0;
        for (double threshold : thresholds) {
            sum += threshold;
        }
        return sum / trials;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double mean = mean();
        double sum = 0;
        for (double threshold : thresholds) {
            sum += (threshold - mean) * (threshold - mean);
        }
        return Math.sqrt(sum / (trials - 1));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}