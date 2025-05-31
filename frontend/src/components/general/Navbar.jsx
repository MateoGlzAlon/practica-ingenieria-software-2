'use client';

import { GoogleLogin } from '@react-oauth/google';
import { useState, useEffect } from 'react';
import Link from "next/link";
import { Landmark } from "lucide-react";
import getUserIdFromLocalStorage from '@/hooks/getUserIdAuth';
import { useWallet } from '@/hooks/walletContext';
import logInUser from '@/api/post/postLogInUser';
import { useLoggedIn } from '@/hooks/loggedInContext';

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
                console.log("Login successful");
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
            setLoggedIn(false);
        } catch (error) {
            console.error('Logout error:', error);
        }
    }

    return (
        <>
            <nav className="fixed top-0 left-0 w-full bg-white border-b border-gray-300 z-50 px-6 py-4 flex justify-between items-center shadow-sm">
                <div className="w-1/3" />

                <Link href="/" className="flex justify-center w-1/3">
                    <div className="flex items-center text-2xl font-bold text-black hover:cursor-pointer">
                        <Landmark size={28} />
                        <span className="pl-2 text-2xl">Stoa</span>
                    </div>
                </Link>

                <div className="w-1/3 flex justify-end items-center gap-3">
                    {loggedIn ? (
                        <>
                        {wallet !== null && (
                            <span className="text-gray-800 font-medium mr-2">
                            Wallet {wallet} €
                            </span>
                        )}
                            <Link
                                href="/profile"
                                className="px-4 py-2 rounded-xl bg-blue-100 text-blue-700 hover:bg-blue-200 transition-all shadow-sm font-medium"
                            >
                                Profile
                            </Link>

                            <button
                                onClick={handleLogout}
                                className="px-4 py-2 rounded-xl bg-red-100 text-red-700 hover:bg-red-200 transition-all shadow-sm font-medium"
                            >
                                Log out
                            </button>
                        </>
                    ) : (
                        <GoogleLogin
                            onSuccess={handleSuccess}
                            onError={() => console.error('Login error')}
                        />
                    )}
                </div>
            </nav>

            <div className="mt-[72px]"></div>
        </>
    );
}