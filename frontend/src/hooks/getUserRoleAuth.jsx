export default function getUserRoleFromLocalStorage() {

    if (typeof window !== 'undefined' && localStorage) {
        return localStorage.getItem('userRole');
    }
    return null;
};


