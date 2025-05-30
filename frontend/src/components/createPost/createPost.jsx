"use client"

import { useState, useRef, useEffect } from "react"
import { generatePostAI } from "@/utils/openai";
import { X, Search, ChevronDown, Plus, ImageIcon } from "lucide-react"
import createPost from "@/api/post/postCreatePost"
import { uploadFile } from "@/components/awsComponents/UploadImage"
import getUserIdFromLocalStorage from '@/hooks/getUserIdAuth';


export default function CreatePost() {

    const [userId] = getUserIdFromLocalStorage();


    console.log("zanahoria", userId);



    const [formData, setFormData] = useState({
        title: "",
        summary: "",
        content: "",
        //TODO
        tagId: 1,
        userId: userId,
        imageLinks: [],
    })
    const [isFormVisible, setIsFormVisible] = useState(false)
    const [uploadedImages, setUploadedImages] = useState([])
    const [isTagDropdownOpen, setIsTagDropdownOpen] = useState(false)
    const [tagSearchQuery, setTagSearchQuery] = useState("")
    const [isDragOver, setIsDragOver] = useState(false)
    const tagDropdownRef = useRef(null)
    const fileInputRef = useRef(null)

    const availableTags = [
        { id: 1, name: "Technology" },
        { id: 2, name: "Design" },
        { id: 3, name: "Programming" },
        { id: 4, name: "Tutorial" },
        { id: 5, name: "News" },
        { id: 6, name: "Review" },
        { id: 7, name: "Opinion" },
        { id: 8, name: "Guide" },
        { id: 9, name: "Tips" },
        { id: 10, name: "Discussion" },
    ]

    const filteredTags = tagSearchQuery
        ? availableTags.filter((tag) => tag.name.toLowerCase().includes(tagSearchQuery.toLowerCase()))
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

    async function fetchPostMeta(content) {
        const res = await fetch("/api/generate-meta", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ content }),
        });

        if (!res.ok) throw new Error("Failed to generate metadata");
        const { data, usage } = await res.json();

        console.log("🧠 AI Suggestion:", data);
        console.log("💰 Estimated cost:", `$${usage.totalCost.toFixed(6)}`);

        return data;
    }


    const handleChange = (e) => {
        const { name, value } = e.target
        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }))
    }

    const selectTag = (tagId, tagName) => {
        setFormData((prev) => ({
            ...prev,
            tagId: tagId,
        }))
        setIsTagDropdownOpen(false)
        setTagSearchQuery("")
    }

    const handleDragOver = (e) => {
        e.preventDefault()
        setIsDragOver(true)
    }

    const handleDragLeave = (e) => {
        e.preventDefault()
        setIsDragOver(false)
    }

    const handleDrop = (e) => {
        e.preventDefault()
        setIsDragOver(false)
        const files = Array.from(e.dataTransfer.files).filter((file) => file.type.startsWith("image/"))
        if (files.length > 0) {
            processImages(files)
        }
    }

    const handleImageChange = (e) => {
        const files = Array.from(e.target.files)
        if (files.length > 0) {
            processImages(files)
        }
    }

    const processImages = (files) => {
        const newImages = files.map((file) => ({
            file,
            preview: URL.createObjectURL(file),
        }));

        setUploadedImages((prev) => [...prev, ...newImages]);
    };



    const removeImage = (indexToRemove) => {
        setUploadedImages((prev) => prev.filter((_, index) => index !== indexToRemove))
        setFormData((prev) => ({
            ...prev,
            imageLinks: prev.imageLinks.filter((_, index) => index !== indexToRemove),
        }))
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log("Submitting post with formData:", formData);

        try {
            const uploadedImageLinks = await Promise.all(
                uploadedImages.map(async ({ file }) => {
                    const uploaded = await uploadFile(file);

                    console.log("Uploaded image:", uploaded);

                    return uploaded.url[0]; // Clean URL
                })
            );

            const finalPostData = {
                ...formData,
                imageLinks: uploadedImageLinks,
            };

            await createPost(finalPostData);

            setFormData({
                title: "",
                content: "",
                //TODO
                tagId: 1,
                userId: userId,
                imageLinks: [],
            });
            setUploadedImages([]);
            setIsFormVisible(false);
        } catch (error) {
            console.error("Error creating post:", error);
        }
    };


    const getTagNameById = (id) => {
        const tag = availableTags.find((tag) => tag.id === id)
        return tag ? tag.name : ""
    }

    return (
        <div className="bg-white rounded-2xl shadow-lg border border-gray-100 overflow-hidden">
            {/* Header */}
            <div className=" px-8 py-6 border-b border-gray-100">
                <div className="flex justify-between items-center">
                    <div>
                        <h2 className="text-2xl font-bold text-gray-900 mb-1">Create New Post</h2>
                        <p className="text-gray-600 text-sm">Share your thoughts with the community</p>
                    </div>
                    <button
                        type="button"
                        onClick={() => setIsFormVisible(!isFormVisible)}
                        className={`flex items-center gap-2 px-6 py-3 rounded-xl font-medium transition-all duration-200 ${isFormVisible
                            ? "bg-gray-100 text-gray-700 hover:bg-gray-200"
                            : "bg-blue-600 text-white hover:bg-blue-700 shadow-lg hover:shadow-xl"
                            }`}
                    >
                        {isFormVisible ? (
                            <>
                                <X className="w-4 h-4" />
                                Close
                            </>
                        ) : (
                            <>
                                <Plus className="w-4 h-4" />
                                Create Post
                            </>
                        )}
                    </button>
                </div>
            </div>

            {/* Form */}
            {isFormVisible && (
                <div className="p-8">
                    <form onSubmit={handleSubmit} className="space-y-8">
                        {/* Title Section */}
                        <div className="space-y-3">
                            <label htmlFor="title" className="block text-sm font-semibold text-gray-800">
                                Post Title
                            </label>
                            <input
                                type="text"
                                id="title"
                                name="title"
                                value={formData.title}
                                onChange={handleChange}
                                className="w-full px-4 py-3 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all duration-200 text-gray-900 placeholder-gray-400"
                                placeholder="What's your post about?"
                                required
                            />
                        </div>



                        {/* Summary Section */}
                        <div className="space-y-3">
                            <label htmlFor="content" className="block text-sm font-semibold text-gray-800">
                                Summary
                            </label>
                            <textarea
                                id="summary"
                                name="summary"
                                value={formData.summary}
                                onChange={handleChange}
                                rows={6}
                                className="w-full px-4 py-3 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all duration-200 text-gray-900 placeholder-gray-400 resize-none"
                                placeholder="Summary"
                                required
                            />
                        </div>

                        {/* Content Section */}
                        <div className="space-y-3">
                            <div className=" flex justify-between">
                                <label htmlFor="content" className=" flex items-center text-sm font-semibold text-gray-800">
                                    Content
                                </label>
                                <button
                                    type="button"
                                    onClick={async () => {
                                        try {
                                            if (formData.content.trim() === "") {
                                                throw new Error("Content is empty");
                                            }

                                            const result = await fetchPostMeta(formData.content);

                                            console.log("🧠 AI Suggestion:", result);

                                            setFormData((prev) => ({
                                                ...prev,
                                                title: result.title,
                                                summary: result.summary,
                                            }));

                                            console.log("2", formData)


                                            console.log("3")


                                            console.log("🧠 Suggested:", result);
                                        } catch (err) {
                                            alert("Failed to generate title/summary/tag.");
                                        }
                                    }}
                                    className="px-4 py-2 mt-2 bg-gray-100 border-2 border-gray-200 text-sm rounded-lg hover:bg-gray-200"
                                >
                                    ✨ Auto-generate Title and summary based on Content
                                </button>
                            </div>
                            <textarea
                                id="content"
                                name="content"
                                value={formData.content}
                                onChange={handleChange}
                                rows={6}
                                className="w-full px-4 py-3 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all duration-200 text-gray-900 placeholder-gray-400 resize-none"
                                placeholder="Share your thoughts, ideas, or story..."
                                required
                            />
                        </div>

                        {/* Tag Section */}
                        <div className="space-y-3">
                            <label htmlFor="tag" className="block text-sm font-semibold text-gray-800">
                                Category
                            </label>
                            <div className="relative" ref={tagDropdownRef}>
                                <div
                                    className={`flex items-center justify-between w-full px-4 py-3 border rounded-xl cursor-pointer transition-all duration-200 ${isTagDropdownOpen
                                        ? "border-blue-500 ring-2 ring-blue-500 ring-opacity-20"
                                        : "border-gray-200 hover:border-gray-300"
                                        }`}
                                    onClick={() => setIsTagDropdownOpen(!isTagDropdownOpen)}
                                >
                                    <div className="flex-1 flex items-center">
                                        {isTagDropdownOpen ? (
                                            <input
                                                type="text"
                                                value={tagSearchQuery}
                                                onChange={(e) => setTagSearchQuery(e.target.value)}
                                                placeholder="Search categories..."
                                                className="w-full outline-none text-gray-900 placeholder-gray-400"
                                                onClick={(e) => e.stopPropagation()}
                                                autoFocus
                                            />
                                        ) : (
                                            <span className="text-gray-900 font-medium">
                                                {getTagNameById(formData.tagId) || "Select a category"}
                                            </span>
                                        )}
                                    </div>
                                    <div className="flex items-center">
                                        {isTagDropdownOpen ? (
                                            <Search className="h-5 w-5 text-blue-500" />
                                        ) : (
                                            <ChevronDown
                                                className={`h-5 w-5 text-gray-400 transition-transform duration-200 ${isTagDropdownOpen ? "rotate-180" : ""
                                                    }`}
                                            />
                                        )}
                                    </div>
                                </div>

                                {isTagDropdownOpen && (
                                    <div className="absolute z-10 w-full mt-2 bg-white border border-gray-200 rounded-xl shadow-xl max-h-60 overflow-auto">
                                        {filteredTags.length > 0 ? (
                                            filteredTags.map((tag) => (
                                                <div
                                                    key={tag.id}
                                                    className={`px-4 py-3 cursor-pointer transition-colors duration-150 ${formData.tagId === tag.id
                                                        ? "bg-blue-50 text-blue-700 font-medium"
                                                        : "hover:bg-gray-50 text-gray-700"
                                                        }`}
                                                    onClick={() => selectTag(tag.id, tag.name)}
                                                >
                                                    {tag.name}
                                                </div>
                                            ))
                                        ) : (
                                            <div className="px-4 py-3 text-gray-500 text-center">No categories found</div>
                                        )}
                                    </div>
                                )}
                            </div>
                        </div>

                        {/* Images Section */}
                        <div className="space-y-4">
                            <label className="block text-sm font-semibold text-gray-800">Images</label>

                            {/* Image Preview Grid */}
                            {uploadedImages.length > 0 && (
                                <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 mb-6">
                                    {uploadedImages.map((image, index) => (
                                        <div key={index} className="relative group">
                                            <div className="aspect-square rounded-xl overflow-hidden bg-gray-100">
                                                <img
                                                    src={image.preview || "/placeholder.svg"}
                                                    alt={`Preview ${index + 1}`}
                                                    className="w-full h-full object-cover"
                                                />
                                            </div>
                                            <button
                                                type="button"
                                                onClick={() => removeImage(index)}
                                                className="absolute -top-2 -right-2 bg-red-500 text-white rounded-full p-1.5 shadow-lg hover:bg-red-600 transition-colors duration-200 opacity-0 group-hover:opacity-100"
                                            >
                                                <X className="h-4 w-4" />
                                            </button>
                                        </div>
                                    ))}
                                </div>
                            )}

                            {/* Upload Area */}
                            <div
                                className={`border-2 border-dashed rounded-xl p-8 text-center transition-all duration-200 ${isDragOver ? "border-blue-400 bg-blue-50" : "border-gray-300 hover:border-gray-400 hover:bg-gray-50"
                                    }`}
                                onDragOver={handleDragOver}
                                onDragLeave={handleDragLeave}
                                onDrop={handleDrop}
                            >
                                <div className="flex flex-col items-center space-y-4">
                                    <div className={`p-4 rounded-full ${isDragOver ? "bg-blue-100" : "bg-gray-100"}`}>
                                        <ImageIcon className={`h-8 w-8 ${isDragOver ? "text-blue-600" : "text-gray-400"}`} />
                                    </div>
                                    <div>
                                        <p className="text-lg font-medium text-gray-700 mb-1">
                                            {isDragOver ? "Drop your images here" : "Upload images"}
                                        </p>
                                        <p className="text-sm text-gray-500">Drag and drop or click to select files</p>
                                    </div>
                                    <input
                                        type="file"
                                        ref={fileInputRef}
                                        accept="image/*"
                                        onChange={handleImageChange}
                                        className="hidden"
                                        multiple
                                    />
                                    <button
                                        type="button"
                                        onClick={() => fileInputRef.current?.click()}
                                        className="px-6 py-2.5 bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200 transition-colors duration-200 font-medium"
                                    >
                                        Select Images
                                    </button>
                                </div>
                            </div>
                        </div>

                        {/* Submit Section */}
                        <div className="flex justify-end pt-6 border-t border-gray-100">
                            <button
                                type="submit"
                                className="px-8 py-3 bg-gradient-to-r from-blue-600 to-indigo-600 text-white rounded-xl font-semibold hover:from-blue-700 hover:to-indigo-700 transition-all duration-200 shadow-lg hover:shadow-xl transform hover:-translate-y-0.5"
                            >
                                Publish Post
                            </button>
                        </div>
                    </form>
                </div>
            )}
        </div>
    )
}
