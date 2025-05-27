
import { Award } from "lucide-react"
import { mockData } from "@/app/mockData"
import { ArrowDown, ArrowUp, MessageSquare } from "lucide-react"
import { useState, useEffect } from "react"
import getCommentsOfAPost from "@/api/getCommentsOfAPost"

import createCommentVote from "@/api/comment/createCommentVote"
import getIsCommentVoted from "@/api/comment/getIsCommentVoted"
import { sort } from "core-js/core/array"

export default function AnswersSection({ acceptedAnswer, setAcceptedAnswer, idPost, refreshTrigger, sortOrder}) {

    const [commentsData, setCommentsData] = useState(null)
    const [expandedComments, setExpandedComments] = useState({})
    const [votedComments, setVotedComments] = useState({})
    const [commentVotes, setCommentVotes] = useState({})

    useEffect(() => {
        if (!idPost) return
        const fetchComments = async () => {
            try {
                const comments = await getCommentsOfAPost(idPost, sortOrder)
                setCommentsData(comments)
            } catch (error) {
                console.error('Error fetching comments:', error)
            }
        }
        fetchComments()
    }, [idPost, refreshTrigger, sortOrder])


    if (!commentsData) {
        return <p className="text-center py-10">Loading comments...</p>
    }


    const toggleComments = (index) => {
        setExpandedComments({
            ...expandedComments,
            [index]: !expandedComments[index],
        })
    }

    const handleCommentVote = async (commentId) => {
        const userId = 10 // TO-DO

        try {
            await createCommentVote({ userId, commentId })
            const isVoted = await getIsCommentVoted({ userId, commentId })

            setVotedComments(prev => ({
                ...prev,
                [commentId]: isVoted
            }))

            setCommentVotes(prev => ({
                ...prev,
                [commentId]: prev[commentId] + (isVoted ? 1 : -1)
            }))
        } catch (error) {
            console.error("Error voting comment:", error)
        }
    }


    return (
        <div className="space-y-6">
            {commentsData.map((answer, index) => (
                <div
                    key={answer.id}
                    className={`bg-white p-6 border ${acceptedAnswer === index ? "border-green-500" : "border-gray-200"
                        } rounded-md ${acceptedAnswer === index ? "ring-1 ring-green-500" : ""}`}
                >
                    <div className="flex gap-4">
                        <div className="flex flex-col items-center">
                            <button
                                onClick={() => handleCommentVote(answer.id)}
                                className="text-gray-400 hover:text-orange-500 transition"
                                aria-label="Upvote"
                            >
                                <ArrowUp
                                    size={32}
                                    className={`${votedComments[answer.id] ? "text-green-600" : ""} hover:text-pink-500`}
                                />
                            </button>
                            <span className="text-xl font-bold my-2 text-gray-700">
                                {Number(commentVotes[answer.id] ?? answer.votes) || 0}
                            </span>
                            <button
                                onClick={() => setAcceptedAnswer(index)}
                                className={`mt-4 ${acceptedAnswer === index ? "text-green-500" : "text-gray-400 hover:text-green-500"
                                    } transition`}
                                aria-label="Accept answer"
                            >
                                <Award size={18} />
                            </button>
                        </div>

                        <div className="flex-1">
                            <div className="prose max-w-none">
                                <p className="text-gray-700 leading-relaxed mb-4">{answer.content}</p>

                                {answer.code && (
                                    <div className="bg-gray-50 rounded-md border border-gray-200 mb-6">
                                        <div className="bg-gray-100 px-4 py-2 border-b border-gray-200 text-sm font-medium text-gray-700">
                                            Solution
                                        </div>
                                        <pre className="p-4 overflow-x-auto text-sm">
                                            <code className="language-javascript">{answer.code}</code>
                                        </pre>
                                    </div>
                                )}

                                <div className="flex justify-between items-center pt-4 border-t border-gray-200">
                                    <div className="flex space-x-4">
                                        <button
                                            onClick={() => toggleComments(index)}
                                            className="flex items-center text-sm text-gray-500 hover:text-gray-700"
                                        >
                                            <MessageSquare size={16} className="mr-1" />
                                            <span>{answer.commentCount || "N/A"} comments</span>
                                        </button>
                                    </div>

                                    <div className="flex items-center bg-blue-50 p-2 rounded-md">
                                        <img
                                            src={answer.authorProfilePicture || "https://placehold.co/600x400?text=Error"}
                                            alt="User avatar"
                                            className="w-10 h-10 rounded-full mr-2 border-[0.5px] border-gray-600 object-contain"
                                        />
                                        <div className="text-sm">
                                            <div className="font-medium text-blue-600">{answer.author || "N/A"}</div>
                                            <div className="text-gray-500">Answered {answer.createdAt || "N/A"}</div>
                                        </div>
                                    </div>
                                </div>

                                {expandedComments[index] && (
                                    <div className="mt-4 pt-4 border-t border-gray-200">
                                        <h4 className="text-sm font-medium mb-2">Comments</h4>
                                        <ul className="space-y-3">
                                            <li className="text-sm flex">
                                                <span className="text-gray-500 mr-2">@user4:</span>
                                                <span className="text-gray-700">This solution worked perfectly for me!</span>
                                            </li>
                                            <li className="text-sm flex">
                                                <span className="text-gray-500 mr-2">@user5:</span>
                                                <span className="text-gray-700">Could you explain the second part in more detail?</span>
                                            </li>
                                        </ul>
                                        <div className="mt-3 flex">
                                            <input
                                                type="text"
                                                placeholder="Add a comment..."
                                                className="flex-1 border border-gray-300 rounded-l-md px-3 py-1.5 text-sm"
                                            />
                                            <button className="bg-gray-100 text-gray-700 px-3 py-1.5 rounded-r-md border border-l-0 border-gray-300 text-sm hover:bg-gray-200">
                                                Add
                                            </button>
                                        </div>
                                    </div>
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    )
}
