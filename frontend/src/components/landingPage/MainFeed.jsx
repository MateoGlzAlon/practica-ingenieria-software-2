"use client"

import { useEffect, useState } from "react"
import Post from "./Post"
import getFeedPosts from "@/api/getLandingPageFeedPosts"
import CreatePost from "@/components/createPost/createPost"
import getUserIdFromLocalStorage from '@/hooks/getUserIdAuth';
import { useTagFilter } from "@/hooks/tagsContext"

export default function MainFeed() {

    const userId = getUserIdFromLocalStorage();

    const [posts, setPosts] = useState([])
    const [page, setPage] = useState(0)
    const [isLoading, setIsLoading] = useState(true)
    const [isLoadingMore, setIsLoadingMore] = useState(false)
    const [hasMore, setHasMore] = useState(true)
    const PAGE_SIZE = 5

    const { selectedTags } = useTagFilter()

    const fetchPosts = async (pageNumber, tags = []) => {

        console.log("Selected Tags", selectedTags)

        try {
            const data = await getFeedPosts(pageNumber, PAGE_SIZE, userId, tags)
            if (data.length < PAGE_SIZE) {
                setHasMore(false)
            }

            if (pageNumber === 0) {
                setPosts(data)
            } else {
                setPosts(prev => [...prev, ...data])
            }

            console.log("Mainfeed data", data)
        } catch (error) {
            console.error("Error fetching feed posts:", error)
        } finally {
            setIsLoading(false)
            setIsLoadingMore(false)
        }
    }

    // Initial + tag-based fetch
    useEffect(() => {
        setPage(0)
        setHasMore(true)
        setIsLoading(true)
        fetchPosts(0, selectedTags)
    }, [selectedTags])

    const loadMore = () => {
        const nextPage = page + 1
        setPage(nextPage)
        setIsLoadingMore(true)
        fetchPosts(nextPage, selectedTags)
    }
    return (
        <div className="flex-1 flex flex-col gap-4 px-16 border-x-[1px] border-gray-300 py-2">
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
                <>
                    <CreatePost />

                    {posts.map((post) => (
                        <Post postData={post} key={post.id} userId={userId} />
                    ))}
                    {hasMore && (
                        <button
                            onClick={loadMore}
                            disabled={isLoadingMore}
                            className="bg-blue-500 text-white px-4 py-2 rounded self-center mt-4 hover:bg-blue-600 disabled:opacity-50"
                        >
                            {isLoadingMore ? "Loading..." : "Load More"}
                        </button>
                    )}
                </>
            )}
        </div>
    )
}
