import axios from 'axios';
import { jwtDecode } from 'jwt-decode';


export default function getUserIdFromLocalStorage() {

  if (typeof window !== 'undefined' && localStorage) {
    return localStorage.getItem('userId');
  }
  return null;
};


