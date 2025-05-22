'use client';

import { GoogleLogin } from '@react-oauth/google';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import { useAuth } from '@/context/AuthContext';

export default function LoginPage() {
  const { login, setUser } = useAuth();
  const router = useRouter();
/*************  ✨ Windsurf Command ⭐  *************/
  /**
   * Handles the successful login with Google
   * @param {Object} credentialResponse - The response from Google containing the credential
   * @returns {Promise<void>}
   */
/*******  0e7f095a-7198-454c-ac31-30ce369056ab  *******/
  const handleSuccess = async (credentialResponse) => {
    const token = credentialResponse.credential;

    try {
      const response = await axios.post('http://localhost:8080/auth/google', {
        credential: token,
      });

      const backendToken = response.data?.token;
      const userData = response.data?.user;
      if (!userData) throw new Error('User data not found in response');
      if (!backendToken) throw new Error('Token not found in response');
      login(backendToken, userData);
      setUser(userData);
      router.push('/'); 
      console.log('Login successfully:', backendToken);
      localStorage.setItem('token', backendToken);
      localStorage.setItem('user', JSON.stringify(userData));
      
    } catch (error) {
      console.error('Error authenticating with the backend', error);
    }
  };

  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <h1 className="text-2xl font-semibold mb-4">Sign in with Google</h1>
      <GoogleLogin
        onSuccess={handleSuccess}
        onError={() => console.log('Login error')}
      />
    </div>
  );
}
