'use client';

import { GoogleLogin } from '@react-oauth/google';
import { useState, useEffect } from 'react';
import Link from "next/link";
import { Landmark } from "lucide-react";
import getUserIdFromLocalStorage from '@/hooks/getUserIdAuth';
import logInUser from '@/api/post/postLogInUser';

export default function Navbar() {

    const [userIdLS, setUserIdLS] = useState(null);
    const [loggedIn, setLoggedIn] = useState(false);

    useEffect(() => {
        const id = getUserIdFromLocalStorage();
        setUserIdLS(id);
    }, []);


    async function handleSuccess(credentialResponse) {
        try {
            await logInUser(credentialResponse);
            setLoggedIn(true);
        } catch (error) {
            console.error('Error authenticating with the backend', error);
        }
    };

    async function handleLogout() {
        try {
            localStorage.setItem('userId', null);
            localStorage.setItem('userRole', null);
            setLoggedIn(false);
        } catch (error) {
            console.error('Error authenticating with the backend', error);
        }

    };

    return (
        <>
            <nav className="fixed top-0 left-0 w-full bg-white border-b-[0.5px] border-gray-500 z-50 px-6 py-4 flex justify-between items-center">
                <div className='w-1/3' />

                <Link href="/" className="flex justify-center w-1/3">
                    <div className="flex items-center text-2xl font-bold text-black hover:cursor-pointer">
                        <Landmark size={28} />
                        <span className="pl-2 text-2xl">Stoa</span>
                    </div>
                </Link>

                <div className='w-1/3 flex justify-end'>
                    {loggedIn ?
                        <>
                            <Link
                                href="/profile"
                                className='mr-4 px-4 py-2 bg-gray-200 rounded'
                            >
                                Perfil
                            </Link>

                            <button
                                onClick={handleLogout}
                                className='mr-4 px-4 py-2 bg-gray-200 rounded'
                            >
                                Log out
                            </button>
                        </>
                        :
                        <GoogleLogin
                            onSuccess={handleSuccess}
                            onError={() => console.error('Login error')}
                        />
                    }
                </div>


            </nav>

            <div className="mt-[72px]"></div>
        </>
    );
}
