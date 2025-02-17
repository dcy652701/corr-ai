"use client";

// import { useWallet } from "@aptos-labs/wallet-adapter-react";
import { Button } from "../ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "../ui/dropdown-menu";
import { useCallback, useEffect, useMemo, useState } from "react";
import { useAptosWallet } from "@razorlabs/wallet-kit";
import { AccountInfo } from "@aptos-labs/wallet-standard";

export function ConnectedWalletButton() {
  const { adapter, disconnect } = useAptosWallet();
  const [account, setAccount] = useState<AccountInfo | null>(null);
  const handleDisconnect = useCallback(async () => {
    try {
      await disconnect();
    } catch (error) {
      console.error("Failed to disconnect:", error);
    }
  }, [adapter]);
  
  const getAccount = useCallback(async () => {
    if (!adapter) return;
    const account = await adapter.account();
    setAccount(account);
  }, [adapter]);

  useEffect(() => {
    getAccount();
  }, [getAccount]);

  const shortenAddress = (address: string) => {
    return `${address.slice(0, 4)}...${address.slice(-4)}`;
  };

  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button
          variant="outline"
          className="bg-gradient-to-r from-[#ffac06] to-[#ffb52e] hover:from-[#ffb52e] hover:to-[#ffac06] text-white font-medium px-6 py-2 h-11 border-0 rounded-xl shadow-[0_8px_16px_-3px_rgba(255,172,6,0.3)] hover:shadow-[0_12px_20px_-3px_rgba(255,172,6,0.4)] transition-all duration-300 ease-out hover:scale-[1.02] backdrop-blur-sm w-[160px] justify-center relative overflow-hidden group"
        >
          <span className="relative z-10 flex items-center gap-2">
            <span className="w-2 h-2 rounded-full bg-green-400 animate-pulse"></span>
            {account ? shortenAddress(account.address.toString()) : "Connected"}
          </span>
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent
        align="end"
        className="bg-white/80 backdrop-blur-md border-0 shadow-[0_15px_40px_-15px_rgba(0,0,0,0.2)] rounded-xl w-[160px] mt-2 p-1.5 transition-all duration-200 animate-in slide-in-from-top-2"
      >
        <DropdownMenuItem
          className="cursor-pointer rounded-lg m-0.5 font-medium transition-all duration-200 hover:bg-gradient-to-r hover:from-red-50 hover:to-red-100 text-gray-700 hover:text-red-600 px-4 py-2.5 flex items-center gap-2"
          onClick={handleDisconnect}
        >
          <svg 
            className="w-4 h-4" 
            fill="none" 
            stroke="currentColor" 
            viewBox="0 0 24 24"
          >
            <path 
              strokeLinecap="round" 
              strokeLinejoin="round" 
              strokeWidth={2} 
              d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" 
            />
          </svg>
          Disconnect
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  );
}
