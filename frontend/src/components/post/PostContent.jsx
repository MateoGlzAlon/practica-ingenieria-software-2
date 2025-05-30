import { ArrowDown, ArrowUp, MessageSquare } from "lucide-react"
import { mockData } from "@/app/mockData"
import { useState } from 'react'
import MarkdownRenderer from "@/components/general/MarkDownRenderer"
import createPostVotes from "@/api/post/postCreatePostVote";
import getIsVoted from "@/api/getIsVoted";
import ProjectImageCarousel from "@/components/post/ProjectImageCarousel"


export default function PostContent({ questionVotes, setQuestionVotes, showComments, setShowComments, postData, userId }) {

    const [votedStatus, setVotedStatus] = useState()

    async function handleVote() {

        try {
            const data = await createPostVotes(userId, postData.id)
            const voted = await getIsVoted(userId, postData.id)
            setVotedStatus(voted)
            console.log("Post data", data)

            console.log("Postdata.voted ", postData)

        } catch (error) {
            console.error("Error voting:", error)
        }

        console.log("votedStatus", votedStatus)

    }


    return (
        <div className="bg-white p-6 border border-gray-200 rounded-md">
            <div className="flex gap-4">
                <div className="flex flex-col items-center">
                    <button
                        onClick={() => handleVote()}
                        className="text-gray-400 hover:text-orange-500 transition"
                        aria-label="Upvote"
                    >
                        <ArrowUp
                            size={32}
                            className={`${votedStatus ? "text-green-600" : ""} hover:text-pink-500`}
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
                            <div className="flex space-x-4" />

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

