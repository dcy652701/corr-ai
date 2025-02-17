"use client";

import {
  APTOS_CONNECT_ACCOUNT_URL,
  AboutAptosConnect,
  AboutAptosConnectEducationScreen,
  AnyAptosWallet,
  AptosPrivacyPolicy,
  WalletItem,
  WalletSortingOptions,
  groupAndSortWallets,
  isAptosConnectWallet,
  isInstallRequired,
  truncateAddress,
  useWallet,
} from "@aptos-labs/wallet-adapter-react";
import {
  ArrowLeft,
  ArrowRight,
  ChevronDown,
  Copy,
  LogOut,
  User,
} from "lucide-react";
import { useCallback, useState } from "react";
import { Button } from "./ui/button";
import {
  Collapsible,
  CollapsibleContent,
  CollapsibleTrigger,
} from "./ui/collapsible";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "./ui/dialog";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "./ui/dropdown-menu";
import { useToast } from "./ui/use-toast";

export function WalletSelector(walletSortingOptions: WalletSortingOptions) {
  const { account, connected, disconnect, wallet } = useWallet();
  const { toast } = useToast();
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const closeDialog = useCallback(() => setIsDialogOpen(false), []);

  const copyAddress = useCallback(async () => {
    if (!account?.address) return;
    try {
      await navigator.clipboard.writeText(account.address);
      toast({
        title: "Success",
        description: "Copied wallet address to clipboard.",
      });
    } catch {
      toast({
        variant: "destructive",
        title: "Error",
        description: "Failed to copy wallet address.",
      });
    }
  }, [account?.address, toast]);

  return connected ? (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button>
          {account?.ansName || truncateAddress(account?.address) || "Unknown"}
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent align="end">
        <DropdownMenuItem onSelect={copyAddress} className="gap-2">
          <Copy className="h-4 w-4" /> Copy address
        </DropdownMenuItem>
        {wallet && isAptosConnectWallet(wallet) && (
          <DropdownMenuItem asChild>
            <a
              href={APTOS_CONNECT_ACCOUNT_URL}
              target="_blank"
              rel="noopener noreferrer"
              className="flex gap-2"
            >
              <User className="h-4 w-4" /> Account
            </a>
          </DropdownMenuItem>
        )}
        <DropdownMenuItem onSelect={disconnect} className="gap-2">
          <LogOut className="h-4 w-4" /> Disconnect
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  ) : (
    <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
      <DialogTrigger asChild>
        <Button>Connect a Wallet</Button>
      </DialogTrigger>
      <ConnectWalletDialog close={closeDialog} {...walletSortingOptions} />
    </Dialog>
  );
}

interface ConnectWalletDialogProps extends WalletSortingOptions {
  close: () => void;
}

