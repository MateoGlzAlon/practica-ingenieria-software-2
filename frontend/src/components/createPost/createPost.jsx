"use client"

import { useState, useRef, useEffect } from "react"
import { Upload, X, Search, ChevronDown } from "lucide-react"
import { DATA } from "@/app/data"
import { handle } from "express/lib/application"

export default function CreatePost() {
    const [formData, setFormData] = useState({
        title: "",
        content: "",
        tag: "",
        images: [],
    })
    const [isFormVisible, setIsFormVisible] = useState(false)

    const [isTagDropdownOpen, setIsTagDropdownOpen] = useState(false)
    const [tagSearchQuery, setTagSearchQuery] = useState("")
    const tagDropdownRef = useRef(null)

    // TODO: Fetch tags from API
    const availableTags = DATA.tags

    const filteredTags = tagSearchQuery
        ? availableTags.filter((tag) => tag.toLowerCase().includes(tagSearchQuery.toLowerCase()))
        : availableTags

    useEffect(() => {
        function handleClickOutside(event) {
            if (tagDropdownRef.current && !tagDropdownRef.current.contains(event.target)) {
                setIsTagDropdownOpen(false)
            }
        }

        document.addEventListener("mousedown", handleClickOutside)
        return () => {
            document.removeEventListener("mousedown", handleClickOutside)
        }
    }, [])

    const handleChange = (e) => {
        const { name, value } = e.target
        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }))
    }

    const selectTag = (tag) => {
        setFormData((prev) => ({
            ...prev,
            tag,
        }))
        setIsTagDropdownOpen(false)
        setTagSearchQuery("")
    }

    const handleImageChange = (e) => {
        const files = Array.from(e.target.files)
        if (files.length > 0) {
            const newImages = files.map((file) => ({
                file,
                preview: URL.createObjectURL(file),
            }))

            setFormData((prev) => ({
                ...prev,
                images: [...prev.images, ...newImages],
            }))
        }
    }

    const removeImage = (indexToRemove) => {
        setFormData((prev) => ({
            ...prev,
            images: prev.images.filter((_, index) => index !== indexToRemove),
        }))
    }

    const handleSubmit = (e) => {
        e.preventDefault()
        console.log("Post data:", formData)


        setFormData({
            title: "",
            content: "",
            tag: "",
            images: [],
        })
    }

    return (
        <div className="bg-white rounded-lg shadow-md p-6 mb-4">
            <div className="flex justify-between items-center mb-6">
                <h2 className="text-2xl font-semibold text-gray-900">Create New Post</h2>
                <button
                    type="button"
                    onClick={() => setIsFormVisible(!isFormVisible)}
                    className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition-colors"
                >
                    {isFormVisible ? "Close" : "Create post"}
                </button>
            </div>

            {isFormVisible && (
                <form onSubmit={handleSubmit} className="space-y-6">
                    {/* Title input */}
                    <div>
                        <label htmlFor="title" className="block text-sm font-medium text-gray-700 mb-1">
                            Title
                        </label>
                        <input
                            type="text"
                            id="title"
                            name="title"
                            value={formData.title}
                            onChange={handleChange}
                            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                            placeholder="What's your post about?"
                            required
                        />
                    </div>

                    {/* Content textarea */}
                    <div>
                        <label htmlFor="content" className="block text-sm font-medium text-gray-700 mb-1">
                            Content
                        </label>
                        <textarea
                            id="content"
                            name="content"
                            value={formData.content}
                            onChange={handleChange}
                            rows={6}
                            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                            placeholder="Share your thoughts..."
                            required
                        />
                    </div>

                    {/* Searchable Tag select */}
                    <div>
                        <label htmlFor="tag" className="block text-sm font-medium text-gray-700 mb-1">
                            Tag
                        </label>
                        <div className="relative" ref={tagDropdownRef}>
                            <div
                                className="flex items-center justify-between w-full px-3 py-2 border border-gray-300 rounded-md cursor-pointer"
                                onClick={() => setIsTagDropdownOpen(!isTagDropdownOpen)}
                            >
                                <div className="flex-1 flex items-center">
                                    {isTagDropdownOpen ? (
                                        <input
                                            type="text"
                                            value={tagSearchQuery}
                                            onChange={(e) => setTagSearchQuery(e.target.value)}
                                            placeholder="Search tags..."
                                            className="w-full outline-none"
                                            onClick={(e) => e.stopPropagation()}
                                            autoFocus
                                        />
                                    ) : (
                                        <span className={formData.tag ? "text-gray-900" : "text-gray-400"}>
                                            {formData.tag || "Select a tag"}
                                        </span>
                                    )}
                                </div>
                                <div className="flex items-center">
                                    {isTagDropdownOpen ? (
                                        <Search className="h-4 w-4 text-gray-400" />
                                    ) : (
                                        <ChevronDown className="h-4 w-4 text-gray-400" />
                                    )}
                                </div>
                            </div>

                            {isTagDropdownOpen && (
                                <div className="absolute z-10 w-full mt-1 bg-white border border-gray-300 rounded-md shadow-lg max-h-60 overflow-auto">
                                    {filteredTags.length > 0 ? (
                                        filteredTags.map((tag) => (
                                            <div
                                                key={tag}
                                                className={`px-3 py-2 cursor-pointer hover:bg-gray-100 ${formData.tag === tag ? "bg-blue-50 text-blue-600" : ""
                                                    }`}
                                                onClick={() => selectTag(tag)}
                                            >
                                                {tag}
                                            </div>
                                        ))
                                    ) : (
                                        <div className="px-3 py-2 text-gray-500">No tags found</div>
                                    )}
                                </div>
                            )}
                        </div>
                    </div>

                    {/* Image upload */}
                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-1">Images</label>
                        {formData.images.length > 0 && (
                            <div className="grid grid-cols-2 md:grid-cols-3 gap-4 mb-4">
                                {formData.images.map((image, index) => (
                                    <div key={index} className="relative">
                                        <img
                                            src={image.preview || "/placeholder.svg"}
                                            alt={`Preview ${index + 1}`}
                                            className="w-full h-40 object-cover rounded-lg"
                                        />
                                        <button
                                            type="button"
                                            onClick={() => removeImage(index)}
                                            className="absolute top-2 right-2 bg-white rounded-full p-1 shadow-md hover:bg-gray-100"
                                        >
                                            <X className="h-5 w-5 text-gray-700" />
                                        </button>
                                    </div>
                                ))}
                            </div>
                        )}
                        <div className="border-2 border-dashed border-gray-300 rounded-lg p-6 text-center">
                            <Upload className="h-10 w-10 text-gray-400 mx-auto mb-2" />
                            <p className="text-sm text-gray-500 mb-2">Click to upload images or drag and drop</p>
                            <input type="file" id="image" accept="image/*" onChange={handleImageChange} className="hidden" multiple />
                            <button
                                type="button"
                                onClick={() => document.getElementById("image").click()}
                                className="px-4 py-2 bg-gray-100 text-gray-700 rounded-md hover:bg-gray-200 text-sm"
                            >
                                Select Images
                            </button>
                        </div>
                    </div>

                    {/* Submit button */}
                    <div className="flex justify-end">
                        <button
                            type="submit"
                            className="px-6 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition-colors"
                        >
                            Post
                        </button>
                    </div>
                </form>
            )}
        </div>
    )
}
