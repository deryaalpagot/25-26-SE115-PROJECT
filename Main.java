import java.io.*;
import java.util.Scanner;

public class Main {

    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;

    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
            "July","August","September","October","November","December"};

    static int [][][] profits = new int [MONTHS][DAYS][COMMS];

    public static void loadData() {
        for (int m = 0; m < MONTHS; m++) {
            String fileName = "Data_Files/" + months[m] + ".txt";
            File file = new File(fileName);

            try (Scanner sc = new Scanner(file)) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine().trim();

                    if (line.isEmpty())
                        continue;

                    String[] parts = line.split(",");
                    if (parts.length != 3)
                        continue;

                    String dayStr = parts[0].trim();
                    String commodityName = parts[1].trim();
                    String profitStr = parts[2].trim();

                    int day;
                    int profitValue;
                    try {
                        day = Integer.parseInt(dayStr);
                        profitValue = Integer.parseInt(profitStr);
                    } catch (Exception e) {
                        continue;
                    }

                    int commodityIndex = -1;
                    for (int i = 0; i < COMMS; i++) {
                        if (commodities[i].equals(commodityName)) {
                            commodityIndex = i;
                            break;
                        }
                    }

                    if (commodityIndex != -1 && day >= 1 && day <= DAYS) {
                        profits[m][day - 1][commodityIndex] = profitValue;
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month >= MONTHS)
            return "INVALID_MONTH";

        int max = Integer.MIN_VALUE;
        int best = -1;

        for (int c = 0; c < COMMS; c++) {
            int sum = 0;
            for (int day = 0; day < DAYS; day++) {
                sum += profits[month][day][c];
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
            sum += profits[month][day - 1][c];
        }
        return sum;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {

        if (from < 1 || to > DAYS || from > to)
            return -99999;

        int cIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(commodity)) {
                cIndex = i;
            break;
        }
        }
        if (cIndex == -1)
            return -99999;

        int sum = 0;
        for (int month = 0; month < MONTHS; month++) {
            for (int day = from - 1; day <= to - 1; day++) {
                sum += profits[month][day][cIndex];
            }
        }
        return sum;
    }

    public static int bestDayOfMonth(int month) {
        if (month < 0 || month >= MONTHS)
            return -1;

        int bestDay = 1;
        int max = Integer.MIN_VALUE;

        for (int day = 0; day < DAYS; day++) {
            int sum = 0;
            for (int c = 0; c < COMMS; c++) {
                sum += profits[month][day][c];
            }
            if (sum > max) {
                max = sum;
                bestDay = day + 1;
            }
        }
        return bestDay;
}

    public static String bestMonthForCommodity(String comm) {
        int cIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                cIndex = i;
                break;
            }
        }
        if (cIndex == -1)
            return "INVALID_COMMODITY";

        int bestMonth = -1;
        int max = Integer.MIN_VALUE;

        for (int month = 0; month < MONTHS; month++) {
            int sum = 0;
            for (int day = 0; day < DAYS; day++) {
                sum += profits[month][day][cIndex];
            }
            if (sum > max) {
                max = sum;
                bestMonth = month;
            }
        }
        return months[bestMonth];
    }

    public static int consecutiveLossDays(String comm) {
        int cIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                cIndex = i;
            break;
            }
        }
        if (cIndex == -1)
            return -1;

        int maxStreak = 0;
        int current = 0;

        for (int month = 0; month < MONTHS; month++) {
            for (int day = 0; day < DAYS; day++) {
                if (profits[month][day][cIndex] < 0) {
                    current++;
                    if (current > maxStreak)
                        maxStreak = current;
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
            if (commodities[i].equals(comm)) {
                cIndex = i;
            break;
            }
        }
        if (cIndex == -1)
            return -1;

        int count = 0;
        for (int month = 0; month < MONTHS; month++) {
            for (int day = 0; day < DAYS; day++) {
                if (profits[month][day][cIndex] > threshold)
                    count++;
            }
        }
        return count;
}

    public static int biggestDailySwing(int month) {
        if (month < 0 || month >= MONTHS)
            return -99999;

        int maxSwing = 0;

        for (int day = 0; day < DAYS - 1; day++) {
            int today = 0;
            int tomorrow = 0;

            for (int c = 0; c < COMMS; c++) {
                today += profits[month][day][c];
                tomorrow += profits[month][day + 1][c];
            }
            int diff = Math.abs(today - tomorrow);
            if (diff > maxSwing) maxSwing = diff;
        }
        return maxSwing;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        int i1 = -1, i2 = -1;

        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(c1)) i1 = i;
            if (commodities[i].equals(c2)) i2 = i;
        }
        if (i1 == -1 || i2 == -1)
            return "INVALID_COMMODITY";

        int sum1 = 0, sum2 = 0;
        for (int month = 0; month < MONTHS; month++) {
            for (int day = 0; day < DAYS; day++) {
                sum1 += profits[month][day][i1];
                sum2 += profits[month][day][i2];
            }
        }

        if (sum1 > sum2) return c1 + " is better by " + (sum1 - sum2);
        if (sum2 > sum1) return c2 + " is better by " + (sum2 - sum1);
        return "Equal";
    }

    public static String bestWeekOfMonth(int month) {
        if (month < 0 || month >= MONTHS)
            return "INVALID_MONTH";

        int bestWeek = 1;
        int max = Integer.MIN_VALUE;

        for (int w = 0; w < 4; w++) {
            int sum = 0;
            for (int day = w * 7; day < (w + 1) * 7; day++) {
                for (int c = 0; c < COMMS; c++) {
                    sum += profits[month][day][c];
                }
            }
            if (sum > max) {
                max = sum;
                bestWeek = w + 1;
            }
        }
        return "Week " + bestWeek;
    }

    public static void main(String[] args) {
        loadData();
    }
}