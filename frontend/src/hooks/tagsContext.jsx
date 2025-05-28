"use client"
import { createContext, useContext, useState } from "react";

const TagFilterContext = createContext();

export const TagFilterProvider = ({ children }) => {
    const [selectedTags, setSelectedTags] = useState([]);

    return (
        <TagFilterContext.Provider value={{ selectedTags, setSelectedTags }}>
            {children}
        </TagFilterContext.Provider>
    );
};

export const useTagFilter = () => useContext(TagFilterContext);
