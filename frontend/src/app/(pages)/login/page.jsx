'use client';

import { GoogleLogin } from '@react-oauth/google';
import axios from 'axios';


export default function LoginPage() {
  const handleSuccess = async (credentialResponse) => {
    const token = credentialResponse.credential;

    try {
      const response = await axios.post('http://localhost:8080/auth/google', {
        token: token,
      });

      const backendToken = response.data.token;
      localStorage.setItem('token', backendToken);
      console.log('Login successfully:', backendToken);
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
