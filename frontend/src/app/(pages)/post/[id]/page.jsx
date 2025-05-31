"use client"

import { useEffect, useState } from "react"
import PostContent from "@/components/post/PostContent"
import SidebarLeft from "@/components/post/SideBarLeft"
import AnswersSection from "@/components/post/AnswersSection"
import postAnswer from "@/api/comment/postAnswers"

import Link from "next/link"

import getHotQuestionPosts from "@/api/getHotQuestionPosts"
import getIndividualPost from "@/api/getIndividualPost"
import { useParams } from 'next/navigation'
import getUserIdFromLocalStorage from '@/hooks/getUserIdAuth';
import { useLoggedIn } from "@/hooks/loggedInContext"



export default function Post({ params }) {

    //this is for post information
    const { id } = useParams()

    const userId = getUserIdFromLocalStorage();

    const [postData, setDataPost] = useState(null)
    const [content, setContent] = useState("");
    const [refreshComments, setRefreshComments] = useState(false);
    const [totalComments, setTotalComments] = useState(0);

    const [questionVotes, setQuestionVotes] = useState(0)
    const [acceptedAnswer, setAcceptedAnswer] = useState([])
    const [showComments, setShowComments] = useState(false)

    const [sortOrder, setSortOrder] = useState("votes")


    useEffect(() => {
        if (!id) return

        const fetchPost = async () => {
            try {
                const post = await getIndividualPost(id)
                setDataPost(post)
                setQuestionVotes(post.votes)

                console.log("Post data", post)
            } catch (error) {
                console.error('Error fetching post:', error)
            }
        }



        fetchPost()
    }, [id])

    if (!postData) {
        return <p className="text-center py-10">Loading post...</p>
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!userId) return;

        try {
            await postAnswer({
                postId: parseInt(id),
                userId: userId,
                content: content.trim(),
            });

            setRefreshComments(prev => !prev);
            setContent("");
        } catch (error) {
            console.error("Error submitting answer:", error);
        }
    };


    return (
        <div className="min-h-screen bg-gray-50">
            <main className="container mx-auto px-4 py-6">
                <div className="flex flex-col md:flex-row gap-4">
                    <SidebarLeft />

                    <div className="flex-1">
                        <div className="mb-4 flex flex-wrap items-center justify-between">
                            <h1 className="text-2xl font-bold text-gray-900 mb-2 md:mb-0">
                                {postData.title || "N/A"}
                            </h1>
                        </div>


                        <PostContent
                            questionVotes={questionVotes}
                            setQuestionVotes={setQuestionVotes}
                            showComments={showComments}
                            setShowComments={setShowComments}
                            postData={postData}
                            userId={userId}
                        />

                        <div className="flex items-center justify-between my-8">
                            <h2 className="text-xl font-bold text-gray-900">{totalComments} Answers</h2>
                            <div className="flex items-center space-x-2">
                                <span className="text-sm text-gray-500">Sort by:</span>
                                <select className="bg-white border border-gray-300 rounded-md px-2 py-1 text-sm"
                                    value={sortOrder}
                                    onChange={(e) => setSortOrder(e.target.value)}>
                                    <option value="votes">Votes</option>
                                    <option value="newest">Newest</option>
                                    <option value="oldest">Oldest</option>
                                </select>
                            </div>
                        </div>

                        <AnswersSection
                            acceptedAnswer={acceptedAnswer}
                            setAcceptedAnswer={setAcceptedAnswer}
                            idPost={id}
                            refreshTrigger={refreshComments}
                            setTotalComments={setTotalComments}
                            sortOrder={sortOrder}
                            authorId={postData.authorId}
                        />

                        <form onSubmit={handleSubmit} className="mt-8 bg-white p-6 border border-gray-200 rounded-md">
                            <h3 className="text-lg font-bold mb-4">Your Answer</h3>
                            <textarea
                                className="w-full border border-gray-300 rounded-md p-3 min-h-[200px]"
                                placeholder="Write your answer here..."
                                value={content}
                                onChange={(e) => setContent(e.target.value)}
                            ></textarea>
                            <div className="mt-4">
                                <button
                                    type="submit"
                                    className="px-4 py-2 bg-orange-500 text-white rounded-md hover:bg-orange-600 transition"
                                >
                                    Post Your Answer
                                </button>
                            </div>
                        </form>
                    </div>

                    <RightSidebar />
                </div>
            </main>
        </div>
    )
}


function RightSidebar() {

    const [hotQuestions, setHotQuestions] = useState([])
    const { loggedIn, setLoggedIn } = useLoggedIn()



    const fetchHotQuestions = async () => {
        try {
            const data = await getHotQuestionPosts()
            setHotQuestions(data)
        } catch (error) {
            console.error("Error fetching hot questions:", error)
        }
    }

    useEffect(() => {
        fetchHotQuestions()
    }, [])


    return (
        <div className="hidden lg:block w-[300px] shrink-0">
            <div className="sticky top-24 space-y-6">
                <div className="bg-white p-4 border border-gray-200 rounded-md">
                    <h3 className="text-sm font-semibold mb-3">Hot Network Questions</h3>
                    {hotQuestions.length === 0 ? (
                        <p className="text-sm text-gray-500">Loading some of the most recent posts...</p>
                    ) : (
                        <ul className="space-y-2 text-sm">
                            {hotQuestions.map((q) => (
                                <li key={q.id}>
                                    <Link
                                        href={`/post/${q.id}`}
                                        className="text-blue-600 hover:text-blue-800"
                                    >
                                        {q.title}
                                    </Link>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>

                {loggedIn ?
                    <></>
                    :
                    <div className="bg-orange-50 p-4 border border-orange-100 rounded-md">
                        <h3 className="text-sm font-semibold text-orange-800 mb-2">Join the Community</h3>
                        <p className="text-sm text-orange-700 mb-3">
                            Get access to exclusive content and connect with other developers.
                        </p>
                        <button className="w-full px-3 py-1.5Get access to exclusive content and connect with other developers. bg-orange-500 text-white rounded-md hover:bg-orange-600 transition text-sm">
                            Sign Up
                        </button>
                    </div>
                }


            </div>
        </div>
    )
}