function ConnectWalletDialog({
  close,
  ...walletSortingOptions
}: ConnectWalletDialogProps) {
  const { wallets = [] } = useWallet();
  const { aptosConnectWallets, availableWallets, installableWallets } =
    groupAndSortWallets(wallets, walletSortingOptions);
  const hasAptosConnectWallets = !!aptosConnectWallets.length;

  return (
    <DialogContent className="max-h-[85vh] overflow-auto bg-white/90 backdrop-blur-md border-0 shadow-[0_15px_40px_-15px_rgba(0,0,0,0.2)] rounded-2xl p-6">
      <AboutAptosConnect renderEducationScreen={renderEducationScreen}>
        <DialogHeader>
          <DialogTitle className="flex flex-col text-center leading-snug text-2xl font-semibold bg-gradient-to-r from-gray-900 to-gray-600 bg-clip-text text-transparent">
            {hasAptosConnectWallets ? (
              <>
                <span>Log in or sign up</span>
                <span className="text-[#ffac06]">with Social + Aptos Connect</span>
              </>
            ) : (
              "Connect Wallet"
            )}
          </DialogTitle>
        </DialogHeader>

        {hasAptosConnectWallets && (
          <div className="flex flex-col gap-3 pt-4">
            {aptosConnectWallets.map((wallet) => (
              <AptosConnectWalletRow
                key={wallet.name}
                wallet={wallet}
                onConnect={close}
              />
            ))}
            <p className="flex gap-1 justify-center items-center text-gray-600 text-sm mt-2">
              Learn more about{" "}
              <AboutAptosConnect.Trigger className="flex gap-1 py-3 items-center text-[#ffac06] hover:text-[#ffac06]/80 transition-colors duration-200">
                Aptos Connect <ArrowRight size={16} />
              </AboutAptosConnect.Trigger>
            </p>
            <AptosPrivacyPolicy className="flex flex-col items-center py-2">
              <p className="text-xs leading-5 text-gray-500">
                <AptosPrivacyPolicy.Disclaimer />{" "}
                <AptosPrivacyPolicy.Link className="text-[#ffac06] hover:text-[#ffac06]/80 underline underline-offset-4" />
                <span className="text-gray-400">.</span>
              </p>
              <AptosPrivacyPolicy.PoweredBy className="flex gap-1.5 items-center text-xs leading-5 text-gray-400 mt-1" />
            </AptosPrivacyPolicy>
            <div className="flex items-center gap-3 py-4 text-gray-400">
              <div className="h-px w-full bg-gradient-to-r from-transparent via-gray-200 to-transparent" />
              <span className="text-sm font-medium">Or</span>
              <div className="h-px w-full bg-gradient-to-r from-transparent via-gray-200 to-transparent" />
            </div>
          </div>
        )}

        <div className="flex flex-col gap-3 pt-2">
          {availableWallets.map((wallet) => (
            <WalletRow key={wallet.name} wallet={wallet} onConnect={close} />
          ))}
          {!!installableWallets.length && (
            <Collapsible className="flex flex-col gap-3">
              <CollapsibleTrigger asChild>
                <Button 
                  size="sm" 
                  variant="ghost" 
                  className="gap-2 text-gray-500 hover:text-gray-700 transition-colors duration-200"
                >
                  More wallets <ChevronDown className="w-4 h-4" />
                </Button>
              </CollapsibleTrigger>
              <CollapsibleContent className="flex flex-col gap-3">
                {installableWallets.map((wallet) => (
                  <WalletRow
                    key={wallet.name}
                    wallet={wallet}
                    onConnect={close}
                  />
                ))}
              </CollapsibleContent>
            </Collapsible>
          )}
        </div>
      </AboutAptosConnect>
    </DialogContent>
  );
}

interface WalletRowProps {
  wallet: AnyAptosWallet;
  onConnect?: () => void;
}

function WalletRow({ wallet, onConnect }: WalletRowProps) {
  return (
    <WalletItem
      wallet={wallet}
      onConnect={onConnect}
      className="flex items-center justify-between px-5 py-4 gap-4 rounded-xl bg-white/50 hover:bg-white/80 border border-gray-100 hover:border-gray-200 transition-all duration-200 shadow-sm hover:shadow-md group"
    >
      <div className="flex items-center gap-4">
        <WalletItem.Icon className="h-7 w-7 group-hover:scale-110 transition-transform duration-200" />
        <WalletItem.Name className="text-base font-medium text-gray-700" />
      </div>
      {isInstallRequired(wallet) ? (
        <Button 
          size="sm" 
          variant="ghost" 
          asChild 
          className="text-[#ffac06] hover:text-[#ffac06]/80 hover:bg-[#ffac06]/10"
        >
          <WalletItem.InstallLink />
        </Button>
      ) : (
        <WalletItem.ConnectButton asChild>
          <Button 
            size="sm"
            className="bg-[#ffac06] hover:bg-[#ffac06]/90 text-white shadow-[0_4px_12px_-3px_rgba(255,172,6,0.3)] hover:shadow-[0_6px_16px_-3px_rgba(255,172,6,0.4)] transition-all duration-200"
          >
            Connect
          </Button>
        </WalletItem.ConnectButton>
      )}
    </WalletItem>
  );
}

