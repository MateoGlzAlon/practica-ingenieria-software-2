"use client"

import { useState } from "react"
import PostContent from "@/components/post/PostContent"
import SidebarLeft from "@/components/post/SideBarLeft"
import AnswersSection from "@/components/post/AnswersSection"

import Link from "next/link"
import { mockData } from "@/app/mockData"

export default function Post({ params }) {

    const dataPost = mockData.postExampleDetails

    const [questionVotes, setQuestionVotes] = useState(42)
    const [answerVotes, setAnswerVotes] = useState([125, 37, 12])
    const [acceptedAnswer, setAcceptedAnswer] = useState(0)
    const [showComments, setShowComments] = useState(false)

    return (
        <div className="min-h-screen bg-gray-50">
            <main className="container mx-auto px-4 py-6">
                <div className="flex flex-col md:flex-row gap-4">
                    <SidebarLeft />

                    <div className="flex-1">
                        <div className="mb-4 flex flex-wrap items-center justify-between">
                            <h1 className="text-2xl font-bold text-gray-900 mb-2 md:mb-0">
                                {dataPost.title || "N/A"}
                            </h1>
                        </div>

                        <PostContent
                            questionVotes={questionVotes}
                            setQuestionVotes={setQuestionVotes}
                            showComments={showComments}
                            setShowComments={setShowComments}
                        />

                        <div className="flex items-center justify-between my-8">
                            <h2 className="text-xl font-bold text-gray-900">{mockData.commentsExamples.length} Answers</h2>
                            <div className="flex items-center space-x-2">
                                <span className="text-sm text-gray-500">Sort by:</span>
                                <select className="bg-white border border-gray-300 rounded-md px-2 py-1 text-sm">
                                    <option>Votes</option>
                                    <option>Newest</option>
                                    <option>Oldest</option>
                                </select>
                            </div>
                        </div>

                        <AnswersSection
                            answerVotes={answerVotes}
                            setAnswerVotes={setAnswerVotes}
                            acceptedAnswer={acceptedAnswer}
                            setAcceptedAnswer={setAcceptedAnswer}
                        />

                        <div className="mt-8 bg-white p-6 border border-gray-200 rounded-md">
                            <h3 className="text-lg font-bold mb-4">Your Answer</h3>
                            <textarea
                                className="w-full border border-gray-300 rounded-md p-3 min-h-[200px]"
                                placeholder="Write your answer here..."
                            ></textarea>
                            <div className="mt-4">
                                <button className="px-4 py-2 bg-orange-500 text-white rounded-md hover:bg-orange-600 transition">
                                    Post Your Answer
                                </button>
                            </div>
                        </div>
                    </div>

                    <RightSidebar />
                </div>
            </main>
        </div>
    )
}


function RightSidebar() {
    return (
        <div className="hidden lg:block w-[300px] shrink-0">
            <div className="sticky top-24 space-y-6">
                <div className="bg-white p-4 border border-gray-200 rounded-md">
                    <h3 className="text-sm font-semibold mb-3">Hot Network Questions</h3>
                    <ul className="space-y-2 text-sm">
                        <li>
                            <Link href="#" className="text-blue-600 hover:text-blue-800">
                                Why is TypeScript becoming so popular?
                            </Link>
                        </li>
                        <li>
                            <Link href="#" className="text-blue-600 hover:text-blue-800">
                                What's the difference between var, let, and const?
                            </Link>
                        </li>
                        <li>
                            <Link href="#" className="text-blue-600 hover:text-blue-800">
                                How to deploy a Next.js app to Vercel?
                            </Link>
                        </li>
                    </ul>
                </div>

                <div className="bg-orange-50 p-4 border border-orange-100 rounded-md">
                    <h3 className="text-sm font-semibold text-orange-800 mb-2">Join the Community</h3>
                    <p className="text-sm text-orange-700 mb-3">
                        Get access to exclusive content and connect with other developers.
                    </p>
                    <button className="w-full px-3 py-1.5 bg-orange-500 text-white rounded-md hover:bg-orange-600 transition text-sm">
                        Sign Up
                    </button>
                </div>
            </div>
        </div>
    )
}