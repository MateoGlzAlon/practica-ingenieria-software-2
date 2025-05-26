'use client';

import { GoogleLogin } from '@react-oauth/google';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';

/*************  ✨ Windsurf Command ⭐  *************/
/**
 * Handles successful authentication by sending the Google credential to the backend
 * for verification and obtaining a backend token. The backend token is stored in 
 * local storage for future authentication. Logs success or error messages.
 *
 * @param {Object} credentialResponse - The response object containing Google's credential.
 * @param {string} credentialResponse.credential - The credential token from Google.
 */

/*******  a6a01a4c-7e0f-45bc-96c6-a0c6959de9dd  *******/
export default function LoginPage() {
/*************  ✨ Windsurf Command ⭐  *************/
/**
 * Handles the successful Google authentication process by decoding the credential
 * response, extracting user information, and sending it to the backend for further
 * verification. If the backend responds with a token, it is stored in localStorage.
 * Logs success or error messages.
 *
 * @param {Object} credentialResponse - The response object from Google containing the credential.
 * @param {string} credentialResponse.credential - The credential token from Google.
 */

/*******  b172c1e6-795d-4eab-8792-4e2b7ca96507  *******/
  const handleSuccess = async (credentialResponse) => {
    const token = credentialResponse.credential;
    const decoded = jwtDecode(token);

    const googleUser = {
      email: decoded.email,
      username: decoded.given_name,
      avatarUrl: decoded.picture,
    };
    try {
      const response = await axios.post('http://localhost:8080/auth/google', googleUser);
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