function AptosConnectWalletRow({ wallet, onConnect }: WalletRowProps) {
  return (
    <WalletItem wallet={wallet} onConnect={onConnect}>
      <WalletItem.ConnectButton asChild>
        <Button 
          size="lg" 
          variant="outline" 
          className="w-full gap-4 bg-gradient-to-r from-white to-gray-50 hover:from-gray-50 hover:to-white border border-gray-100 hover:border-gray-200 shadow-sm hover:shadow-md transition-all duration-200 group"
        >
          <WalletItem.Icon className="h-5 w-5 group-hover:scale-110 transition-transform duration-200" />
          <WalletItem.Name className="text-base font-medium text-gray-700" />
        </Button>
      </WalletItem.ConnectButton>
    </WalletItem>
  );
}

function renderEducationScreen(screen: AboutAptosConnectEducationScreen) {
  return (
    <div className="flex flex-col">
      <DialogHeader className="grid grid-cols-[1fr_4fr_1fr] items-center space-y-0 mb-6">
        <Button 
          variant="ghost" 
          size="icon" 
          onClick={screen.cancel}
          className="w-10 h-10 rounded-full hover:bg-gray-100/80 transition-colors duration-200"
        >
          <ArrowLeft className="w-5 h-5 text-gray-600" />
        </Button>
        <DialogTitle className="leading-snug text-lg font-semibold bg-gradient-to-r from-gray-900 to-gray-700 bg-clip-text text-transparent">
          About Aptos Connect
        </DialogTitle>
      </DialogHeader>

      <div className="flex h-[180px] pb-3 items-end justify-center relative">
        <div className="absolute inset-0 bg-gradient-to-b from-white/0 via-[#ffac06]/5 to-white/0 rounded-2xl" />
        <screen.Graphic className="relative z-10 transform transition-transform duration-500 hover:scale-105" />
      </div>

      <div className="flex flex-col gap-3 text-center pb-6 px-4">
        <screen.Title className="text-2xl font-semibold text-gray-800 bg-gradient-to-r from-gray-900 to-gray-700 bg-clip-text text-transparent" />
        <screen.Description className="text-base text-gray-600 leading-relaxed [&>a]:text-[#ffac06] [&>a]:underline [&>a]:underline-offset-4 [&>a]:decoration-[#ffac06]/30 [&>a]:hover:decoration-[#ffac06] [&>a]:transition-all" />
      </div>

      <div className="grid grid-cols-3 items-center px-2 pt-4 border-t border-gray-100">
        <Button
          size="sm"
          variant="ghost"
          onClick={screen.back}
          className="justify-self-start text-gray-600 hover:text-gray-900 transition-colors duration-200"
        >
          Back
        </Button>

        <div className="flex items-center gap-2 place-self-center">
          {screen.screenIndicators.map((ScreenIndicator, i) => (
            <ScreenIndicator key={i} className="py-4">
              <div className="h-1 w-8 rounded-full transition-all duration-300 bg-gray-200 [[data-active]>&]:bg-[#ffac06] [[data-active]>&]:w-12" />
            </ScreenIndicator>
          ))}
        </div>

        <Button
          size="sm"
          variant="ghost"
          onClick={screen.next}
          className="gap-2 justify-self-end group hover:bg-[#ffac06] hover:text-white transition-all duration-300"
        >
          <span className="relative">
            {screen.screenIndex === screen.totalScreens - 1 ? "Finish" : "Next"}
          </span>
          <ArrowRight 
            size={16} 
            className="transform transition-transform duration-300 group-hover:translate-x-1"
          />
        </Button>
      </div>
    </div>
  );
}
