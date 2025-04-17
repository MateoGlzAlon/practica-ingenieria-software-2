"use client"

import { useEffect, useState } from "react"
import Post from "./Post"
import getFeedPosts from "@/api/getLandingPageFeedPosts"

export default function MainFeed() {
    const [posts, setPosts] = useState([])
    const [isLoading, setIsLoading] = useState(true)

    useEffect(() => {
        const fetchPosts = async () => {
            try {
                const data = await getFeedPosts()
                setPosts(data)
                setIsLoading(false)
            } catch (error) {
                console.error("Error fetching feed posts:", error)
                setIsLoading(false)
            }
        }

        fetchPosts()
    }, [])

    return (
        <div className="flex-1 flex flex-col gap-4 px-32 border-x-[1px] border-gray-300">
            <h1 className="text-xl font-bold text-center py-2">For you page</h1>

            {isLoading ? (
                <div className="space-y-4">
                    {[1, 2, 3].map((i) => (
                        <div key={i} className="bg-white p-4 rounded shadow animate-pulse">
                            <div className="h-5 bg-gray-200 rounded w-3/4 mb-3"></div>
                            <div className="h-4 bg-gray-200 rounded w-full mb-2"></div>
                            <div className="h-4 bg-gray-200 rounded w-2/3"></div>
                        </div>
                    ))}
                </div>
            ) : (
                posts.map((post) => (
                    <Post postData={post} key={post.id} />
                ))
            )}
        </div>
    )
}
