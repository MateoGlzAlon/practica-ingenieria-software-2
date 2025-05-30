"use client";
import { createContext, useContext, useEffect, useState } from "react";

const LoggedInContext = createContext();

export const LoggedInProvider = ({ children }) => {
    const [loggedIn, setLoggedIn] = useState(false);

    useEffect(() => {
        const isLoggedIn = localStorage.getItem("userId") !== null;
        setLoggedIn(isLoggedIn);
    }, []);

    return (
        <LoggedInContext.Provider value={{ loggedIn, setLoggedIn }}>
            {children}
        </LoggedInContext.Provider>
    );
};

export const useLoggedIn = () => useContext(LoggedInContext);
