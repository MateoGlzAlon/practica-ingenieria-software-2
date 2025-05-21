import { useState } from "react";
import { ArrowUp, ArrowDown, Bookmark } from "lucide-react";
import Link from "next/link"
import { format } from 'date-fns';
import createPostVotes from "@/api/post/postCreatePostVote";
import getIsVoted from "@/api/getIsVoted";



export default function Post({ postData }) {

    const userId = 1

    const [votedStatus, setVotedStatus] = useState()

    async function handleVote() {

        // TODO : VOTE FUNCTION

        try {
            const data = await createPostVotes(userId, postData.id) // TODO : GET USERID FROM CONTEXT
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

        <div className="bg-white rounded-lg shadow-md hover:shadow-xl transition-shadow duration-300 p-6 flex flex-col gap-6 mb-4">

            <div className="flex-1">
                <h2 className="text-2xl font-semibold text-gray-900 mb-4">{postData.title}</h2>

                <Link href={`/post/${postData.id}`} >
                    <div className="w-full h-auto bg-gray-200 rounded-lg overflow-hidden">
                        <img
                            src={postData.imageURL}
                            className="w-full h-full object-cover"
                            alt="Post Image"
                        />
                    </div>
                </Link>

                {/* TODO SHORT VERSION OF THE CONTENT (WE CAN JUST TAKE THE 100 FIRST CHARACTERS)*/}
                <p className="text-gray-700 mb-4 line-clamp-3 py-4">{postData.content}</p>

                <div className=" flex flex-row justify-between">

                    <div className="flex flex-row w-1/4 items-center justify-between ">
                        <button
                            onClick={() => handleVote()}
                            className={`${votedStatus ? "text-green-600" : ""} hover:text-pink-500`}
                        >
                            <ArrowUp size={28} />
                        </button>
                        <span className="font-semibold text-lg text-gray-900">{postData.likes}</span>
                        <button
                            onClick={() => { }}
                            className="text-gray-400 hover:text-yellow-500"
                        >
                            <Bookmark size={28} />
                        </button>
                    </div>

                    <div className="flex justify-between items-center text-sm text-gray-500 ">
                        <div className="flex items-center gap-2">
                            <span>{postData.commentCount || "N/A"} {postData.commentCount === 1 ? "comment" : "comments"}</span>
                        </div>
                        <div className="flex items-center gap-2  ml-2">
                            <span className="text-gray-600">{postData.authorUsername || "N/A"}</span>
                            <span>•</span>
                            <span className="text-gray-400 text-xs">{format(new Date(postData.createdAt), 'dd/MM/yyyy') || "N/A"}</span>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    );
}
