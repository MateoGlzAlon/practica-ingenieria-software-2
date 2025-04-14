"use client";

import { useState } from "react";
import Sidebar from "@/components/post/SideBarLeft";
import PostContent from "@/components/post/PostContent";
import AnswersSection from "@/components/post/AnswersSection";

export default function StackOverflowPost({ postId }) {
    const [questionVotes, setQuestionVotes] = useState(42);
    const [answerVotes, setAnswerVotes] = useState([125, 37, 12]);
    const [acceptedAnswer, setAcceptedAnswer] = useState(0);

    return (
        <div className="min-h-screen bg-gray-50">
            <main className="container mx-auto px-4 py-6 flex gap-8">
                <div className="w-[10%]" />
                <Sidebar />
                <div className="w-full">
                    <PostContent questionVotes={questionVotes} setQuestionVotes={setQuestionVotes} />
                    <AnswersSection
                        answerVotes={answerVotes}
                        setAnswerVotes={setAnswerVotes}
                        acceptedAnswer={acceptedAnswer}
                        setAcceptedAnswer={setAcceptedAnswer}
                    />
                </div>
                <div className="w-[20%]" />
            </main>
        </div>
    );
}
