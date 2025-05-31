'use client';
import { createContext, useContext, useState, useEffect } from 'react';

const WalletContext = createContext();

export const WalletProvider = ({ children }) => {
    const [wallet, setWallet] = useState(null);

    useEffect(() => {
        const storedWallet = localStorage.getItem('walletBalance');
        const parsedWallet = parseFloat(storedWallet);
        if (!isNaN(parsedWallet)) {
            setWallet(parsedWallet);
        } else {
            setWallet(0); 
        }
    }, []);

    const updateWallet = (newBalance) => {
        localStorage.setItem('walletBalance', newBalance.toString());
        setWallet(newBalance);
    };

    return (
        <WalletContext.Provider value={{ wallet, updateWallet }}>
            {children}
        </WalletContext.Provider>
    );
};

export const useWallet = () => useContext(WalletContext);
