export function calculateDailyCapitalSeries(records: any[]): { date: string; dailyChange: number }[] {
  const dailyMap: { [date: string]: number } = {};

  // Helper function to parse the close_date string into a YYYY-MM-DD format.
  // Expected format: "datetime.datetime(2025,2,16,12,40,41,781000, tzinfo=datetime.timezone.utc)"
  const parseDateFromCloseDate = (dateStr: string): string | null => {
    const regex = /datetime\.datetime\((\d+),\s*(\d+),\s*(\d+),/;
    const match = regex.exec(dateStr);
    if (match) {
      const year = match[1].padStart(4, "0");
      const month = match[2].padStart(2, "0");
      const day = match[3].padStart(2, "0");
      return `${year}-${month}-${day}`;
    }
    return null;
  };

  records.forEach(record => {
    const value = record.value;
    // Only process exit records
    if (value && typeof value === "object" && value.type === "exit" && typeof value.close_date === "string") {
      const date = parseDateFromCloseDate(value.close_date);
      if (date) {
        const profit = parseFloat(value.profit_amount);
        if (!isNaN(profit)) {
          dailyMap[date] = (dailyMap[date] || 0) + profit;
        }
      }
    }
  });

  // Convert the dailyMap to an array and sort by date
  const dailySeries = Object.keys(dailyMap)
    .sort()
    .map(date => ({
      date,
      dailyChange: dailyMap[date],
    }));

  return dailySeries;
} 