import React from "react";

export default function LandingPage() {
  return (
    <main className="min-h-screen bg-[#151515] flex flex-col items-center justify-center text-center px-6 py-12">
      <header className="mb-12">
        <h1 className="text-4xl md:text-6xl font-extrabold text-white mb-4">
          AI-Driven Trading Platform
        </h1>
        <p className="text-lg md:text-2xl text-gray-300">
          No-Code Backtesting | On-Chain Transparency | AI-Assisted Strategies
        </p>
      </header>

      <section className="max-w-2xl text-white">
        <p className="mb-8">
          Harness the power of AI-driven trading robots to boost your performance.
          Seamlessly test strategies without coding and track immutable on-chain records.
        </p>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-12">
          {/* Card 1 */}
          <div className="relative overflow-hidden rounded-2xl group">
            {/* Hover Gradient Glow */}
            <div className="absolute inset-0 bg-gradient-to-r from-[#ffac06] to-[#ffb52e] opacity-0 group-hover:opacity-30 transition-opacity duration-300"></div>
            <div className="relative bg-[#151515] border border-gray-800 p-6 rounded-2xl shadow-xl transform transition duration-300 group-hover:-translate-y-2 group-hover:scale-105">
              <h2 className="text-xl font-bold mb-2 text-white">On-Chain Transparency</h2>
              <p className="text-gray-400">
                Immutable trading performance verified on-chain.
              </p>
            </div>
          </div>
          {/* Card 2 */}
          <div className="relative overflow-hidden rounded-2xl group">
            <div className="absolute inset-0 bg-gradient-to-r from-[#ffac06] to-[#ffb52e] opacity-0 group-hover:opacity-30 transition-opacity duration-300"></div>
            <div className="relative bg-[#151515] border border-gray-800 p-6 rounded-2xl shadow-xl transform transition duration-300 group-hover:-translate-y-2 group-hover:scale-105">
              <h2 className="text-xl font-bold mb-2 text-white">No-Code Backtesting</h2>
              <p className="text-gray-400">
                Optimize your strategies quickly with our intuitive no-code tools.
              </p>
            </div>
          </div>
          {/* Card 3 */}
          <div className="relative overflow-hidden rounded-2xl group">
            <div className="absolute inset-0 bg-gradient-to-r from-[#ffac06] to-[#ffb52e] opacity-0 group-hover:opacity-30 transition-opacity duration-300"></div>
            <div className="relative bg-[#151515] border border-gray-800 p-6 rounded-2xl shadow-xl transform transition duration-300 group-hover:-translate-y-2 group-hover:scale-105">
              <h2 className="text-xl font-bold mb-2 text-white">AI Assistance</h2>
              <p className="text-gray-400">
                Leverage advanced AI to generate and refine trading strategies effortlessly.
              </p>
            </div>
          </div>
          {/* Card 4 */}
          <div className="relative overflow-hidden rounded-2xl group">
            <div className="absolute inset-0 bg-gradient-to-r from-[#ffac06] to-[#ffb52e] opacity-0 group-hover:opacity-30 transition-opacity duration-300"></div>
            <div className="relative bg-[#151515] border border-gray-800 p-6 rounded-2xl shadow-xl transform transition duration-300 group-hover:-translate-y-2 group-hover:scale-105">
              <h2 className="text-xl font-bold mb-2 text-white">Robot Marketplace</h2>
              <p className="text-gray-400">
                Manage and hire trading robots under a unified digital identity.
              </p>
            </div>
          </div>
        </div>

        <a
          href="https://beta.corr.ai"
          className="bg-gradient-to-r from-[#ffac06] to-[#ffb52e] rounded-full px-8 py-4 text-lg font-bold text-white shadow-lg hover:scale-105 transform transition-all duration-300"
        >
          Get Started
        </a>
      </section>
    </main>
  );
} 