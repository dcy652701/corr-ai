# Move Contest Project

This project is built based on the open-source [scaffold-move](https://github.com/rootMUD/scaffold-move) template and has been customized for blockchain technology competitions to showcase our innovative practices in decentralized application (dApp) development.

## Project Overview

Our project is a customized decentralized application (dApp) contest entry that supports the development and deployment of smart contracts on the Move chain. It provides a user-friendly frontend interface that delivers a complete interactive experience.

## Start Guide

1. git clone <repository_url>
2. `cd corrai-move`
3. `yarn # Install the necessary front-end packages, pay attention to your local network environment`
4. Environment configuration, some global variables are in .env.local, which will be injected into the process started by yarn by default. Attention beginners, the testnet faucet url provided by aptos official website cannot be used directly.
5. `yarn dev`
6.`yarn build #compiled next.js application`
7. A Quick way to deploy: `yarn vercel --prod`

