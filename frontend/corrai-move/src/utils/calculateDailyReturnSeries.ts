export function calculateDailyReturnSeries(
  records: any[],
  initialCapital: number = 10000
): { date: string; dailyReturnPercent: number }[] {
  // 辅助函数：解析日期字符串，支持格式如 "at 2025-02-13 11:02:58.244033 00:00" 和
  // "datetime.datetime(2025, 2,16,12,40,41,781000, tzinfo=datetime.timezone.utc)"。
  const parseDate = (dateStr: string | undefined): Date | null => {
    if (!dateStr) return null;
    let clean = dateStr.replace(/^at\s*/, "").trim();
    if (clean.startsWith("datetime.datetime(")) {
      const regex = /datetime\.datetime\((\d+),\s*(\d+),\s*(\d+)/;
      const match = regex.exec(clean);
      if (match) {
        const year = parseInt(match[1], 10);
        const month = parseInt(match[2], 10) - 1; // JavaScript月份从0开始
        const day = parseInt(match[3], 10);
        return new Date(year, month, day);
      }
    }
    const d = new Date(clean);
    return isNaN(d.getTime()) ? null : d;
  };

  // 辅助函数：将 Date 对象格式化为 YYYY-MM-DD。
  const formatDate = (d: Date): string => {
    const yyyy = d.getFullYear();
    const mm = String(d.getMonth() + 1).padStart(2, "0");
    const dd = String(d.getDate()).padStart(2, "0");
    return `${yyyy}-${mm}-${dd}`;
  };

  // 辅助函数：解析类似 "at 123.45" 的数值字段。
  const parseNumericField = (field: string | undefined): number => {
    if (!field) return 0;
    let clean = field.replace(/^at\s*/, "").trim();
    const num = parseFloat(clean);
    return isNaN(num) ? 0 : num;
  };

  // 定义内部类型
  interface TradeRecord {
    trade_id: string;
    open_date?: string;
    close_date?: string;
    stake_amount?: string;
    profit_amount?: string | number;
    type?: string;
  }
  interface GroupedTrade {
    open?: TradeRecord;
    exit?: TradeRecord;
  }
  interface DailyEvent {
    date: string;
    change: number;
  }

  // 根据 trade_id 将交易记录分组。对于每个 trade，open 记录为负的资金流（投入），exit 记录的资金流为投入加盈亏。
  const trades: { [tradeId: string]: GroupedTrade } = {};
  records.forEach(record => {
    const value = record.value;
    if (value && typeof value === "object" && value.trade_id) {
      const id = value.trade_id;
      if (!trades[id]) {
        trades[id] = {};
      }
      if (value.type && value.type.toLowerCase() === "exit") {
        trades[id].exit = value;
      } else {
        if (!trades[id].open) {
          trades[id].open = value;
        } else {
          const existing = parseDate(trades[id].open.open_date);
          const current = parseDate(value.open_date);
          if (current && existing && current < existing) {
            trades[id].open = value;
          }
        }
      }
    }
  });

  // 根据分组生成每日事件数组
  const events: DailyEvent[] = [];
  Object.keys(trades).forEach(tradeId => {
    const grouped = trades[tradeId];
    if (grouped.open) {
      const openDateObj = parseDate(grouped.open.open_date);
      if (openDateObj) {
        const dStr = formatDate(openDateObj);
        const stake = parseNumericField(grouped.open.stake_amount);
        // 开仓时投入资金为负值
        events.push({ date: dStr, change: -stake });
      }
    }
    if (grouped.exit) {
      const exitDateObj = parseDate(grouped.exit.close_date);
      if (exitDateObj) {
        const dStr = formatDate(exitDateObj);
        let stake = 0;
        if (grouped.open) {
          stake = parseNumericField(grouped.open.stake_amount);
        }
        const profit = parseNumericField(
          grouped.exit.profit_amount ? grouped.exit.profit_amount.toString() : "0"
        );
        // 平仓时返还资金和盈亏
        events.push({ date: dStr, change: stake + profit });
      }
    }
  });

  // 按日期累计每日现金流
  const dailyMap: { [date: string]: number } = {};
  events.forEach(event => {
    dailyMap[event.date] = (dailyMap[event.date] || 0) + event.change;
  });

  // 确定日期范围（连续日期区间）
  const eventDates = Object.keys(dailyMap).sort();
  if (eventDates.length === 0) return [];
  const startDate = new Date(eventDates[0]);
  const endDate = new Date(eventDates[eventDates.length - 1]);
  const fullDates: string[] = [];
  for (let d = new Date(startDate); d <= endDate; d.setDate(d.getDate() + 1)) {
    fullDates.push(formatDate(new Date(d)));
  }

  // 基于初始资本和每日现金流计算累积投资组合价值，并转换为累积百分比变化
  // 公式：((当前组合价值 / 初始资本) - 1) * 100
  const series: { date: string; dailyReturnPercent: number }[] = [];
  let portfolioValue = initialCapital;
  fullDates.forEach(date => {
    const cashFlow = dailyMap[date] || 0;
    portfolioValue += cashFlow;
    const cumulativeReturn = ((portfolioValue / initialCapital) - 1) * 100;
    series.push({ date, dailyReturnPercent: cumulativeReturn });
  });

  return series;
} 