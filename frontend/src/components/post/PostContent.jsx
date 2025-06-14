import { ArrowDown, ArrowUp, MessageSquare } from "lucide-react"
import { useState, useEffect } from 'react'
import MarkdownRenderer from "@/components/general/MarkDownRenderer"
import createPostVotes from "@/api/post/postCreatePostVote";
import getIsVoted from "@/api/getIsVoted";
import ProjectImageCarousel from "@/components/post/ProjectImageCarousel"
import getUserRoleFromLocalStorage from "@/hooks/getUserRoleAuth";
import { useLoggedIn } from "@/hooks/loggedInContext";
import deletePost from "@/api/delete/deletePost";
import Router from "next/router";
import { toast } from 'sonner'


export default function PostContent({ questionVotes, setQuestionVotes, showComments, setShowComments, postData, userId, setShouldRedirect }) {

    const [votedStatus, setVotedStatus] = useState()
    const [userRoleLS] = useState(getUserRoleFromLocalStorage())
    const {loggedIn, setLoggedIn} = useLoggedIn()

    useEffect(() => {
        if (!userId || !postData.id) return

        let mounted = true
            ; (async () => {
                try {
                    const voted = await getIsVoted(userId, postData.id)
                    if (mounted) setVotedStatus(voted)
                } catch (err) {
                    console.error('Error checking voted status:', err)
                }
            })()

        return () => {
            mounted = false
        }
    }, [userId, postData.id])

    async function handleVote() {

        if (!userId) return;

        try {
            const data = await createPostVotes(userId, postData.id)
            const voted = await getIsVoted(userId, postData.id)
            setVotedStatus(voted)
            setQuestionVotes(prev => prev + (voted ? 1 : -1));

        } catch (error) {
            console.error("Error voting:", error)
        }

    }

    async function handleDeletePost(postId) {
        if (!userId) return;
        
        try {
            await deletePost(postId);

            toast.success("Post deleted successfully");

            setShouldRedirect(true);
        } catch (error) {
            console.error("Error borrando el post:", error);
        }
    }


    return (
        <div className="bg-white p-6 border border-gray-200 rounded-md">
            <div className="flex gap-4">
                <div className="flex flex-col items-center">
                    <button
                        onClick={() => handleVote()}
                        className={
                            votedStatus
                                ? "text-orange-500 transition"
                                : "text-gray-400 hover:text-orange-500 transition"
                        }
                        aria-label="Upvote"
                    >
                        <ArrowUp
                            size={32}
                            className="hover:text-green-700"
                        />
                    </button>
                    <span className="text-xl font-bold my-2 text-gray-700">{questionVotes}</span>

                </div>

                <div className="flex-1">
                    <div className="prose max-w-none">

                        <div className="prose max-w-none">
                            <MarkdownRenderer content={postData.content} />
                        </div>


                        <div className="bg-gray-50 rounded-md border border-gray-200 mb-6 w-full max-w-4xl mx-auto">
                            <ProjectImageCarousel projectImages={postData.postImages} />
                        </div>





                        <div className="flex justify-between items-center pt-4 border-t border-gray-200">
                            
                            <div className="flex space-x-4 items-center">
                                {loggedIn && userRoleLS === "ADMIN" && (
                                    <button
                                        onClick={() => handleDeletePost(postData.id)}
                                        className="px-2 py-1 text-sm border-2 border-red-500 text-red-500 hover:bg-red-200 transition"
                                    >
                                         ❌ Delete Post
                                    </button>
                                )}
                            </div>

                            <div className="flex items-center bg-blue-50 p-2 rounded-md ">
                                <img
                                    src={postData.authorProfilePicture || "https://placehold.co/600x400?text=Placehol"}
                                    alt="User avatar"
                                    className="w-8 h-8 rounded-full mr-2"
                                />
                                <div className="text-sm">
                                    <div className="font-medium text-blue-600">{postData.author || "N/A"}</div>
                                    <div className="text-gray-500">Created {new Date(postData.date).toLocaleDateString("en-GB") || "N/A"}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

