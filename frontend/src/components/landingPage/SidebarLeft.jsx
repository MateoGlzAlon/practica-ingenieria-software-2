"use client"

import getAllTags from "@/api/getAllTags"
import { useEffect, useState } from "react"
import { useTagFilter } from "@/hooks/tagsContext"

export default function SidebarLeft() {
    const [tags, setTags] = useState([])
    const [isDropdownOpen, setIsDropdownOpen] = useState(false)
    const [searchTerm, setSearchTerm] = useState("")
    const { selectedTags, setSelectedTags } = useTagFilter()

    useEffect(() => {
        const fetchTags = async () => {
            try {
                const data = await getAllTags()
                setTags(data.tags)

                // Set all tags as selected by default only if none are selected
                if (selectedTags.length === 0) {
                    setSelectedTags(data.tags)
                }
            } catch (error) {
                console.error("Error fetching tags:", error)
            }
        }
        fetchTags()
    }, [])

    const toggleTag = (tag) => {
        setSelectedTags((prev) => (prev.includes(tag) ? prev.filter((t) => t !== tag) : [...prev, tag]))
    }

    const removeTag = (tagToRemove) => {
        setSelectedTags((prev) => prev.filter((tag) => tag !== tagToRemove))
    }

    const clearAllTags = () => {
        setSelectedTags([])
    }

    const selectAllTags = () => {
        setSelectedTags(tags)
    }

    const handleDropdownToggle = () => {
        setIsDropdownOpen(!isDropdownOpen)
        if (isDropdownOpen) {
            setSearchTerm("") // Clear search when closing
        }
    }

    // Filter available tags based on search term
    const availableTags = tags.filter(
        (tag) => !selectedTags.includes(tag) && tag.toLowerCase().includes(searchTerm.toLowerCase()),
    )

    return (
        <div className="w-[20%] min-w-[200px] flex flex-col gap-6 sticky top-20 self-start">
            {/* Tags Section */}
            <div className="bg-white border border-gray-200 rounded-xl shadow-sm">
                <div className="p-4 border-b border-gray-100">
                    <div className="flex items-center justify-between mb-3">
                        <h3 className="font-semibold text-gray-800">Filter by Tags</h3>
                        <span className="text-xs text-gray-500 bg-gray-100 px-2 py-1 rounded-full">
                            {selectedTags.length} selected
                        </span>
                    </div>

                    {/* Custom Dropdown */}
                    <div className="relative">
                        <button
                            onClick={handleDropdownToggle}
                            className="w-full flex items-center justify-between px-3 py-2 bg-gray-50 border border-gray-200 rounded-lg hover:bg-gray-100 transition-colors duration-200 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                        >
                            <span className="text-sm text-gray-600">
                                {tags.filter((tag) => !selectedTags.includes(tag)).length > 0 ? "Add tags..." : "All tags selected"}
                            </span>
                            <svg
                                className={`w-4 h-4 text-gray-400 transition-transform duration-200 ${isDropdownOpen ? "rotate-180" : ""}`}
                                fill="none"
                                stroke="currentColor"
                                viewBox="0 0 24 24"
                            >
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 9l-7 7-7-7" />
                            </svg>
                        </button>

                        {/* Dropdown Menu */}
                        {isDropdownOpen && (
                            <div className="absolute top-full left-0 right-0 mt-1 bg-white border border-gray-200 rounded-lg shadow-lg z-10 max-h-64 overflow-hidden">
                                {/* Search Input */}
                                <div className="p-3 border-b border-gray-100">
                                    <div className="relative">
                                        <input
                                            type="text"
                                            placeholder="Search tags..."
                                            value={searchTerm}
                                            onChange={(e) => setSearchTerm(e.target.value)}
                                            className="w-full pl-8 pr-8 py-2 text-sm border border-gray-200 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                                            autoFocus
                                        />
                                        <svg
                                            className="absolute left-2.5 top-2.5 w-4 h-4 text-gray-400"
                                            fill="none"
                                            stroke="currentColor"
                                            viewBox="0 0 24 24"
                                        >
                                            <path
                                                strokeLinecap="round"
                                                strokeLinejoin="round"
                                                strokeWidth={2}
                                                d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
                                            />
                                        </svg>
                                        {searchTerm && (
                                            <button
                                                onClick={() => setSearchTerm("")}
                                                className="absolute right-2 top-2 p-0.5 text-gray-400 hover:text-gray-600 transition-colors duration-150"
                                            >
                                                <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                                                </svg>
                                            </button>
                                        )}
                                    </div>
                                </div>

                                {/* Tags List */}
                                <div className="max-h-48 overflow-y-auto">
                                    {availableTags.length > 0 ? (
                                        <>
                                            {!searchTerm && (
                                                <div className="p-2 border-b border-gray-100">
                                                    <button
                                                        onClick={() => {
                                                            selectAllTags()
                                                            setIsDropdownOpen(false)
                                                        }}
                                                        className="w-full text-left px-2 py-1 text-xs text-blue-600 hover:bg-blue-50 rounded transition-colors duration-150"
                                                    >
                                                        Select all remaining ({tags.filter((tag) => !selectedTags.includes(tag)).length})
                                                    </button>
                                                </div>
                                            )}
                                            {availableTags.map((tag) => (
                                                <button
                                                    key={tag}
                                                    onClick={() => {
                                                        toggleTag(tag)
                                                        setSearchTerm("")
                                                        setIsDropdownOpen(false)
                                                    }}
                                                    className="w-full text-left px-3 py-2 text-sm text-gray-700 hover:bg-gray-50 transition-colors duration-150 border-b border-gray-50 last:border-b-0 flex items-center justify-between group"
                                                >
                                                    <span>{tag}</span>
                                                    <svg
                                                        className="w-4 h-4 text-gray-400 opacity-0 group-hover:opacity-100 transition-opacity duration-150"
                                                        fill="none"
                                                        stroke="currentColor"
                                                        viewBox="0 0 24 24"
                                                    >
                                                        <path
                                                            strokeLinecap="round"
                                                            strokeLinejoin="round"
                                                            strokeWidth={2}
                                                            d="M12 6v6m0 0v6m0-6h6m-6 0H6"
                                                        />
                                                    </svg>
                                                </button>
                                            ))}
                                        </>
                                    ) : (
                                        <div className="p-4 text-center">
                                            {searchTerm ? (
                                                <div className="text-sm text-gray-500">
                                                    <div className="mb-2">No tags found for "{searchTerm}"</div>
                                                    <button
                                                        onClick={() => setSearchTerm("")}
                                                        className="text-blue-500 hover:text-blue-700 transition-colors duration-150"
                                                    >
                                                        Clear search
                                                    </button>
                                                </div>
                                            ) : (
                                                <div className="text-sm text-gray-500">All tags are selected</div>
                                            )}
                                        </div>
                                    )}
                                </div>
                            </div>
                        )}
                    </div>
                </div>

                {/* Selected Tags */}
                <div className="p-4">
                    <div className="flex items-center justify-between mb-3">
                        <span className="text-sm font-medium text-gray-700">Selected Tags</span>
                        {selectedTags.length > 0 && (
                            <button
                                onClick={clearAllTags}
                                className="text-xs text-red-500 hover:text-red-700 transition-colors duration-150"
                            >
                                Clear all
                            </button>
                        )}
                    </div>

                    {selectedTags.length > 0 ? (
                        <div className="h-96 border-2 border-dashed border-gray-200 rounded-lg p-3 overflow-y-auto flex flex-wrap items-start content-start gap-2">
                            {selectedTags.map((tag) => (
                                <div
                                    key={tag}
                                    className="flex items-center px-3 py-1 bg-blue-100 text-blue-800 text-sm rounded-full border border-blue-200 group hover:bg-blue-200 transition-colors duration-150 shrink-0"
                                >
                                    <span>{tag}</span>
                                    <button
                                        onClick={() => removeTag(tag)}
                                        className="ml-1 text-blue-600 hover:text-blue-800 transition-colors duration-150"
                                    >
                                        <svg className="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                                        </svg>
                                    </button>
                                </div>
                            ))}
                        </div>
                    ) : (
                        <div className="h-96 text-sm text-gray-500 text-center py-4 border-2 border-dashed border-gray-200 rounded-lg">
                            No tags selected
                        </div>
                    )}

                </div>
            </div>
        </div>
    )
}
