import Image from "next/image";
import { NavItem } from "./NavItem";
import {
  MODULE_URL
} from "../lib/utils/constants";
import { WalletButton } from "@/components/wallet/WalletButton";

export function NavBar() {
  return (
    <nav className="fixed top-0 left-0 right-0 z-50 backdrop-blur-md bg-black/10">
      <div className="max-w-7xl mx-auto">
        <div className="flex items-center justify-between h-20 px-6">
          {/* Logo and Navigation Section */}
          <div className="flex items-center gap-8">
            <a 
              href="https://beta.corr.ai" 
              target="_blank" 
              rel="noreferrer"
              className="relative group"
            >
              <div className="absolute -inset-2 bg-gradient-to-r from-[#ffac06]/20 via-[#ffac06]/10 to-transparent rounded-full blur-lg group-hover:from-[#ffac06]/30 group-hover:via-[#ffac06]/20 transition-all duration-500 opacity-0 group-hover:opacity-100" />
              <Image 
                src="/logo.png" 
                width={48} 
                height={48} 
                alt="logo"
                className="relative transform transition-all duration-500 group-hover:scale-105 group-hover:brightness-110" 
              />
            </a>

            {/* Navigation Items */}
            <ul className="flex items-center gap-8">
              <NavItem 
                href="/" 
                title="Home" 
                className="relative font-medium text-gray-300 hover:text-[#ffac06] transition-all duration-300 after:absolute after:left-0 after:-bottom-1 after:h-0.5 after:w-0 after:bg-gradient-to-r after:from-[#ffac06] after:to-[#ffb52e] after:transition-all after:duration-300 hover:after:w-full" 
              />
              <NavItem 
                href="https://beta.corr.ai" 
                title="Create Agent" 
                className="relative px-6 py-2.5 font-medium text-white bg-gradient-to-r from-[#ffac06] to-[#ffb52e] rounded-full shadow-[0_8px_16px_-3px_rgba(255,172,6,0.3)] hover:shadow-[0_12px_20px_-3px_rgba(255,172,6,0.4)] transition-all duration-300 hover:scale-[1.02] hover:brightness-105 before:absolute before:inset-0 before:rounded-full before:bg-gradient-to-r before:from-[#ffac06] before:to-[#ffb52e] before:opacity-0 before:transition-opacity before:duration-300 hover:before:opacity-100 overflow-hidden" 
              />
            </ul>
          </div>

          {/* Wallet Button Section */}
          <div className="flex items-center">
            <div className="relative group">
              <div className="absolute -inset-2 pointer-events-none bg-gradient-to-r from-transparent via-[#ffac06]/10 to-[#ffac06]/20 rounded-full blur-lg opacity-0 group-hover:opacity-100 transition-all duration-500" />
              <WalletButton />
            </div>
          </div>
        </div>
      </div>
    </nav>
  );
}
