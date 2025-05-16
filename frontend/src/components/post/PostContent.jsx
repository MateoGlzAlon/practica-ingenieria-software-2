import { ArrowDown, ArrowUp, MessageSquare } from "lucide-react"
import { mockData } from "@/app/mockData"
import { useState } from 'react'
import MarkdownRenderer from "./MarkDownRenderer"


export default function PostContent({ questionVotes, setQuestionVotes, showComments, setShowComments, data }) {
    //const data = mockData.postExampleDetails

    const [voteStatus, setVoteStatus] = useState(0)

    return (
        <div className="bg-white p-6 border border-gray-200 rounded-md">
            <div className="flex gap-4">
                <div className="flex flex-col items-center">
                    <button
                        onClick={() => {
                            if (voteStatus === 1) {
                                setQuestionVotes(q => q - 1)
                                setVoteStatus(0)
                            } else {
                                setQuestionVotes(q => q + (voteStatus === -1 ? 2 : 1))
                                setVoteStatus(1)
                            }
                        }}
                        className={`transition ${
                        voteStatus === 1
                            ? 'text-orange-500'
                            : 'text-gray-400 hover:text-orange-500'
                        }`}
                        aria-label="Upvote"
                    >
                        <ArrowUp size={32} />
                    </button>
                    <span className="text-xl font-bold my-2 text-gray-700">{questionVotes}</span>
                    <button
                        onClick={() => {
                            if (voteStatus === -1) {
                                setQuestionVotes(q => q + 1)
                                setVoteStatus(0)
                            } else {
                                setQuestionVotes(q => q - (voteStatus === 1 ? 2 : 1))
                                setVoteStatus(-1)
                            }
                        }}
                        className={`transition ${
                          voteStatus === -1
                            ? 'text-orange-500'
                            : 'text-gray-400 hover:text-gray-600'
                        }`}
                        aria-label="Downvote"
                    >
                        <ArrowDown size={32} />
                    </button>
                </div>

                <div className="flex-1">
                    <div className="prose max-w-none">

                        <div className="prose max-w-none">
                            <MarkdownRenderer content={data.content} />
                        </div>


                        <div className="flex flex-wrap  gap-y-2 gap-[1%] bg-gray-50 rounded-md border border-gray-200 mb-6 p-2">
                            {data.postImages.map((image, index) => (
                                <img
                                    key={index}
                                    src={image}
                                    alt={`Post Image ${index + 1}`}
                                    className="w-[49.5%] rounded-md object-cover"
                                />
                            ))}
                        </div>


                        <div className="flex justify-between items-center pt-4 border-t border-gray-200">
                            <div className="flex space-x-4">
                                <button
                                    onClick={() => setShowComments(!showComments)}
                                    className="flex items-center text-sm text-gray-500 hover:text-gray-700"
                                >
                                    <MessageSquare size={16} className="mr-1" />
                                    <span>5 comments</span>
                                </button>
                            </div>

                            <div className="flex items-center bg-blue-50 p-2 rounded-md ">
                                <img
                                    src={data.authorProfilePicture}
                                    alt="User avatar"
                                    className="w-8 h-8 rounded-full mr-2"
                                />
                                <div className="text-sm">
                                    <div className="font-medium text-blue-600">{data.author || "N/A"}</div>
                                    <div className="text-gray-500">Created {data.date || "N/A"}</div>
                                </div>
                            </div>
                        </div>

                        {showComments && (
                            <div className="mt-4 pt-4 border-t border-gray-200">
                                <h4 className="text-sm font-medium mb-2">Comments</h4>
                                <ul className="space-y-3">
                                    <li className="text-sm flex">
                                        <span className="text-gray-500 mr-2">@user1:</span>
                                        <span className="text-gray-700">Have you tried using useEffect for this?</span>
                                    </li>
                                    <li className="text-sm flex">
                                        <span className="text-gray-500 mr-2">@user2:</span>
                                        <span className="text-gray-700">
                                            I had a similar issue and fixed it by updating React to the latest version.
                                        </span>
                                    </li>
                                    <li className="text-sm flex">
                                        <span className="text-gray-500 mr-2">@user3:</span>
                                        <span className="text-gray-700">Check the React docs about controlled components.</span>
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
    )
}
