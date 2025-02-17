import { NextResponse } from "next/server";

export async function GET(request: Request) {
  const url = "https://corrai.tech/app-api/coin/chain/query?address=0x9d32d6054e7bcb842074f115a6110026621ce635f82b9e7e20efa77076c43d8e";

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