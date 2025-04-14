import { useEffect, useState } from "react";
import Post from "./Post";
import { mockData } from "@/app/mockData";
import getFeedPosts from "@/api/getLandingPageFeedPosts";
import Link from "next/link";

export default function MainFeed() {
    const [posts, setPosts] = useState([]);

    useEffect(() => {
        const fetchPosts = async () => {
            try {
                const data = await getFeedPosts();
                setPosts(data);
                console.log("Los posts son ", data)
            } catch (error) {
                console.error("Error fetching feed posts:", error);
            }
        };

        fetchPosts();
    }, []);

    return (
        <div className="flex-1 flex flex-col gap-4 px-4  border-r-2 border-black">
            <h1 className="text-xl font-bold text-center">For you page</h1>

            {posts.length > 0 ? (
                posts.map((post) => (
                    <Link href={`/post/${post.id}`}>
                        <Post key={post.id} postData={post} />
                    </Link>
                ))
            ) : (
                <p>Loading posts...</p>
            )}
        </div>
    );
}
