import { NextResponse } from "next/server";

export async function GET(request: Request) {
  const url = "https://corrai.tech/app-api/coin/freqtrade/botdata";

  const res = await fetch(url, {
    method: "GET",
    cache: "no-store", // 禁用缓存，确保最新数据
  });

  if (!res.ok) {
    return NextResponse.error();
  }
  
  const data = await res.json();
  return NextResponse.json(data);
} 