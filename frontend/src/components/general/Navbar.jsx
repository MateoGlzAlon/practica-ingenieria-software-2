'use client';

import { GoogleLogin } from '@react-oauth/google';
import { useState, useEffect } from 'react';
import Link from "next/link";
import { Landmark } from "lucide-react";
import getUserIdFromLocalStorage from '@/hooks/getUserIdAuth';
import { useWallet } from '@/hooks/walletContext';
import logInUser from '@/api/post/postLogInUser';
import { useLoggedIn } from '@/hooks/loggedInContext';
import { User, LogOut, Wallet } from "lucide-react"


export default function Navbar() {
    const [userIdLS, setUserIdLS] = useState(null);
    const { loggedIn, setLoggedIn } = useLoggedIn();
    const { wallet, updateWallet } = useWallet();
    useEffect(() => {
        const id = getUserIdFromLocalStorage();
        setUserIdLS(id);
    }, []);

    async function handleSuccess(credentialResponse) {
        try {
            const success = await logInUser(credentialResponse, updateWallet);
            if (success === true) {
                setLoggedIn(true);
            } else {
                console.warn('Login failed');
            }
        } catch (error) {
            console.error('Login error:', error);
        }
    }

    async function handleLogout() {
        try {
            localStorage.removeItem('userId');
            localStorage.removeItem('userRole');
            localStorage.removeItem('avatar');
            localStorage.removeItem('walletBalance');
            setLoggedIn(false);
        } catch (error) {
            console.error('Logout error:', error);
        }
    }

    return (
        <>
            <nav className="fixed top-0 left-0 w-full bg-white border-b border-gray-300 z-50 px-6 py-4 flex justify-between items-center shadow-sm">
                <div className="w-1/3" />

                <Link href="/" className="flex justify-center w-1/3" >
                    <div className="flex items-center text-2xl font-bold text-black hover:cursor-pointer" >
                        <Landmark size={28} />
                        <span className="pl-2 text-2xl" id="navbar-title">Stoa</span>
                    </div>
                </Link>

                <div className="w-1/3 flex justify-end items-center gap-3">
                    <div className="flex items-center gap-4">
                        {loggedIn ? (
                            <>
                                {wallet !== null && (
                                    <div className="flex items-center gap-1.5 text-gray-600">
                                        <Wallet className="h-6 w-6" />
                                        <span className="text-sm font-mono">{wallet}€</span>
                                    </div>
                                )}

                                <Link
                                    href="/profile"
                                    className="flex items-center gap-1.5 px-3 py-1.5 text-gray-700 hover:text-gray-900 hover:bg-gray-200 rounded transition-colors"
                                >
                                    <img
                                        src={localStorage.getItem('avatar')}
                                        className="h-6 w-6 rounded-2xl"
                                    />
                                    Profile
                                </Link>

                                <button
                                    onClick={handleLogout}
                                    className="flex items-center gap-1.5 px-3 py-1.5 text-sm text-gray-500 hover:text-red-600 hover:bg-red-50 rounded transition-colors"
                                >
                                    <LogOut className="h-6 w-6" />
                                    Sign out
                                </button>
                            </>
                        ) : (
                            <GoogleLogin
                                onSuccess={handleSuccess}
                                onError={() => console.error('Login error')}
                            />
                        )}
                    </div>
                </div>
            </nav>

            <div className="mt-[72px]"></div>
        </>
    );
}