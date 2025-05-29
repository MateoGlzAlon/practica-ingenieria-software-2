export default function getUserRoleFromLocalStorage() {

    if (typeof window !== 'undefined' && localStorage) {
        console.log('getUserIdAuthRole', localStorage.getItem('userRole'));
        return localStorage.getItem('userRole');
    }
    return null;
};


