import axios from 'axios';
import { jwtDecode } from 'jwt-decode';


export default function getUserIdFromLocalStorage() {

  if (typeof window !== 'undefined' && localStorage) {
    console.log('getUserIdAuth4', localStorage.getItem('userId'));
    return localStorage.getItem('userId');
  }
  return null;
};


