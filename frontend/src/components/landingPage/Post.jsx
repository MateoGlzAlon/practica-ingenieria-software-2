import { useState, useEffect } from "react";
import { ArrowUp, ArrowDown, Bookmark } from "lucide-react";
import Link from "next/link"
import { format } from 'date-fns';
import createPostVotes from "@/api/post/postCreatePostVote";
import getIsVoted from "@/api/getIsVoted";
import MarkdownRenderer from "@/components/general/MarkDownRenderer"
import getUserIdFromLocalStorage from "@/hooks/getUserIdAuth";


export default function Post({ postData, userId }) {

    const [votedStatus, setVotedStatus] = useState(false)
    const [votes, setVotes] = useState(postData.votes)
    const [userIdLS, setUserIdLS] = useState(getUserIdFromLocalStorage());

    useEffect(() => {
        if (!userIdLS) return;

        let mounted = true;

        (async () => {
            try {
                const already = await getIsVoted(userIdLS, postData.id);
                if (mounted) setVotedStatus(already);
            } catch (err) {
                console.error("Error checking vote status:", err);
            }
        })();
        return () => { mounted = false };
    }, [userIdLS, postData.id]);

    async function handleVote() {

        if (!userIdLS) return;

        try {
            const data = await createPostVotes(userId, postData.id)
            const voted = await getIsVoted(userId, postData.id)
            setVotedStatus(voted)
            setVotes(prev => prev + (voted ? 1 : -1))

        } catch (error) {
            console.error("Error voting:", error)
        }

    }

    return (

        <div className="bg-white rounded-lg shadow-md hover:shadow-xl transition-shadow duration-300 p-6 flex flex-col gap-6 mb-4">
            <Link href={`/post/${postData.id}`} >
                <div className="flex-1">
                    <h2 className="text-2xl font-semibold text-gray-900 mb-4">{postData.title}</h2>


                    <div className="w-full h-auto bg-gray-200 rounded-lg overflow-hidden">
                        <img
                            src={postData.imageURL}
                            className="w-full h-full object-cover"
                            alt="Post Image"
                        />
                    </div>

                    {/* TODO SHORT VERSION OF THE CONTENT (WE CAN JUST TAKE THE 100 FIRST CHARACTERS)*/}
                    <div className="py-5">
                        <MarkdownRenderer
                            className="text-gray-700 mb-4 line-clamp-3 py-4"
                            content={postData.summary}
                        />
                    </div>


                    <div className=" flex flex-row justify-between">

                        <div className="flex flex-row w-1/8 items-center justify-between ">

                            {userIdLS ?
                                <button
                                    onClick={(e) => {
                                        e.stopPropagation()
                                        e.preventDefault()
                                        handleVote()
                                    }}
                                    className={
                                        votedStatus
                                            ? "text-orange-500 transition"
                                            : "text-gray-400 hover:text-orange-500 transition"
                                    }
                                    aria-label="Upvote"
                                    disabled={!userIdLS}
                                >
                                    <ArrowUp
                                        size={28}
                                        className="hover:text-green-700"
                                    />
                                </button>
                                :
                                <button
                                    onClick={(e) => {
                                        e.stopPropagation();
                                        e.preventDefault();
                                    }}
                                    className={
                                        votedStatus
                                            ? "text-orange-500 transition"
                                            : "text-gray-400 hover:text-orange-500 transition"
                                    }
                                >
                                    <ArrowUp
                                        size={28}
                                        className="hover:text-green-700"
                                    />
                                </button>
                            }

                            <span className="font-semibold text-lg text-gray-900">{votes}</span>
                            <div className='w-1/2' />
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
            </Link>

        </div>
    );
}
