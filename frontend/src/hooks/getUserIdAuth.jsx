import axios from 'axios';
import jwtDecode from 'jwt-decode';

const getUserIdAuth = async () => {
  try {

    const token = localStorage.getItem('token');
    if (!token) {
        //error, couldnt find token
        return;
    }

    const decoded = jwtDecode(token);
    const email = decoded.email;

    const response = await axios.get('http://localhost:8080/users/getId', {
        params: { email }
    });

    const userId = response.data;
    localStorage.setItem('userId', userId);
    
    

  } catch (error) {
    console.error('ERROR could not retreive the email:', error);
  }
};

export default getUserIdAuth;
