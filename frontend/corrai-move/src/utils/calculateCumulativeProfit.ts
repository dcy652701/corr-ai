export function calculateCumulativeProfit(records: any[]): number {
  let cumulativeProfit = 0;
  records.forEach(record => {
    if (record.value && typeof record.value === 'object' && record.value.type === 'exit') {
      const profit = parseFloat(record.value.profit_amount);
      if (!isNaN(profit)) {
        cumulativeProfit += profit;
      }
    }
  });
  return cumulativeProfit;
} 