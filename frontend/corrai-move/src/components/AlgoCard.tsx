"use client";

import React, { useState } from "react";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  ResponsiveContainer,
} from "recharts";

// Define the types for the strategy data.
interface TradesData {
  profit_closed_coin: number;
  profit_closed_fiat: number;
  trade_count: number;
  first_trade_date: string;
  first_trade_humanized: string;
  latest_trade_humanized: string;
  best_pair: string;
  best_rate: number;
  winning_trades: number;
  losing_trades: number;
  winrate: number;
  profit_factor: number;
  expectancy: number;
  max_drawdown: number;
  max_drawdown_abs: number;
  trading_volume: number;
  bot_start_date: string;
  avg_duration: string;
}

// Define type for daily return data.
interface DailyReturnData {
  date: string;
  dailyReturnPercent: number;
}

interface AlgoCardProps {
  data: TradesData;
  dailyReturns: DailyReturnData[];
  botdata: any;
}

export default function AlgoCard({ data, dailyReturns, botdata }: AlgoCardProps) {
  const [selectedTab, setSelectedTab] = useState("overview");
  const [currentPage, setCurrentPage] = useState(1);
  const tradesPerPage = 6;
  
  const accountName =
    botdata?.[0]?.key?.[1] ?? "On-Chain Quantitative Strategy";

  // 截断长字符串的辅助函数
  const truncateString = (str: string, maxLength: number = 20) => {
    if (str.length <= maxLength) return str;
    const start = str.slice(0, 8);
    const end = str.slice(-8);
    return `${start}...${end}`;
  };

  // 重新组织关键指标
  const metrics = [
    { 
      label: "Total Profit",
      value: `${data.profit_closed_fiat.toFixed(2)} USDT`,
      color: data.profit_closed_fiat >= 0 ? "success" : "danger"
    },
    { 
      label: "Win Rate",
      value: `${(data.winrate * 100).toFixed(2)}%`,
      color: data.winrate >= 0.5 ? "success" : "warning"
    },
    { 
      label: "Profit Factor",
      value: data.profit_factor.toFixed(3),
      color: data.profit_factor >= 1 ? "success" : "danger"
    },
    { 
      label: "Expectancy",
      value: `${data.expectancy.toFixed(2)} USDT`,
      color: data.expectancy >= 0 ? "success" : "danger"
    },
  ];

  const additionalStats = [
    {
      label: "Trading Volume",
      value: `${data.trading_volume.toLocaleString()} USDT`
    },
    {
      label: "Max Drawdown",
      value: `${(data.max_drawdown * 100).toFixed(2)}%`,
      subtext: `${data.max_drawdown_abs.toFixed(2)} USDT`
    },
    {
      label: "Avg Duration",
      value: data.avg_duration
    },
    {
      label: "Total Trades",
      value: data.trade_count,
      subtext: `${data.winning_trades}W/${data.losing_trades}L`
    }
  ];

  // 使用所有链上记录（7 条）来生成交易列表，若 type 不存在，则标记为Open
  const recentTrades = botdata?.map((record: any) => {
    const val = record.value;
    const openRateVal =
      typeof val.open_rate === "string"
        ? val.open_rate.replace("at ", "")
        : val.open_rate !== undefined
        ? val.open_rate.toString()
        : "0";
    const closeRateVal =
      val.close_rate !== undefined
        ? typeof val.close_rate === "string"
          ? val.close_rate.replace("at ", "")
          : val.close_rate.toString()
        : openRateVal; // 如果没有close_rate，则用open_rate
    const profitVal =
      val.profit_amount !== undefined
        ? typeof val.profit_amount === "string"
          ? val.profit_amount.replace("at ", "")
          : val.profit_amount.toString()
        : "0";
    return {
      tradeId: val.trade_id,
      pair:
        typeof val.pair === "string" ? val.pair.replace("at ", "") : val.pair || "",
      openRate: parseFloat(openRateVal),
      closeRate: parseFloat(closeRateVal),
      profit: parseFloat(profitVal),
      date: val.close_date || val.open_date,
      type: (val.type === "exit" ? "Exit" : "Open"),
    };
  });

  // 分页逻辑：当前页数、总页数及当前页显示的数据
  const totalTrades = recentTrades?.length || 0;
  const totalPages = Math.ceil(totalTrades / tradesPerPage);
  const indexOfLastTrade = currentPage * tradesPerPage;
  const indexOfFirstTrade = indexOfLastTrade - tradesPerPage;
  const currentTrades = recentTrades ? recentTrades.slice(indexOfFirstTrade, indexOfLastTrade) : [];

  const handlePrevPage = () => {
    if (currentPage > 1) setCurrentPage(currentPage - 1);
  };

  const handleNextPage = () => {
    if (currentPage < totalPages) setCurrentPage(currentPage + 1);
  };

  return (
    <div className="algo-card">
      <div className="glass-effect">
        <div className="header">
          <div className="title-section">
            <h2 title={accountName}>{truncateString(accountName)}</h2>
            <div className="badge">Active</div>
          </div>
          <p className="subtitle">
            {`${data.first_trade_humanized} - ${data.latest_trade_humanized}`}
          </p>
        </div>

        <div className="tab-navigation">
          <button 
            className={`tab-button ${selectedTab === 'overview' ? 'active' : ''}`}
            onClick={() => setSelectedTab('overview')}
          >
            Overview
          </button>
          <button 
            className={`tab-button ${selectedTab === 'trades' ? 'active' : ''}`}
            onClick={() => setSelectedTab('trades')}
          >
            Recent Trades
          </button>
        </div>

        {selectedTab === 'overview' ? (
          <>
            <div className="metrics-grid">
              {metrics.map((metric, index) => (
                <div key={index} className={`metric-card ${metric.color}`}>
                  <div className="metric-value">{metric.value}</div>
                  <div className="metric-label">{metric.label}</div>
                </div>
              ))}
            </div>
            <div className="chart-section">
              <h3>Performance Overview</h3>
              <div className="chart-container">
                <ResponsiveContainer>
                  <LineChart data={dailyReturns}>
                    <defs>
                      <linearGradient id="colorReturn" x1="0" y1="0" x2="0" y2="1">
                        <stop offset="5%" stopColor="#10B981" stopOpacity={0.8}/>
                        <stop offset="95%" stopColor="#10B981" stopOpacity={0}/>
                      </linearGradient>
                    </defs>
                    <CartesianGrid strokeDasharray="3 3" stroke="rgba(255,255,255,0.1)" />
                    <XAxis 
                      dataKey="date" 
                      stroke="#9CA3AF"
                      tick={{ fill: '#9CA3AF' }}
                      tickLine={{ stroke: '#9CA3AF' }}
                    />
                    <YAxis 
                      stroke="#9CA3AF"
                      tick={{ fill: '#9CA3AF' }}
                      tickLine={{ stroke: '#9CA3AF' }}
                      tickFormatter={(val) => `${val}%`}
                    />
                    <Tooltip
                      contentStyle={{
                        backgroundColor: 'rgba(17, 24, 39, 0.95)',
                        border: 'none',
                        borderRadius: '8px',
                        boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)'
                      }}
                      formatter={(val: number) => [`${val.toFixed(2)}%`, 'Return']}
                    />
                    <Line
                      type="monotone"
                      dataKey="dailyReturnPercent"
                      stroke="#10B981"
                      strokeWidth={2}
                      dot={false}
                      activeDot={{ r: 6, fill: '#10B981' }}
                      fill="url(#colorReturn)"
                    />
                  </LineChart>
                </ResponsiveContainer>
              </div>
            </div>
          </>
        ) : (
          <div className="trades-section">
            <div className="trades-table">
              <div className="table-header">
                <div className="col">Trade ID</div>
                <div className="col">Pair</div>
                <div className="col">Type</div>
                <div className="col">Price</div>
                <div className="col">P/L</div>
              </div>
              {currentTrades.map((trade: any, index: any) => (
                <div key={index} className="table-row">
                  <div className="col">{trade.tradeId}</div>
                  <div className="col">{trade.pair}</div>
                  <div className="col">
                    <span className={`type-badge ${trade.type.toLowerCase()}`}>
                      {trade.type}
                    </span>
                  </div>
                  <div className="col">
                    <div>{trade.openRate.toFixed(2)}</div>
                    <div className="secondary">{trade.closeRate.toFixed(2)}</div>
                  </div>
                  <div className={`col ${trade.profit >= 0 ? 'profit' : 'loss'}`}>
                    {trade.profit.toFixed(4)}
                  </div>
                </div>
              ))}
            </div>
            {totalPages > 1 && (
              <div className="pagination">
                <button onClick={handlePrevPage} disabled={currentPage === 1}>
                  Prev
                </button>
                <span>
                  Page {currentPage} of {totalPages}
                </span>
                <button onClick={handleNextPage} disabled={currentPage === totalPages}>
                  Next
                </button>
              </div>
            )}
          </div>
        )}

        <div className="stats-grid">
          {additionalStats.map((stat, index) => (
            <div key={index} className="stat-card">
              <div className="stat-label">{stat.label}</div>
              <div className="stat-value">{stat.value}</div>
              {stat.subtext && <div className="stat-subtext">{stat.subtext}</div>}
            </div>
          ))}
        </div>
      </div>

      <style jsx>{`
        .algo-card {
          padding: 1px;
          background: linear-gradient(
            135deg,
            rgba(45, 45, 45, 0.5),
            rgba(80, 80, 80, 0.5)
          );
          border-radius: 24px;
          margin: 2rem 0;
        }

        .glass-effect {
          background: rgba(18, 18, 18, 0.95);
          backdrop-filter: blur(20px);
          border-radius: 24px;
          padding: 2rem;
        }

        .header {
          margin-bottom: 2rem;
        }

        .title-section {
          display: flex;
          align-items: center;
          gap: 1rem;
          margin-bottom: 0.5rem;
        }

        .title-section h2 {
          margin: 0;
          font-size: 1.5rem;
          font-weight: 600;
          color: #F9FAFB;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
        }

        .badge {
          background: rgba(16, 185, 129, 0.1);
          color: #10B981;
          padding: 0.25rem 0.75rem;
          border-radius: 9999px;
          font-size: 0.875rem;
          font-weight: 500;
        }

        .subtitle {
          color: #9CA3AF;
          margin: 0;
          font-size: 0.875rem;
        }

        .tab-navigation {
          display: flex;
          gap: 1rem;
          margin-bottom: 2rem;
          border-bottom: 1px solid rgba(255, 255, 255, 0.1);
          padding-bottom: 1rem;
        }

        .tab-button {
          background: none;
          border: none;
          color: #9CA3AF;
          padding: 0.5rem 1rem;
          cursor: pointer;
          font-size: 0.9rem;
          transition: all 0.2s ease;
          position: relative;
        }

        .tab-button.active {
          color: #FFFFFF;
        }

        .tab-button.active:after {
          content: '';
          position: absolute;
          bottom: -1rem;
          left: 0;
          width: 100%;
          height: 2px;
          background: #FFFFFF;
        }

        .trades-section {
          background: rgba(30, 30, 30, 0.5);
          border-radius: 16px;
          padding: 1.5rem;
        }

        .trades-table {
          width: 100%;
        }

        .table-header {
          display: grid;
          grid-template-columns: 1fr 2fr 1fr 1.5fr 1fr;
          padding: 1rem;
          background: rgba(40, 40, 40, 0.5);
          border-radius: 8px;
          margin-bottom: 0.5rem;
          font-size: 0.85rem;
          color: #9CA3AF;
        }

        .table-row {
          display: grid;
          grid-template-columns: 1fr 2fr 1fr 1.5fr 1fr;
          padding: 1rem;
          border-bottom: 1px solid rgba(255, 255, 255, 0.05);
          transition: background-color 0.2s ease;
        }

        .table-row:hover {
          background: rgba(40, 40, 40, 0.3);
        }

        .col {
          display: flex;
          flex-direction: column;
          justify-content: center;
        }

        .type-badge {
          padding: 0.25rem 0.5rem;
          border-radius: 4px;
          font-size: 0.8rem;
          width: fit-content;
        }

        .type-badge.open {
          background: rgba(16, 185, 129, 0.1);
          color: #10B981;
        }

        .type-badge.exit {
          background: rgba(239, 68, 68, 0.1);
          color: #EF4444;
        }

        .secondary {
          color: #6B7280;
          font-size: 0.85rem;
          margin-top: 0.25rem;
        }

        .profit {
          color: #10B981;
        }

        .loss {
          color: #EF4444;
        }

        .metrics-grid {
          display: grid;
          grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
          gap: 1rem;
          margin-bottom: 2rem;
        }

        .metric-card {
          background: rgba(255, 255, 255, 0.03);
          padding: 1.25rem;
          border-radius: 16px;
          border: 1px solid rgba(255, 255, 255, 0.05);
          transition: transform 0.2s ease;
        }

        .metric-card:hover {
          transform: translateY(-2px);
        }

        .metric-card.success .metric-value { color: #10B981; }
        .metric-card.danger .metric-value { color: #EF4444; }
        .metric-card.warning .metric-value { color: #F59E0B; }

        .metric-value {
          font-size: 1.25rem;
          font-weight: 600;
          margin-bottom: 0.5rem;
        }

        .metric-label {
          color: #9CA3AF;
          font-size: 0.875rem;
        }

        .stats-grid {
          display: grid;
          grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
          gap: 1rem;
          margin-top: 2rem;
        }

        .stat-card {
          background: rgba(255, 255, 255, 0.02);
          padding: 1rem;
          border-radius: 12px;
          text-align: center;
        }

        .stat-label {
          color: #9CA3AF;
          font-size: 0.875rem;
          margin-bottom: 0.5rem;
        }

        .stat-value {
          color: #F9FAFB;
          font-size: 1.125rem;
          font-weight: 600;
        }

        .stat-subtext {
          color: #6B7280;
          font-size: 0.75rem;
          margin-top: 0.25rem;
        }

        .chart-section {
          background: rgba(255, 255, 255, 0.02);
          border-radius: 16px;
          padding: 1.5rem;
        }

        .chart-section h3 {
          margin: 0 0 1rem 0;
          color: #F9FAFB;
          font-size: 1.125rem;
        }

        .chart-container {
          height: 300px;
        }

        .pagination {
          display: flex;
          justify-content: center;
          align-items: center;
          gap: 1rem;
          margin-top: 1rem;
        }

        .pagination button {
          padding: 0.5rem 1rem;
          background: #1e1e1e;
          color: #f9fafb;
          border: 1px solid rgba(255, 255, 255, 0.1);
          border-radius: 4px;
          cursor: pointer;
          transition: background 0.2s;
        }

        .pagination button:disabled {
          opacity: 0.5;
          cursor: not-allowed;
        }
      `}</style>
    </div>
  );
} 