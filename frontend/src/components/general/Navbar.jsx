"use client";
import Link from "next/link"; 
import { useEffect, useState } from "react";
import { Landmark } from 'lucide-react';
import { useAuth } from "@/context/AuthContext";

export default function Navbar() {
    const {user, setUser, logout} = useAuth();

    const handleLogout = () => {
        logout();
        window.location.href = "/";
    };
    return (
        <>
            <nav className="fixed top-0 left-0 w-full bg-white border-b-[0.5px] border-gray-500 z-50 px-6 py-4 flex justify-between items-center">
                <div />

                <Link href="/" className="flex items-center">
                    <div className="flex items-center text-2xl font-bold text-black  hover:cursor-pointer">
                        <Landmark size={28} />
                        <span className="pl-2 text-2xl">Stoa</span>
                    </div>
                </Link>

                {user ? (
                    <div className="flex items-center gap-4">
                        {user.avatarUrl && (
                            <img
                                src={user.avatarUrl}
                                alt="User Avatar"
                                className="w-8 h-8 rounded-full"
                            />
                        )}
                        <Link href="/profile">
                            <button className="bg-orange-500 text-white px-4 py-2 rounded hover:bg-orange-600 transition">
                                Perfil
                            </button>
                        </Link>
                        <button
                            onClick={handleLogout}
                            className="bg-gray-400 text-white px-4 py-2 rounded hover:bg-gray-700 transition"
                        >
                            Logout
                        </button>
                    </div>
                ) : (
                    <Link href="/login">
                        <button className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition">
                            Login
                        </button>
                    </Link>
                )}
            </nav>

            <div className="mt-[72px]"></div>
        </>
    );
}
