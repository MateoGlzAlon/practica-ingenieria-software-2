'use client';

import { GoogleLogin } from '@react-oauth/google';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import Link from "next/link";
import { Landmark } from "lucide-react";

export default function Navbar() {


    const handleSuccess = async (credentialResponse) => {
        const token = credentialResponse.credential;
        const decoded = jwtDecode(token);

        const googleUser = {
            email: decoded.email,
            username: decoded.name,
            avatarUrl: decoded.picture,
        };
        try {
            const response = await axios.post('http://localhost:8080/api/auth/google', googleUser);
            const backendToken = response.data.token;
            localStorage.setItem('token', backendToken);
            console.log('Login successfully:', backendToken);
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

                <Link href="/login" className='w-1/3 flex justify-end'>
                    <GoogleLogin
                        onSuccess={handleSuccess}
                        onError={() => console.log('Login error')}
                    />
                </Link>
            </nav>

            <div className="mt-[72px]"></div>
        </>
    );
}
