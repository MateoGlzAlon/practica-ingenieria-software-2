import { DATA } from "@/app/data"
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';

export default async function logInUser(credentialResponse) {
    const token = credentialResponse.credential;
    const decoded = jwtDecode(token);

    const googleUser = {
        email: decoded.email,
        username: decoded.name,
        avatarUrl: decoded.picture,
    };
    try {
        const response = await axios.post(`${DATA.apiURL}/auth/google`, googleUser);

        console.log('Response from /auth/google:', response.data);
        localStorage.setItem('userId', response.data.id);
        localStorage.setItem('userRole', response.data.role);
        localStorage.setItem('avatar', response.data.avatarUrl);
        console.log('Login successfully:', response.data.id);

        return true
    } catch (error) {
        console.error('Error authenticating with the backend', error);
        return false
    }
}