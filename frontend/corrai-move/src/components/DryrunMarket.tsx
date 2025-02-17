"use client";

import React, { useState, useEffect } from "react";
import AlgoCard from "./AlgoCard"; // 引入 AlgoCard 组件
import { calculateCumulativeProfit } from "@/utils/calculateCumulativeProfit";
import { calculateDailyCapitalSeries } from "@/utils/calculateDailyCapitalSeries";
import { calculateDailyPositionSeries } from "@/utils/calculateDailyPositionSeries";
import { calculateDailyReturnSeries } from "@/utils/calculateDailyReturnSeries";
import Loading from "./Loading";

function parseDetailRecords(records: any[]): any[] {
    return records.map((record) => {
      let parsedValue = record.value;
      if (typeof parsedValue === "string") {
        try {
          parsedValue = JSON.parse(parsedValue);
        } catch (error) {
          console.error("Error parsing record value:", parsedValue, error);
        }
      }
      return {
        key: record.key,
        value: parsedValue,
        versionstamp: record.versionstamp,
      };
    });
  } 
  
export default function DryrunMarket() {
  const [tradesData, setTradesData] = useState<any>(null);
  const [detailData, setDetailData] = useState<any>(null);
  const [dailyReturnSeries, setDailyReturnSeries] = useState<any>(null);
  useEffect(() => {
    async function fetchAPIs() {
      try {
        // Fetch trades data
        const tradesRes = await fetch("/api/getTrades", {
          method: "GET",
          cache: "no-store",
        });
        if (!tradesRes.ok) {
          console.error("Failed to fetch trades data");
          return;
        }
        const tradesJson = await tradesRes.json();
        console.log("Trades JSON:", tradesJson);
        // If the API returns a nested object (e.g. { data: { ... } }), unwrap it:
        const trades = tradesJson.data ? tradesJson.data : tradesJson;
        setTradesData(trades);

        // Fetch detail data (similarly)
        const detailRes = await fetch("/api/getDetail", {
          method: "GET",
          cache: "no-store",
        });
        if (!detailRes.ok) {
          console.error("Failed to fetch detail data");
          return;
        }
        const detailJson = await detailRes.json();
        const parsedRecords = parseDetailRecords(detailJson.data);
        const totalProfit = calculateCumulativeProfit(parsedRecords);
        console.log("Cumulative Profit:", totalProfit);
        const dailySeries = calculateDailyCapitalSeries(parsedRecords);
        console.log("Daily Capital Series:", dailySeries);
        const dailyReturnSeries = calculateDailyReturnSeries(parsedRecords, 10000);
        console.log("Daily Return Series:", dailyReturnSeries);
        setDetailData(parsedRecords);
        setDailyReturnSeries(dailyReturnSeries);
        console.log(parsedRecords);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    }
    fetchAPIs();
  }, []);

  return (
    <>
    <div className="text-center p-4 text-gray-300 text-sm pt-20">
      Below are live interactive dry run data from our trading agents on the Moves blockchain ecosystem. Our platform features a strategy-writing agent that crafts innovative trading ideas, a strategy management agent that monitors and optimizes performance, and an order execution agent that ensures seamless market entries and exits. Together, they deliver an AI-powered, transparent, and dynamic trading experience, empowering users with real-time insights and decentralized collaboration. (Note: the strategies shown here are casual test-phase examples.)
    </div>
    <div style={{ padding: "20px", paddingTop: "0px", backgroundColor: "#151515", color: "#eee", minHeight: "80vh" }}>
      {tradesData ? (
        <AlgoCard data={tradesData} dailyReturns={dailyReturnSeries} botdata={detailData}/>
      ) : (
        <Loading />
      )}
      {/* <h2>Detail Data (Raw JSON)</h2>
      <pre>{JSON.stringify(detailData, null, 2)}</pre> */}
    </div>
    </>
  );
}

