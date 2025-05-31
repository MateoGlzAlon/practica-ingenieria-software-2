import { DATA } from "@/app/data"
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import { useWallet } from '@/hooks/walletContext'; 

export default async function logInUser(credentialResponse, updateWallet) {
    const token = credentialResponse.credential;
    const decoded = jwtDecode(token);

    const googleUser = {
        email: decoded.email,
        username: decoded.name,
        avatarUrl: decoded.picture,
    };
    try {
        const response = await axios.post(`${DATA.apiURL}/auth/google`, googleUser);

        localStorage.setItem('userId', response.data.id);
        localStorage.setItem('userRole', response.data.role);
        localStorage.setItem('avatar', response.data.avatarUrl);
        localStorage.setItem('walletBalance', response.data.wallet.toString());
        if (updateWallet) updateWallet(response.data.wallet);

        return true
    } catch (error) {
        console.error('Error authenticating with the backend', error);
        return false
    }
}