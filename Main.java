// Main.java — Students version
import java.io.*;
import java.util.*;

public class Main {

    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;

    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
            "July","August","September","October","November","December"};
    static int [][][] profit = new int [MONTHS][DAYS][COMMS];

    public static void loadData() {
        profit = new int[MONTHS][COMMS][DAYS];
    }


    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month >= MONTHS)
            return "INVALID_MONTH";

        int max = Integer.MIN_VALUE;
        int best = -1;

        for (int c = 0; c < COMMS; c++) {
            int sum = 0;
            for (int d = 0; d < DAYS; d++) {
                sum += profit[month][d][c];
            }
            if (sum > max) {
                max = sum;
                best = c;
            }
        }
        return commodities[best] + " " + max;
    }

    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month >= MONTHS || day < 1 || day > DAYS)
            return -99999;
        int sum = 0;
        for (int c = 0; c < COMMS; c++) {
            sum += profit[month][day - 1][c];
        }
        return sum;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {

        if (from < 1 || to > DAYS || from > to)
            return -99999;

        int cIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(commodity))
                cIndex = i;
        }
        if (cIndex == -1)
            return -99999;

        int sum = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = from - 1; d <= to - 1; d++) {
                sum += profit[m][d][cIndex];
            }
        }
        return sum;
    }

    public static int bestDayOfMonth(int month) {
        if (month < 0 || month >= MONTHS)
            return -1;

        int bestDay = 1;
        int max = Integer.MIN_VALUE;

        for (int d = 0; d < DAYS; d++) {
            int sum = 0;
            for (int c = 0; c < COMMS; c++) {
                sum += profit[month][d][c];
            }
            if (sum > max) {
                max = sum;
                bestDay = d + 1;
            }
        }
        return bestDay;
}

    public static String bestMonthForCommodity(String comm) {
        int cIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm))
                cIndex = i;
        }
        if (cIndex == -1)
            return "INVALID_COMMODITY";

        int max = Integer.MIN_VALUE;
        int bestMonth = -1;

        for (int m = 0; m < MONTHS; m++) {
            int sum = 0;
            for (int d = 0; d < DAYS; d++) {
                sum += profit[m][d][cIndex];
            }
            if (sum > max) {
                max = sum;
                bestMonth = m;
            }
        }
        return months[bestMonth];
    }

    public static int consecutiveLossDays(String comm) {
        int cIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm))
                cIndex = i;
        }
        if (cIndex == -1) return -1;

        int maxStreak = 0;
        int current = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (profit[m][d][cIndex] < 0) {
                    current++;
                    if (current > maxStreak) maxStreak = current;
                } else {
                    current = 0;
                }
            }
        }
        return maxStreak;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        int cIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm))
                cIndex = i;
        }
        if (cIndex == -1)
            return -1;

        int count = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (profit[m][d][cIndex] > threshold) count++;
            }
        }
        return count;
}

    public static int biggestDailySwing(int month) {
        if (month < 0 || month >= MONTHS)
            return -99999;

        int maxSwing = 0;

        for (int d = 0; d < DAYS - 1; d++) {
            int today = 0;
            int tomorrow = 0;

            for (int c = 0; c < COMMS; c++) {
                today += profit[month][d][c];
                tomorrow += profit[month][d + 1][c];
            }
            int diff = Math.abs(today - tomorrow);
            if (diff > maxSwing) maxSwing = diff;
        }
        return maxSwing;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        return "DUMMY is better by 1234";
    }

    public static String bestWeekOfMonth(int month) {
        return "DUMMY";
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}