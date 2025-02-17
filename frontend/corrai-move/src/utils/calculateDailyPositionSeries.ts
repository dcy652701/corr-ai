export function calculateDailyPositionSeries(records: any[]): { date: string; dailyChange: number }[] {
  // 辅助函数：将日期字符串解析为 Date 对象
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

  // 辅助函数：将 Date 对象格式化为 YYYY-MM-DD 格式
  const formatDate = (d: Date): string => {
    const yyyy = d.getFullYear();
    const mm = String(d.getMonth() + 1).padStart(2, "0");
    const dd = String(d.getDate()).padStart(2, "0");
    return `${yyyy}-${mm}-${dd}`;
  };

  // 辅助函数：解析类似 "at 123.45" 的数值字段
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

  // 先按 trade_id 对记录进行分组
  const trades: { [tradeId: string]: GroupedTrade } = {};
  records.forEach(record => {
    const value = record.value;
    if (value && typeof value === "object" && value.trade_id) {
      const id = value.trade_id;
      if (!trades[id]) {
        trades[id] = {};
      }
      // 根据 type 判断记录属于 open 还是 exit
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

  // 构造每日事件数组：开放事件为负的投入资金，退出事件为回收资金及盈亏
  const events: DailyEvent[] = [];
  Object.keys(trades).forEach(tradeId => {
    const grouped = trades[tradeId];
    if (grouped.open) {
      const openDateObj = parseDate(grouped.open.open_date);
      if (openDateObj) {
        const dStr = formatDate(openDateObj);
        const stake = parseNumericField(grouped.open.stake_amount);
        // 开仓时扣除资金
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
        // 平仓时收回投入资金和盈亏
        events.push({ date: dStr, change: stake + profit });
      }
    }
  });

  // 按日期对事件进行累加
  const dailyMap: { [date: string]: number } = {};
  events.forEach(event => {
    dailyMap[event.date] = (dailyMap[event.date] || 0) + event.change;
  });

  // 按日期范围输出每日变化数列
  const eventDates = Object.keys(dailyMap).sort();
  if (eventDates.length === 0) return [];
  const startDate = new Date(eventDates[0]);
  const endDate = new Date(eventDates[eventDates.length - 1]);
  const series: { date: string; dailyChange: number }[] = [];
  for (let d = new Date(startDate); d <= endDate; d.setDate(d.getDate() + 1)) {
    const ds = formatDate(d);
    series.push({
      date: ds,
      dailyChange: dailyMap[ds] ?? 0,
    });
  }
  return series;
} 